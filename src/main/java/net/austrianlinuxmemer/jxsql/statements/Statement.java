package net.austrianlinuxmemer.jxsql.statements;

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
