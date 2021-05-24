package com.example.okrwebsite.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Employee {
    @NonNull
    String id;
    @NonNull
    String username;
    @NonNull
    String password;
}
