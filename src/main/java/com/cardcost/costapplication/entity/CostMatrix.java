package com.cardcost.costapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table("COST_MATRIX")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CostMatrix implements Persistable<UUID> {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column("COST_ID")
    private UUID costId;
    @Column("COUNTRY")
    private String country;
    @Column("COST")
    private BigDecimal cost;

    @JsonIgnore
    @Override
    public UUID getId() {
        return costId;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        boolean result = Objects.isNull(costId);
        this.costId = result ? UUID.randomUUID() : this.costId;
        return result;
    }
}
