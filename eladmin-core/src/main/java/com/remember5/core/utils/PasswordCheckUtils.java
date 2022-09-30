package com.remember5.core.utils;

import com.remember5.core.properties.PasswordCheckProperties;

import java.util.Arrays;
import java.util.List;

/**
 * 密码校验类
 * see <a href="https://www.jianshu.com/p/ab041d3763e2">弱密码检测工具</a>
 *
 * @author wangjiahao
 * @date 2022/8/12 15:29
 */
public class PasswordCheckUtils {

    /**
     * 检测密码中字符长度
     *
     * @param password 密码
     * @return 符合长度要求 返回
     */
    public static boolean checkPasswordLength(String password) {
        boolean flag = false;

        // 如未设置最大长度，仅判断最小长度即可
        if ("".equals(PasswordCheckProperties.MAX_LENGTH)) {
            if (password.length() >= Integer.parseInt(PasswordCheckProperties.MIN_LENGTH)) {
                flag = true;
            }
        } else {
            if (password.length() >= Integer.parseInt(PasswordCheckProperties.MIN_LENGTH)
                    && password.length() <= Integer.parseInt(PasswordCheckProperties.MAX_LENGTH)) {
                flag = true;
            }
        }

        return flag;
    }

    /**
     * 检查密码中是否包含数字
     *
     * @param password 密码
     * @return 包含数字 返回true
     */
    public static boolean checkContainDigit(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int numCount = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isDigit(chPass[i])) {
                numCount++;
            }
        }
        if (numCount >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * 检查密码中是否包含字母(不区分大小写)
     *
     * @param password 密码
     * @return 包含字母 返回true
     */
    public static boolean checkContainCase(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int charCount = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isLetter(chPass[i])) {
                charCount++;
            }
        }

        if (charCount >= 1) {
            flag = true;
        }

        return flag;
    }

    /**
     * 检查密码中是否包含小写字母
     *
     * @param password 密码
     * @return 包含小写字母 返回true
     */
    public static boolean checkContainLowerCase(String password) {
        boolean flag = false;
        char[] chPass = password.toCharArray();
        int charCount = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isLowerCase(chPass[i])) {
                charCount++;
            }
        }

        if (charCount >= 1) {
            flag = true;
        }

        return flag;
    }

    /**
     * 检查密码中是否包含大写字母
     *
     * @param password 密码
     * @return 包含大写字母 返回true
     */
    public static boolean checkContainUpperCase(String password) {
        boolean flag = false;
        char[] chPass = password.toCharArray();
        int charCount = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isUpperCase(chPass[i])) {
                charCount++;
            }
        }

        if (charCount >= 1) {
            flag = true;
        }

        return flag;
    }

    /**
     * 检查密码中是否包含特殊字符
     *
     * @param password 密码
     * @return 包含特殊字符 返回true
     */
    public static boolean checkContainSpecialChar(String password) {
        boolean flag = false;
        char[] chPass = password.toCharArray();
        int specialCount = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (PasswordCheckProperties.CHECK_CONTAIN_SPECIAL_CHAR.indexOf(chPass[i]) != -1) {
                specialCount++;
            }
        }

        if (specialCount >= 1) {
            flag = true;
        }

        return flag;
    }

    /**
     * 键盘规则匹配器 横向连续检测
     *
     * @param password 密码
     * @return 含有横向连续字符串 返回true
     */
    public static boolean checkLateralKeyboardSite(String password) {

        String t_password = new String(password);
        // 将字符串内所有字符转为小写
        t_password = t_password.toLowerCase();
        int n = t_password.length();

        /**
         * 键盘横向规则检测
         */
        boolean flag = false;
        int arrLen = PasswordCheckProperties.KEYBOARD_HORIZONTAL_ARR.length;
        int limitNum = Integer.parseInt(PasswordCheckProperties.LIMIT_HORIZONTAL_NUM_KEY);

        for (int i = 0; i + limitNum <= n; i++) {
            String str = t_password.substring(i, i + limitNum);
            String distinguishStr = password.substring(i, i + limitNum);

            for (int j = 0; j < arrLen; j++) {
                String configStr = PasswordCheckProperties.KEYBOARD_HORIZONTAL_ARR[j];
                String revOrderStr = new StringBuffer(PasswordCheckProperties.KEYBOARD_HORIZONTAL_ARR[j]).reverse()
                        .toString();

                // 检查包含字母(区分大小写)
                if ("enable".equals(PasswordCheckProperties.CHECK_DISTINGGUISH_CASE)) {
                    // 考虑 大写键盘匹配的情况
                    String upperStr = PasswordCheckProperties.KEYBOARD_HORIZONTAL_ARR[j].toUpperCase();
                    if ((configStr.indexOf(distinguishStr) != -1) || (upperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                    // 考虑逆序输入情况下 连续输入
                    String revUpperStr = new StringBuffer(upperStr).reverse().toString();
                    if ((revOrderStr.indexOf(distinguishStr) != -1) || (revUpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                } else {
                    if (configStr.indexOf(str) != -1) {
                        flag = true;
                        return flag;
                    }
                    // 考虑逆序输入情况下 连续输入
                    if (revOrderStr.indexOf(str) != -1) {
                        flag = true;
                        return flag;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 键盘规则匹配器 斜向规则检测
     *
     * @param password
     * @return 含有斜向连续字符串 返回true
     */
    public static boolean checkKeyboardSlantSite(String password) {
        String tPassword = new String(password);
        tPassword = tPassword.toLowerCase();
        int n = tPassword.length();
        /**
         * 键盘斜线方向规则检测
         */
        boolean flag = false;
        int arrLen = PasswordCheckProperties.KEYBOARD_SLOPE_ARR.length;
        int limit_num = Integer.parseInt(PasswordCheckProperties.LIMIT_SLOPE_NUM_KEY);

        for (int i = 0; i + limit_num <= n; i++) {
            String str = tPassword.substring(i, i + limit_num);
            String distinguishStr = password.substring(i, i + limit_num);
            for (int j = 0; j < arrLen; j++) {
                String configStr = PasswordCheckProperties.KEYBOARD_SLOPE_ARR[j];
                String revOrderStr = new StringBuffer(PasswordCheckProperties.KEYBOARD_SLOPE_ARR[j]).reverse().toString();
                // 检测包含字母(区分大小写)
                if ("enable".equals(PasswordCheckProperties.CHECK_DISTINGGUISH_CASE)) {

                    // 考虑 大写键盘匹配的情况
                    String UpperStr = PasswordCheckProperties.KEYBOARD_SLOPE_ARR[j].toUpperCase();
                    if ((configStr.indexOf(distinguishStr) != -1) || (UpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                    // 考虑逆序输入情况下 连续输入
                    String revUpperStr = new StringBuffer(UpperStr).reverse().toString();
                    if ((revOrderStr.indexOf(distinguishStr) != -1) || (revUpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                } else {
                    if (configStr.indexOf(str) != -1) {
                        flag = true;
                        return flag;
                    }
                    // 考虑逆序输入情况下 连续输入
                    if (revOrderStr.indexOf(str) != -1) {
                        flag = true;
                        return flag;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 评估a-z,z-a这样的连续字符
     *
     * @param password 密码
     * @return 含有a-z,z-a连续字符串 返回true
     */
    public static boolean checkSequentialChars(String password) {
        String tPassword = new String(password);
        boolean flag = false;
        int limitNum = Integer.parseInt(PasswordCheckProperties.LIMIT_LOGIC_NUM_CHAR);
        int normalCount = 0;
        int reversedCount = 0;

        // 检测包含字母(区分大小写)
        if ("enable".equals(PasswordCheckProperties.CHECK_DISTINGGUISH_CASE)) {

        } else {
            tPassword = tPassword.toLowerCase();
        }
        int n = tPassword.length();
        char[] pwdCharArr = tPassword.toCharArray();

        for (int i = 0; i + limitNum <= n; i++) {
            normalCount = 0;
            reversedCount = 0;
            for (int j = 0; j < limitNum - 1; j++) {
                if (pwdCharArr[i + j + 1] - pwdCharArr[i + j] == 1) {
                    normalCount++;
                    if (normalCount == limitNum - 1) {
                        return true;
                    }
                }

                if (pwdCharArr[i + j] - pwdCharArr[i + j + 1] == 1) {
                    reversedCount++;
                    if (reversedCount == limitNum - 1) {
                        return true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 评估aaaa, 1111这样的相同连续字符
     *
     * @param password 密码
     * @return 含有aaaa, 1111等连续字符串 返回true
     */
    public static boolean checkSequentialSameChars(String password) {
        String tPassword = new String(password);
        int n = tPassword.length();
        char[] pwdCharArr = tPassword.toCharArray();
        boolean flag = false;
        int limitNum = Integer.parseInt(PasswordCheckProperties.LIMIT_NUM_SAME_CHAR);
        int count = 0;
        for (int i = 0; i + limitNum <= n; i++) {
            count = 0;
            for (int j = 0; j < limitNum - 1; j++) {
                if (pwdCharArr[i + j] == pwdCharArr[i + j + 1]) {
                    count++;
                    if (count == limitNum - 1) {
                        return true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 检测常用词库
     *
     * @param password 密码
     * @return 含有常见词库 返回true
     */
    public static boolean checkSimpleWord(String password) {
        List<String> simpleWords = Arrays.asList(PasswordCheckProperties.SIMPLE_WORDS);
        return simpleWords.contains(password.toLowerCase());
    }

    /**
     * 评估密码中包含的字符类型是否符合要求
     *
     * @param password 密码
     * @return 符合要求 返回true
     */
    public static boolean evalPassword(String password) {
        if (password == null || "".equals(password)) {
            return false;
        }
        boolean flag = false;

        /**
         * 检测长度
         */
        if ("enable".equals(PasswordCheckProperties.CHECK_PASSWORD_LENGTH)) {
            flag = checkPasswordLength(password);
            if (!flag) {
                return false;
            }
        }

        /**
         * 检测包含数字
         */
        if ("enable".equals(PasswordCheckProperties.CHECK_CONTAIN_DIGIT)) {
            flag = checkContainDigit(password);
            if (!flag) {
                return false;
            }
        }

        /**
         * 检测包含字母
         */
        if ("enable".equals(PasswordCheckProperties.CHECK_CONTAIN_CASE)) {
            flag = checkContainCase(password);
            if (!flag) {
                return false;
            }
        }

        /**
         * 检测字母区分大小写
         */
        if ("enable".equals(PasswordCheckProperties.CHECK_DISTINGGUISH_CASE)) {
            // 检测包含小写字母
            if ("enable".equals(PasswordCheckProperties.CHECK_LOWER_CASE)) {
                flag = checkContainLowerCase(password);
                if (!flag) {
                    return false;
                }
            }

            // 检测包含大写字母
            if ("enable".equals(PasswordCheckProperties.CHECK_UPPER_CASE)) {
                flag = checkContainUpperCase(password);
                if (!flag) {
                    return false;
                }
            }
        }

        /**
         * 检测包含特殊符号
         */
        if ("enable".equals(PasswordCheckProperties.CHECK_CONTAIN_SPECIAL_CHAR)) {
            flag = checkContainSpecialChar(password);
            if (!flag) {
                return false;
            }
        }

        /**
         * 检测键盘横向连续
         */
        if ("enable".equals(PasswordCheckProperties.CHECK_HORIZONTAL_KEY_SEQUENTIAL)) {
            flag = checkLateralKeyboardSite(password);
            if (flag) {
                return false;
            }
        }

        /**
         * 检测键盘斜向连续
         */
        if ("enable".equals(PasswordCheckProperties.CHECK_SLOPE_KEY_SEQUENTIAL)) {
            flag = checkKeyboardSlantSite(password);
            if (flag) {
                return false;
            }
        }

        /**
         * 检测逻辑位置连续
         */
        if ("enable".equals(PasswordCheckProperties.CHECK_LOGIC_SEQUENTIAL)) {
            flag = checkSequentialChars(password);
            if (flag) {
                return false;
            }
        }

        /**
         * 检测相邻字符是否相同
         */
        if ("enable".equals(PasswordCheckProperties.CHECK_SEQUENTIAL_CHAR_SAME)) {
            flag = checkSequentialSameChars(password);
            if (flag) {
                return false;
            }
        }

        /**
         * 检测常用词库
         */
        if ("enable".equals(PasswordCheckProperties.CHECK_SIMPLE_WORD)) {
            flag = checkSimpleWord(password);
            if (flag) {
                return false;
            }
        }

        return true;
    }
}
