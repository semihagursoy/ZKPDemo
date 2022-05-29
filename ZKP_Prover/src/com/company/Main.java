package com.company;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Main {

    public static void main(String[] args) {

        System.out.println("Connecting to the server...");
        try(Socket socket = new Socket("localhost", 5000)) {
            Thread.sleep(1000);
            Prover prover = new Prover();
            socket.setSoTimeout(4000);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter proverSends = new PrintWriter(socket.getOutputStream(), true);
            String response;

            System.out.println("Your random p(mod) value: " + prover.getP());
            System.out.println("Your random g(generator) value: " + prover.getG());
            System.out.println("Your secret: " + prover.getSecret());
            System.out.println("Your random r value: " + prover.getRandomY());

            System.out.println("Sending P, g, X and Y to the verifier");
            JSONObject jo = new JSONObject();
            jo.put("p", prover.getP());
            jo.put("g", prover.getG());
            jo.put("X", prover.getX());
            jo.put("Y" , prover.getY());
            proverSends.println(jo);

            response = bufferedReader.readLine(); // get the data from the server
            System.out.println("Verifier sent c:" + response);

            prover.calculateZ(Integer.parseInt(response));
            System.out.println("Calculated Z as: " + prover.getZ());

            System.out.println("Sending Z to the verifier");
            JSONObject proof = new JSONObject();
            proof.put("Z" , prover.getZ());
            proverSends.println(proof);

            response = bufferedReader.readLine();
            System.out.println(response);

        } catch (SocketTimeoutException e){
            System.out.println("The socket timed out");
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testCode(){
        Prover prover = new Prover();
        System.out.println("Prover sends to the Verifier: ");
        System.out.println("X=" + prover.getX());
        System.out.println("Y=" + prover.getY());


        int challenge = 1;
        System.out.println("Verifier sends c="+challenge);
        prover.calculateZ(1);

        BigInteger val1 = prover.getY().multiply(prover.getX().pow(challenge)).mod(prover.getP());
        BigInteger val2 = prover.getG().modPow(prover.getZ(),prover.getP());


        System.out.println("val1=" + val1);
        System.out.println("val2=" + val2);

        if(val1.equals(val2)){
            System.out.println("Verified!");
        } else {
            System.out.println("Not verified!");
        }
    }
}
