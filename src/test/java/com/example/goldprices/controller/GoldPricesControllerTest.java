package com.example.goldprices.controller;

import com.example.goldprices.service.UrlService;
import com.example.goldprices.service.impl.GoldPricesUrlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GoldPricesControllerTest {
    @Autowired
    MockMvc mvc;
    @Test
    void testSaveGoldPrices() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders.get("/saveGoldPrices"))
                .andExpect(status().isOk())
                .andExpect(content().string("Data saved successfully"));
    }


    @Test
    void testGetGoldPriceOnSpecificDate_Success() throws Exception {

        mvc.perform(
                        MockMvcRequestBuilders.get("/getPrice/2017-05-04"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(156.09)));
    }

    @Test
    void testGetGoldPriceOnSpecificDate_Failure() throws Exception {

        mvc.perform(
                        MockMvcRequestBuilders.get("/getPrice/2016-05-04"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Provided date isn't in the database. Please check your date again"));
    }

    @Test
    void testDeleteGoldPricesOnADateRange() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders.delete("/deletePricesBetween/2017-05-18/2018-04-04"))
                .andExpect(status().isOk())
                .andExpect(content().string("Records Deleted Successfully"));
    }

    @Test
    void testDeleteGoldPricesOnADateRange_InvalidDate() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders.delete("/deletePricesBetween/2017-05-18/2018-04666-04"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid date format. Please use 'yyyy-MM-dd' format"));
    }
}