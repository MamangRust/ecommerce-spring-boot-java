package com.sanedge.ecommerce_midtrans.controllers;

import com.sanedge.ecommerce_midtrans.domain.request.midtrans.CreateMidtransRequest;
import com.sanedge.ecommerce_midtrans.domain.response.midtrans.SnapMidtransResponse;
import com.sanedge.ecommerce_midtrans.service.SnapClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/midtrans")
public class MidtransController {

    private final SnapClientService snapClientService;

    public MidtransController(SnapClientService snapClientService) {
        this.snapClientService = snapClientService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<SnapMidtransResponse> createTransaction(
            @RequestBody CreateMidtransRequest request) {
        try {
            SnapMidtransResponse response = snapClientService.createTransaction(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    SnapMidtransResponse.builder()
                            .statusCode("500")
                            .errorMessages(java.util.List.of(e.getMessage()))
                            .build()
            );
        }
    }

}
