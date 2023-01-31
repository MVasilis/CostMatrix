package com.cardcost.costapplication.service.impl;

import com.cardcost.costapplication.controller.model.CostMatrixDTO;
import com.cardcost.costapplication.entity.CostMatrix;
import com.cardcost.costapplication.repository.CostMatrixRepository;
import com.cardcost.costapplication.service.CostMatrixService;
import com.cardcost.costapplication.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Service
public class CostMatrixImpl implements CostMatrixService {

    @Autowired
    private CostMatrixRepository costMatrixRepository;

    /**
     * Save cost matrix
     * @param costMatrix
     * @return
     */
    @Override
    public Mono<CostMatrixDTO> saveCostMatrix(CostMatrixDTO costMatrix) {
        try{
            return costMatrixRepository.save(CostMatrix.builder().cost(costMatrix.getCost()).country(costMatrix.getCountry()).build())
                    .flatMap(costMatrix1 -> {
                        CostMatrixDTO costMatrixDTO = CostMatrixDTO.builder()
                                .costId(costMatrix1.getId().toString())
                                .country(costMatrix1.getCountry())
                                .cost(costMatrix1.getCost()).build();
                        return Mono.just(costMatrixDTO);
                    });
        } catch (Exception ex) {
            log.error("Error while save cost " + Arrays.toString(ex.getStackTrace()));
            throw new ServiceException(ex, "Error while save cost ");
        }
    }

    /**
     * get all cost matrix
     * @return
     */
    @Override
    public Flux<CostMatrix> getAllCosts() {
        return costMatrixRepository.findAll();
    }

    /**
     * Delete cost matrix
     * @param costId
     * @return
     */
    @Override
    public Mono<Void> deleteCostMatrix(String costId) {
        try {
            return costMatrixRepository.deleteById(UUID.fromString(costId));
        } catch (Exception ex) {
            log.error("Error while deleting cost " + Arrays.toString(ex.getStackTrace()));
            throw new ServiceException(ex, "Error while deleting cost ");
        }
    }

    /**
     * Update cost matrix
     * @param costMatrix
     * @return
     */
    @Override
    public Mono<ResponseEntity<CostMatrix>> update(CostMatrixDTO costMatrix) {
        try {
            Mono<CostMatrix> entityMono = costMatrixRepository.findByCostId(UUID.fromString(costMatrix.getCostId()));
            CostMatrix objectToSave = entityMono.share().block();
            objectToSave.setCost(costMatrix.getCost());

            Mono<CostMatrix> updatedMono = costMatrixRepository.save(objectToSave);
            return Mono.just(new ResponseEntity<>(updatedMono.share().block(), HttpStatus.OK));
        } catch (Exception ex){
            log.error("Error while update cost " + Arrays.toString(ex.getStackTrace()));
            throw new ServiceException(ex, "Error while update cost ");
        }

    }

    /**
     *  Find cost matrix by country
     * @param country
     * @return
     */
    @Override
    public Mono<CostMatrix> findByCountry(String country) {
        try {
            return costMatrixRepository.findByCountry(country);
        }catch (Exception ex){
            log.error("Error while finding cost by country " + Arrays.toString(ex.getStackTrace()));
            throw new ServiceException(ex, "Error while finding cost by country ");
        }
    }
}
