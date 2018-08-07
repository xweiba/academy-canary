package com.ptteng.academy.util;

import java.util.Random;

/**
 * description:
 * author:Lin
 * Date:2018/8/4
 * Time:16:07
 */
public class RandomUtil {
        public static String getRandomCode(int number){
            String codeNum = "";
            int [] code = new int[3];
            Random random = new Random();
            for (int i = 0; i < number; i++) {
                int num = random.nextInt(10) + 48;
                int uppercase = random.nextInt(26) + 65;
                int lowercase = random.nextInt(26) + 97;
                code[0] = num;
                code[1] = uppercase;
                code[2] = lowercase;
                codeNum+=(char)code[random.nextInt(3)];
            }
            System.out.println(codeNum);

            return codeNum;
        }
    }
