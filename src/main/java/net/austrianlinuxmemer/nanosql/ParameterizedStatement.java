package net.austrianlinuxmemer.nanosql;

import java.util.Arrays;
import java.util.LinkedList;

public class ParameterizedStatement implements Statement {

    private final String preparedStatement;
    private ParameterizedStatement(String preparedStatement) {
        this.preparedStatement = preparedStatement;
    }
    public static ParameterizedStatement prepare(String statement, SQLFormatter sqlFormatter, Object... args) {
        if (statement == null) {
            throw new IllegalArgumentException("There must be a Statement");
        }
        if (sqlFormatter == null) {
            throw new IllegalArgumentException("There must be a SQL Formatter");
        }
        int[] codepoints = statement.codePoints().toArray();
        long placeholders = Arrays.stream(codepoints).filter(c -> c == 63).count();

        if (args == null || args.length == 0) {
            if (placeholders == 0) {
                return new ParameterizedStatement(statement);
            } else {
                throw new IllegalArgumentException("There must be exactly as many arguments as placeholders");
            }
        }

        if (args.length != placeholders) {
            throw new IllegalArgumentException("There must be exactly as many arguments as placeholders");
        }
        StringBuilder characters = new StringBuilder();
        LinkedList<Object> objects = new LinkedList<>(Arrays.asList(args));
        Arrays.stream(codepoints).forEach(codepoint -> {
            String c = Character.toString(codepoint);
            if (c.equals("?")) {
                characters.append(sqlFormatter.parse(objects.pop(), sqlFormatter));
            } else {
                characters.append(c);
            }
        });
        return new ParameterizedStatement(characters.toString());
    }
    @Override
    public String toString() {
        return preparedStatement;
    }
}
