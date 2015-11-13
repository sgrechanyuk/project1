package foo.bar.cases;

import junit.framework.Assert;

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
}
