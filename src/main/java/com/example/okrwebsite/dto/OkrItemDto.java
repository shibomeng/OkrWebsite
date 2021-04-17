package com.example.okrwebsite.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.example.okrwebsite.model.KeyResult;
import com.example.okrwebsite.model.OkrItem;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * The data model to save to and retrieve from Dynamo.
 */
@ToString
@Getter
@Setter
@DynamoDBTable(tableName="OkrItem")
public class OkrItemDto {
    private static final Gson GSON = new Gson();
    private static final Type STRING_LIST = new TypeToken<List<String>>() {}.getType();
    private static final Type KEY_RESULTS = new TypeToken<List<KeyResult>>() {}.getType();

    @DynamoDBAttribute
    private String target;
    @DynamoDBAttribute
    private String departments;
    @DynamoDBAttribute
    private String startDate;
    @DynamoDBAttribute
    private String endDate;
    @DynamoDBAttribute
    private String assignee;
    @DynamoDBAttribute
    private String score;
    @DynamoDBAttribute
    private String keyResults;

    @DynamoDBHashKey(attributeName = "HashKey")
    public String getHashKey() {
        return this.assignee;
    }

    public void setHashKey(final String HashKey) {
        //No-op
    }

    @DynamoDBRangeKey(attributeName = "RangeKey")
    public String getRangeKey() {
        return this.target;
    }

    public void setRangeKey(final String RangeKey) {
        //No-op
    }

    public OkrItemDto() {
    }

    private String getRangeStart(final String startTime) {
        final List<String> queryComponent = ImmutableList.of(
                this.target,
                this.departments,
                startTime,
                this.score,
                this.keyResults
        );

        return Joiner.on("#").join(queryComponent);
    }

    public OkrItemDto(final OkrItem okrItem) {
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
