package com.example.goldprices.controller;

import com.example.goldprices.exceptions.DateValueNotFoundException;
import com.example.goldprices.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    @Autowired
    public GoldPricesController(UrlService urlService) {
        this.urlService = urlService;
    }


    @GetMapping("/saveGoldPrices")
    public ResponseEntity<String> saveGoldPrices() {
        try {
            urlService.saveUrlData(url);
            return ResponseEntity.ok("Data saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Filed to save data: " + e.getMessage());
        }
    }

    @GetMapping("/getPrice/{date}")
    public ResponseEntity<String> getGoldPriceOnSpecificDate(@PathVariable String date) {
        Double result = urlService.getDataOnSpecificDate(date);
        if(result == -1){
            throw new DateValueNotFoundException("Provided date isn't in the database. Please check your date again");
        }
        return ResponseEntity.ok(String.valueOf(result));
    }

    @DeleteMapping("/deletePricesBetween/{startDate}/{endDate}")
    public ResponseEntity<String> deleteGoldPricesOnADateRange(@PathVariable String startDate, @PathVariable String endDate) {
        urlService.deleteRecordsBetweenDates(startDate, endDate);
        return ResponseEntity.ok().body("Records Deleted Successfully");
    }

}
