package jile.nilex;

public interface Encodeable {
    public Code encode() throws Languages.NoLanguageAssociatedException;
}