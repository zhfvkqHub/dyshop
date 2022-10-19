package com.zhfvkq.dyshop;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

//@SpringBootTest
class DyshopApplicationTests {

    @Test
    void testHasText() {
        Assert.assertTrue(StringUtils.hasLength(" ")); // true
        Assert.assertFalse(StringUtils.hasLength(""));
        Assert.assertTrue(StringUtils.hasLength("흥"));
        Assert.assertFalse(StringUtils.hasLength(null));
    }

}
