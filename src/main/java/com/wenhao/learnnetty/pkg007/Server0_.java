package com.wenhao.learnnetty.pkg007;

import java.io.IOException;

public class Server0_ {
    public static void main(String[] args) throws IOException {
        new BossEvenLoop().register();
    }
}
