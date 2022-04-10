package com.wenhao.learnnetty.pkg005;

import com.wenhao.learnnetty.utils.ByteBufferUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ScatteringRead0_ {

    public static void main(String[] args) {
        try {
            RandomAccessFile file = new RandomAccessFile("dataScattering.txt", "rw");
            FileChannel channel = file.getChannel();
            ByteBuffer a = ByteBuffer.allocate(3);
            ByteBuffer b = ByteBuffer.allocate(3);
            ByteBuffer c = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{a, b, c});
            a.flip();
            b.flip();
            c.flip();
            ByteBufferUtil.debugAll(a);
            ByteBufferUtil.debugAll(b);
            ByteBufferUtil.debugAll(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
