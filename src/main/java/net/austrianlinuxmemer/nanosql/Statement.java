package net.austrianlinuxmemer.nanosql;

import java.util.stream.Collectors;

public interface Statement {

    static Statement prepare(String statement) {
        return new Statement() {
            @Override
            public String toString() {
                return statement;
            }
        };
    }
    String toString();
}
