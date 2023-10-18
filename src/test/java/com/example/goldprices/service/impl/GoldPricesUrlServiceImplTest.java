package com.example.goldprices.service.impl;

import com.example.goldprices.model.GoldPrice;
import com.example.goldprices.repository.GoldPricesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoldPricesUrlServiceImplTest {

    @Mock
    private GoldPricesRepository goldPricesRepository;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper objectMapper;
    private final String url = "https://somewebsite.com";
    @InjectMocks
    private GoldPricesUrlServiceImpl goldPricesUrlServiceImpl;

    @Test
    public void testUrlSaveData() throws JsonProcessingException {
        String mockResponse = "[{\"data\":\"2017-04-27\",\"cena\":157.24},{\"data\":\"2017-04-28\",\"cena\":157.43}]";

        doNothing().when(goldPricesRepository).deleteAll();
        when(restTemplate.getForObject(url, String.class)).thenReturn(mockResponse);
        when(objectMapper.readValue(mockResponse, GoldPrice[].class)).thenReturn(new GoldPrice[]{new GoldPrice()});
        when(goldPricesRepository.saveAll(Mockito.anyIterable())).thenReturn(null);

        goldPricesUrlServiceImpl.saveUrlData(url);

        verify(restTemplate, times(1)).getForObject(url, String.class);
        verify(goldPricesRepository, times(1)).saveAll(Mockito.anyIterable());
    }

    @Test
    void testGetDataOnSpecificDate() throws Exception {

        String dateString = "2017-05-09";
        Date givenDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

        double value = 151.8;
        GoldPrice goldPriceEntity = new GoldPrice();
        goldPriceEntity.setData(givenDate);
        goldPriceEntity.setCena(value);

        when(goldPricesRepository.findByData(givenDate)).thenReturn(Optional.of(goldPriceEntity));

        // Calling the service function and testing its response
        Double response = goldPricesUrlServiceImpl.getDataOnSpecificDate(dateString);

        Assertions.assertEquals(response, value);
    }

    @Test
    void testDeleteRecordsBetweenDates() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startingDate = "2017-05-12", endingDate = "2018-04-10";
        Date startDate = sdf.parse(startingDate);
        Date endDate = sdf.parse(endingDate);

        doNothing().when(goldPricesRepository).deleteByDataBetween(startDate, endDate);

        goldPricesUrlServiceImpl.deleteRecordsBetweenDates(startingDate, endingDate);

        verify(goldPricesRepository, times(1)).deleteByDataBetween(startDate, endDate);
    }

    @Test
    void testParseDateFromString() throws ParseException {

        String date = "2018-04-03";
        Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        Date serviceResponse = goldPricesUrlServiceImpl.parseDateFromString(date);

        assertEquals(parsedDate, serviceResponse);
    }
}