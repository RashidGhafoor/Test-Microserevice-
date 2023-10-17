package com.example.goldprices.service;


import org.springframework.http.ResponseEntity;

/**
 * Service that parses the data from an url and persists it into the database. Additionally, this service allows
 * accessing and deleting data entries from the database.
 */
public interface UrlService {
    /**
     * Parses the data form an url and saves it in a database
     * @return ResponseEntity<String> request response to the http request to save data data
     */
    public ResponseEntity<String> saveUrlData(String url);

    /**
     * Finds and returns data from a specific date
     * @param date the date on which the data is to be returned
     * @return ResponseEntity<String> request response containing the data
     */
    Double getDataOnSpecificDate(String date);

    /**
     * Deletes records between a range of dates from the database
     * @param startingDate starting date of range
     * @param endingDate ending date of range
     * @return ResponseEntity<String> http response to inform the result
     */
    ResponseEntity<String> deleteRecordsBetweenDates(String startingDate, String endingDate);
}
