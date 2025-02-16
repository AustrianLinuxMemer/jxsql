package net.austrianlinuxmemer.nanosql;

@FunctionalInterface
public interface FieldParser<T> {
    T parse(String s);
}
