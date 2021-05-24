package com.example.okrwebsite.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.okrwebsite.dto.EmployeeDto;
import com.example.okrwebsite.exception.EmployeeAlreadyExistException;
import com.example.okrwebsite.factory.DynamoMapperFactory;
import com.example.okrwebsite.model.Employee;
import com.example.okrwebsite.util.UuidGenerator;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.*;

@Log4j2
@Repository
public class EmployeeDao {

    @NonNull
    private final DynamoDBMapper dynamoDBMapper;

    public EmployeeDao() {
        this.dynamoDBMapper = DynamoMapperFactory.getDynamoDBMapper();
    }

    public List<Employee> getAllEmployee() {
        final DynamoDBScanExpression scanAll = new DynamoDBScanExpression();

        final PaginatedScanList<EmployeeDto> employeeDtos = dynamoDBMapper.scan(EmployeeDto.class, scanAll);
        final List<Employee> employeeList = new ArrayList<>();
        employeeDtos.forEach(dto -> {
            employeeList.add(dto.toBusinessObject());
        });

        return employeeList;
    }

    public Employee login(final String username, final String password) {
        return findTheMatchingEmployee(username, password);
    }

    public Employee register(final String username, final String password) {
        if (findTheMatchingEmployee(username, password) == null) {
            final Employee employee = Employee.builder()
                    .id(UuidGenerator.generateUuid())
                    .username(username)
                    .password(password)
                    .build();
            System.out.println("Creating Employee {}" + employee.toString());
            dynamoDBMapper.save(new EmployeeDto(employee));
            return employee;
        } else {
            throw new EmployeeAlreadyExistException();
        }
    }

    private Employee findTheMatchingEmployee(final String username, final String password) {
        final DynamoDBQueryExpression<EmployeeDto> queryExpression = new DynamoDBQueryExpression<EmployeeDto>()
                .withHashKeyValues(new EmployeeDto(Employee.builder()
                        .id("null")
                        .password(password)
                        .username(username)
                        .build()));

        final Optional<Employee> matchingEmployee = dynamoDBMapper.query(EmployeeDto.class, queryExpression)
                .stream()
                .map(EmployeeDto::toBusinessObject)
                .filter(employee -> employee.getPassword().equals(password) &&
                        employee.getUsername().equals(username))
                .findAny();


        System.out.println("Found matching employees {}" + matchingEmployee);

        return matchingEmployee.orElse(null);
    }
}
