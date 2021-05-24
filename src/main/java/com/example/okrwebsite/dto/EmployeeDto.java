package com.example.okrwebsite.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.example.okrwebsite.model.Employee;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@DynamoDBTable(tableName="Employee")
public class EmployeeDto {

    @DynamoDBAttribute
    private String username;
    @DynamoDBAttribute
    private String password;
    @DynamoDBAttribute
    private String id;

    @DynamoDBHashKey(attributeName = "hashKey")
    public String getHashKey() {
        return this.username;
    }

    @DynamoDBRangeKey(attributeName = "rangeKey")
    public String getRangeKey() {
        return this.password;
    }

    public void setHashKey(final String HashKey) {
        //No-op
    }

    public void setRangeKey(final String RangeKey) {
        //No-op
    }

    public EmployeeDto() {
    }

    public EmployeeDto(final Employee employee) {
        this.id = employee.getId();
        this.username = employee.getUsername();
        this.password = employee.getPassword();
    }

    public Employee toBusinessObject() {
        return Employee.builder()
                .id(this.id)
                .password(this.password)
                .username(this.username)
                .build();
    }
}
