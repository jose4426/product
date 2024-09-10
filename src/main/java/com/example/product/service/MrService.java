package com.example.product.service;

import com.example.product.dto.EmailRequestMr;
import com.example.product.dto.ResponseTasa;
import com.example.product.product.Dolar;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor

public class MrService {
    private CambioService cambioService;
    private EmailService emailService;


    public Dolar start() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                if (Thread.currentThread().getId() == 1) { // El ID del hilo que deseas depurar
                    int x = 0; // Punto de interrupción condicional aquí
                }
                EmailRequestMr request = new EmailRequestMr("gonzalezjar231@gmail.com", "Mr100", "");
                scrapingMr(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        // Ejecutar el task cada 5 minutos
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
        return null;
    }

    @Transactional
    public Dolar scrapingMr(EmailRequestMr request) throws IOException {
        Document doc = Jsoup.connect("https://bitinfocharts.com/bitcoin/address/1Ay8vMC7R1UbyCCZRVULMV7iQpHSAbguJP").get();
        Element dolarElement = doc.select("span.text-success").first();
        Element element = doc.select("#table_maina .trb td:nth-child(5) ").first();

        if (dolarElement == null) {
            throw new IOException("No se encontro el elemento del precio ");
        }
        String valorString = dolarElement.text().trim();
        String valorStrin = element.text().trim();
        System.out.println(" hace el scraping " +valorString);

        float precio;
        String nombre;
        String signo;
        // float precio = Float.parseFloat(valorString.replace(",", "."));
        try {
            nombre = valorStrin.substring(18, 26);

            signo = valorString.substring(0,1);
            precio = Float.parseFloat(valorString.substring(1, 7));



        } catch (NumberFormatException e) {
            throw new IOException("Error al parsear el precio: " + valorString, e);
        }

        ResponseTasa requestProduct = ResponseTasa.builder()
                .id(7)
                .tasa(precio)
                .nombre(nombre).build();

        ResponseTasa tasa = cambioService.getById(requestProduct.getId());
        log.info("trae data "+ tasa);

        if (tasa == null) {
            throw new IOException("No se encontro la tasa con el id 7");
        }

        if (tasa.getTasa() != requestProduct.getTasa()) {
            cambioService.updateCambio(requestProduct);
            emailService.sendEmailMr(request.getTo(),   signo + " " + requestProduct.getTasa(), "precio: " + requestProduct.getNombre() +  "\nCantidad: " +signo +" " + requestProduct.getTasa() );
            return new Dolar(requestProduct.getNombre() + requestProduct.getNombre(), precio);
        } else {
            // throw new IOException("no hay actualizaciones ");
            System.out.println("No hay actualizaciones ");
            return new Dolar(tasa.getNombre(), tasa.getTasa());
        }

    }

    public Dolar scrapeBtc() throws IOException {
        Document doc = Jsoup.connect("https://www.binance.com/es/trade/BTC_USDT?type=spot").get();

        Element priceElement = doc.select("title").first();
        String priceString = priceElement.text().trim();
        float price = Float.parseFloat(priceString.replace("| BTCUSDT | Binance Spot", ""));
        ResponseTasa requestProduct = ResponseTasa.builder()
                .id(18)
                .tasa(price)
                .nombre("Bitcoins").build();

        cambioService.updateCambio(requestProduct);
        return new Dolar("Bitcoin", price);
    }
}
