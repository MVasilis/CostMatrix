package com.cardcost.costapplication.service.impl;

import com.cardcost.costapplication.common.Utils;
import com.cardcost.costapplication.controller.model.CostDTO;
import com.cardcost.costapplication.entity.CostMatrix;
import com.cardcost.costapplication.repository.CostMatrixRepository;
import com.cardcost.costapplication.service.CardClearingCostService;
import com.cardcost.costapplication.service.CostMatrixService;
import com.cardcost.costapplication.thirdparty.CardClearingCostClient;
import com.cardcost.costapplication.thirdparty.response.BinListClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.cardcost.costapplication.common.Constant.OTHER_VALUE;

@Service
public class CardClearingCostServiceImpl implements CardClearingCostService {

    @Autowired
    private CardClearingCostClient cardClearingCostClient;
    @Autowired
    private CostMatrixRepository costMatrixRepository;

    @Autowired
    private CostMatrixService costMatrixService;

    @Override
    public Mono<CostDTO> getCardClearingCost(String cardNumber) {

        String cardPan = Utils.retrieveDesiredNumber(cardNumber, 0, 6);
        //cardNumber.replaceAll("\\s", "").substring(0,6);

        BinListClientResponse responseObject = cardClearingCostClient.retrieveBisListData(cardPan)
                .share().block();

        Mono<CostMatrix> costMatrixMono = costMatrixService.findByCountry(responseObject.getCountry().alpha2)
                .defaultIfEmpty(Objects.requireNonNull(costMatrixService.findByCountry(OTHER_VALUE).share().block()));

        return costMatrixMono.flatMap(costMatrix -> {
            CostDTO cost = CostDTO.builder().country(costMatrix.getCountry()).cost(costMatrix.getCost()).build();
            return Mono.just(cost);
        });
    }
}
