package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.ongkos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RajaOngkirOngkosResponse {
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
        private QueryOngkos query;

        @JsonProperty("status")
        private StatusOngkos status;

        @JsonProperty("origin_details")
        private CityDetailOngkos originDetails;

        @JsonProperty("destination_details")
        private CityDetailOngkos destinationDetails;

        @JsonProperty("results")
        private List<CourierResultOngkos> results;

       
        public QueryOngkos getQuery() {
            return query;
        }

        public void setQuery(QueryOngkos query) {
            this.query = query;
        }

        public StatusOngkos getStatus() {
            return status;
        }

        public void setStatus(StatusOngkos status) {
            this.status = status;
        }

        public CityDetailOngkos getOriginDetails() {
            return originDetails;
        }

        public void setOriginDetails(CityDetailOngkos originDetails) {
            this.originDetails = originDetails;
        }

        public CityDetailOngkos getDestinationDetails() {
            return destinationDetails;
        }

        public void setDestinationDetails(CityDetailOngkos destinationDetails) {
            this.destinationDetails = destinationDetails;
        }

        public List<CourierResultOngkos> getResults() {
            return results;
        }

        public void setResults(List<CourierResultOngkos> results) {
            this.results = results;
        }
    }
}
