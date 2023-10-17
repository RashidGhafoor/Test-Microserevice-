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
    @Mock
    UrlService urlService;
    @Test
    void saveGoldPrices() throws Exception {

        String url = "https://somewebsite.com";
        Mockito.when(urlService.saveUrlData(url)).thenReturn(ResponseEntity.ok().body("Mocked response"));
        System.out.println(urlService.saveUrlData(url));

        mvc.perform(
                        MockMvcRequestBuilders.get("/saveGoldPrices"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mocked response"));
    }
}