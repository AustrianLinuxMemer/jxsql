package net.austrianlinuxmemer.nanosql;

import java.util.List;

public record Result(int rowcount, List<Row> rows) {
}
