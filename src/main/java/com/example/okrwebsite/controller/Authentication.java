package com.example.okrwebsite.controller;

import com.example.okrwebsite.dao.EmployeeDao;
import com.example.okrwebsite.model.Employee;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Log4j2
@RestController
public class Authentication {
    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String LOCAL_HOST = "http://localhost:8080";
    private static final int HTTP_SUCCESS_CODE = 200;
    private static final int HTTP_SERVER_ERROR_CODE = 500;
    private static final int HTTP_CLIENT_ERROR_CODE = 400;
    private static final Gson GSON = new Gson();

    @Inject
    private EmployeeDao employeeDao;

    @CrossOrigin(origins = LOCAL_HOST)
    @GetMapping(LOGIN)
    public ResponseEntity<?> login(@RequestParam("username") final String username,
                                   @RequestParam("password") final String password) {
        try {
            final Employee employee = employeeDao.login(username, password);

            return ResponseEntity.status(HTTP_SUCCESS_CODE)
                    .body(employee);
        } catch (final Exception e) {
            return ResponseEntity.status(HTTP_SERVER_ERROR_CODE).build();
        }
    }

    @CrossOrigin(origins = LOCAL_HOST)
    @PostMapping(REGISTER)
    public ResponseEntity<?> register(@RequestBody final String usernameAndPassword) {
        final JsonObject jsonObject = GSON.fromJson(usernameAndPassword, JsonObject.class);
        try {
            final Employee employee = employeeDao.register(jsonObject.get("username").getAsString(),
                    jsonObject.get("password").getAsString());

            return ResponseEntity.status(HTTP_SUCCESS_CODE)
                    .body(employee);
        } catch (final Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HTTP_SERVER_ERROR_CODE).build();
        }
    }
}
