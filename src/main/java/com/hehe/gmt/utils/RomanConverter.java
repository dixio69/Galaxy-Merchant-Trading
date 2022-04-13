package com.hehe.gmt.utils;

import com.hehe.gmt.enumerations.RomanEnum;
import com.hehe.gmt.exceptions.UnknownRomanNumberException;

public class RomanConverter {
    public static long romanToAlphabetNumber(String roman) throws UnknownRomanNumberException {
        long res = 0;
        for (int i = 0; i < roman.length(); i++) {
            RomanEnum s1 = RomanEnum.getByLetter(String.valueOf(roman.charAt(i)));
            if (s1 == null)
                throw new UnknownRomanNumberException();
            if (i + 1 < roman.length()) {
                RomanEnum s2 = RomanEnum.getByLetter(String.valueOf(roman.charAt(i + 1)));
                if (s2 == null)
                    throw new UnknownRomanNumberException();
                if (s1.getValue() >= s2.getValue()) {
                    res = res + s1.getValue();
                } else {
                    res = res + s2.getValue() - s1.getValue();
                    i++;
                }
            } else {
                res = res + s1.getValue();
            }
        }
        return res;
    }
}
