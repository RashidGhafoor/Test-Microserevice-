package com.example.goldprices.service.impl;

import com.example.goldprices.exceptions.InvalidDateFormatException;
import com.example.goldprices.model.GoldPrice;
import com.example.goldprices.repository.GoldPricesRepository;
import com.example.goldprices.service.UrlService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class GoldPricesUrlServiceImpl implements UrlService {

    private final GoldPricesRepository goldPricesRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String dateFormatPattern = "\\d{4}-\\d{2}-\\d{2}";

    public GoldPricesUrlServiceImpl(GoldPricesRepository goldPricesRepository, RestTemplate restTemplate, ObjectMapper objectMapper){
        this.goldPricesRepository = goldPricesRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void saveUrlData(String url) throws JsonProcessingException {

        // Deleting previous entries to avoid duplication
        goldPricesRepository.deleteAll();

        String urlResponse = restTemplate.getForObject(url, String.class);
        GoldPrice[] goldPrices = objectMapper.readValue(urlResponse, GoldPrice[].class);
        List<GoldPrice> goldPriceList = Arrays.asList(goldPrices);

        goldPricesRepository.saveAll(goldPriceList);
    }

    @Override
    @Cacheable(value = "goldPriceCache", key = "#date")
    public Double getDataOnSpecificDate(String date) {

        Date givenDate = parseDateFromString(date);

        Optional<GoldPrice> goldPriceEntity = goldPricesRepository.findByData(givenDate);

        return goldPriceEntity.orElse(new GoldPrice(givenDate, -1)).getCena();
    }

    @Override
    @Transactional
    public void deleteRecordsBetweenDates(String startDate, String endDate) {

        Date startingDate = parseDateFromString(startDate);
        Date endingDate = parseDateFromString(endDate);
        goldPricesRepository.deleteByDataBetween(startingDate, endingDate);
    }

    // Helper method
    public Date parseDateFromString (String date) {

        if (!date.matches(dateFormatPattern)) {
            throw new InvalidDateFormatException();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new InvalidDateFormatException(e.getMessage());
        }
    }
}
