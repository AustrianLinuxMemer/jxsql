package net.austrianlinuxmemer.nanosql;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@FunctionalInterface
public interface SQLFormatter {
    String parse(Object o, SQLFormatter childFormatter);
    static String parseString(CharSequence s) {
        return "'" + s.codePoints().mapToObj(Character::toString).map(c -> c.equals("'") ? "''" : c).collect(Collectors.joining()) + "'";
    }
    static String parseCollection(Collection<?> collection, SQLFormatter formatter) {
        StringBuilder builder = new StringBuilder();
        collection.forEach(c -> builder.append(formatter.parse(c, formatter)).append(","));
        builder.delete(builder.length()-2, builder.length()-1);
        return builder.toString();
    }
    static String parseArray(Object[] array, SQLFormatter formatter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1) {
                builder.append(formatter.parse(array[i], formatter));
            } else {
                builder.append(formatter.parse(array[i], formatter)).append(",");
            }
        }
        return builder.toString();
    }
    static String parseArray(long[] array, SQLFormatter formatter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1) {
                builder.append(formatter.parse(array[i], formatter));
            } else {
                builder.append(formatter.parse(array[i], formatter)).append(",");
            }
        }
        return builder.toString();
    }
    static String parseArray(int[] array, SQLFormatter formatter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1) {
                builder.append(formatter.parse(array[i], formatter));
            } else {
                builder.append(formatter.parse(array[i], formatter)).append(",");
            }
        }
        return builder.toString();
    }
    static String parseArray(short[] array, SQLFormatter formatter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1) {
                builder.append(formatter.parse(array[i], formatter));
            } else {
                builder.append(formatter.parse(array[i], formatter)).append(",");
            }
        }
        return builder.toString();
    }
    static String parseArray(byte[] array, SQLFormatter formatter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1) {
                builder.append(formatter.parse(array[i], formatter));
            } else {
                builder.append(formatter.parse(array[i], formatter)).append(",");
            }
        }
        return builder.toString();
    }
    static String parseArray(boolean[] array, SQLFormatter formatter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1) {
                builder.append(formatter.parse(array[i], formatter));
            } else {
                builder.append(formatter.parse(array[i], formatter)).append(",");
            }
        }
        return builder.toString();
    }
    static String parseArray(float[] array, SQLFormatter formatter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1) {
                builder.append(formatter.parse(array[i], formatter));
            } else {
                builder.append(formatter.parse(array[i], formatter)).append(",");
            }
        }
        return builder.toString();
    }
    static String parseArray(double[] array, SQLFormatter formatter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1) {
                builder.append(formatter.parse(array[i], formatter));
            } else {
                builder.append(formatter.parse(array[i], formatter)).append(",");
            }
        }
        return builder.toString();
    }
    static String parseArray(char[] array, SQLFormatter formatter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1) {
                builder.append(formatter.parse(array[i], formatter));
            } else {
                builder.append(formatter.parse(array[i], formatter)).append(",");
            }
        }
        return builder.toString();
    }
    Function<Class<?>, IllegalArgumentException> noFormattingCase = (c) -> new IllegalArgumentException(String.format("for Class %s does not exist a formatting case", c.getName()));
}
