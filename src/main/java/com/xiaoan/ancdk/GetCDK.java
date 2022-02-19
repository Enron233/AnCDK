package com.xiaoan.ancdk;

import java.util.Random;

public class GetCDK {
    private static final Random random = new Random();
    public static String getCDK(){   //生成九位及以上CDK
        int a,b,c;
        a = random.nextInt(360000) + 2000;
        b = random.nextInt(300000) + 2000;
        c = random.nextInt(66000) + 2000;
        if (random.nextInt(10) % 2 == 0){
            return (Integer.toString(a, 16) + Integer.toString(b, 16) + Integer.toString(c, 16));
        }
        return (Integer.toString(a, 36) + Integer.toString(b, 36) + Integer.toString(c, 36));
    }
}
