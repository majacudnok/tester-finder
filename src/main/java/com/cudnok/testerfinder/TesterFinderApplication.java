package com.cudnok.testerfinder;

import com.cudnok.testerfinder.finder.GenericCriteria;
import com.cudnok.testerfinder.finder.SearchResult;
import com.cudnok.testerfinder.finder.TesterFinder;
import com.cudnok.testerfinder.finder.TesterSearchCriteria;
import com.cudnok.testerfinder.loader.DataLoader;
import com.cudnok.testerfinder.model.Device;
import com.cudnok.testerfinder.model.Tester;

import java.util.*;

public class TesterFinderApplication {

	public static void main(String[] args) {

		Map<Tester, Set<Device>> resultData = new DataLoader().getResultData();

		Scanner scanner = new Scanner(System.in);

		System.out.println("Search by country (if you want all, please type all in command line):");
		GenericCriteria countries = new GenericCriteria(scanner.nextLine());

		System.out.println("Search by device (if you want all, please type all in command line):");
		GenericCriteria devices = new GenericCriteria(scanner.nextLine());

		List<SearchResult> searchResultList = new TesterFinder(resultData)
				.search(new TesterSearchCriteria(devices, countries));

		for (SearchResult result : searchResultList) {
			System.out.println(result.getFirstName() + ' ' + result.getLastName() + ' ' + result.getExperience());
		}

	}

}
