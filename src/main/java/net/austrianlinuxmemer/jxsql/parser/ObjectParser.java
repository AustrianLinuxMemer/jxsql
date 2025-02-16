package net.austrianlinuxmemer.jxsql.parser;

@FunctionalInterface
public interface ObjectParser<T> {
    T parse(Object[] s);
}
