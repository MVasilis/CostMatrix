package com.cardcost.costapplication.service;

import com.cardcost.costapplication.controller.model.CostDTO;
import reactor.core.publisher.Mono;

public interface CardClearingCostService {

    Mono<CostDTO> getCardClearingCost(String cardNumber);
}
