package com.cudnok.testerfinder.finder;

import com.cudnok.testerfinder.model.Device;
import com.cudnok.testerfinder.model.Tester;

import java.util.*;
import java.util.stream.Collectors;

public class TesterFinder {

    public TesterFinder(Map<Tester, Set<Device>> testersDevicesMap) {
        this.testersDevicesMap = testersDevicesMap;
    }

    private Map<Tester, Set<Device>> testersDevicesMap;
    private Map<Tester, Set<Device>> filteredMap;

    public List<SearchResult> search(TesterSearchCriteria searchCriteria) {

        filteredMap = new HashMap<>(testersDevicesMap);

        searchByCountry(searchCriteria.getCountries());
        searchByDevice(searchCriteria.getDevices());

        return getSortedSearchResult();
    }

    private void searchByCountry(GenericCriteria criteria) {
        if (!criteria.isAll()) {
            filteredMap = filteredMap.entrySet().stream()
                    .filter(entry -> criteria.getValuesSet().contains(entry.getKey().getCountry()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
    }

    private void searchByDevice(GenericCriteria criteria) {
        if (!criteria.isAll()) {
            filteredMap.replaceAll((k, v) -> filterDevicesByName(criteria.getValuesSet(), v));
            filteredMap.values().removeAll(Collections.singleton(null));
        }
    }

    private List<SearchResult> getSortedSearchResult() {
        List<SearchResult> resultList = new ArrayList<>();
        filteredMap.forEach((k,v) ->
                resultList.add(
                        new SearchResult(
                                k.getFirstName(),
                                k.getLastName(),
                                v.stream().flatMap(device -> device.getAssignedBugs().stream()).collect(Collectors.toSet()).size()
                        )
                )
        );

        return resultList.stream()
                .sorted(Comparator.comparing(SearchResult::getExperience).reversed())
                .collect(Collectors.toList());
    }

    private Set<Device> filterDevicesByName(Set<String> devicesNames, Set<Device> devicesSet) {
        Set<Device> filteredDevices = devicesSet.stream()
                .filter(device -> devicesNames.contains(device.getDescription()))
                .collect(Collectors.toSet());

        return filteredDevices.isEmpty() ? null : filteredDevices;
    }

}
