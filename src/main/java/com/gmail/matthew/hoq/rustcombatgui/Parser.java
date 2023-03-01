/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.matthew.hoq.rustcombatgui;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Matthew
 */
public class Parser {

    public HashMap<String, String> idHash;
    String path;
    int lastCombatLog;

    public Parser(String path) {
        this.path = path;
    }

    public void loadIds() {
        idHash = new HashMap();
        List<String> idData = Parser.loadData("id.txt");
        for (int i = 0; i < idData.size(); i++) {
            String line = idData.get(i);
            System.out.println("loaded id "+idData);
            String[] splitLine = line.split(" ");
            idHash.put(splitLine[0], splitLine[1]);
        }
    }

    public static void loadSettings() {
        List<String> settings = loadData("settings.txt");
        if (!settings.get(0).equals("directory")) {
            MainFrame.singletonFrame.outputDir = settings.get(0);
            MainFrame.singletonFrame.jButton1ActionPerformed(null);
        }
    }

    public static List<String> loadData(String path) {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            return lines;
        } catch (IOException e) {
            // do something
            e.printStackTrace();
        }
        System.out.println("ERROR NULL NO SETTINGS LOADED");
        return null;

    }

    public void parseCheck() {
        System.out.println("Parse Check");
        // List<Object> lines = Collections.emptyList();
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {

            // do something
            e.printStackTrace();
        }

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("time") && lines.get(i).contains("attacker")) {
                this.lastCombatLog = i;
            }

        }

        System.out.println(this.lastCombatLog);
        int currentLine = lastCombatLog + 1;
        String regexFilter = "\\d*[.]\\d*.*";
        String outgoingFilter = "\\d*[.]\\d*s\\s*you\\s*\\d*\\s*player.*";
        boolean isLog = lines.get(currentLine).matches(regexFilter);
        // System.out.println(lines.get(currentLine));
        // System.out.println("islog");
        // System.out.println(isLog);

        String[][] combatLogs = new String[30][];
        int index = 0;
        while (isLog) {
            if (lines.get(currentLine).matches(outgoingFilter)) {
                String strippedLine = lines.get(currentLine).replaceAll("semi(\\s)*auto(\\s)*rifle", "semiautorifle");

                // really janky way of tokenization but I was in a bit of a hurry and couldn't
                // figure out varying whitespace delimiters

                strippedLine = strippedLine.replaceAll("\\s", "\\|");
                strippedLine = strippedLine.replaceAll("\\|\\|*", "\\|");
                strippedLine = strippedLine.replaceAll("\\|\\|", "\\|");


                String[] split = strippedLine.split("\\|");
                combatLogs[index] = split;
                System.out.println("combatlogss [" + index + "]=" + String.join(", ", split));
                index++;
            }
            currentLine++;
            isLog = lines.get(currentLine).matches(regexFilter);

        }
        MainFrame mainframe = MainFrame.singletonFrame;
        JTable table = mainframe.jTable1;

        Object[] emptyRow = { null, null, null, null, null, null, null, null, null, null, null, null };

        Object[][] tableModelArr = new Object[index][];
        for (int i = 0; i < index - 1; i++) {
            tableModelArr[i] = emptyRow.clone();
        }
        table.setModel(new javax.swing.table.DefaultTableModel(tableModelArr, new String[] {
            "Name", "hp ->", "hp", "Area", "Time", "Dist", "Info", "Integrity"
        }));

        DefaultTableModel model = (DefaultTableModel) table.getModel();


        for (int i = index - 1; i > -1; i--) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                String[] currentEntry = combatLogs[index - 1 - i];
                int[] indices = { 4, 9, 10, 7,0, 8, 11, 13};
                if (indices[j] == 4) {
                    String id = currentEntry[indices[j]];
                    String value = id;
                    if (idHash.get(id) != null) {
                        value = idHash.get(id);
                    }

                    model.setValueAt(value, i, j);
                }else {
                    model.setValueAt(currentEntry[indices[j]], i, j);
                    if(indices[j] == 10 || indices[j]==9){
                        table.getColumnModel().getColumn(j).setCellRenderer(new StatusColumnCellRenderer());
                    }
                }

            }
        }

    }

//    public static void main(String args[]) {
//        Parser parser = new Parser("Z:\\SteamLibrary\\steamapps\\common\\Rust\\output_log.txt");
//        parser.parseCheck();
//
//    }
}
