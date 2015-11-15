package foo.bar;

import foo.bar.cases.Case1;
import foo.bar.cases.Case2;
import foo.bar.cases.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Created by kristinashandrenko on 06.11.15.
 */
public class MySuiteRunner extends Suite{
    static
    {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(new ConsoleAppender(
                new PatternLayout("%-6r [%p] %c - %m%n")));
    }
    private static foo.bar.cases.Suite suite;

    public MySuiteRunner(Class<?> klass) throws Exception {
        super(loadSuite(klass), Collections.EMPTY_LIST);
    }

    @Override
    protected List<Runner> getChildren() {
        List<Runner> runners = new ArrayList<>();
        try {
            for (TestCase testCase : suite.getCases()){
                if (testCase.childrenCases().isEmpty()){


                    runners.add(new MyPlainTestRunner(getTestClass().getJavaClass(), Arrays.asList(testCase)));
                }else{
//                    runners.add(new MyPlainTestRunner(getTestClass().getJavaClass(), testCase.childrenCases()));
//                    runners.add(new MyPlainTestRunner2(getTestClass().getJavaClass(), testCase.childrenCases()));
                }
            }
            runners.add(new MyPlainTestRunner(Case1.class, suite.getCases().get(0).childrenCases()));
            runners.add(new MyPlainTestRunner2(Case2.class, suite.getCases().get(1).childrenCases()));
//                    suite.getCases().get(0)

        } catch (InitializationError initializationError) {
            initializationError.printStackTrace();
        }
        return runners;
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
        Class<URLClassLoader> clazz= URLClassLoader.class;

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
