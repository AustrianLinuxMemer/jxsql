package net.austrianlinuxmemer.nanosql;

import java.util.function.Function;

public record Row(String... data) {
    public int getInt(int col) {
        return Integer.parseInt(data[col]);
    }
    public long getLong(int col) {
        return Long.parseLong(data[col]);
    }
    public short getShort(int col) {
        return Short.parseShort(data[col]);
    }
    public byte getByte(int col) {
        return Byte.parseByte(data[col]);
    }
    public boolean getBoolean(int col) {
        return Boolean.parseBoolean(data[col]);
    }
    public float getFloat(int col) {
        return Float.parseFloat(data[col]);
    }
    public double getDouble(int col) {
        return Double.parseDouble(data[col]);
    }
    public String getString(int col) {
        return data[col];
    }
    public <T> T getObject(int col, FieldParser<T> parser) {
        return parser.parse(data[col]);
    }
    public <T> T parseObject(ObjectParser<T> parser) {
        return parser.parse(data);
    }
    public int size() {
        return data.length;
    }
}
