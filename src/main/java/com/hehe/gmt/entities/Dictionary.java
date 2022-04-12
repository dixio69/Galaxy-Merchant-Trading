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
public class Dictionary extends BaseEntity {
    private String keyword;
    private String letter;
    private long value;
}
