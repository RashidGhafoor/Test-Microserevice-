package com.example.goldprices.controller;

import com.example.goldprices.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    @Mock
    UrlService urlService;

    @Test
    void saveGoldPrices() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders.get("/saveGoldPrices"))
                .andExpect(status().isOk())
                .andExpect(content().string("Data saved successfully"));
    }
}