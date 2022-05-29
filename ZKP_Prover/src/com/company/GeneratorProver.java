package com.company;

public class GeneratorProver {
    public static void prove(int generator, int number){
        for(int i=0; i<number-1; i++ ){
            System.out.println(Math.pow(generator, i)%number);
        }
    }
}
