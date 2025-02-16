package net.austrianlinuxmemer.jxsql.database;

import net.austrianlinuxmemer.jxsql.result.Result;
import net.austrianlinuxmemer.jxsql.statements.Statement;

//
public interface Database extends AutoCloseable {
    Result execute(Statement statement);
    void beginTransaction();
    void commitTransaction();
    void rollbackTransaction();
}
