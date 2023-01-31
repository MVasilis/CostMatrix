package com.cardcost.costapplication.repository;

import com.cardcost.costapplication.entity.CostMatrix;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CostMatrixRepository extends ReactiveCrudRepository<CostMatrix, UUID> {

    @Query("Select * from COST_MATRIX where COUNTRY = ?1")
    Mono<CostMatrix> findByCountry(String country);

    @Query("Select * from COST_MATRIX where COST_ID = ?1")
    Mono<CostMatrix> findByCostId(UUID costId);
}
