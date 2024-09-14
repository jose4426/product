package com.example.product.controller;

import com.example.product.dto.EmailRequestMr;
import com.example.product.product.Dolar;
import com.example.product.service.MrService;
import com.example.product.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/cambio")
@CrossOrigin(origins = "*")
public class ScrapingController {
    @Autowired
    private ScraperService scrapingService;
    @Autowired
    private MrService mrService;

    @GetMapping("/dolar")
    public Dolar scrapeDolarValue() throws IOException {
        return scrapingService.scrapeDolarValue();
    }
    @GetMapping("/btc")
    public Dolar scrapeBtc() throws IOException {
        return scrapingService.scrapeBtc();
    }
    @PostMapping("/mr")
    public Dolar scrapingMrValue(@RequestBody EmailRequestMr request) throws IOException {
        return mrService.scrapingMr(request);
    }
    @PostMapping("/mrs")
    public Dolar   scrapingMr() throws IOException {
        return mrService.start();
    }
}
