package com.hehe.gmt.entities;

import com.hehe.gmt.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question extends BaseEntity {
    private double questionLong;
    private String questionStr;
    private String unit;
    private int index;
    private String answer;
}
