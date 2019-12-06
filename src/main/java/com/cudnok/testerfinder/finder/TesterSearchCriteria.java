package com.cudnok.testerfinder.finder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TesterSearchCriteria {

    private GenericCriteria devices;
    private GenericCriteria countries;
}
