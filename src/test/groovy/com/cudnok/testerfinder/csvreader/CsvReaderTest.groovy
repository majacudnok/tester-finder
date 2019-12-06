package com.cudnok.testerfinder.csvreader
import com.cudnok.testerfinder.csvreader.csvdatamodel.BugCsv
import spock.lang.Specification

class CsvReaderTest extends Specification{

    private CsvReader csvReader = new CsvReader()
    private static final String BUGS_FILE = "bugs.csv"

    def "should read csv file"() {
        given:
        String fileName = BUGS_FILE

        when:
        List<BugCsv> bugCsvList = csvReader.readObjectList(BugCsv.class, fileName)

        then:
        assert !bugCsvList.isEmpty()
    }

    def "should map properly data from file"() {
        given:
        String fileName = BUGS_FILE

        when:
        List<BugCsv> bugCsvList = csvReader.readObjectList(BugCsv.class, fileName)
        BugCsv firstBug = bugCsvList.get(0)

        then:
        assert firstBug.getBugId() == (1)
        assert firstBug.getDeviceId() == (1)
        assert firstBug.getTesterId() == (4)
    }

}
