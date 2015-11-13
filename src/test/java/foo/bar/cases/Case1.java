package foo.bar.cases;


import org.junit.Assert;

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
}
