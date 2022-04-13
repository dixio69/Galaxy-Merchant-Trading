package com.hehe.gmt.entities;

import com.hehe.gmt.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statement extends BaseEntity {
    private String item;
    private String unit;
    @Column(precision = 10, scale = 2, columnDefinition="DECIMAL(10,2)")
    private BigDecimal value;
}
