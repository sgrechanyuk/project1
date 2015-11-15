package foo.bar;

import foo.bar.cases.TestCase;
import org.junit.runner.Description;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Parameterized;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by kristinashandrenko on 15.11.15.
 */
public class MyPlainTestRunner extends BlockJUnit4ClassRunner {

    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InitializationError if the test class is malformed.
     */

    private final String parentName;


    private List<FrameworkMethod> methods;

    public MyPlainTestRunner(Class<?> klass, List<TestCase> cases, String parentName) throws InitializationError {
        super(klass);
        methods = getTestMethods(cases);
        this.parentName = parentName;
    }

    @Override
    protected String getName() {
        return parentName;
    }

    @Override
    protected Description describeChild(FrameworkMethod method) {
        return Description.createTestDescription(parentName, method.getName());
    }

    protected List<FrameworkMethod> computeTestMethods() {
        if (methods==null){
            // we should return some dummy methods in order to pass validation in ParentRunner called from constructor
            return Arrays.asList(new FrameworkMethod(null));
        }
        return methods;
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        if (method instanceof MyFrameworkMethod) {
            MyFrameworkMethod myFrameworkMethod = (MyFrameworkMethod) method;
            return super.methodInvoker(method, myFrameworkMethod.getTarget());
        }else{
            return super.methodInvoker(method, test);
        }
    }

    protected static List<FrameworkMethod> getTestMethods(List<TestCase> cases) {
        List<FrameworkMethod> methods = new ArrayList<FrameworkMethod>();
        for (TestCase tCase : cases) {
            try {
                Method m = tCase.getClass().getMethod("execute");
                methods.add(new MyFrameworkMethod(m, tCase));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return methods;
    }

}
