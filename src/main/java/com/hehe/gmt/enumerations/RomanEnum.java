package com.hehe.gmt.enumerations;

import java.util.Arrays;
import java.util.List;

public enum RomanEnum {
    I(1),
    V(5),
    X(10),
    L(50),
    C(100),
    D(500),
    M(1000),
    ;
    private long value;

    RomanEnum(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static List<RomanEnum> LIST = Arrays.asList(I, V, X, L, C, D, M);

    public static RomanEnum getByLetter(String letter) {
        for (RomanEnum r : LIST) {
            if (r.name().equals(letter))
                return r;
        }
        return null;
    }
}
