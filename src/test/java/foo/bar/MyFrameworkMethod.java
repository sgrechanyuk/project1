package foo.bar;

import foo.bar.cases.TestCase;
import org.junit.runners.model.FrameworkMethod;

import java.lang.reflect.Method;

public class MyFrameworkMethod extends FrameworkMethod{

    private final TestCase target;

    public MyFrameworkMethod(Method method, TestCase target) {
        super(method);
        this.target = target;
    }

    public TestCase getTarget(){
        return target;
    }

    @Override
    public String getName() {
        return getTarget().getName();
    }
}
