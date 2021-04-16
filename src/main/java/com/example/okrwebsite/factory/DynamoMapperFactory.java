package com.example.okrwebsite.factory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public final class DynamoMapperFactory {
    public static DynamoDBMapper getDynamoDBMapper() {
        final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

        return new DynamoDBMapper(client);
    }
}
