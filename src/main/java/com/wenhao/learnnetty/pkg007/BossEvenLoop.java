package com.wenhao.learnnetty.pkg007;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class BossEvenLoop implements Runnable {

    private Selector boss;

    private WorkerEventLoop[] workers;

    private volatile boolean start = false;

    private AtomicInteger index = new AtomicInteger();

    public void register() throws IOException {
        if (!start) {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(8765));
            server.configureBlocking(false);
            boss = Selector.open();
            SelectionKey sscKey = server.register(boss, 0, null);
            sscKey.interestOps(SelectionKey.OP_ACCEPT);
            workers = initWorkerEventLoop();
            new Thread(this, "boss").start();
            log.debug("boss start");
            start = true;
        }
    }

    private WorkerEventLoop[] initWorkerEventLoop() {
        WorkerEventLoop[] workerEventLoops = new WorkerEventLoop[2];
        for (int i = 0; i < workerEventLoops.length; i++) {
            workerEventLoops[i] = new WorkerEventLoop(i);
        }
        return workerEventLoops;
    }

    @Override
    public void run() {
        while (true) {
            try {
                boss.select();
                Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        ServerSocketChannel c = (ServerSocketChannel) key.channel();
                        SocketChannel sc = c.accept();
                        sc.configureBlocking(false);
                        log.debug("connected {}", sc.getRemoteAddress());
                        workers[index.getAndIncrement() % workers.length].register(sc);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
