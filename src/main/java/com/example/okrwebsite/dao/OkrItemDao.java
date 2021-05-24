package com.example.okrwebsite.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.example.okrwebsite.dto.OkrItemDto;
import com.example.okrwebsite.factory.DynamoMapperFactory;
import com.example.okrwebsite.model.OkrItem;
import com.example.okrwebsite.util.UuidGenerator;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Dao to access data in the OkrItem Dynamo
 */
@Log4j2
@Repository
public class OkrItemDao {
    @NonNull
    private final DynamoDBMapper dynamoDBMapper;

    public OkrItemDao() {
        this.dynamoDBMapper = DynamoMapperFactory.getDynamoDBMapper();
    }

    public void createOrUpdateOkrItem(final OkrItem okrItem, final String assignee, final String okrId) {
        final OkrItemDto okrItemDto = new OkrItemDto(okrItem);
        okrItemDto.setAssignee(assignee);
        if (okrId != null) {
            okrItemDto.setId(okrId);
        } else {
            okrItemDto.setId(UuidGenerator.generateUuid());
        }

        try {
            log.debug("okrItemDao.createOrUpdateOkrItem with input {}", okrItemDto);
            dynamoDBMapper.save(okrItemDto);
        } catch (final RuntimeException e) {
            log.error("dynamoDBMapper failed to save okrItem {}", okrItemDto);
            throw new RuntimeException(e);
        }
    }

    public List<OkrItem> getAllOkrItems() {
        final DynamoDBScanExpression scanAll = new DynamoDBScanExpression();

        final PaginatedScanList<OkrItemDto> okrItemDtos = dynamoDBMapper.scan(OkrItemDto.class, scanAll);
        final List<OkrItem> okrItemList = new ArrayList<>();
        okrItemDtos.forEach(dto -> {
            okrItemList.add(dto.toBusinessObject());
        });

        return okrItemList;
    }

    public List<OkrItem> getOkrItem(final String assignee) {
        final DynamoDBQueryExpression<OkrItemDto> query = new DynamoDBQueryExpression<>();
        final OkrItemDto dtoToQuery = new OkrItemDto();
        dtoToQuery.setAssignee(assignee);
        query.setHashKeyValues(dtoToQuery);

        log.debug("okrItemDao.getOkrItem for assignee {}", assignee);
        final PaginatedQueryList<OkrItemDto> okrItemDtos = dynamoDBMapper.query(OkrItemDto.class, query);
        final List<OkrItem> okrItemList = new ArrayList<>();
        okrItemDtos.forEach(okrItemDto -> {
            okrItemList.add(okrItemDto.toBusinessObject());
        });
        return okrItemList;
    }
}
