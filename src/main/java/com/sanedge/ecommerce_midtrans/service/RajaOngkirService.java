package com.sanedge.ecommerce_midtrans.service;

import com.sanedge.ecommerce_midtrans.domain.request.rajaongkir.OngkirRequest;
import com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.city.RajaOngkirCityResponse;
import com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.ongkos.RajaOngkirOngkosResponse;
import com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.provinsi.RajaOngkirProvinsiResponse;

public interface RajaOngkirService {
    public RajaOngkirProvinsiResponse getProvinsi() throws Exception;
    public RajaOngkirCityResponse getCity(int idProv) throws Exception;
    public RajaOngkirOngkosResponse getCost(OngkirRequest request) throws Exception;
}
