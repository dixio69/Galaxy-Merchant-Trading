package com.hehe.gmt.enumerations;

import java.util.Arrays;
import java.util.List;

public enum RomanEnum {
    I(1, true, null),
    V(5, false, I),
    X(10, true, I),
    L(50, false, X),
    C(100, true, X),
    D(500, false, C),
    M(1000, true, C),
    ;
    private long value;
    private boolean isRepeatable;
    private RomanEnum subtractor;

    RomanEnum(long value, boolean isRepeatable, RomanEnum subtractor) {
        this.value = value;
        this.isRepeatable = isRepeatable;
        this.subtractor = subtractor;
    }

    public long getValue() {
        return value;
    }

    public boolean isRepeatable() {
        return isRepeatable;
    }

    public RomanEnum getSubtractor() {
        return subtractor;
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
