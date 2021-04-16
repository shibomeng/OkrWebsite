package com.example.okrwebsite.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.example.okrwebsite.model.KeyResult;
import com.example.okrwebsite.model.OkrItem;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.util.List;

import static java.lang.Integer.parseInt;

@DynamoDBTable(tableName="OkrItem")
@ToString
@EqualsAndHashCode
public class OkrItemDto {
    private static final Gson GSON = new Gson();
    private static final Type STRING_LIST = new TypeToken<List<String>>() {}.getType();
    private static final Type KEY_RESULTS = new TypeToken<List<KeyResult>>() {}.getType();

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAttribute
    private final String id;
    @DynamoDBAttribute
    private final String target;
    @DynamoDBAttribute
    private final String departments;
    @DynamoDBAttribute
    private final String startDate;
    @DynamoDBAttribute
    private final String endDate;
    @DynamoDBRangeKey(attributeName = "assignee")
    @DynamoDBAttribute
    private final String assignee;
    @DynamoDBAttribute
    private final String score;
    @DynamoDBAttribute
    private final String keyResults;


    public OkrItemDto(final OkrItem okrItem) {
        this.id = OkrItem.getId();
        this.target = okrItem.getTarget();
        this.departments = GSON.toJson(okrItem.getDepartments());
        this.startDate = okrItem.getStartDate().toString();
        this.endDate = okrItem.getEndDate().toString();
        this.assignee = okrItem.getAssignee();
        this.score = okrItem.getScore().toString();
        this.keyResults = GSON.toJson(okrItem.getKeyResults());
    }

    public OkrItem toBusinessObject() {
        return OkrItem.builder()
                .target(this.target)
                .departments(GSON.fromJson(this.departments, STRING_LIST))
                .startDate(DateTime.parse(this.startDate))
                .endDate(DateTime.parse(this.endDate))
                .assignee(this.assignee)
                .score(parseInt(this.score))
                .keyResults(GSON.fromJson(this.keyResults, KEY_RESULTS))
                .build();
    }
}
