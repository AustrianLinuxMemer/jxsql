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
        case CharSequence c -> SQLFormatter.formatString(c);
        case Number n -> n.toString();
        case Boolean b -> b ? "TRUE" : "FALSE";
        case Collection<?> c -> SQLFormatter.formatCollection(c, f);
        case Object[] a -> SQLFormatter.formatArray(a, f);
        case long[] a -> SQLFormatter.formatArray(a, f);
        case int[] a -> SQLFormatter.formatArray(a, f);
        case short[] a -> SQLFormatter.formatArray(a, f);
        case byte[] a -> SQLFormatter.formatArray(a, f);
        case char[] a -> SQLFormatter.formatArray(a, f);
        case boolean[] a -> SQLFormatter.formatArray(a, f);
        case float[] a -> SQLFormatter.formatArray(a, f);
        case double[] a -> SQLFormatter.formatArray(a, f);
        default -> throw SQLFormatter.noFormattingCase.apply(o.getClass());
    };
    static String formatString(CharSequence s) {
        return "'" + s.codePoints().mapToObj(Character::toString).map(c -> c.equals("'") ? "''" : c).collect(Collectors.joining()) + "'";
    }
    static String formatCollection(Collection<?> collection, SQLFormatter formatter) {
        return collection.stream().map(o -> formatter.format(o, formatter)).collect(Collectors.joining(","));
    }
    static String formatArray(Object[] array, SQLFormatter formatter) {
        return Arrays.stream(array).map(o -> formatter.format(o, formatter)).collect(Collectors.joining(","));
    }
    static String formatArray(long[] array, SQLFormatter formatter) {
        return Arrays.stream(array).mapToObj(l -> formatter.format(l, formatter)).collect(Collectors.joining(","));
    }
    static String formatArray(int[] array, SQLFormatter formatter) {
        return Arrays.stream(array).mapToObj(i -> formatter.format(i, formatter)).collect(Collectors.joining(","));
    }
    static String formatArray(short[] array, SQLFormatter formatter) {
        Short[] boxed = new Short[array.length];
        for (int i = 0; i < boxed.length; i++) {
            boxed[i] = array[i];
        }
        return formatArray(boxed, formatter);
    }
    static String formatArray(byte[] array, SQLFormatter formatter) {
        Byte[] boxed = new Byte[array.length];
        for (int i = 0; i < boxed.length; i++) {
            boxed[i] = array[i];
        }
        return formatArray(boxed, formatter);
    }
    static String formatArray(boolean[] array, SQLFormatter formatter) {
        Boolean[] boxed = new Boolean[array.length];
        for (int i = 0; i < boxed.length; i++) {
            boxed[i] = array[i];
        }
        return formatArray(boxed, formatter);
    }
    static String formatArray(float[] array, SQLFormatter formatter) {
        Float[] boxed = new Float[array.length];
        for (int i = 0; i < boxed.length; i++) {
            boxed[i] = array[i];
        }
        return formatArray(boxed, formatter);
    }
    static String formatArray(double[] array, SQLFormatter formatter) {
        return Arrays.stream(array).mapToObj(d -> formatter.format(d, formatter)).collect(Collectors.joining(","));
    }
    static String formatArray(char[] array, SQLFormatter formatter) {
        Character[] boxed = new Character[array.length];
        for (int i = 0; i < boxed.length; i++) {
            boxed[i] = array[i];
        }
        return formatArray(boxed, formatter);
    }
    Function<Class<?>, IllegalArgumentException> noFormattingCase = (c) -> new IllegalArgumentException(String.format("for Class %s does not exist a formatting case", c.getName()));
}
