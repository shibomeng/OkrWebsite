package com.example.okrwebsite.controller;

import com.example.okrwebsite.dao.OkrItemDao;
import com.example.okrwebsite.model.OkrItem;
import lombok.extern.log4j.Log4j2;
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
    private static final String SEARCH_OKR_ITEM = "/getOkrItemForEmployee";
    private static final String LOCAL_HOST = "http://localhost:8080";

    @Inject
    private OkrItemDao okrItemDao;

    @CrossOrigin(origins = LOCAL_HOST)
    @GetMapping(GET_ALL_OKR_ITEM)
    public List<OkrItem> getAllOkrItems() {
        log.debug("SearchOkrItem.getAllOkrItems()");
        return okrItemDao.getAllOkrItems();
    }

    @CrossOrigin(origins = LOCAL_HOST)
    @GetMapping(SEARCH_OKR_ITEM)
    public List<OkrItem> getOkrItemsForEmployee(@RequestParam(value = "employee") final String employee) {
        log.debug("SearchOkrItem.getOkrItemsForEmployee() with input {}", employee);
        return okrItemDao.getOkrItem(employee);
    }
}
