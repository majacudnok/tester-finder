package com.cudnok.testerfinder.finder

import com.cudnok.testerfinder.loader.DataLoader
import com.cudnok.testerfinder.model.Bug
import com.cudnok.testerfinder.model.Device
import com.cudnok.testerfinder.model.Tester
import spock.lang.Specification

class TesterFinderTest extends Specification {
    private final DataLoader dataLoader = Mock()

    private Map<Tester, Set<Device>> map = new HashMap<>()

    private static final String GB_COUNTRY = "GB"
    private static final String US_COUNTRY = "US"

    private static final Tester TESTER_1 = new Tester(
            1L,
            "firstName1",
            "lastName1",
            "GB"
    )

    private static final Tester TESTER_2 = new Tester(
            2L,
            "firstName2",
            "lastName2",
            "US"
    )

    private static final Tester TESTER_3 = new Tester(
            3L,
            "firstName3",
            "lastName3",
            "US"
    )

    private static final Device DEVICE_1 = new Device(
            1L,
            "Device1",
            new HashSet<Bug>()
    )

    private static final Device DEVICE_2 = new Device(
            2L,
            "Device2",
            new HashSet<Bug>()
    )

    private static final Bug BUG_1 = new Bug(1L)
    private static final Bug BUG_2 = new Bug(2L)
    private static final Bug BUG_3 = new Bug(3L)

    def setup() {
        Set<Device> deviceSet1 = new HashSet<>()
        DEVICE_1.getAssignedBugs().add(BUG_1)
        deviceSet1.add(DEVICE_1)

        Set<Device>deviceSet2 = new HashSet<>()
        DEVICE_2.getAssignedBugs().add(BUG_2)
        DEVICE_2.getAssignedBugs().add(BUG_3)
        deviceSet2.add(DEVICE_1)
        deviceSet2.add(DEVICE_2)

        Set<Device> deviceSet3 = new HashSet<>()
        DEVICE_1.getAssignedBugs().add(BUG_2)
        DEVICE_1.getAssignedBugs().add(BUG_3)
        deviceSet3.add(DEVICE_1)
        deviceSet3.add(DEVICE_2)


        map.put(TESTER_1, deviceSet1)
        map.put(TESTER_2, deviceSet2)
        map.put(TESTER_3, deviceSet3)

    }

    def "should find one tester by proper country"(){
        given:
        dataLoader.getResultData() >> this.map
        TesterSearchCriteria testerSearchCriteria = new TesterSearchCriteria(
                new GenericCriteria(""),
                new GenericCriteria(GB_COUNTRY)
        )

        when:
        List<SearchResult> searchResultList = new TesterFinder(this.map).search(testerSearchCriteria)

        then:
        assert !searchResultList.isEmpty()
        assert searchResultList.size() == 1
        assert searchResultList.get(0).getExperience() == DEVICE_1.getAssignedBugs().size()

    }

    def "should find multiple testers by proper country and sort by experience"(){
        given:
        dataLoader.getResultData() >> this.map
        TesterSearchCriteria testerSearchCriteria = new TesterSearchCriteria(
                new GenericCriteria(""),
                new GenericCriteria(US_COUNTRY)
        )

        when:
        List<SearchResult> searchResultList = new TesterFinder(this.map).search(testerSearchCriteria)

        then:
        assert !searchResultList.isEmpty()
        assert searchResultList.size() == 2
        assert searchResultList.get(0).getExperience() >= searchResultList.get(1).getExperience()

    }
}
