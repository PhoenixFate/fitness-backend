package com.phoenix.fitness.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 获取首字母 汉字全拼音
 * @author: admin
 * @date: Created in 2019/8/30 15:19
 * @version:
 * @modified By:
 */
public class ChinesePinyinUtil {
    /**
     * 得到 汉字的全拼
     *
     * @param src 中文字符串
     * @return
     */
    public static String getPingYin(String src) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder sb = new StringBuilder();
        char[] srcArray = src.toCharArray();
        try {
            for (int i = 0; i < srcArray.length; i++) {
                // 判断是否为汉字字符
                if (Character.toString(srcArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] targetArray = PinyinHelper.toHanyuPinyinStringArray(srcArray[i], format);
                    sb.append(targetArray[0]);
                } else {
                    sb.append(Character.toString(srcArray[i]));
                }
            }
            return sb.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 得到中文首字母,例如"专科"得到zk返回
     *
     * @param str 中文字符串
     * @return
     */
    public static String getPinYinHeadChar(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char word = str.charAt(i);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                sb.append(pinyinArray[0].charAt(0));
            } else {
                sb.append(word);
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 将字符串转移为ASCII码
     *
     * @param cnStr 中文字符串
     * @return
     */
    public static String getCnASCII(String cnStr) {
        StringBuilder sb = new StringBuilder();
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            sb.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return sb.toString();
    }

    /**
     * 是否是中文
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 是否是英文
     *
     * @return
     */
    public static boolean isEnglish(String charaString) {
        return charaString.matches("^[a-zA-Z]*");
    }

    public static boolean isChinese(String str){
        String regEx = "[\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }


    public static void main(String[] args) throws ParseException {
        String cnStr = "螺丝纹钢材类";
        System.out.println(cnStr);
        System.out.println(getPingYin(cnStr));
        System.out.println(getPinYinHeadChar(cnStr));
        System.out.println(getCnASCII(cnStr));

        String enStr = "#head";
        System.out.println(enStr);
        System.out.println(getPingYin(enStr));
        System.out.println(getPinYinHeadChar(enStr));
        System.out.println(getCnASCII(enStr));


        System.out.println(isChinese('员'));
        System.out.println(isChinese('s'));
        System.out.println(isEnglish("程序员之家"));
        System.out.println(isEnglish("robert我"));
        System.out.println(isChinese("程序员论坛"));

        System.out.println(isChinese("#"));
        System.out.println(isEnglish("#"));
    }
}

