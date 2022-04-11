package com.wenhao.learnnetty.pkg006;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Split0_ {
    public static void main(String[] args) {
        //Hello,world\nI'm zhangsan\nHo
        //w are you?\n
        ByteBuffer buffer = ByteBuffer.allocate(40);
        buffer.put("Hello,world\\nI'm zhangsan\\nHo".getBytes(StandardCharsets.UTF_8));
        buffer.flip();

    }
}
