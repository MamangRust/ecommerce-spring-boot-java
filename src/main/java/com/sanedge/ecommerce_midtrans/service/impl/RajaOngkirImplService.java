package com.sanedge.ecommerce_midtrans.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanedge.ecommerce_midtrans.domain.request.rajaongkir.OngkirRequest;
import com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.city.RajaOngkirCityResponse;
import com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.ongkos.RajaOngkirOngkosResponse;
import com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.provinsi.RajaOngkirProvinsiResponse;
import com.sanedge.ecommerce_midtrans.service.RajaOngkirService;
import com.sanedge.ecommerce_midtrans.utils.RajaOngkir;



@Service
public class RajaOngkirImplService implements RajaOngkirService {

    private final RajaOngkir rajaOngkirAPI;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RajaOngkirImplService(RajaOngkir rajaOngkirAPI, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.rajaOngkirAPI = rajaOngkirAPI;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public RajaOngkirProvinsiResponse getProvinsi() throws Exception {
        String endpoint = "/starter/province";
        String url = rajaOngkirAPI.getBaseUrl() + endpoint;

        
        RajaOngkir.ConnectionAndHeaders connectionAndHeaders = rajaOngkirAPI.getConnectionAndHeaders();
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(connectionAndHeaders.getHeaders());  // Use the headers from the connection

        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Failed to fetch province data from RajaOngkir API. Status code: " + responseEntity.getStatusCode());
        }

        return objectMapper.readValue(responseEntity.getBody(), RajaOngkirProvinsiResponse.class);
    }

    public RajaOngkirCityResponse getCity(int idProv) throws Exception {
        String endpoint = String.format("/starter/city?province=%d", idProv);
        String url = rajaOngkirAPI.getBaseUrl() + endpoint;

        
        RajaOngkir.ConnectionAndHeaders connectionAndHeaders = rajaOngkirAPI.getConnectionAndHeaders();
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(connectionAndHeaders.getHeaders());  

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Failed to fetch city data from RajaOngkir API. Status code: " + responseEntity.getStatusCode());
        }

        return objectMapper.readValue(responseEntity.getBody(), RajaOngkirCityResponse.class);
    }

    public RajaOngkirOngkosResponse getCost(OngkirRequest request) throws Exception {
        String endpoint = "/starter/cost";
        String url = rajaOngkirAPI.getBaseUrl() + endpoint;

        String payload = String.format("origin=%s&destination=%s&weight=%d&courier=%s",
                request.getAsal(), request.getTujuan(), request.getBerat(), request.getKurir());

        
        RajaOngkir.ConnectionAndHeaders connectionAndHeaders = rajaOngkirAPI.getConnectionAndHeaders();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAll(connectionAndHeaders.getHeaders());  

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Failed to get shipping cost from RajaOngkir API. Status code: " + responseEntity.getStatusCode());
        }

        return objectMapper.readValue(responseEntity.getBody(), RajaOngkirOngkosResponse.class);
    }
}

