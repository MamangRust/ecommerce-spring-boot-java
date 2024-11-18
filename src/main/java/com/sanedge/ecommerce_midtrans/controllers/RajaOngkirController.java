package com.sanedge.ecommerce_midtrans.controllers;

import com.sanedge.ecommerce_midtrans.domain.request.rajaongkir.OngkirRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.city.RajaOngkirCityResponse;
import com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.ongkos.RajaOngkirOngkosResponse;
import com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.provinsi.RajaOngkirProvinsiResponse;
import com.sanedge.ecommerce_midtrans.service.RajaOngkirService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/rajaongkir")
public class RajaOngkirController {

    private final RajaOngkirService rajaOngkirService;

    @Autowired
    public RajaOngkirController(RajaOngkirService rajaOngkirService) {
        this.rajaOngkirService = rajaOngkirService;
    }

    // Endpoint to get province data
    @GetMapping("/provinsi")
    public ResponseEntity<MessageResponse> getProvinsi() {
        try {
            log.info("Fetching province data");
            RajaOngkirProvinsiResponse response = rajaOngkirService.getProvinsi();
            return ResponseEntity.ok(MessageResponse.builder()
                    .message("Province data retrieved successfully")
                    .data(response)
                    .statusCode(200)
                    .build());
        } catch (Exception e) {
            log.error("Error fetching province data", e);
            return ResponseEntity.status(500).body(MessageResponse.builder()
                    .message("Failed to fetch province data")
                    .data(null)
                    .statusCode(500)
                    .build());
        }
    }

    // Endpoint to get cities by province id
    @GetMapping("/city/{provinceId}")
    public ResponseEntity<MessageResponse> getCity(@PathVariable int provinceId) {
        try {
            log.info("Fetching city data for province ID: {}", provinceId);
            RajaOngkirCityResponse response = rajaOngkirService.getCity(provinceId);
            return ResponseEntity.ok(MessageResponse.builder()
                    .message("City data retrieved successfully")
                    .data(response)
                    .statusCode(200)
                    .build());
        } catch (Exception e) {
            log.error("Error fetching city data for province ID: {}", provinceId, e);
            return ResponseEntity.status(500).body(MessageResponse.builder()
                    .message("Failed to fetch city data")
                    .data(null)
                    .statusCode(500)
                    .build());
        }
    }

    // Endpoint to get shipping cost
    @PostMapping("/cost")
    public ResponseEntity<MessageResponse> getCost(@RequestBody OngkirRequest request) {
        try {
            log.info("Fetching shipping cost for request: {}", request);
            RajaOngkirOngkosResponse response = rajaOngkirService.getCost(request);
            return ResponseEntity.ok(MessageResponse.builder()
                    .message("Shipping cost retrieved successfully")
                    .data(response)
                    .statusCode(200)
                    .build());
        } catch (Exception e) {
            log.error("Error fetching shipping cost for request: {}", request, e);
            return ResponseEntity.status(500).body(MessageResponse.builder()
                    .message("Failed to fetch shipping cost")
                    .data(null)
                    .statusCode(500)
                    .build());
        }
    }
}
