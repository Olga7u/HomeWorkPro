package otus.java.pro.tests;

import lombok.extern.slf4j.Slf4j;
import otus.java.pro.annotations.After;
import otus.java.pro.annotations.Before;
import otus.java.pro.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestRunner {

    private final static String EXMSG = "Can't use @Before, @After and @Test annotations together";
    private final static String FAILED = " failed: ";

    private static boolean runBeforeTest(Method method) {
        try {
            if (method.isAnnotationPresent(Test.class) || method.isAnnotationPresent(After.class)) {
                throw new Exception(EXMSG);
            }
            method.invoke(null);

        } catch (InvocationTargetException targetException) {
            log.error(method.getName() + '.' + method.getName() + " failed: " + targetException.getCause().getMessage());
            return false;
        } catch (Exception exception) {
            log.error(method.getName() + FAILED + exception.getMessage());
            return false;
        }
        return true;
    }

    private static void runAfterTest(Method method) {
        try {
            if (method.isAnnotationPresent(Before.class) || method.isAnnotationPresent(Test.class)) {
                throw new Exception(EXMSG);
            }
            method.invoke(null);

        } catch (InvocationTargetException targetException) {
            log.error(method.getName() + FAILED + targetException.getCause().getMessage());
        } catch (Exception exception) {
            log.error(method.getName() + FAILED + exception.getMessage());
        }
    }

    private static void runTest(Method method) throws Exception {

        if (method.isAnnotationPresent(Before.class) || method.isAnnotationPresent(After.class)) {
            throw new Exception(EXMSG);
        }
        method.invoke(null);


    }

    public static void runAllTestsFor(Class<?> cls) {
        System.out.println("Run tests for " + cls.getName());

        int allTestsCount = 0;
        int failedTestsCount = 0;

        List<Method> beforeTests = new ArrayList<>();
        List<Method> tests = new ArrayList<>();
        List<Method> afterTests = new ArrayList<>();

        for (Method mtd : cls.getMethods()) {
            if (mtd.getAnnotation(Before.class) != null) {
                beforeTests.add(mtd);
            }
            if (mtd.getAnnotation(Test.class) != null) {
                tests.add(mtd);
            }
            if (mtd.getAnnotation(After.class) != null) {
                afterTests.add(mtd);
            }
        }

        for (Method method : tests) {
            allTestsCount++;
            try {
                for (Method beforeMethod : beforeTests) {
                    if (!runBeforeTest(beforeMethod)) {
                        throw new Exception("Test failed");
                    }
                }
                runTest(method);
            } catch (InvocationTargetException targetException) {
                log.error(method.getName() + FAILED + targetException.getCause().getMessage());
                failedTestsCount++;
            } catch (Exception exception) {
                log.error(method.getName() + FAILED + exception.getMessage());
                failedTestsCount++;
            }
            for (Method afterMethod : afterTests) {
                runAfterTest(afterMethod);
            }
        }
        log.info("All tests count: " + allTestsCount + ", failed tests count: " + failedTestsCount);
    }


}
