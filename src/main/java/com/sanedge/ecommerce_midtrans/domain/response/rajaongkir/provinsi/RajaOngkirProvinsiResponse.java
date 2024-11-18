package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.provinsi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RajaOngkirProvinsiResponse {
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
        private List<QueryProvinsi> query;

        @JsonProperty("status")
        private StatusProvinsi status;

        @JsonProperty("results")
        private List<ResultsProvinsi> results;

       
        public List<QueryProvinsi> getQuery() {
            return query;
        }

        public void setQuery(List<QueryProvinsi> query) {
            this.query = query;
        }

        public StatusProvinsi getStatus() {
            return status;
        }

        public void setStatus(StatusProvinsi status) {
            this.status = status;
        }

        public List<ResultsProvinsi> getResults() {
            return results;
        }

        public void setResults(List<ResultsProvinsi> results) {
            this.results = results;
        }
    }
}
