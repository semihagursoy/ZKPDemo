package com.company;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class CyclicGroupGenerator {
    private BigInteger p;
    private BigInteger g;
    private ArrayList<BigInteger> numbers = new ArrayList<>();

    public CyclicGroupGenerator() {
        generate();
        this.p = getP();
        this.g = getG();
    }
    public BigInteger getP() {
        return p;
    }

    public BigInteger getG() {
        return g;
    }

    private void generate()
    {
        SecureRandom r = new SecureRandom();

        do {
            p = BigInteger.probablePrime(10, r);
        } while(!isSafePrime(p));

        //p = new BigInteger("97");
        numbers = zGenerator(p, p.subtract(BigInteger.ONE).intValue());

        //get one value from the array of generators of the cyclic group
        int index = (int)(Math.random() * numbers.size());
        g = numbers.get(index);
    }

    private boolean isSafePrime(BigInteger i)
    {
        return i.subtract(BigInteger.ONE).divide(new BigInteger("2")).isProbablePrime(90);
    }

    private ArrayList<BigInteger> zGenerator(BigInteger i, int count)
    {
        Set<BigInteger> numbers = new TreeSet<BigInteger>();

        BigInteger num = new BigInteger("2");
        BigInteger exp = i.subtract(BigInteger.ONE).divide(new BigInteger("2"));
        //exp = (i-1)/2
        do
        {
            if(num.modPow(exp, i).compareTo(BigInteger.ONE) > 0 && num.modPow(num, i).compareTo(BigInteger.ONE) > 0 ){
                numbers.add(num);
            }
            else
            {
                if(num.negate().mod(i).compareTo(BigInteger.ONE) != 0 ){
                    numbers.add(num.negate().mod(i));
                }
            }
            num = num.add(BigInteger.ONE);
        }while((--count > 0) && (num.compareTo(i) < 0));


        return (new ArrayList<BigInteger>(numbers));
    }

}
