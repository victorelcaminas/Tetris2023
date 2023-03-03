/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ieselcaminas.tetris;

import java.io.*;
import java.util.*;

/**
 *
 * @author victor
 */
public class Scores {
    
    public static final int NUM_HIGH_SCORES = 5;
    private List<Score>[] lists;

    
    public Scores() {
        lists = new ArrayList[3];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new ArrayList<>();
        }
        readListFromFile();
        for (List<Score> list : lists) {
            System.out.println(list);
        }
    }
    
    public List<Score>[] getLists() {
        return lists;
    }
    
    public void addScore(Score score) {
        int level = score.getLevel();
        List<Score> list = lists[level];
        if (list.size() >= NUM_HIGH_SCORES) {
            if (score.getScore() > getMinScore(level)) {
                list.add(score);
                Collections.sort(list);
                list.remove(list.size() - 1);
            }
        } else {
            list.add(score);
            Collections.sort(list);
        }
        writeListsToFile();
    }
    
    private int getMinScore(int level) {
        int min = Integer.MAX_VALUE;
        for (Score s : lists[level]) {
            if (s.getScore() <= min) {
                min = s.getScore();
            }
        }
        return min;
    }
    
    private void writeListsToFile() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(
                    new FileOutputStream("scores.dat"));
            for (List<Score> list : lists) {
                out.writeObject(list);
            }
        } catch(IOException ex) {
            ex.printStackTrace();               
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private void readListFromFile() {
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(
                    new FileInputStream("scores.dat"));
            int i = 0;
            while(true) {
                lists[i] = (List<Score>) input.readObject();
                i++;
            }
        } catch (EOFException ex) {
            
        } catch (FileNotFoundException ex) {
                        
        } catch(IOException ex) {
            ex.printStackTrace();               
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
