package com.company;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;

public class Verifier extends Thread {
    private Socket socket;
    BigInteger Y,X,Z,p,g;

    public Verifier(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try{

        System.out.println("Client connected.");
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);


                JSONObject clientValues = getJSON(input.readLine());
                System.out.println("Received from the client: ");
                System.out.println("p: " + clientValues.get("p"));
                System.out.println("g: " + clientValues.get("g"));
                System.out.println("X: " + clientValues.get("X"));
                System.out.println("Y: " + clientValues.get("Y"));

                int challenge = (int) Math.round(Math.random());
                output.println(challenge); //verifier sends the challenge 0 or 1

                JSONObject clientProof = new JSONObject(input.readLine());
                this.Z = new BigInteger(clientProof.get("Z").toString());

                BigInteger val1 = Y.multiply(X.pow(challenge)).mod(p);
                BigInteger val2 = g.modPow(Z,p);

                System.out.println("Value1= "+ val1);
                System.out.println("Value2= "+ val2);

                if(val1.equals(val2)){
                    System.out.println("Verified!");
                    output.println("Verified!");
                } else {
                    System.out.println("Not verified.");
                    output.println("Not verified.");
                }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getJSON(String clientString){
        JSONObject jo = new JSONObject(clientString);
        this.Y = new BigInteger(jo.get("Y").toString());
        this.X = new BigInteger(jo.get("X").toString());
        this.p = new BigInteger(jo.get("p").toString());
        this.g = new BigInteger(jo.get("g").toString());

        return new JSONObject(clientString);
    }
}
