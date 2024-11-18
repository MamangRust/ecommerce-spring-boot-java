package com.sanedge.ecommerce_midtrans.service;


import com.sanedge.ecommerce_midtrans.domain.request.slider.CreateSliderRequest;
import com.sanedge.ecommerce_midtrans.domain.request.slider.UpdateSliderRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;


public interface SliderService {
    public MessageResponse getSliders();
    public MessageResponse getSlider(int sliderId);
    public MessageResponse create(CreateSliderRequest createSliderRequest);
    public MessageResponse update(UpdateSliderRequest updateSliderRequest);
    public MessageResponse delete(int sliderId);
}
