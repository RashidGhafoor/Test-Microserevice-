package com.example.goldprices.service.impl;

import com.example.goldprices.model.GoldPrice;
import com.example.goldprices.repository.GoldPricesRepository;
import com.example.goldprices.service.UrlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class GoldPricesUrlServiceImpl implements UrlService {

    private final GoldPricesRepository goldPricesRepository;
    private final RestTemplate restTemplate;

    public GoldPricesUrlServiceImpl(GoldPricesRepository goldPricesRepository, RestTemplate restTemplate){
        this.goldPricesRepository = goldPricesRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<String> saveUrlData(String url) {

        // Deleting previous entries to avoid duplication
        goldPricesRepository.deleteAll();

        String urlResponse = restTemplate.getForObject(url, String.class);
        GoldPrice[] goldPrices;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            goldPrices = objectMapper.readValue(urlResponse, GoldPrice[].class);
            List<GoldPrice> goldPriceList = Arrays.asList(goldPrices);

            goldPricesRepository.saveAll(goldPriceList);

            return ResponseEntity.ok("Data saved successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Filed to save data");
        }
    }

    @Override
    @Cacheable(value = "goldPriceCache", key = "#date")
    public Double getDataOnSpecificDate(String date) {

        Date givenDate = parseDateFromString(date);

        GoldPrice goldPriceEntity = goldPricesRepository.findByDate(givenDate);

        return goldPriceEntity.getCena();
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteRecordsBetweenDates(String startDate, String endDate) {

        String dateFormatPattern = "\\d{4}-\\d{2}-\\d{2}";

        if (!(startDate.matches(dateFormatPattern) && endDate.matches(dateFormatPattern))) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Invalid date format. Please use 'yyyy-MM-dd' format.");
        }

        Date startingDate = parseDateFromString(startDate);
        Date endingDate = parseDateFromString(endDate);
        goldPricesRepository.deleteRecordsBetweenDates(startingDate, endingDate);

        return ResponseEntity.ok("Records Deleted Successfully");
    }

    public Date parseDateFromString (String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
