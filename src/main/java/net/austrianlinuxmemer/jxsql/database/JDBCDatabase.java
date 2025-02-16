package net.austrianlinuxmemer.jxsql.database;

import net.austrianlinuxmemer.jxsql.result.Result;
import net.austrianlinuxmemer.jxsql.result.Row;
import net.austrianlinuxmemer.jxsql.statements.Statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCDatabase implements Database {
    private Connection connection;
    public JDBCDatabase(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Result execute(Statement statement) {
        try(java.sql.Statement statement1 = connection.createStatement()) {
            if (statement1.execute(statement.toString())) {
                ResultSet resultSet = statement1.getResultSet();
                ResultSetMetaData metaData = resultSet.getMetaData();
                List<Row> rows = new ArrayList<>();
                int rowcount = 0;
                while (resultSet.next()) {
                    List<String> col = new ArrayList<>();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        col.add(resultSet.getString(i));
                    }
                    rows.add(new Row(col.toArray(String[]::new)));
                    rowcount++;
                }
                return new Result(rowcount, rows);
            } else {
                return new Result(statement1.getUpdateCount(), List.of());
            }
        } catch (SQLException e) {
            throw new DatabaseException("SQLException occurred", e);
        }
    }

    @Override
    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DatabaseException("SQLException occurred", e);
        }
    }

    @Override
    public void commitTransaction() {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseException("SQLException occurred", e);
        }
    }

    @Override
    public void rollbackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DatabaseException("SQLException occurred", e);
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
