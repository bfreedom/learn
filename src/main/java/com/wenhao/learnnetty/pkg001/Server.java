package com.wenhao.learnnetty.pkg001;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(8888);
            server.bind(inetSocketAddress);
            ByteBuffer byteBuffer = ByteBuffer.allocate(20);
            while (true) {
                SocketChannel socketChannel = server.accept();
                int read = socketChannel.read(byteBuffer);
                while (read != -1){
                    if (byteBuffer.hasRemaining()) {
                        byteBuffer.flip();
                        byte b = byteBuffer.get();
                        System.out.println(new String(new byte[]{b}));
                    }
                }
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
