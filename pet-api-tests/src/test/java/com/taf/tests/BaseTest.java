package com.taf.tests;

import com.taf.config.PetTestConfig;
import io.qameta.allure.testng.AllureTestNg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Listeners;

@SpringBootTest(classes = PetTestConfig.class)
@Listeners({AllureTestNg.class})
@Slf4j
public class BaseTest extends AbstractTestNGSpringContextTests {
}
