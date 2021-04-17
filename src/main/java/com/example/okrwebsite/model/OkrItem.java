package com.example.okrwebsite.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class OkrItem {
    @NonNull
    String target;
    @NonNull
    List<String> departments;
    @NonNull
    DateTime startDate;
    @NonNull
    DateTime endDate;
    @NonNull
    String assignee;
    @NonNull
    Integer score;
    @NonNull
    List<KeyResult> keyResults;
}
