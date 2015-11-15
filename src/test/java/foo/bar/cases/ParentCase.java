package foo.bar.cases;

import java.util.List;

/**
 * Created by kristinashandrenko on 15.11.15.
 */
public class ParentCase extends TestCase {

    private final String name;

    private final List<TestCase> children;

    public ParentCase(String name, List<TestCase> children) {
        this.name = name;
        this.children = children;
    }


    @Override
    public void execute() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<TestCase> childrenCases() {
        return children;
    }
}
