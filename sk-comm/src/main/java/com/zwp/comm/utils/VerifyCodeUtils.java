package com.zwp.comm.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @program: seckiller
 * @description: 验证图片生成工具
 * @author: zwp-flyz
 * @create: 2019-07-01 20:01
 * @version: v1.0
 **/

public class VerifyCodeUtils {

    private static int WIDTH = 70;//宽度
    private static int HEIGHT = 20;//高度
    private static int FONT_HIGH=16;//字体高度
    private static  ThreadLocalRandom random = ThreadLocalRandom.current();

    /**
     * 由给定地验证码生成验证图片
     * @param verifyCode
     * @return
     */
    public static BufferedImage generateVerifyPicture(String verifyCode){
        int codeLen = verifyCode.length();
        //生成图形对象
        BufferedImage image =
                new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        Graphics gd = image.getGraphics();
        //白底填充
        gd.setColor(Color.BLACK);
        gd.fillRect(0,0,WIDTH,HEIGHT);
        gd.setFont(new Font("Fixedsys", Font.BOLD,FONT_HIGH));


        gd.setColor(Color.WHITE);
        gd.drawRect(0,0,WIDTH-1,HEIGHT-1);

        int g=random.nextInt(255),r=random.nextInt(255),
                b=random.nextInt(255);
        gd.setColor(Color.WHITE);
        gd.drawString(verifyCode,15,15);

        return image;
    }

}
