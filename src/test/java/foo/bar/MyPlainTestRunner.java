package foo.bar;

import foo.bar.cases.TestCase;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Parameterized;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;
import java.util.ArrayList;
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


    public MyPlainTestRunner(Class<?> klass, List<TestCase> cases, String parentName) throws InitializationError {
        super(processTests(klass, cases));
        this.parentName = parentName;
    }

    @Override
    protected String getName() {
        return parentName;
    }

    private static Class processTests(Class klass, List<TestCase> cases){
        methods = getTestMethods(cases);
        return klass;
    }

    private static List<FrameworkMethod> methods;

    protected List<FrameworkMethod> computeTestMethods() {
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
