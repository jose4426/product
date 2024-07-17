package com.example.product.controller;

import com.example.product.product.Dolar;
import com.example.product.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/cambio")
@CrossOrigin(origins = "http://localhost:4200")
public class ScrapingController {
    @Autowired
    private ScraperService scrapingService;

    @GetMapping("/dolar")
    public Dolar scrapeDolarValue() throws IOException {
        return scrapingService.scrapeDolarValue();
    }
    @GetMapping("/btc")
    public Dolar scrapeBtc() throws IOException {
        return scrapingService.scrapeBtc();
    }
}
