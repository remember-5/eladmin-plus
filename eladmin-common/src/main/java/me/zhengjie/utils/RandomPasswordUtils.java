package me.zhengjie.utils;
import cn.hutool.core.util.RandomUtil;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 随机密码生成
 *  <a href="https://blog.csdn.net/qingquanyingyue/article/details/106336742">随机密码生成</a>
 * @author wangjiahao
 * @date 2022/9/23 11:27
 */
public class RandomPasswordUtils {

    public static final String LOW_STR = "abcdefghijklmnopqrstuvwxyz";
    public static final String ALL_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String SPECIAL_STR = "~!@#$%^&*()_+/-=[]{};:'<>?.";
    public static final String NUM_STR = "0123456789";

    // 随机获取字符串字符
    private static char getRandomChar(String str) {
        SecureRandom random = new SecureRandom();
        return str.charAt(random.nextInt(str.length()));
    }

    // 随机获取小写字符
    private static char getLowChar() {
        return getRandomChar(LOW_STR);
    }

    // 随机获取大写字符
    private static char getUpperChar() {
        return Character.toUpperCase(getLowChar());
    }

    // 随机获取数字字符
    private static char getNumChar() {
        return getRandomChar(NUM_STR);
    }

    // 随机获取特殊字符
    private static char getSpecialChar() {
        return getRandomChar(SPECIAL_STR);
    }

    private static char getRandomChar(int funNum) {
        switch (funNum) {
            case 0:
                return getLowChar();
            case 1:
                return getUpperChar();
            case 2:
                return getNumChar();
            default:
                return getSpecialChar();
        }
    }

    private static String getRandomPwd(int num) {
        if (num > 20 || num < 8) {
            System.out.println("长度不满足要求");
            return "";
        }
        // 先把 4 种字符每种来一个放进 list
        List<Character> list = new ArrayList<>(num);
        list.add(getLowChar());
        list.add(getUpperChar());
        list.add(getNumChar());
        list.add(getSpecialChar());

        // 因为已经把 4 种字符放进list了，所以 i 取值从 4开始
        // 产生随机数用于随机调用生成字符的函数
        for (int i = 4; i < num; i++) {
            SecureRandom random = new SecureRandom();
            int funNum = random.nextInt(4);
            list.add(getRandomChar(funNum));
        }

        Collections.shuffle(list);   // 打乱排序
        StringBuilder stringBuilder = new StringBuilder(list.size());
        for (Character c : list) {
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }



    public static String randomPassword(int strLength, int numLength, int specialLength) {
        List<Object> resultList = new ArrayList<>();


        char[] strChars = RandomUtil.randomString(ALL_STR, strLength).toCharArray();
        for (char b : strChars) {
            resultList.add(b);
        }
        for (int i = 0; i < numLength; i++) {
            resultList.add(RandomUtil.randomNumber());
        }
        char[] specialChars  = RandomUtil.randomString(SPECIAL_STR, specialLength).toCharArray();
        for (char b : specialChars) {
            resultList.add(b);
        }
        Collections.shuffle(resultList);
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : resultList) {
            stringBuilder.append(o.toString());
        }
        return stringBuilder.toString();
    }


    public static String randomPassword(int strLength, int numLength, String specialStr, int specialLength) {
        List<Object> resultList = new ArrayList<>();


        char[] strChars = RandomUtil.randomString(ALL_STR, strLength).toCharArray();
        for (char b : strChars) {
            resultList.add(b);
        }
        for (int i = 0; i < numLength; i++) {
            resultList.add(RandomUtil.randomNumber());
        }
        char[] specialChars  = RandomUtil.randomString(specialStr, specialLength).toCharArray();
        for (char b : specialChars) {
            resultList.add(b);
        }
        Collections.shuffle(resultList);
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : resultList) {
            stringBuilder.append(o.toString());
        }
        return stringBuilder.toString();
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int num = 10;
//            System.out.println(getRandomPwd(num));
            System.out.println(randomPassword(8,2,"@",1));
        }
    }
}
