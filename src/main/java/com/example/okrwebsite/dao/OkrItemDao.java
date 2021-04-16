package com.example.okrwebsite.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.example.okrwebsite.dto.OkrItemDto;
import com.example.okrwebsite.factory.DynamoMapperFactory;
import com.example.okrwebsite.model.OkrItem;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
public class OkrItemDao {
    @NonNull
    private final DynamoDBMapper dynamoDBMapper;

    public OkrItemDao() {
        this.dynamoDBMapper = DynamoMapperFactory.getDynamoDBMapper();
    }

    public void createOrUpdateOkrItem(@NonNull final OkrItem okrItem) {
        final OkrItemDto okrItemDto = new OkrItemDto(okrItem);
        try {
            log.debug("okrItemDao.createOrUpdateOkrItem with input {}", okrItem);
            dynamoDBMapper.save(okrItem);
        } catch (final RuntimeException e) {
            log.error("dynamoDBMapper failed to save okrItem {}", okrItem);
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

    public Optional<List<OkrItem>> getOkrItem(final String assignee) {
        return Optional.empty();
    }
}
