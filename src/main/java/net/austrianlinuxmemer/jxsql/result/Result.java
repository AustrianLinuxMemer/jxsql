package net.austrianlinuxmemer.jxsql.result;

import java.util.List;

public record Result(int rowcount, List<Row> rows) {
}
