package com.cardcost.costapplication.service;

import com.cardcost.costapplication.controller.model.CostMatrixDTO;
import com.cardcost.costapplication.entity.CostMatrix;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CostMatrixService {

    Mono<CostMatrixDTO> saveCostMatrix(CostMatrixDTO costMatrix);

    Flux<CostMatrix> getAllCosts();

    Mono<Void> deleteCostMatrix(String costId);

    Mono<ResponseEntity<CostMatrix>> update(CostMatrixDTO costMatrix);

    Mono<CostMatrix> findByCountry(String country);
}
