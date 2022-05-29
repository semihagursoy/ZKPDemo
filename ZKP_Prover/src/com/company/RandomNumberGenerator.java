package com.company;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomNumberGenerator {

    public static BigInteger generate(){
        SecureRandom secureRandom = new SecureRandom();
        return BigInteger.probablePrime(100, secureRandom);
    }
}
