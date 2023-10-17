package com.example.goldprices.service;


import com.example.goldprices.exceptions.InvalidDateFormatException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

/**
 * Service that parses the data from an url and persists it into the database. Additionally, this service allows
 * accessing and deleting data entries from the database.
 */
public interface UrlService {
    /**
     * Parses the data form an url and saves it in a database
     * @return true if the data is saved successfully
     */
    public boolean saveUrlData(String url) throws JsonProcessingException;

    /**
     * Finds and returns data from a specific date
     * @param date the date on which the data is to be returned
     * @return ResponseEntity<String> request response containing the data
     */
    Double getDataOnSpecificDate(String date) throws InvalidDateFormatException;

    /**
     * Deletes records between a range of dates from the database
     * @param startingDate starting date of range
     * @param endingDate ending date of range
     * @return ResponseEntity<String> http response to inform the result
     */
    boolean deleteRecordsBetweenDates(String startingDate, String endingDate) throws InvalidDateFormatException;
}
