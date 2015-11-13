package foo.bar;

import foo.bar.cases.Suite;
import foo.bar.cases.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.runner.Description;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class MyTestRunner extends BlockJUnit4ClassRunner {
    static
    {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(new ConsoleAppender(
                new PatternLayout("%-6r [%p] %c - %m%n")));
    }
    private static Suite suite;

    private List<FrameworkMethod> methods;

    public MyTestRunner(Class clazz) throws Exception {
        super(loadSuite(clazz));

    }

    protected List<FrameworkMethod> computeTestMethods() {
        if (methods!=null){
            return methods;
        }

        methods = this.getTestMethods();
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

    protected List<FrameworkMethod> getTestMethods() {
        List<FrameworkMethod> methods = new ArrayList<FrameworkMethod>();
        for (TestCase tCase : suite.getCases()) {
            try {
                Method m = tCase.getClass().getMethod("execute");
                methods.add(new MyFrameworkMethod(m, tCase));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return methods;
    }

    private static Class loadSuite(Class clazz) throws Exception {

        RunConfiguration conf = (RunConfiguration) clazz.getAnnotation(RunConfiguration.class);
        for (Map.Entry<String, String> entry : parseConfString(conf.sysProps()).entrySet()) {
            System.setProperty(entry.getKey(), entry.getValue());
        }


        for (String extraCP : conf.extraClassPath().split(";")) {
            if (StringUtils.isBlank(extraCP)) {
                continue;
            }
            addURL(new File(extraCP.trim()).toURL());

        }

        ApplicationContext ctx= new ClassPathXmlApplicationContext(System.getProperty("xmlFile"));
        suite = ctx.getBean("testSuite", foo.bar.cases.Suite.class);

        return clazz;
    }

    private static void addURL(URL url) throws Exception {
        System.out.println("adding url "+url);
        URLClassLoader classLoader
                = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class clazz= URLClassLoader.class;

        // Use reflection
        Method method= clazz.getDeclaredMethod("addURL", new Class[] { URL.class });
        method.setAccessible(true);
        method.invoke(classLoader, new Object[] { url });
    }

    private static Map<String, String> parseConfString(String congString){
        Map<String, String> result = new HashMap<String, String>();
        for (String option : congString.split(";")){
            if (StringUtils.isBlank(option)){
                continue;
            }
            String[] parts = option.split("=");
            result.put(parts[0], parts[1]);
//            System.setProperty(parts[0].trim(), parts[1].trim());
        }
        return result;
    }
}
