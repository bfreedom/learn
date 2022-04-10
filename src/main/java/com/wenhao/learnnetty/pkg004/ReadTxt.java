package com.wenhao.learnnetty.pkg004;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class ReadTxt {

    public static void main(String[] args) {
        try {
            RandomAccessFile accessFile = new RandomAccessFile("data.txt", "rw");
            FileChannel channel = accessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(10);
            do {
                int read = channel.read(buffer);
                log.debug("读取到的字节：{}", read);
                if (read == -1) {
                    break;
                }
                buffer.flip();
                while (buffer.hasRemaining()) {
                    log.debug("{}", (char) buffer.get());
                }
                buffer.clear();
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
