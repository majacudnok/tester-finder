package com.cudnok.testerfinder.loader;

import com.cudnok.testerfinder.csvreader.CsvReader;
import com.cudnok.testerfinder.csvreader.csvdatamodel.BugCsv;
import com.cudnok.testerfinder.csvreader.csvdatamodel.DeviceCsv;
import com.cudnok.testerfinder.csvreader.csvdatamodel.TesterCsv;
import com.cudnok.testerfinder.csvreader.csvdatamodel.TesterDeviceCsv;
import com.cudnok.testerfinder.model.Bug;
import com.cudnok.testerfinder.model.Device;
import com.cudnok.testerfinder.model.Tester;

import java.util.*;
import java.util.stream.Collectors;

public class DataLoader {

    private static final String DEVICES_FILE = "devices.csv";
    private static final String BUGS_FILE = "bugs.csv";
    private static final String TESTERS_FILE = "testers.csv";
    private static final String TESTERS_DEVICE_FILE = "tester_device.csv";

    private CsvReader csvReader;

    public DataLoader() {
        this.csvReader = new CsvReader();
    }

    public Map<Tester, Set<Device>> getResultData() {
        List<DeviceCsv> deviceCsvList = csvReader.readObjectList(DeviceCsv.class, DEVICES_FILE);
        List<BugCsv> bugCsvList = csvReader.readObjectList(BugCsv.class, BUGS_FILE);
        List<TesterCsv> testerCsvList = csvReader.readObjectList(TesterCsv.class, TESTERS_FILE);
        List<TesterDeviceCsv> testerDeviceCsvList = csvReader.readObjectList(TesterDeviceCsv.class, TESTERS_DEVICE_FILE);

        return testerCsvList.stream()
                .map(testerCsv -> new Tester(
                        testerCsv.getTesterId(),
                        testerCsv.getFirstName(),
                        testerCsv.getLastName(),
                        testerCsv.getCountry()
                ))
                .collect(Collectors.toMap(e -> e,
                        e -> getDevicesAssignedToTester(e, testerDeviceCsvList, deviceCsvList, bugCsvList)));
    }

    private Set<Device> getDevicesAssignedToTester(Tester tester, List<TesterDeviceCsv> testerDeviceCsvList,
                                                   List<DeviceCsv> deviceCsvList, List<BugCsv> bugCsvList) {
        Set<Long> devicesIds = testerDeviceCsvList.stream()
                .filter(testerDeviceCsv -> testerDeviceCsv.getTesterId() == tester.getId())
                .map(TesterDeviceCsv::getDeviceId)
                .collect(Collectors.toSet());

        return deviceCsvList.stream()
                .filter(deviceCsv -> devicesIds.contains(deviceCsv.getDeviceId()))
                .map(devCsv -> new Device(devCsv.getDeviceId(),
                        devCsv.getDescription(),
                        getBugsAssignedToDevice(devCsv, bugCsvList, tester)))
                .collect(Collectors.toSet());
    }

    private Set<Bug> getBugsAssignedToDevice(DeviceCsv device, List<BugCsv> bugCsvList, Tester tester) {
        return bugCsvList.stream()
                .filter(bugCsv -> device.getDeviceId() == bugCsv.getDeviceId())
                .filter(bugCsv -> tester.getId() == bugCsv.getTesterId())
                .map(bugCsv -> new Bug(bugCsv.getBugId()))
                .collect(Collectors.toSet());
    }



}
