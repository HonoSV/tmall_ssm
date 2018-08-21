package com.how2java.tmall.test;

import java.util.Scanner;

public class Number {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int r = 0;
        while (true){
            n = n/5;
            if(0==n)
                break;
            r += n;
        }
        System.out.println(r);
    }
}
