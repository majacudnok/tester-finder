package com.cudnok.testerfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tester {

    private long id;
    private String firstName;
    private String lastName;
    private String country;
}
