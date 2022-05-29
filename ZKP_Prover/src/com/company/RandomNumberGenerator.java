package com.company;

import java.util.Random;

public class RandomNumberGenerator {

    public static int generate(){
        int num = 0;
        Random rand = new Random();

        while (!isPrime(num)) {
            num = rand.nextInt(1000) + 1;
        }
        return num;
    }

    private static boolean isPrime(int inputNum)
    {
        if (inputNum <= 3 || inputNum % 2 == 0)
            return inputNum == 2 || inputNum == 3;
        int divisor = 3;
        while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0))
            divisor += 2;
        return inputNum % divisor != 0;
    }
}
