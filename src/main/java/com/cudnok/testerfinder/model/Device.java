package com.cudnok.testerfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Device {

    private long id;
    private String description;
    Set<Bug> assignedBugs;
}
