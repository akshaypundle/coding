package pundle.regexp.lex;

import com.google.common.base.MoreObjects;

public class Token {
    private final TokenType type;
    private final Character ch;
    private final int pos;

    public Token(TokenType type, Character ch) {
        this.type = type;
        this.ch = ch;
        this.pos = -1;
    }

    public Token(TokenType type, Character ch, int pos) {
        this.type = type;
        this.ch = ch;
        this.pos = pos;
    }

    public TokenType getType() {
        return type;
    }

    public Character getCh() {
        return ch;
    }

    public int getPos() {
        return pos;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("Type", this.type).add("ch", this.ch).add("pos", this.pos)
                .toString();
    }
}