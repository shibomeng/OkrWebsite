package com.example.okrwebsite.factory;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/**
 * Factory method to create a DynamoDBMapper in US_EAST_2 region in your AWS account.
 */
public final class DynamoMapperFactory {
    public static DynamoDBMapper getDynamoDBMapper() {
        final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .build();

        return new DynamoDBMapper(client);
    }
}
