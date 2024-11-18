package com.sanedge.ecommerce_midtrans.utils;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RajaOngkir {
    private final String baseUrl;
    private final String apiKey;


    public RajaOngkir(
            @Value("${rajaongkir.base-url:https://api.rajaongkir.com}") String baseUrl,
            @Value("${rajaongkir.api-key}") String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public ConnectionAndHeaders getConnectionAndHeaders() {
        HttpClient client = HttpClient.newHttpClient();
        Map<String, String> headers = new HashMap<>();
        headers.put("key", this.apiKey);
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return new ConnectionAndHeaders(client, headers);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    
    public static class ConnectionAndHeaders {
        private final HttpClient client;
        private final Map<String, String> headers;

        public ConnectionAndHeaders(HttpClient client, Map<String, String> headers) {
            this.client = client;
            this.headers = headers;
        }

        public HttpClient getClient() {
            return client;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }
    }
}
