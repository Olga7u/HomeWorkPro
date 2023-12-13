package otus.java.pro.tests;

import otus.java.pro.annotations.After;
import otus.java.pro.annotations.Before;
import otus.java.pro.annotations.Test;

public class AnnotationTest3 {
    @Before
    public static void a3TestBefore1() {
        System.out.println("Before tests 1");
    }

    @Before
    public static void a3TestBefore2() {
        System.out.println("Before tests 2");
    }

    @Test
    public static void a3TestMethod1() {
        System.out.println("Run method1");
    }

    @Test
    public static void a3TestMethod2() {
        System.out.println("Run method2");
    }

    @After
    public static void a3TestAfter() {
        System.out.println("After tests");
    }
}
