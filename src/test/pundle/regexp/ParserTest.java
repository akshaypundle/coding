package pundle.regexp;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class ParserTest {

    @Test
    public void testSimple() {
        doTest("a*kshay|vidya|amrita", "amrita");
        doTest("a*kshay|vidya|amrita", "vidya");
        doTest("a*kshay|vidya|amrita", "akshay");
        doTest("a*kshay|vidya|amrita", "kshay");
        doTest("a?kshay|vidya|amrita", "amrita");
        doTest("a?kshay|vidya|amrita", "vidya");
        doTest("a?kshay|vidya|amrita", "akshay");
        doTest("a?kshay|vidya|amrita", "kshay");
        doTest("a?kshay|vidya|amrita", "avidya");
        doTest("(d|e|f+)*((d|e|f+)+)+(def+)+", "defdededefdedededefefdedd");
        doTest(".*akshay.*", "defdededefdakshayedededefefdedd");
        doTest(".*akshay.*\\*", "defdefdakshayedededededd*");
        doTest(".*akshay.*\\*", "defdefdakshayedededededd");
        doTest(".*akshay.*\\*\\.\\|\\?\\+\\(\\)", "defdefdakshayedededededd.*?");
        doTest(".*akshay.*\\*\\.\\|\\?\\+\\(\\)", "defdefdakshayedededededd*.|?+()");
        doTest(".*akshay.*\\*\\.\\|\\?\\+\\(\\)\\\\", "defdefdakshayedededededd*.|?+()\\");
    }

    @Test
    public void testCharacterClasses() {
        doTest("\\s*", "  ");
        doTest("\\s+", "  ");
        doTest("\\S*", "  ");
        doTest("\\S+", "  ");
        doTest("\\s*", " a ");
        doTest("\\s+", "\n \t \f ");
        doTest("\\S*", "akshay");
        doTest("\\S+", "asd");

        doTest("\\d+", "asd");
        doTest("\\d+", "1234019282319237");
        doTest("\\d+", "123d");
        doTest("\\d+", "");
        doTest("\\D+", "123d");
        doTest("\\D+", "abc");
        doTest("\\D+", "");

        doTest("\\w*", "asd__");
        doTest("\\w*", "1234019282319237");
        doTest("\\w+", "123d");
        doTest("\\w*", "");
        doTest("\\W+", "123d");
        doTest("\\W+", "abc");
        doTest("\\W+", "");
    }

    static void doTest(String reg, String input) {
        NFA nfa2 = new Parser(reg).getNfa();
        Pattern p = Pattern.compile(reg);
        Assert.assertEquals(p.matcher(input).matches(), nfa2.matches(input));
    }
}
