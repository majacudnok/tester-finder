package com.cudnok.testerfinder.csvreader.csvdatamodel;

import lombok.Data;

@Data
public class TesterCsv {

    private long testerId;
    private String firstName;
    private String lastName;
    private String country;
    private String lastLogin;

}

