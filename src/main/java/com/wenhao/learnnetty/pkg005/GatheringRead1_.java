package com.wenhao.learnnetty.pkg005;

import com.wenhao.learnnetty.utils.ByteBufferUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GatheringRead1_ {

    public static void main(String[] args) {
        try {
            RandomAccessFile file = new RandomAccessFile("dataGathering.txt", "rw");
            FileChannel channel = file.getChannel();
            channel.position(11);
            ByteBuffer a = ByteBuffer.allocate(4);
            ByteBuffer b = ByteBuffer.allocate(4);
            a.put(new byte[]{'f', 'o', 'u', 'r'});
            b.put(new byte[]{'f', 'i', 'v', 'e'});
            a.flip();
            b.flip();
            ByteBufferUtil.debugAll(a);
            ByteBufferUtil.debugAll(b);
            channel.write(new ByteBuffer[]{a, b});
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
