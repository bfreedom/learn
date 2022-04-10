package com.wenhao.learnnetty.pkg004;

import com.wenhao.learnnetty.utils.ByteBufferUtil;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Buf01_ {

    public static void main(String[] args) {
        ByteBuffer _01 = StandardCharsets.UTF_8.encode("你好");
        ByteBuffer _02 = Charset.forName("utf-8").encode("你好");
        ByteBufferUtil.debugAll(_01);
        ByteBufferUtil.debugAll(_02);
    }
}
