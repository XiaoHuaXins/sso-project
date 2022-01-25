package com.smart.sso.demo.utils;

import java.util.Arrays;

/**
 * @Author xhx
 * @Date 2022/1/25 11:34
 */
public class LocalStringUtils {

    public static String[] splitString(String s) {
        String[] split = s.split(",|，|\\s+");
        return split;
    }

    public static int kmp(String prefer, String[] classify) {
        int idx = -1;
        double rate = 0;
        for(int i = 0; i < classify.length; i++) {
            int v = doKMP(" " + prefer, " " + classify);
            double curRate = (v - 1) * 100 / classify[i].length();
            if(curRate > rate) {
                idx = i;
                rate = curRate;
            }
        }
        return rate > 50 ? -1:idx;
    }

    public static int doKMP(String s, String p) {
        int n = s.length(), m = p.length();
        char[] sc = s.toCharArray();
        char[] pc = p.toCharArray();
        //预处理最长公共前后缀
        int[] next = new int[m + 1];
        for(int i = 2, j = 0; i <= m; i++) {
            //回溯
            while(j > 0 && pc[i] != pc[j + 1]) j = next[j];
            if(pc[i] == pc[j + 1])j++;
            next[i] = j;
        }
        int maxlen = 0;
        for(int i = 1, j = 0; i <= n; i++) {
            while(j > 0 && sc[i] != pc[j + 1])j = next[j];
            if(sc[i] == pc[j + 1])j++;
            if(j == m)return i - m;
            maxlen = Math.max(j, maxlen);
        }
        return  maxlen;
    }
}
