package com.example.product.service;

import com.example.product.dto.ResponseTasa;
import com.example.product.product.Dolar;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
@AllArgsConstructor
public class ScraperService {

    private CambioService cambioService;

        public Dolar scrapeDolarValue() throws IOException {
            Document doc = Jsoup.connect("https://www.bcv.org.ve/").get();

            Element dolarElement = doc.select("#dolar .recuadrotsmc .centrado strong").first();
            String valorString = dolarElement.text().trim();
            float precio = Float.parseFloat(valorString.replace(",", "."));
            ResponseTasa requestProduct=  ResponseTasa.builder()
                    .id(7)
                    .tasa(precio)
                    .nombre("BCV").build();

            cambioService.updateCambio(requestProduct);

            return new Dolar("USD" , precio);
        }

    public Dolar scrapeBtc() throws IOException {
        Document doc = Jsoup.connect("https://www.binance.com/es/trade/BTC_USDT?type=spot").get();

        Element priceElement = doc.select("title").first();
        String priceString = priceElement.text().trim();
        float price = Float.parseFloat(priceString.replace("| BTCUSDT | Binance Spot","" ));
        ResponseTasa requestProduct=  ResponseTasa.builder()
                .id(18)
                .tasa(price)
                .nombre("Bitcoins").build();

        cambioService.updateCambio(requestProduct);
        return new Dolar("Bitcoin" , price);
    }
}
