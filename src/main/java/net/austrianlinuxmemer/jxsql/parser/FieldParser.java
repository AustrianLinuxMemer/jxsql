package net.austrianlinuxmemer.jxsql.parser;

@FunctionalInterface
public interface FieldParser<T> {
    T parse(Object s);
}
