package net.austrianlinuxmemer.jxsql;

import net.austrianlinuxmemer.jxsql.database.Database;
import net.austrianlinuxmemer.jxsql.database.JDBCDatabase;
import net.austrianlinuxmemer.jxsql.formatters.SQLFormatter;
import net.austrianlinuxmemer.jxsql.parser.FieldParser;
import net.austrianlinuxmemer.jxsql.result.Result;
import net.austrianlinuxmemer.jxsql.result.Row;
import net.austrianlinuxmemer.jxsql.statements.ParameterizedInsertionStatement;
import net.austrianlinuxmemer.jxsql.statements.ParameterizedStatement;
import net.austrianlinuxmemer.jxsql.statements.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:")) {
            Database database = new JDBCDatabase(connection);
            Statement statement = Statement.prepare("CREATE TABLE myTable (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, active BOOLEAN);");
            database.execute(statement);
            Row[] data = {
                    new Row("leo", "email1", true),
                    new Row("füreder", "email2", false),
                    new Row("hallo", "email3", false)
            };
            Statement insert = ParameterizedInsertionStatement.prepare("INSERT INTO myTable (name, email, active) VALUES", SQLFormatter.defaultFormatter, data);
            Result result = database.execute(insert);
            System.out.printf("Rows inserted: %d\n", result.rowcount());
            Statement select = Statement.prepare("SELECT * FROM myTable;");
            Result result1 = database.execute(select);
            result1.rows().forEach(r -> System.out.println(Arrays.toString(r.data())));
            FieldParser<Boolean> sqLiteBooleanParser = (b) -> !"0".equals(b);
            result1.rows().stream().map(r -> new User(r.getString(1), r.getString(2), r.getObject(3, sqLiteBooleanParser))).forEach(System.out::println);
            Set<String> set = Set.of("one", "two", "three");
            Statement statement1 = ParameterizedStatement.prepare("SELECT BOGUS (?)", SQLFormatter.defaultFormatter, set);
            System.out.println(statement1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private record User(String name, String email, boolean isActive) {}
}