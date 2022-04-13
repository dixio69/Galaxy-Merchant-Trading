package com.hehe.gmt;

import com.hehe.gmt.utils.RomanConverter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RomanConverterTest {

	@SneakyThrows
    @Test
	void contextLoads() {
        String roman = "MCMIII";
        assert(RomanConverter.romanToAlphabetNumber(roman) == 1903);
    }

}
