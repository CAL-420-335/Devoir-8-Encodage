package org.INF335;

import java.util.ArrayList;
import java.util.List;

public class ReadWriteBuffer {

    private int readCursor = 0;
    private List<Byte> data = new ArrayList<>();

    public int write(byte[] writeData){
        for(byte b : writeData) {
            data.add(b);
        }

        return writeData.length;
    }

    public int read(byte[] buffer) {
        int read = 0;

        while(read < buffer.length && readCursor + read < data.size()) {
            buffer[read] = data.get(readCursor + read);
            read++;
        }

        readCursor += read;
        return read == 0 ? -1 : read;
    }
}
