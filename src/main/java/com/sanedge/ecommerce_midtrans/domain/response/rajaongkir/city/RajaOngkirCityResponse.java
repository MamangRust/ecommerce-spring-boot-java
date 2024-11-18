package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.city;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RajaOngkirCityResponse {
    
    @JsonProperty("rajaongkir")
    private RajaOngkir rajaongkir;

    
    public RajaOngkir getRajaongkir() {
        return rajaongkir;
    }

    public void setRajaongkir(RajaOngkir rajaongkir) {
        this.rajaongkir = rajaongkir;
    }

    public static class RajaOngkir {
        @JsonProperty("query")
        private QueryCity query;

        @JsonProperty("status")
        private StatusCity status;

        @JsonProperty("results")
        private List<ResultsCity> results;

       
        public QueryCity getQuery() {
            return query;
        }

        public void setQuery(QueryCity query) {
            this.query = query;
        }

        public StatusCity getStatus() {
            return status;
        }

        public void setStatus(StatusCity status) {
            this.status = status;
        }

        public List<ResultsCity> getResults() {
            return results;
        }

        public void setResults(List<ResultsCity> results) {
            this.results = results;
        }
    }
}
