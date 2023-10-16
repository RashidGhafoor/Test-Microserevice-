package com.example.goldprices.service.impl;

import com.example.goldprices.model.GoldPrice;
import com.example.goldprices.repository.GoldPricesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoldPricesUrlServiceImplTest {

    @Mock
    private GoldPricesRepository goldPricesRepository;
    String url = "https://api.nbp.pl/api/cenyzlota/";
    @InjectMocks
    private GoldPricesUrlServiceImpl goldPricesUrlServiceImpl;

    @Test
    public void testUrlSaveData() {


        doNothing().when(goldPricesRepository).deleteAll();
        Mockito.when(goldPricesRepository.saveAll(Mockito.anyIterable())).thenReturn(null);

        ResponseEntity<String> result = goldPricesUrlServiceImpl.saveUrlData(url);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Data saved successfully", result.getBody());

    }

    @Test
    void getDataOnSpecificDate() throws Exception {

        String dateString = "2017-05-09";
        Date givenDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

        double value = 151.8;
        GoldPrice goldPriceEntity = new GoldPrice();
        goldPriceEntity.setData(givenDate);
        goldPriceEntity.setCena(value);

        when(goldPricesRepository.findByDate(givenDate)).thenReturn(goldPriceEntity);

        // Calling the service function and testing its response
        Double response = goldPricesUrlServiceImpl.getDataOnSpecificDate(dateString);

        Assertions.assertEquals(response, value);
    }

    @Test
    void deleteRecordsBetweenDates() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startingDate = "2017-05-12", endingDate = "2018-04-10";
        Date startDate = sdf.parse(startingDate);
        Date endDate = sdf.parse(endingDate);

        doNothing().when(goldPricesRepository).deleteRecordsBetweenDates(startDate, endDate);

        ResponseEntity<String> result = goldPricesUrlServiceImpl.deleteRecordsBetweenDates(startingDate, endingDate);

        verify(goldPricesRepository, times(1)).deleteRecordsBetweenDates(startDate, endDate);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Records Deleted Successfully", result.getBody());
    }

    @Test
    void parseDateFromString() throws ParseException {

        String date = "2018-04-03";
        Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        Date serviceResponse = goldPricesUrlServiceImpl.parseDateFromString(date);

        assertEquals(parsedDate, serviceResponse);
    }
}