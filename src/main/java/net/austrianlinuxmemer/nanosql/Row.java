package net.austrianlinuxmemer.nanosql;

import java.util.function.Function;

public record Row(Object... data) {
    public int getInt(int col) {
        return (Integer) data[col];
    }
    public long getLong(int col) {
        return (Long) data[col];
    }
    public short getShort(int col) {
        return (Short) data[col];
    }
    public byte getByte(int col) {
        return (Byte) data[col];
    }
    public boolean getBoolean(int col) {
        return (Boolean) data[col];
    }
    public float getFloat(int col) {
        return (Float) data[col];
    }
    public double getDouble(int col) {
        return (Double) data[col];
    }
    public String getString(int col) {
        return (String) data[col];
    }
    public <T> T getObject(int col, FieldParser<T> converter) {
        return converter.parse(data[col]);
    }
    public <T> T parseObject(Function<Object[], T> converter) {
        return converter.apply(data);
    }
    public int size() {
        return data.length;
    }
}
