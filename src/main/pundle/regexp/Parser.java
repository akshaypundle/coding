package pundle.regexp;

import java.nio.channels.IllegalSelectorException;
import java.util.Stack;
import java.util.function.IntSupplier;
import java.util.regex.Pattern;

import pundle.regexp.lex.Lexer;
import pundle.regexp.lex.Token;
import pundle.regexp.lex.TokenType;
import pundle.regexp.literal.Literal;
import pundle.regexp.operator.BinaryOperator;
import pundle.regexp.operator.Operator;
import pundle.regexp.operator.UnaryOperator;
import pundle.regexp.transition.AnyCharTransition;
import pundle.regexp.transition.CharTransition;
import pundle.regexp.transition.CharacterClassTransition;
import pundle.regexp.transition.EpsilonTransition;
import pundle.regexp.transition.Transition;

class Parser {

    private final String input;
    private NFA nfa;
    private StateIdGenerator stateIdGenerator;

    Parser(String input) {
        this.input = input;
        this.stateIdGenerator = new StateIdGenerator();
        parse();
    }

    public NFA getNfa() {
        return nfa;
    }

    private void parse() {
        Stack<Fragment> operandStack = new Stack<>();
        Stack<Token> operatorStack = new Stack<>();
        Lexer lexer = new Lexer(this.input);
        for (Token token : lexer.getLexemes()) {
            if (token instanceof UnaryOperator) {
                Operator operator = (Operator) token;
                operator.evaluate(operandStack, stateIdGenerator);
            } else if (token instanceof BinaryOperator) {
                BinaryOperator operator = (BinaryOperator) token;
                operator.manipulateStack(operatorStack, operandStack, stateIdGenerator);
            } else if (token instanceof Literal) {
                Literal literal = (Literal) token;
                operandStack.push(literal.createFragment(stateIdGenerator));
            } else if (TokenType.LPAREN.equals(token.getType())) {
                operatorStack.push(token);
            } else if (TokenType.RPAREN.equals(token.getType())) {
                Token popped = null;
                do {
                    popped = operatorStack.pop();
                    if (popped instanceof Operator) {
                        BinaryOperator operator = (BinaryOperator) popped;
                        operator.evaluate(operandStack, stateIdGenerator);
                    }
                } while (!popped.getType().equals(TokenType.LPAREN));
            } else {
                throw new IllegalStateException();
            }

        }
        while (!operatorStack.isEmpty()) {
            Operator popped = (Operator) operatorStack.pop();
            popped.evaluate(operandStack, stateIdGenerator);
        }

        Fragment frag = operandStack.pop();

        System.out.println("regexp: " + this.input);
        System.out.println("start: " + frag.getStart());
        System.out.println("\n\ndotty format:\ndigraph {");
        for (Transition t : frag.getTransitions()) {
            String label = "";
            if (t instanceof EpsilonTransition) {
                label = "\u03f5";
            } else if (t instanceof CharTransition) {
                CharTransition ct = (CharTransition) t;
                label = String.valueOf(ct.getCh());
            } else if (t instanceof AnyCharTransition) {
                label = ".";
            } else if (t instanceof CharacterClassTransition) {
                label = t.toString();
            } else if (t instanceof CharTransition) {
                label = String.valueOf(((CharTransition) t).getCh());
            } else {
                throw new IllegalSelectorException();
            }
            System.out.println("  " + t.getStartState() + "->" + t.getEndState() + " [label=\""
                    + label + "\"];");
        }
        System.out.println("}");
        this.nfa = new NFA(frag.getStart(), -1, frag.getTransitions());

    }

    public static void main(String[] args) {
        doTest(".*akshay.*\\*\\.\\|\\?\\+\\(\\)\\\\", "defdefdaksdedededd*.|?+()\\");
    }

    static void doTest(String reg, String input) {
        NFA nfa2 = new Parser(reg).getNfa();
        Pattern p = Pattern.compile(reg);
        boolean answer = false;
        long repCount = 1;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < repCount; i++) {
            answer = nfa2.matches(input);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("NFA took " + (t2 - t1) + "ms");
        boolean answer2 = false;
        for (int i = 0; i < repCount; i++) {
            answer2 = p.matcher(input).matches();
        }
        long t3 = System.currentTimeMillis();
        System.out.println("matches: " + answer);

        if (answer != answer2) {
            throw new IllegalStateException();
        } else {
            System.out.println("matches: " + answer);
        }
        System.out.println("Regexp: " + reg + " Input: " + input + "\nJava/NFA time: "
                + (t3 - t2) * 1d / (t2 - t1));
    }

    private static final class StateIdGenerator implements IntSupplier {

        private int nextStart;

        StateIdGenerator() {
            nextStart = 0;
        }

        @Override
        public int getAsInt() {
            return this.nextStart++;
        }

    }
}
