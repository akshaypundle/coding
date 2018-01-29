package pundle.regexp.lex;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import pundle.regexp.literal.AnyCharLiteral;
import pundle.regexp.literal.CharLiteral;
import pundle.regexp.literal.CharacterClassLiteral;
import pundle.regexp.operator.AlternationOperator;
import pundle.regexp.operator.ConcatOperator;
import pundle.regexp.operator.KleenPlusOperator;
import pundle.regexp.operator.KleenStarOperator;
import pundle.regexp.operator.OptionalOperator;

import com.google.common.collect.ImmutableSet;

public class Lexer {
    private static final Token CONCAT_TOKEN = new ConcatOperator();

    private final Set<Character> ESCAPABLE_CHARS = ImmutableSet.of('(',
            ')',
            '*',
            '|',
            '+',
            '.',
            '?',
            '\\',
            'd',
            'D',
            's',
            'S',
            'w',
            'W');
    private ImmutableList<Token> lexemes;

    public Lexer(String input) {
        init(new MyIterator(input));
    }

    private void init(MyIterator it) {
        Builder<Token> parsedBuilder = new ImmutableList.Builder<Token>();
        Token lastToken = null;
        while (it.hasNext()) {
            Token curToken = tokenForChar(it);
            if (needsConcatBeforeToken(curToken, lastToken)) {
                parsedBuilder.add(CONCAT_TOKEN);
            }
            lastToken = curToken;
            parsedBuilder.add(curToken);
        }
        this.lexemes = parsedBuilder.build();
    }

    public List<Token> getLexemes() {
        return lexemes;
    }

    private Token tokenForChar(MyIterator it) {
        if (!it.hasNext()) {
            return null;
        }
        int pos = it.nextIndex();
        char ch = it.next();
        switch (ch) {
        case ')': {
            return new Token(TokenType.RPAREN, ')', pos);
        }
        case '(': {
            return new Token(TokenType.LPAREN, '(', pos);
        }
        case '*': {
            return new KleenStarOperator(pos);
        }
        case '+': {
            return new KleenPlusOperator(pos);
        }
        case '?': {
            return new OptionalOperator(pos);
        }
        case '|': {
            return new AlternationOperator(pos);
        }
        case '.': {
            return new AnyCharLiteral(pos);
        }
        case '\\': {
            if (!it.hasNext()) {
                throw new IllegalStateException("unexpected \\ at end of regexp");
            } else {
                char nextChar = it.next();
                if (!ESCAPABLE_CHARS.contains(nextChar)) {
                    throw new IllegalStateException(
                            "'" + nextChar + "' should not be escaped at pos " + pos);
                }
                return getTokenForEscapedCharacter(nextChar, pos);
            }

        }
        default:
            return new CharLiteral(ch, pos);
        }
    }

    private Token getTokenForEscapedCharacter(char nextChar, int pos) {
        if (nextChar == 'd') {
            return new CharacterClassLiteral(pos, IS_DIGIT_CHAR);
        } else if (nextChar == 'D') {
            return new CharacterClassLiteral(pos, IS_NOT_DIGIT_CHAR);
        } else if (nextChar == 's') {
            return new CharacterClassLiteral(pos, IS_SPACE_CHAR);
        } else if (nextChar == 'S') {
            return new CharacterClassLiteral(pos, IS_NOT_SPACE_CHAR);
        } else if (nextChar == 'w') {
            return new CharacterClassLiteral(pos, IS_WORD_CHAR);
        } else if (nextChar == 'W') {
            return new CharacterClassLiteral(pos, IS_NOT_WORD_CHAR);
        } else {
            return new CharLiteral(nextChar, pos);
        }
    }

    private boolean needsConcatBeforeToken(Token curToken, Token lastToken) {
        if ((TokenType.CHAR.equals(curToken.getType())
                || TokenType.LPAREN.equals(curToken.getType())) && lastToken != null) {
            if (!TokenType.ALTERNATION.equals(lastToken.getType())
                    && !TokenType.LPAREN.equals(lastToken.getType())) {
                return true;
            }
        }
        return false;
    }

    private class MyIterator implements java.util.Iterator<Character> {
        private final String input;
        private int nextPos;

        MyIterator(String input) {
            this.input = input;
            nextPos = 0;

        }

        @Override
        public Character next() {
            return input.charAt(nextPos++);
        }

        @Override
        public boolean hasNext() {
            return nextPos < this.input.length();
        }

        public int nextIndex() {
            return this.nextPos;
        }
    }

    private Predicate<Character> IS_SPACE_CHAR = new Predicate<Character>() {

        @Override
        public boolean test(Character t) {
            return Character.isWhitespace(t);
        }

        public String toString() {
            return "space";
        };
    };
    private Predicate<Character> IS_NOT_SPACE_CHAR = new Predicate<Character>() {

        @Override
        public boolean test(Character t) {
            return !Character.isWhitespace(t);
        }

        public String toString() {
            return "non space";
        };
    };
    private Predicate<Character> IS_WORD_CHAR = new Predicate<Character>() {

        @Override
        public boolean test(Character t) {
            return Character.isLetterOrDigit(t) || t == '_';
        }

        public String toString() {
            return "words";
        };
    };
    private Predicate<Character> IS_NOT_WORD_CHAR = new Predicate<Character>() {

        @Override
        public boolean test(Character t) {
            return !Character.isLetterOrDigit(t) && t != '_';
        }

        public String toString() {
            return "non words";
        };
    };
    private Predicate<Character> IS_DIGIT_CHAR = new Predicate<Character>() {

        @Override
        public boolean test(Character t) {
            return Character.isDigit(t);
        }

        public String toString() {
            return "digits";
        };
    };
    private Predicate<Character> IS_NOT_DIGIT_CHAR = new Predicate<Character>() {

        @Override
        public boolean test(Character t) {
            return !Character.isDigit(t);
        }

        public String toString() {
            return "non digits";
        };
    };
}
