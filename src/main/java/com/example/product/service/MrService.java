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

        Document doc = Jsoup.connect("https://bitinfocharts.com/bitcoin/address/1Ay8vMC7R1UbyCCZRVULMV7iQpHSAbguJP")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .timeout(10000)  // Tiempo de espera de 10 segundos
                .get();

        // Verificar si los elementos existen
        Element dolarElement = doc.select("span.text-success").first();
        Element element = doc.select("#table_maina .trb td:nth-child(5)").first();

        if (dolarElement == null || element == null) {
            throw new IOException("No se encontró uno de los elementos necesarios en la página.");
        }

        String valorString = dolarElement.text().trim();
        String valorStrin = element.text().trim();
        System.out.println("Realizando scraping: " + valorString);

        float precio;
        String nombre;
        String signo;

        try {
            if (valorStrin.length() >= 26) {
                nombre = valorStrin.substring(18, 26);
            } else {
                throw new IOException("El valor de la cadena es muy corto: " + valorStrin);
            }

            signo = valorString.substring(0, 1);
            precio = Float.parseFloat(valorString.substring(1, 7));

        } catch (NumberFormatException e) {
            throw new IOException("Error al parsear el precio: " + valorString, e);
        }

        ResponseTasa requestProduct = ResponseTasa.builder()
                .id(7)
                .tasa(precio)
                .nombre(nombre).build();

        ResponseTasa tasa = cambioService.getById(requestProduct.getId());
        log.info("Datos obtenidos correctamente");

        if (tasa == null) {
            throw new IOException("No se encontró la tasa con el ID 7");
        }

        // Comparar floats con margen de error
        if (Math.abs(tasa.getTasa() - requestProduct.getTasa()) > 0.0001) {
            cambioService.updateCambio(requestProduct);
            emailService.sendEmailMr(request.getTo(), signo + " " + requestProduct.getTasa(),
                    "Precio: " + requestProduct.getNombre() + "\nCantidad: " + signo + " " + requestProduct.getTasa());
            return new Dolar(requestProduct.getNombre() + requestProduct.getNombre(), precio);
        } else {
            System.out.println("No hay actualizaciones");
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
