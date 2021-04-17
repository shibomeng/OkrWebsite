package com.example.okrwebsite.controller;

import com.example.okrwebsite.dao.OkrItemDao;
import com.example.okrwebsite.model.KeyResult;
import com.example.okrwebsite.model.OkrItem;
import com.google.common.collect.ImmutableList;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Restful APIs handler
 */
@Log4j2
@RestController
public class CreateAndUpdateOkrItem {
    private static final String CREATE_OKR_ITEM = "/createOkrItem";
    private static final String UPDATE_OKR_ITEM = "/updateOkrItem";
    private static final String LOCAL_HOST = "http://localhost:8080";
    private static final String SAMPLE_INPUT = "{\"okrItem\": {" +
                "\"target\":\"t1\"," +
                "\"departments\":[\"d1\",\"d2\"]," +
                "\"startDate\":\"1970-01-01T00:00:00Z\"," +
                "\"endDate\":\"1970-01-02T00:00:00Z\"," +
                "\"assignee\":\"John\"," +
                "\"score\":\"12\"," +
                "\"keyResults\": {" +
                    "\"result\":\"sub r1\"," +
                    "\"weight\":\"1\"" +
                    "}" +
                  "}" +
               "}";
    private static final OkrItem OKR_ITEM = OkrItem.builder()
                .target("t")
                .assignee("a")
                .departments(ImmutableList.of("1"))
                .endDate(DateTime.now())
                .score(1)
                .startDate(DateTime.now())
                .keyResults(ImmutableList.of(KeyResult.builder()
                        .result("1")
                        .weight(1)
                        .build()))
                .build();

    @Inject
    private OkrItemDao okrItemDao;

    @CrossOrigin(origins = LOCAL_HOST)
    @PostMapping(CREATE_OKR_ITEM)
    public void createOkrItem(@RequestParam(value = "okrItem") final OkrItem okrItem) {
        log.debug("CreateAndUpdateOkrItem.createOkrItem with input {}", okrItem);
        okrItemDao.createOrUpdateOkrItem(okrItem);
    }

    @CrossOrigin(origins = LOCAL_HOST)
    @PutMapping(UPDATE_OKR_ITEM)
    public void updateOkrItem(@RequestParam(value = "okrItem") final OkrItem okrItem) {
        log.debug("CreateAndUpdateOkrItem.updateOkrItem with input {}", okrItem);
        okrItemDao.createOrUpdateOkrItem(okrItem);
    }
}
