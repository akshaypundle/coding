package pundle.regexp.literal;

import java.util.function.IntSupplier;

import com.google.common.base.MoreObjects;

import pundle.regexp.Fragment;
import pundle.regexp.lex.Token;
import pundle.regexp.lex.TokenType;

public abstract class Literal extends Token {

    public Literal(TokenType type, Character ch) {
        super(type, ch);
    }

    public Literal(TokenType type, Character ch, int pos) {
        super(type, ch, pos);
    }

    public abstract Fragment createFragment(IntSupplier stateIdGenerator);

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("Type", this.getType()).add("ch", this.getCh())
                .add("pos", this.getPos()).toString();
    }
}
