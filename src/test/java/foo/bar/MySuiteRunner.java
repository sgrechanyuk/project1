package foo.bar;

import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Created by kristinashandrenko on 06.11.15.
 */
public class MySuiteRunner extends Suite{
    public MySuiteRunner(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }
}
