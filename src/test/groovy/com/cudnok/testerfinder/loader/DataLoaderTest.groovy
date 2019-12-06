package com.cudnok.testerfinder.loader

import com.cudnok.testerfinder.model.Device
import com.cudnok.testerfinder.model.Tester
import spock.lang.Specification

class DataLoaderTest extends Specification{

    private DataLoader dataLoader = new DataLoader()
    private static final TESTER_1 = new Tester(1, "Miguel", "Bautista", "US")
    private static final TESTER_2 = new Tester(3, "Leonard", "Sutton", "GB")


    def "should generate proper map"() {
        when:
        Map<Tester, Set<Device>> testerSetMap = dataLoader.getResultData()

        then:
        assert !testerSetMap.isEmpty()
        assert testerSetMap.keySet() instanceof Set<Tester>
        assert testerSetMap.get(TESTER_1) != testerSetMap.get(TESTER_2)
        assert testerSetMap.get(TESTER_1) instanceof Set<Device>
    }
}
