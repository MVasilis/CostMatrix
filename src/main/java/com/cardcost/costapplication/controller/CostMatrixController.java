package com.cardcost.costapplication.controller;

import com.cardcost.costapplication.controller.model.CostMatrixDTO;
import com.cardcost.costapplication.entity.CostMatrix;
import com.cardcost.costapplication.service.CostMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cost-matrix")
public class CostMatrixController {

    @Autowired
    private CostMatrixService costMatrixService;

    @PostMapping
    public Mono<CostMatrixDTO> createProduct(@RequestBody CostMatrixDTO request){
        System.out.printf("Request received" + request);
        return costMatrixService.saveCostMatrix(request);
    }

    @GetMapping
    public Flux<CostMatrix> getAllCosts(){
        return costMatrixService.getAllCosts();
    }

    @DeleteMapping("/{costId}")
    public Mono<Void> deleteCostMatrix(@PathVariable String costId){
        return costMatrixService.deleteCostMatrix(costId);
    }

    @PutMapping("/{costId}")
    public Mono<ResponseEntity<CostMatrix>> updateCostMatrix(@PathVariable String costId,
                                                             @RequestBody CostMatrixDTO costMatrix){
        costMatrix.setCostId(costId);
        return costMatrixService.update(costMatrix);
    }

}
