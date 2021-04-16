package com.example.okrwebsite.controller;

import com.example.okrwebsite.dao.OkrItemDao;
import com.example.okrwebsite.model.OkrItem;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
public class SearchOkrItem {
    private static final String GET_ALL_OKR_ITEM = "/getAll";
    private static final String SEARCH_OKR_ITEM = "/getOkrItemForEmployee";

    @Inject
    private OkrItemDao okrItemDao;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping(GET_ALL_OKR_ITEM)
    public List<OkrItem> getAllOkrItems() {
        log.debug("SearchOkrItem.getAllOkrItems()");
        return okrItemDao.getAllOkrItems();
    }

    @GetMapping(SEARCH_OKR_ITEM)
    public List<OkrItem> getOkrItemsForEmployee(@RequestParam(value = "employee") final String employee) {
        log.debug("SearchOkrItem.getOkrItemsForEmployee() with input {}", employee);
        return okrItemDao.getOkrItem(employee).orElse(new ArrayList<>());
    }
}
