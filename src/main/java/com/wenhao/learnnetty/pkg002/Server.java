package com.wenhao.learnnetty.pkg002;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = null;
        try {
            Selector selector = Selector.open();
            server = ServerSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(8888);
            server.bind(inetSocketAddress);
            server.configureBlocking(false);
            SelectionKey serverKey = server.register(selector, 0, null);
            serverKey.interestOps(SelectionKey.OP_ACCEPT);
            ByteBuffer byteBuffer = ByteBuffer.allocate(20);
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey sk = iterator.next();
                    iterator.remove();
                    if (sk.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) sk.channel();
                        SocketChannel sc = channel.accept();
                        sc.configureBlocking(false);
                        SelectionKey register = sc.register(selector, 0, null);
                        register.interestOps(SelectionKey.OP_READ);
                        System.out.println(sc);
                    } else if (sk.isReadable()) {
                        try {
                            SocketChannel channel = (SocketChannel) sk.channel();
                            channel.read(byteBuffer);
                            if (byteBuffer.hasRemaining()) {
                                byteBuffer.flip();
                                byte b = byteBuffer.get();
                                System.out.println(new String(new byte[]{b}));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            sk.cancel();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.close();
        }
    }
}
