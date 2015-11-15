package foo.bar.cases;

import junit.framework.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kristinashandrenko on 05.11.15.
 */
public class Case2 extends TestCase {
    @Override
    public void execute() {
        Assert.assertEquals(2, 2);
    }

    @Override
    public String getName() {
        return "2==2";
    }

    @Override
    public List<TestCase> childrenCases() {
        TestCase tc1 =
                new TestCase() {

                    @Override
                    public void execute() {
                        org.junit.Assert.assertEquals(1, 2);
                    }

                    @Override
                    public String getName() {
                        return "Case 2 child 1";
                    }
                };
        TestCase tc2 =
                new TestCase(){

                    @Override
                    public void execute() {
                        org.junit.Assert.assertEquals(1, 2);
                    }

                    @Override
                    public String getName() {
                        return "Case2 child 2";
                    }
                };
        return Arrays.asList(tc1, tc2);
    }
}
