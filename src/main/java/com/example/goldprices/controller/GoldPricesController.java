package com.example.goldprices.controller;

import com.example.goldprices.service.UrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoldPricesController {

    @Value("${app.GoldPricesUrl}")
    String url;
    private final UrlService urlService;

    public GoldPricesController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/saveGoldPrices")
    public ResponseEntity<String> saveGoldPrices() {
        return urlService.saveUrlData(url);
    }

    @GetMapping("/getPrice/{date}")
    public ResponseEntity<Double> getGoldPriceOnSpecificDate(@PathVariable String date) {
        double goldPrice = urlService.getDataOnSpecificDate(date);
        return ResponseEntity.ok(goldPrice);
    }

    @DeleteMapping("/detePricesBetween/{startDate}/{endDate}")
    public ResponseEntity<String> deleteGoldPricesOnADateRange(
            @PathVariable String startDate,
            @PathVariable String endDate) {
        return urlService.deleteRecordsBetweenDates(startDate, endDate);
    }

}
