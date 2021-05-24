package com.example.okrwebsite.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.joda.time.LocalDate;

import java.util.List;

@Value
@Builder
public class OkrItem {
    @NonNull
    String id;
    @NonNull
    String target;
    @NonNull
    List<String> departments;
    @NonNull
    LocalDate startDate;
    @NonNull
    LocalDate endDate;
    @NonNull
    String assignee;
    @NonNull
    Integer score;
    @NonNull
    List<KeyResult> keyResults;
}
