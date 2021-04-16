package com.example.okrwebsite.controller;

import com.example.okrwebsite.dao.OkrItemDao;
import com.example.okrwebsite.model.OkrItem;
import com.google.gson.Gson;
import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class CreateAndUpdateOkrItem {
    private static final String CREATE_OKR_ITEM = "/create";
    private static final String UPDATE_OKR_ITEM = "/post/goal";
    private static final Gson GSON = new Gson();

    @Inject
    private OkrItemDao okrItemDao;

    @PostMapping(CREATE_OKR_ITEM)
    public void createOkrItem(@RequestParam(value = "okrItem") final String okrItem) {
        final OkrItem okrItemToCreate = GSON.fromJson(okrItem, OkrItem.class);
        log.debug("CreateAndUpdateOk.createOkrItem with input {}", okrItemToCreate);
        okrItemDao.createOrUpdateOkrItem(okrItemToCreate);
    }

    @PutMapping(UPDATE_OKR_ITEM)
    public void updateOkrItem(@RequestParam(value = "okrItem") final String okrItem) {
        final OkrItem okrItemToUpdate = GSON.fromJson(okrItem, OkrItem.class);
        log.debug("CreateAndUpdateOk.updateOkrItem with input {}", okrItemToUpdate);
        okrItemDao.createOrUpdateOkrItem(okrItemToUpdate);
    }
}
