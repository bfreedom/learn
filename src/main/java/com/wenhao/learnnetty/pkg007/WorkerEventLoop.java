package com.wenhao.learnnetty.pkg007;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.wenhao.learnnetty.utils.ByteBufferUtil.debugAll;

@Slf4j
public class WorkerEventLoop implements Runnable {

    //工作线程选择器
    private Selector worker;

    //线程启动标识
    private volatile boolean start = false;

    //工作线程数量下标
    private int index;

    //任务队列
    private final ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();


    public WorkerEventLoop(int index) {
        this.index = index;
    }

    public void register(SocketChannel sc) throws IOException {
        if (!start) {
            worker = Selector.open();
            new Thread(this, "worker-" + index).start();
            start = true;
        }
        tasks.add(() -> {
            try {
                SelectionKey scKey = sc.register(worker, 0, null);
                scKey.interestOps(SelectionKey.OP_READ);
                worker.selectNow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        worker.wakeup();
    }

    @Override
    public void run() {
        while (true) {
            try {
                worker.select();
                Runnable task = tasks.poll();
                if (task != null) {
                    task.run();
                }
                Iterator<SelectionKey> iterator = worker.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(128);
                        try {
                            int read = sc.read(buffer);
                            if (read == -1) {
                                key.cancel();
                                sc.close();
                            } else {
                                buffer.flip();
                                log.debug("message {}", sc.getRemoteAddress());
                                debugAll(buffer);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
