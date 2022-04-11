package com.hehe.gmt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GmtApplicationTests {

    @Test
    void contextLoads() {
        boolean a = false;
        Assertions.assertThat(a == true);
    }

}
