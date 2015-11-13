package foo.bar;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunConfiguration(
        sysProps = "prop1=val1;prop2=val2;xmlFile=Ctx.xml",
        extraClassPath = "dev;dev/foo/bar"
)
@RunWith(MyTestRunner.class)
@ContextConfiguration("classpath:ctx.xml")
public class MySuperTest {
}
