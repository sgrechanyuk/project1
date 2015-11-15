package foo.bar.cases;


import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kristinashandrenko on 05.11.15.
 */
public class Case1 extends TestCase {
    @Override
    public void execute() {
        Assert.assertEquals(1, 2);
    }

    @Override
    public String getName() {
        return "1==2";
    }

    @Override
    public List<TestCase> childrenCases() {
        TestCase tc1 =
            new TestCase(){

                @Override
                public void execute() {
                    Assert.assertEquals(1, 2);
                }

                @Override
                public String getName() {
                    return "Case1 child 1";
                }
            };
        return Arrays.asList(tc1);
    }
}
