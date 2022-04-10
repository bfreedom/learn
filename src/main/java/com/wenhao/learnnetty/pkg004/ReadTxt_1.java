package com.wenhao.learnnetty.pkg004;

import com.wenhao.learnnetty.utils.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class ReadTxt_1 {

    public static void main(String[] args) {
        try {
            RandomAccessFile file = new RandomAccessFile("data.txt", "rw");
            FileChannel channel = file.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            do {
                int read = channel.read(byteBuffer);
                log.debug("读取到的字节**：{}", read);
                if (read == -1) {
                    log.debug("读取完成");
                    break;
                }
                byteBuffer.flip();
                ByteBufferUtil.debugAll(byteBuffer);
                while (byteBuffer.hasRemaining()) {
                    log.debug("读取到的数据：{}", (char) byteBuffer.get());
                }
                byteBuffer.clear();
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
