package jile.nilex;

import java.util.Objects;

import jile.nilex.IndividualTokenType.IndividualToken;

/**
 * Something unfortunate that happened to a token(s), individually or
 * collectively, syntactically or semantically, during an process
 * ({@link Code.Process}), that may or may not be fatal to the process.
 */
abstract public class Mishap {
    final boolean fatal;
    private Code.Process process;
    public final IndividualToken token;

    public Mishap(IndividualToken token, boolean fatal) {
        this.token = token;
        this.fatal = fatal;
    }

    public void setProcess(Code.Process e) {
        if (process == null)
            process = e;
    }

    public Code.Process getProcess() {
        return process;
    }

    abstract public String getReport();

    // abstract public String getSolution();

    @Override
    public final String toString() {

        String string = getReport();

        if (token != null && token.getPosition() != null)
            string += ", at `" + getProcess().getCode().getPositionInfo(token.getPosition()) + "`";

        return string;

    }

    @Override
    public boolean equals(Object other) {
        return Objects.equals(toString(), other.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
