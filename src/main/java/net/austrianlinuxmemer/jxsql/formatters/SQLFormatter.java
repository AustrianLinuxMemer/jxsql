package net.austrianlinuxmemer.jxsql.formatters;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@FunctionalInterface
public interface SQLFormatter {
    String format(Object o, SQLFormatter childFormatter);
    SQLFormatter defaultFormatter = (o, f) -> switch (o) {
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
    static String parseString(CharSequence s) {
        return "'" + s.codePoints().mapToObj(Character::toString).map(c -> c.equals("'") ? "''" : c).collect(Collectors.joining()) + "'";
    }
    static String parseCollection(Collection<?> collection, SQLFormatter formatter) {
        StringBuilder builder = new StringBuilder();
        collection.forEach(c -> builder.append(formatter.format(c, formatter)).append(","));
        builder.delete(builder.length()-1, builder.length());
        return builder.toString();
    }
    static String parseArray(Object[] array, SQLFormatter formatter) {
        return Arrays.stream(array).map(o -> formatter.format(o, formatter)).collect(Collectors.joining(","));
    }
    static String parseArray(long[] array, SQLFormatter formatter) {
        return Arrays.stream(array).mapToObj(l -> formatter.format(l, formatter)).collect(Collectors.joining(","));
    }
    static String parseArray(int[] array, SQLFormatter formatter) {
        return Arrays.stream(array).mapToObj(i -> formatter.format(i, formatter)).collect(Collectors.joining(","));
    }
    static String parseArray(short[] array, SQLFormatter formatter) {
        Short[] boxed = new Short[array.length];
        for (int i = 0; i < boxed.length; i++) {
            boxed[i] = array[i];
        }
        return parseArray(boxed, formatter);
    }
    static String parseArray(byte[] array, SQLFormatter formatter) {
        Byte[] boxed = new Byte[array.length];
        for (int i = 0; i < boxed.length; i++) {
            boxed[i] = array[i];
        }
        return parseArray(boxed, formatter);
    }
    static String parseArray(boolean[] array, SQLFormatter formatter) {
        Boolean[] boxed = new Boolean[array.length];
        for (int i = 0; i < boxed.length; i++) {
            boxed[i] = array[i];
        }
        return parseArray(boxed, formatter);
    }
    static String parseArray(float[] array, SQLFormatter formatter) {
        Float[] boxed = new Float[array.length];
        for (int i = 0; i < boxed.length; i++) {
            boxed[i] = array[i];
        }
        return parseArray(boxed, formatter);
    }
    static String parseArray(double[] array, SQLFormatter formatter) {
        return Arrays.stream(array).mapToObj(d -> formatter.format(d, formatter)).collect(Collectors.joining(","));
    }
    static String parseArray(char[] array, SQLFormatter formatter) {
        Character[] boxed = new Character[array.length];
        for (int i = 0; i < boxed.length; i++) {
            boxed[i] = array[i];
        }
        return parseArray(boxed, formatter);
    }
    Function<Class<?>, IllegalArgumentException> noFormattingCase = (c) -> new IllegalArgumentException(String.format("for Class %s does not exist a formatting case", c.getName()));
}
