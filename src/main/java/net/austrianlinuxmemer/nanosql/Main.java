package net.austrianlinuxmemer.nanosql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:")) {
            Database database = new JDBCDatabase(connection);
            Statement statement = Statement.prepare("CREATE TABLE myTable (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, active BOOLEAN);");
            database.execute(statement);
            Object[][] data = {
                    {"leo", "email1", true},
                    {"füreder", "email2", false},
                    {"hallo", "email3", false}
            };
            Statement insert = ParameterizedInsertionStatement.prepare("INSERT INTO myTable (name, email, active) VALUES", Database.DefaultSQLFormatter, data);
            Result result = database.execute(insert);
            System.out.printf("Rows inserted: %d\n", result.rowcount());
            Statement select = Statement.prepare("SELECT * FROM myTable;");
            Result result1 = database.execute(select);
            result1.rows().forEach(r -> System.out.println(Arrays.toString(r.data())));
            FieldParser<Boolean> sqLiteBooleanParser = (b) -> !"0".equals(b);
            result1.rows().stream().map(r -> new User(r.getString(1), r.getString(2), r.getObject(3, sqLiteBooleanParser))).forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private record User(String name, String email, boolean isActive) {}
}