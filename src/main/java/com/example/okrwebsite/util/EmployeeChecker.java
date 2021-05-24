package com.example.okrwebsite.util;

import com.example.okrwebsite.dao.EmployeeDao;
import com.example.okrwebsite.model.Employee;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Log4j2
@Repository
public class EmployeeChecker {
    @Inject
    private EmployeeDao employeeDao;

    public boolean doesEmployeeExist(final String employeeId) {
        log.debug("Checking if employee {} exists in the database", employeeId);
        final List<Employee> allEmployees = employeeDao.getAllEmployee();

        return allEmployees.stream()
                .map(Employee::getId)
                .anyMatch(id -> id.equals(employeeId));
    }
}
