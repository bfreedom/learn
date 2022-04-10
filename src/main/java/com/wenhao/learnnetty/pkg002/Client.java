package com.wenhao.learnnetty.pkg002;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {

    public static void main(String[] args) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8888);
            socketChannel.connect(inetSocketAddress);
            ByteBuffer byteBuffer = ByteBuffer.allocate(20);
            //byteBuffer.clear();
            //byteBuffer.put()
            socketChannel.write(Charset.defaultCharset().encode("wwww"));
            while (true) {

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /*try {
                assert socketChannel != null;
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }
}
