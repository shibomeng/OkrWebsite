package com.example.okrwebsite.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.example.okrwebsite.model.KeyResult;
import com.example.okrwebsite.model.OkrItem;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.lang.reflect.Type;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * The data model to save to and retrieve from Dynamo.
 */
@ToString
@Getter
@Setter
@DynamoDBTable(tableName="OkrItems")
public class OkrItemDto {
    private static final Gson GSON = new Gson();
    private static final Type STRING_LIST = new TypeToken<List<String>>() {}.getType();
    private static final Type KEY_RESULTS = new TypeToken<List<KeyResult>>() {}.getType();

    @DynamoDBAttribute
    private String id;
    @DynamoDBAttribute
    private String target;
    @DynamoDBAttribute
    private String assignee;
    @DynamoDBAttribute
    private String departments;
    @DynamoDBAttribute
    private String startDate;
    @DynamoDBAttribute
    private String endDate;
    @DynamoDBAttribute
    private String score;
    @DynamoDBAttribute
    private String keyResults;

    @DynamoDBHashKey(attributeName = "hashkey")
    public String getHashKey() {
        return this.assignee;
    }

    public void setHashKey(final String HashKey) {
        //No-op
    }

    @DynamoDBRangeKey(attributeName = "rangeKey")
    public String getRangeKey() {
        return this.target;
    }

    public void setRangeKey(final String RangeKey) {
        //No-op
    }

    public OkrItemDto() {
    }

    public OkrItemDto(final OkrItem okrItem) {
        this.id = okrItem.getId();
        this.target = okrItem.getTarget();
        this.departments = GSON.toJson(okrItem.getDepartments());
        this.startDate = okrItem.getStartDate().toString();
        this.endDate = okrItem.getEndDate().toString();
        this.assignee = okrItem.getAssignee();
        this.score = okrItem.getScore().toString();

        final List<KeyResult> keyResults = okrItem.getKeyResults();
        final JsonArray jsonArray = new JsonArray();
        for (final KeyResult keyResult : keyResults) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", keyResult.getId());
            jsonObject.addProperty("result", keyResult.getResult());
            jsonObject.addProperty("weight", keyResult.getWeight());
            jsonArray.add(jsonObject);
        }
        this.keyResults = GSON.toJson(jsonArray);
    }

    public OkrItem toBusinessObject() {
        return OkrItem.builder()
                .id(this.id)
                .target(this.target)
                .departments(GSON.fromJson(this.departments, STRING_LIST))
                .startDate(LocalDate.parse(this.startDate))
                .endDate(LocalDate.parse(this.endDate))
                .assignee(this.assignee)
                .score(parseInt(this.score))
                .keyResults(GSON.fromJson(this.keyResults, KEY_RESULTS))
                .build();
    }
}
