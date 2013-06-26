package org.ua.oblik.junit;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * http://stackoverflow.com/questions/3089151/specifying-an-order-to-junit-4-tests-at-the-method-level-not-class-level
 */
public class OrderedSpringJUnit4ClassRunner extends SpringJUnit4ClassRunner {

    public OrderedSpringJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> list = super.computeTestMethods();
        Collections.sort(list, new Comparator<FrameworkMethod>() {
            @Override
            public int compare(FrameworkMethod f1, FrameworkMethod f2) {
                TestOrder o1 = f1.getAnnotation(TestOrder.class);
                TestOrder o2 = f2.getAnnotation(TestOrder.class);
                if (o1 == null || o2 == null) {
                    return -1;
                }
                return o1.order() - o2.order();
            }
        });
        return list;
    }
}
