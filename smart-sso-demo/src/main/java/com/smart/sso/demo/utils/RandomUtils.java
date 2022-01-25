package com.smart.sso.demo.utils;

import java.util.Random;

/**
 * @Author xhx
 * @Date 2022/1/25 16:56
 */
public class RandomUtils {
    public static Random r = new Random();

    public static int[] kruth(int n) {
        int[] res = new int[n];
        for(int i = 0; i < n; i++) {
            res[i] = i;
        }
        for(int i = 0; i < n; i++) {
            swap(res, i, r.nextInt() * (n - i) + i);
        }
        return res;
    }

    private static void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
