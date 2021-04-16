package com.example.okrwebsite.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class KeyResult {
    @NonNull
    String result;
    @NonNull
    Integer weight;
}
