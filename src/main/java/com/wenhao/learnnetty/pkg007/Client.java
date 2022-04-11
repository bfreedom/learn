package com.wenhao.learnnetty.pkg007;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

@Slf4j
public class Client {

    public static void main(String[] args) {
        try {
            SocketChannel sc = SocketChannel.open();
            sc.connect(new InetSocketAddress(8765));
            sc.configureBlocking(false);
            sc.write(Charset.defaultCharset().encode("WENHAO"));
            System.in.read();
            /*Selector selector = Selector.open();
            SelectionKey register = sc.register(selector, 0, null);
            register.interestOps(SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isConnectable()) {
                        log.debug(sc.finishConnect() + "已连接");
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.configureBlocking(false);
                        channel.write(Charset.defaultCharset().encode("wenhao"));
                        log.debug("client");
                    }
                }
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
