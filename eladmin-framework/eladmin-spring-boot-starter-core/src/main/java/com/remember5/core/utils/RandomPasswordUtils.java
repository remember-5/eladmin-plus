/**
 * Copyright [2022] [remember5]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.remember5.core.utils;

import cn.hutool.core.util.RandomUtil;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 随机密码生成
 * <a href="https://blog.csdn.net/qingquanyingyue/article/details/106336742">随机密码生成</a>
 *
 * @author wangjiahao
 * @date 2022/9/23 11:27
 */
public class RandomPasswordUtils {

    public static final String LOW_STR = "abcdefghijklmnopqrstuvwxyz";
    public static final String ALL_STR = LOW_STR + LOW_STR.toUpperCase();
    public static final String SPECIAL_STR = "~!@#$%^&*()_+/-=[]{};:'<>?.";
    public static final String NUM_STR = "0123456789";


    /**
     * 随机获取字符串字符
     *
     * @param str 字符串
     * @return 随机的char
     */
    private static char getRandomChar(String str) {
        SecureRandom random = new SecureRandom();
        return str.charAt(random.nextInt(str.length()));
    }

    /**
     * 随机获取小写字符
     *
     * @return 随机的小写字符
     */
    private static char getLowChar() {
        return getRandomChar(LOW_STR);
    }

    /**
     * 随机获取大写字符
     *
     * @return 随机的大写字符
     */
    private static char getUpperChar() {
        return Character.toUpperCase(getLowChar());
    }

    /**
     * 随机获取数字字符
     *
     * @return 随机的数字字符
     */
    private static char getNumChar() {
        return getRandomChar(NUM_STR);
    }

    /**
     * 随机获取特殊字符
     *
     * @return 随机的特殊字符
     */
    private static char getSpecialChar() {
        return getRandomChar(SPECIAL_STR);
    }

    /**
     * 根据传入的数字，随机调用生成字符的函数
     *
     * @param funNum 传入的数字
     * @return 随机的字符
     */
    private static char getRandomChar(int funNum) {
        return switch (funNum) {
            case 0 -> getLowChar();
            case 1 -> getUpperChar();
            case 2 -> getNumChar();
            default -> getSpecialChar();
        };
    }

    /**
     * 生成随机密码
     *
     * @param num 密码长度
     * @return 随机密码
     */
    private static String getRandomPwd(int num) {
        if (num > 20 || num < 8) {
            System.err.println("长度不满足要求");
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


    /**
     * 生成随机密码
     *
     * @param strLength     密码长度
     * @param numLength     密码数字长度
     * @param specialLength 特殊字符长度
     * @return 随机密码
     */
    public static String randomPassword(int strLength, int numLength, int specialLength) {
        List<Object> resultList = new ArrayList<>();

        char[] strChars = RandomUtil.randomString(ALL_STR, strLength).toCharArray();
        for (char b : strChars) {
            resultList.add(b);
        }
        for (int i = 0; i < numLength; i++) {
            resultList.add(RandomUtil.randomNumber());
        }
        char[] specialChars = RandomUtil.randomString(SPECIAL_STR, specialLength).toCharArray();
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

    /**
     * 生成随机密码
     *
     * @param strLength     密码长度
     * @param numLength     密码数字长度
     * @param specialStr    特殊字符
     * @param specialLength 特殊字符长度
     * @return 随机密码
     */
    public static String randomPassword(int strLength, int numLength, String specialStr, int specialLength) {
        List<Object> resultList = new ArrayList<>();


        char[] strChars = RandomUtil.randomString(ALL_STR, strLength).toCharArray();
        for (char b : strChars) {
            resultList.add(b);
        }
        for (int i = 0; i < numLength; i++) {
            resultList.add(RandomUtil.randomNumber());
        }
        char[] specialChars = RandomUtil.randomString(specialStr, specialLength).toCharArray();
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

}
