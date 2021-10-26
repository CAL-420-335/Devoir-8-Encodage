package org.INF335.Serialization;

import junit.framework.TestCase;
import org.INF335.ReadWriteBuffer;
import org.INF335.Serialization.Encodage;
import org.junit.Test;

public class EncodageTest extends TestCase {

    @Test
    public void testEncodeIntPositif(){
        for(int i = 0 ; i < Integer.MAX_VALUE - 7; i+=7) {
            assertTrue("" + i,i >= 0);
            assertEquals( "" + i, i, Encodage.decodeInt(Encodage.encodeInt(i)) );
        }
        assertEquals( Integer.MAX_VALUE, Encodage.decodeInt(Encodage.encodeInt(Integer.MAX_VALUE)));
    }

    @Test
    public void testEncodeIntNegatif(){
        for(int i = Integer.MIN_VALUE ; i < 0; i+=7) {
            assertTrue("" + i, i < 0);
            assertEquals( "" + i, i, Encodage.decodeInt(Encodage.encodeInt(i)) );
        }
    }

    @Test
    public void testEncodeLongPositif(){
        long[] vals = {
                0x0000000000000000l, 0x00000000000000FFl, 0x000000000000FAFAl, 0x00000000EFCDABFFl,
                0x00AB00AB00DE00DEl, 0x7E7E7E7E7E7E7E7El, 0x4F4F4F4F4F4F4F4Fl, 0x1111111111111111l,
                1, 5, 8, 16, 2356, 12361, 1236345, 768583, 234500, 4389134189l, 3425987198123l,
                Long.MAX_VALUE
        };

        for(long v : vals) {
            assertTrue("" + v, v >= 0);
            assertEquals("" + v, v, Encodage.decodeLong(Encodage.encodeLong(v)));
        }
    }

    @Test
    public void testEncodeLongNegatif(){
        long[] vals = {
                0x8000000000000000l, 0x80000000000000FFl, 0x800000000000FAFAl, 0x80000000EFCDABFFl,
                0x80AB00AB00DE00DEl, 0x8E7E7E7E7E7E7E7El, 0x8F4F4F4F4F4F4F4Fl, 0x8111111111111111l,
                -10l, -20, -100, -1000, -1337, -2349845, -2349581209l,
                Long.MIN_VALUE
        };

        for(long v : vals) {
            assertTrue(String.format("0x%016X", v), v < 0);
            assertEquals(String.format("0x%016X", v), v, Encodage.decodeLong(Encodage.encodeLong(v)));
        }
    }

    @Test
    public void testEncodeBoolean() {
        assertTrue(Encodage.decodeBoolean(Encodage.encodeBoolean(true)));
        assertFalse(Encodage.decodeBoolean(Encodage.encodeBoolean(false)));
    }

    @Test
    public void testEncodeFloat() {
        float[] vals = {
                0, 0.1f, 0.165f, 1.2354f, 12354.345f, 92354.3245089f, 1234980.2393f,
                -0f, -0.1f, -0.165f, -1.2354f, -12354.345f, -92354.3245089f, -1234980.2393f
        };

        for(float v : vals ) {
            assertEquals("" + v, v, Encodage.decodeFloat(Encodage.encodeFloat(v)));
        }
    }

    @Test
    public void testEncodeDouble() {
        double[] vals = {
                0, 0.1f, 0.165f, 1.2354f, 12354.345f, 92354.3245089f, 1234980.2393f,
                -0f, -0.1f, -0.165f, -1.2354f, -12354.345f, -92354.3245089f, -1234980.2393f
        };

        for(double v : vals ) {
            assertEquals("" + v, v, Encodage.decodeDouble(Encodage.encodeDouble(v)));
        }
    }

    @Test
    public void testReadInt(){

        int[] vals = {
                0, 1, 10, 100, -2, -42, -465, Integer.MAX_VALUE, Integer.MIN_VALUE
        };

        ReadWriteBuffer rw = new ReadWriteBuffer();

        for(int v : vals) rw.write(Encodage.encodeInt(v));

        for(int v : vals) {
            assertEquals("" + v, v, Encodage.readInt(rw));
        }
    }

    @Test
    public void testReadLong(){
        long[] vals = {
                0, 1, 10, 100, -2, -42, -465, Integer.MAX_VALUE, Integer.MIN_VALUE
        };

        ReadWriteBuffer rw = new ReadWriteBuffer();

        for(long v : vals) rw.write(Encodage.encodeLong(v));

        for(long v : vals) {
            assertEquals("" + v, v, Encodage.readLong(rw));
        }
    }

    @Test
    public void testReadString(){
        String[] vals = {
                "", "test", "la belle peruche de mononcle jean",
                "\0", "\t\t\t", "お前はもう死んでいる"
        };

        ReadWriteBuffer rw = new ReadWriteBuffer();

        for(String v : vals) rw.write(Encodage.encodeString(v));

        for(String v : vals) {
            assertEquals(v, v, Encodage.readString(rw));
        }
    }
}