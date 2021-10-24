package org.INF335;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ReadWriteBufferTest {

    ReadWriteBuffer buffer;

    public static final byte[] LEN_16_BAADBEEF = {
            (byte)0xBA, (byte)0xAD, (byte)0xBE, (byte)0xEF,
            (byte)0xBA, (byte)0xAD, (byte)0xBE, (byte)0xEF,
            (byte)0xBA, (byte)0xAD, (byte)0xBE, (byte)0xEF,
            (byte)0xBA, (byte)0xAD, (byte)0xBE, (byte)0xEF
    };

    public static final byte[] LEN_16_CAFECAFE = {
            (byte)0xCA, (byte)0xFE, (byte)0xCA, (byte)0xFE,
            (byte)0xCA, (byte)0xFE, (byte)0xCA, (byte)0xFE,
            (byte)0xCA, (byte)0xFE, (byte)0xCA, (byte)0xFE,
            (byte)0xCA, (byte)0xFE, (byte)0xCA, (byte)0xFE
    };


    @Before
    public void setup(){
         buffer = new ReadWriteBuffer();
    }

    @Test
    public void testZeroLenRead() {
        byte[] data = new byte[0];

        assertEquals(-1, buffer.read(data));
        buffer.write(LEN_16_BAADBEEF);
        assertEquals(-1, buffer.read(data));
    }

    @Test
    public void testEmptyRead() {
        byte[] data = new byte[16];

        assertEquals(-1, buffer.read(data));
    }

    @Test
    public void testOneDataRead() {
        byte[] data = new byte[16];

        buffer.write(LEN_16_BAADBEEF);
        assertEquals(16, buffer.read(data));
        assertArrayEquals(LEN_16_BAADBEEF, data);
        assertEquals(-1, buffer.read(data));
    }

    @Test
    public void testTwoDataRead() {
        byte[] data = new byte[16];

        buffer.write(LEN_16_BAADBEEF);
        buffer.write(LEN_16_CAFECAFE);

        assertEquals(16, buffer.read(data));
        assertArrayEquals(LEN_16_BAADBEEF, data);
        assertEquals(16, buffer.read(data));
        assertArrayEquals(LEN_16_CAFECAFE, data);
        assertEquals(-1, buffer.read(data));
    }

    @Test
    public void testBiggerRead() {
        byte[] data = new byte[32];

        buffer.write(LEN_16_BAADBEEF);
        buffer.write(LEN_16_CAFECAFE);

        byte[] target = new byte[32];
        for(int i = 0; i < LEN_16_BAADBEEF.length; i++) target[i] = LEN_16_BAADBEEF[i];
        for(int i = 0; i < LEN_16_CAFECAFE.length; i++) target[16 + i] = LEN_16_CAFECAFE[i];

        assertEquals(32, buffer.read(data));
        assertArrayEquals(target, data);
        assertEquals(-1, buffer.read(data));
    }

    @Test
    public void testWayBiggerRead() {
        byte[] data = new byte[64];

        buffer.write(LEN_16_BAADBEEF);
        buffer.write(LEN_16_CAFECAFE);

        byte[] target = new byte[64];
        for(int i = 0; i < LEN_16_BAADBEEF.length; i++) target[i] = LEN_16_BAADBEEF[i];
        for(int i = 0; i < LEN_16_CAFECAFE.length; i++) target[16 + i] = LEN_16_CAFECAFE[i];

        assertEquals(32, buffer.read(data));
        assertArrayEquals(target, data);
        assertEquals(-1, buffer.read(data));
    }
}