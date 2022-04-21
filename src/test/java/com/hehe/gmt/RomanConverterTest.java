package com.hehe.gmt;

import com.hehe.gmt.utils.RomanConverter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class RomanConverterTest {

    @SneakyThrows
    @Test
    void test1903() {
        String roman = "MCMIII";
        assert (RomanConverter.romanToAlphabetNumber(roman) == 1903);
//        assert (RC2.romanToInteger3(roman) == 1903);
    }

    @SneakyThrows
    @Test
    void test3900() {
        var roman = "MMMCM";
        assert (RomanConverter.romanToAlphabetNumber(roman) == 3900);
//        assert (RC2.romanToInteger3(roman) == 3900);
    }

    @SneakyThrows
    @Test
    void test3999() {
        var roman = "MMMCMXCIX";
        assert (RomanConverter.romanToAlphabetNumber(roman) == 3999);
//        assert (RC2.romanToInteger3(roman) == 3999);
    }

    @SneakyThrows
    @Test
    void test3888() {
        var roman = "MMMDCCCLXXXVIII";
        assert (RomanConverter.romanToAlphabetNumber(roman) == 3888);
//        System.out.println(RC2.romanToInteger3(roman));
//        assert (RC2.romanToInteger3(roman) == 3888888);
    }

}
