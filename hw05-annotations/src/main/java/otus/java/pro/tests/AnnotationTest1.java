package otus.java.pro.tests;

import otus.java.pro.annotations.After;
import otus.java.pro.annotations.Before;
import otus.java.pro.annotations.Test;

public class AnnotationTest1 {

    @Before
    public static void a1TestBefore() {
        System.out.println("Before tests");
    }

    @Test
    public static void a1TestMethod1() {
        System.out.println("Run method1");
    }

    @Test
    public static void a1TestMethod2() {
        System.out.println("Run method2");
    }

    @Test
    public static void a1TestBadMethod() throws Exception {
        throw new Exception("Something wrong");
    }

    @After
    public static void a1TestAfter() {
        System.out.println("After tests");
    }
}
