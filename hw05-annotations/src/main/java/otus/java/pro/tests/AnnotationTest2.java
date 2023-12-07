package otus.java.pro.tests;

import otus.java.pro.annotations.After;
import otus.java.pro.annotations.Before;
import otus.java.pro.annotations.Test;

public class AnnotationTest2 {
    @Before
    public static void a2TestBefore() throws Exception {

        throw new Exception("Something completely wrong");
    }

    @Test
    public static void a2TestMethod1() {
        System.out.println("Run method1");
    }

    @After
    public static void a2TestAfter() {
        System.out.println("After tests");
    }
}
