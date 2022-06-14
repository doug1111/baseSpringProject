package com.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 应用测试
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationTests {

	@Test
	void test1() {
        System.out.println("This is test1!");
	}

}