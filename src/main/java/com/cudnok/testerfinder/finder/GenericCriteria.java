package com.cudnok.testerfinder.finder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericCriteria {
    private boolean all;
    private Set<String> valuesSet;

    public GenericCriteria(String valueString) {
        this.isAll(valueString);
    }


    private void isAll(String valueString) {
        this.all = valueString.equals("all") || valueString.isEmpty();

        if (this.all) {
            this.valuesSet = new HashSet<>();
        } else {
            setValuesSet(valueString);
        }
    }

    private void setValuesSet(String valuesString) {
        this.valuesSet = new HashSet<>(Arrays.asList(valuesString.split(",")));
    }
}
