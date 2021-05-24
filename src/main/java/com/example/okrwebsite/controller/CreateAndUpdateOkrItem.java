package com.example.okrwebsite.controller;

import com.example.okrwebsite.dao.OkrItemDao;
import com.example.okrwebsite.model.KeyResult;
import com.example.okrwebsite.model.OkrItem;
import com.example.okrwebsite.util.EmployeeChecker;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.joda.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Restful APIs handler
 */
@Log4j2
@RestController
public class CreateAndUpdateOkrItem {
    private static final String CREATE_OKR_ITEM = "/createOkrItem";
    private static final String UPDATE_OKR_ITEM = "/updateOkrItem";
    private static final String LOCAL_HOST = "http://localhost:8080";
    private static final int HTTP_SUCCESS_CODE = 200;
    private static final int HTTP_SERVER_ERROR_CODE = 500;
    private static final int HTTP_CLIENT_ERROR_CODE = 400;
    private static final Gson GSON = new Gson();
    private static final Type STRING_LIST = new TypeToken<List<String>>() {}.getType();
    private static final Type KEY_RESULTS = new TypeToken<List<KeyResult>>() {}.getType();

    @Inject
    private OkrItemDao okrItemDao;
    @Inject
    private EmployeeChecker employeeChecker;

    @CrossOrigin(origins = LOCAL_HOST)
    @PostMapping(CREATE_OKR_ITEM)
    public ResponseEntity<?> createOkrItem(@RequestBody final String okrItem,
                                        @RequestParam("employeeId") final String employeeId) {
        log.debug("createOkrItem with okrItem {}, employeeId {}", okrItem, employeeId);

        if (!employeeChecker.doesEmployeeExist(employeeId)) {
            log.warn("employeeId {} doesn't exist", employeeId);
            return ResponseEntity.status(HTTP_CLIENT_ERROR_CODE).build();
        }

        log.warn(deserializeOkrItem(okrItem));

        try {
            okrItemDao.createOrUpdateOkrItem(deserializeOkrItem(okrItem.trim()), employeeId, null);
        } catch (final Exception e) {
            return ResponseEntity.status(HTTP_SERVER_ERROR_CODE).build();
        }

        return ResponseEntity.status(HTTP_SUCCESS_CODE).build();
    }

    @CrossOrigin(origins = LOCAL_HOST)
    @PutMapping(UPDATE_OKR_ITEM)
    public ResponseEntity<?> updateOkrItem(@RequestBody final String okrItem,
                              @RequestParam("okrId") final String okrId,
                              @RequestParam("employeeId") final String employeeId) {
        log.debug("updateOkrItem with okrItem {}, employeeId {}, okrId {}", okrItem, employeeId, okrId);

        if (!employeeChecker.doesEmployeeExist(employeeId)) {
            log.warn("employeeId {} doesn't exist", employeeId);
            return ResponseEntity.status(HTTP_CLIENT_ERROR_CODE).build();
        }

        try {
            okrItemDao.createOrUpdateOkrItem(deserializeOkrItem(okrItem.trim()), employeeId, okrId);
        } catch (final Exception e) {
            return ResponseEntity.status(HTTP_SERVER_ERROR_CODE).build();
        }

        return ResponseEntity.status(HTTP_SUCCESS_CODE).build();
    }

    private OkrItem deserializeOkrItem(final String okrItem) {
        log.error(LocalDate.now());
        final JsonObject jsonObject = GSON.fromJson(okrItem, JsonObject.class);
        return OkrItem.builder()
                .target(jsonObject.get("target").getAsString())
                .id(jsonObject.get("id").getAsString())
                .score(jsonObject.get("score").getAsInt())
                .startDate(LocalDate.parse(jsonObject.get("startDate").getAsString()))
                .endDate(LocalDate.parse(jsonObject.get("endDate").getAsString()))
                .assignee(jsonObject.get("assignee").getAsString())
                .departments(GSON.fromJson(jsonObject.get("departments").getAsJsonArray(), STRING_LIST))
                .keyResults(GSON.fromJson(jsonObject.get("keyResults").getAsJsonArray(), KEY_RESULTS))
                .build();
    }
}
