package com.example.okrwebsite.controller;

import com.example.okrwebsite.dao.OkrItemDao;
import com.example.okrwebsite.model.KeyResult;
import com.example.okrwebsite.model.OkrItem;
import com.example.okrwebsite.util.EmployeeChecker;
import com.example.okrwebsite.util.UuidGenerator;
import com.google.common.collect.ImmutableList;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Restful APIs handler
 */
@Log4j2
@RestController
public class SearchOkrItem {
    private static final String GET_ALL_OKR_ITEM = "/getAll";
    private static final String GET_OKR_ITEMS_FOR_EMPLOYEE = "/getOkrItemsForEmployee";
    private static final String LOCAL_HOST = "http://localhost:8080";
    private static final int HTTP_SUCCESS_CODE = 200;
    private static final int HTTP_SERVER_ERROR_CODE = 500;
    private static final int HTTP_CLIENT_ERROR_CODE = 400;

    @Inject
    private OkrItemDao okrItemDao;
    @Inject
    private EmployeeChecker employeeChecker;

    @CrossOrigin(origins = LOCAL_HOST)
    @GetMapping(GET_ALL_OKR_ITEM)
    public ResponseEntity<List<OkrItem>> getAllOkrItems() {
        final List<OkrItem> results;

        try {
            results = okrItemDao.getAllOkrItems();
        } catch (final Exception e) {
            return ResponseEntity.status(HTTP_SERVER_ERROR_CODE).build();
        }

        return ResponseEntity.status(HTTP_SUCCESS_CODE)
                .body(results);
    }

    @CrossOrigin(origins = LOCAL_HOST)
    @GetMapping(GET_OKR_ITEMS_FOR_EMPLOYEE)
    public ResponseEntity<List<OkrItem>> getOkrItemsForEmployee(
            @RequestParam(value = "employeeId") final String employeeId) {
        log.debug("SearchOkrItem.getOkrItemsForEmployee() with employee id {}", employeeId);

        if (!employeeChecker.doesEmployeeExist(employeeId)) {
            return ResponseEntity.status(HTTP_CLIENT_ERROR_CODE).build();
        }

        final List<OkrItem> results;

        try {
            results = okrItemDao.getOkrItem(employeeId);
        } catch (final Exception e) {
            return ResponseEntity.status(HTTP_SERVER_ERROR_CODE).build();
        }

        return ResponseEntity.status(HTTP_SUCCESS_CODE)
                .body(results);
    }
}
