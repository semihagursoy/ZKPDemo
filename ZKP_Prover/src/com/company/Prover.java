package com.company;

import java.math.BigInteger;

public class Prover {
    private BigInteger secret = RandomNumberGenerator.generate();
    private BigInteger randomY;
    private BigInteger X,Y,Z,challenge;
    CyclicGroupGenerator cyclicGroup = new CyclicGroupGenerator();
    private BigInteger p = cyclicGroup.getP();
    private BigInteger g = cyclicGroup.getG();


    public Prover() {
        chooseRandomY();
        calculateX();
        calculateY();
    }

    public BigInteger getX() {
        return X;
    }

    public BigInteger getY() {
        return Y;
    }

    public BigInteger getSecret() {
        return secret;
    }

    public BigInteger getRandomY() {
        return randomY;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getZ() {
        return Z;
    }

    private void calculateX(){
        this.X = cyclicGroup.getG().modPow(secret, cyclicGroup.getP());
    }

    private void calculateY(){
        this.Y = cyclicGroup.getG().modPow(randomY, cyclicGroup.getP());
    }

    private void chooseRandomY(){
        int q = cyclicGroup.getP().intValue();
        int max = q-1;
        int v = (int) ((Math.random() * (max - 1)) + 1);
        this.randomY = BigInteger.valueOf(v);
    }

    public void calculateZ(int challenge){
        this.Z = (randomY.add(secret.multiply(BigInteger.valueOf(challenge)).mod(p)));
    }
}
