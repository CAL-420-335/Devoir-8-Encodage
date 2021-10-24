package org.INF335;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class Encodage {

    public static byte[] encodeInt(int v) {
        return new byte[] {
                (byte)((v >> 0) & 0xFF),
                (byte)((v >> 8) & 0xFF),
                (byte)((v >> 16) & 0xFF),
                (byte)((v >> 24) & 0xFF)
        };
    }

    public static byte[] encodeLong(long v) {
        return new byte[] {
                (byte)((v >> 0) & 0xFF),
                (byte)((v >> 8) & 0xFF),
                (byte)((v >> 16) & 0xFF),
                (byte)((v >> 24) & 0xFF),
                (byte)((v >> 32) & 0xFF),
                (byte)((v >> 40) & 0xFF),
                (byte)((v >> 48) & 0xFF),
                (byte)((v >> 56) & 0xFF)
        };
    }

    public static byte[] encodeBoolean(boolean v) {
        return encodeInt(v ? 1 : 0);
    }

    public static byte[] encodeFloat(float v) {
        return encodeInt( Float.floatToIntBits(v));
    }

    public static byte[] encodeDouble(double v) {
        return encodeLong( Double.doubleToLongBits(v));
    }

    public static byte[] encodeLocalDate(LocalDate v){
        return encodeLong(v.toEpochDay());
    }

    public static byte[] encodeString(String str){
        byte[] stringBytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[ stringBytes.length + 4];

        System.arraycopy(encodeInt(stringBytes.length), 0,
                result, 0, 4);
        System.arraycopy(stringBytes, 0,
                result, 4, stringBytes.length);
        return result;
    }

    public static int decodeInt(byte[] b) {
        int v = 0;
        for(int i = 0 ; i < Math.min(b.length, 4); i++) {
            v |= (b[i]) << (i * 8) & (0xFF << (i * 8));
        }

        return v;
    }

    public static long decodeLong(byte[] b) {
        long v = 0l;
        for(int i = 0 ; i < Math.min(b.length, 8); i++) {
            v |= ((long)b[i]) << (i * 8) & (0xFFl << (i * 8));
        }

        return v;
    }

    public static boolean decodeBoolean(byte[] b){
        return decodeInt(b) != 0;
    }

    public static float decodeFloat(byte[] b) {
        return Float.intBitsToFloat(decodeInt(b));
    }

    public static double decodeDouble(byte[] b) {
        return Double.longBitsToDouble(decodeLong(b));
    }

    public static LocalDate decodeLocalDate(byte[] b) {
        return LocalDate.ofEpochDay(decodeLong(b));
    }

    public static int readInt(ReadWriteBuffer rw) {
        byte[] buf = new byte[4];
        rw.read(buf);
        return decodeInt(buf);
    }

    public static long readLong(ReadWriteBuffer rw) {
        byte[] buf = new byte[8];
        rw.read(buf);
        return decodeLong(buf);
    }

    public static boolean readBoolean(ReadWriteBuffer rw) {
        byte[] buf = new byte[4];
        rw.read(buf);
        return decodeBoolean(buf);
    }

    public static float readFloat(ReadWriteBuffer rw) {
        byte[] buf = new byte[4];
        rw.read(buf);
        return decodeFloat(buf);
    }

    public static double readDouble(ReadWriteBuffer rw) {
        byte[] buf = new byte[8];
        rw.read(buf);
        return decodeDouble(buf);
    }

    public static LocalDate readLocalDate(ReadWriteBuffer rw) {
        byte[] buf = new byte[8];
        rw.read(buf);
        return decodeLocalDate(buf);
    }

    public static String readString(ReadWriteBuffer rw) {
        byte[] buf = new byte[4];
        rw.read(buf);
        int sz = decodeInt(buf);

        byte[] stringBuffer = new byte[sz];
        rw.read(stringBuffer);
        return new String(stringBuffer, StandardCharsets.UTF_8);
    }
}
