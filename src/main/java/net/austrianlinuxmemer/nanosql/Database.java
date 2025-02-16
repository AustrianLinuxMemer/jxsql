package net.austrianlinuxmemer.nanosql;

import java.util.Collection;
import java.util.stream.Collectors;
//
public interface Database extends AutoCloseable {
    SQLFormatter DefaultSQLFormatter = (o, f) -> switch (o) {
        case null -> "NULL";
        case CharSequence c -> SQLFormatter.parseString(c);
        case Number n -> n.toString();
        case Boolean b -> b ? "TRUE" : "FALSE";
        case Collection<?> c -> SQLFormatter.parseCollection(c, f);
        case Object[] a -> SQLFormatter.parseArray(a, f);
        case long[] a -> SQLFormatter.parseArray(a, f);
        case int[] a -> SQLFormatter.parseArray(a, f);
        case short[] a -> SQLFormatter.parseArray(a, f);
        case byte[] a -> SQLFormatter.parseArray(a, f);
        case char[] a -> SQLFormatter.parseArray(a, f);
        case boolean[] a -> SQLFormatter.parseArray(a, f);
        case float[] a -> SQLFormatter.parseArray(a, f);
        case double[] a -> SQLFormatter.parseArray(a, f);
        default -> throw SQLFormatter.noFormattingCase.apply(o.getClass());
    };
    Result execute(Statement statement);
    void beginTransaction();
    void commitTransaction();
    void rollbackTransaction();
}
