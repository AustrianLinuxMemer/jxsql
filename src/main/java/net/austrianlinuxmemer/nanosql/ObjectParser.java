package net.austrianlinuxmemer.nanosql;

@FunctionalInterface
public interface ObjectParser<T> {
    T parse(String[] s);
}
