package net.austrianlinuxmemer.nanosql;

public class ParameterizedInsertionStatement implements Statement {
    private final String preparedStatement;
    private ParameterizedInsertionStatement(String preparedStatement) {
        this.preparedStatement = preparedStatement;
    }
    public static ParameterizedInsertionStatement prepare(String statement, SQLFormatter sqlFormatter, Row... rows) {
        if (rows == null) {
            throw new IllegalArgumentException("the array of rows cannot be null");
        }
        if (rows.length == 0) {
            throw new IllegalArgumentException("There must be rows to insert");
        }
        if (!statement.toLowerCase().startsWith("INSERT".toLowerCase()) || !statement.toLowerCase().endsWith("VALUES".toLowerCase())) {
            throw new IllegalArgumentException(String.format("Statement %s is not an insert statement to be expanded", statement));
        }
        for (int i = 1; i < rows.length; i++) {
            int current = rows[i-1].data().length;
            int next = rows[i].data().length;
            if (current != next) throw new IllegalArgumentException("All rows must be of the same length");
        }
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append(statement);
        rowBuilder.append(" ");
        for (int i = 0; i < rows.length; i++) {
            String row = rowBuilder(rows[i], sqlFormatter);
            rowBuilder.append(row);
            if (i < rows.length - 1) {
                rowBuilder.append(",");
            } else {
                rowBuilder.append(";");
            }
        }
        return new ParameterizedInsertionStatement(rowBuilder.toString());
    }
    private static String rowBuilder(Row row, SQLFormatter sqlFormatter) {
        Object[] args = row.data();
        StringBuilder builder = new StringBuilder("(");
        for (int i = 0; i < args.length; i++) {
            builder.append(sqlFormatter.parse(args[i], sqlFormatter));
            if (i < args.length - 1) {
                builder.append(", ");
            } else {
                builder.append(")");
            }
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return preparedStatement;
    }
}
