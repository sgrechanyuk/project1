package foo.bar.cases;

import junit.framework.Assert;

/**
 * Created by kristinashandrenko on 15.11.15.
 */
public class ChildCase extends TestCase {
    private final String name;

    public ChildCase(String name) {
        this.name = name;
    }

    @Override
    public void execute() {
        Assert.assertEquals(2, 1);
    }

    @Override
    public String getName() {
        return name;
    }
}
