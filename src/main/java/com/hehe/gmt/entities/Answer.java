package com.hehe.gmt.entities;

import com.hehe.gmt.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer extends BaseEntity {
    private long value;

    @OneToOne
    private Question question;
}
