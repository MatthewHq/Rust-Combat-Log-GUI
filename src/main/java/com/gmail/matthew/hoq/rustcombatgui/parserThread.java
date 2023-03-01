/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.matthew.hoq.rustcombatgui;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

/**
 *
 * @author Matthew
 */
public class parserThread implements Runnable {

    String filepath;
    public parserThread(String filepath){
        this.filepath=filepath;
    }
    @Override
    public void run() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(Files.readAllBytes(Paths.get(this.filepath)));
            while (true) {
                byte[] newHash = md.digest(Files.readAllBytes(Paths.get(this.filepath)));
                if (!MessageDigest.isEqual(hash, newHash)) {
                    System.out.println("File has changed+++!");
                    Parser parser = new Parser(this.filepath);
                    parser.loadIds();
                    parser.parseCheck();
                    hash = newHash;
                }
                Thread.sleep(1000); // Sleep for 1 second before checking again
            }
        } catch (Exception e) {
            // Throwing an exception
            e.printStackTrace();
        }
    }

}
