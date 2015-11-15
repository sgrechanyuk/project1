package foo.bar.cases;

import java.util.Collections;
import java.util.List;

/**
 * Created by kristinashandrenko on 05.11.15.
 */
public abstract class TestCase {

    public List<TestCase> childrenCases(){
        return Collections.emptyList();
    }

    public abstract void execute();

    public abstract String getName();
}
