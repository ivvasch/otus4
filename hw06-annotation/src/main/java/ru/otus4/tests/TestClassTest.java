package ru.otus4.tests;

import ru.otus4.annotation.After;
import ru.otus4.annotation.Before;
import ru.otus4.annotation.Test;

public class TestClassTest {

    @Before
    void before() {
        System.out.println("BeforeMethod " + this);
//        throw new RuntimeException();
    }

    @Test
    void testMethod() {
        int c = 10/0;
        System.out.println("TestMethod " + this);
    }

    @Test
    void testMethod2() {
        throw new RuntimeException();
//        System.out.println("TestMethod2 " + this);
    }

    @Test
    void testMethod3() {
        System.out.println("TestMethod3 " + this);
    }

    @After
    void after() {
        System.out.println("AfterMethod " + this);
        System.out.println("-------------------------------------------");
        throw new RuntimeException();
    }
}
