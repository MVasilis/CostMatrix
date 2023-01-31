package com.cardcost.costapplication.controller;

import com.cardcost.costapplication.controller.model.CostDTO;
import com.cardcost.costapplication.entity.CostMatrix;
import com.cardcost.costapplication.service.CardClearingCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/card/{cardNumber}")
public class CardClearingCostController {

    @Autowired
    private CardClearingCostService cardClearingCostService;



    @GetMapping("/clearing-cost")
    public Mono<CostDTO> getClearingCost(@PathVariable String cardNumber){
        return cardClearingCostService.getCardClearingCost(cardNumber);
    }

}
