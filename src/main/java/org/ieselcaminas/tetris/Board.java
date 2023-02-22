/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package org.ieselcaminas.tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

/**
 *
 * @author victor
 */
public class Board extends javax.swing.JPanel {
    
    public static final int NUM_ROWS = 22;
    public static final int NUM_COLS = 10;

    private Timer timer;
    private Shape currentShape;
    private int currentRow;
    private int currentCol;
    private int deltaTime;
    
    private Tetrominoes[][] matrix;

  
    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (canMove(currentRow, currentCol - 1, currentShape)) {
                        currentCol--;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (canMove(currentRow, currentCol + 1, currentShape)) {
                        currentCol++;
                    }
                    break;
                case KeyEvent.VK_UP:
                    Shape rotated = currentShape.rotateLeft();
                    if (canMove(currentRow, currentCol, rotated)) {
                        currentShape = rotated;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (canMove(currentRow + 1, currentCol, currentShape)) {
                        currentRow++;
                    }
                    break;
                default:
                    break;
            }
            repaint();
        }
    }
    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        myInit();
    }
    
    public int squareWidth() {
        return getWidth() / NUM_COLS;
    }
    
    public int squareHeight() {
        return getHeight() / NUM_ROWS;
    }
    
    public void resetMatrix() {
        matrix = new Tetrominoes[NUM_ROWS][NUM_COLS];
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                matrix[row][col] = Tetrominoes.NoShape;
            }
        }
    }
    
    public void resetPosition() {
        currentRow = 0;
        currentCol = NUM_COLS / 2;
    }
    
    public void myInit() {
        setFocusable(true);
        MyKeyAdapter keyAdepter = new MyKeyAdapter();
        addKeyListener(keyAdepter);
        resetMatrix();
        resetPosition();
        currentShape = new Shape();
        deltaTime = 500;
        timer = new Timer(deltaTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tick();
            }
                
        });
        timer.start();
    }
    
    private void tick() {
        if (canMove(currentRow + 1, currentCol, currentShape)) {
           currentRow ++;
        } else {
            movePieceToMatrix();
            resetPosition();
            currentShape.setRandomShape();
        }
        repaint();
    }
    
    private void movePieceToMatrix() {
       for (int i = 0; i < 4; i ++) {
           int row = currentRow + currentShape.getY(i);
           int col = currentCol + currentShape.getX(i);
           matrix[row][col] = currentShape.getShape();
       }
    }
    
    private boolean canMove(int row, int col, Shape piece) {
        if (row + piece.maxY() >= NUM_ROWS) {
            return false;
        }
        if (col + piece.minX() < 0 || col + piece.maxX() >= NUM_COLS) {
            return false;
        }
        if (colidesWithMatrix(row, col, piece)) {
            return false;
        }
        return true;
    }
    
    private boolean colidesWithMatrix(int row, int col, Shape shape) {
        for (int i = 0; i < 4; i ++) {
            int newRow = row + shape.getY(i);
            int newCol = col + shape.getX(i);
            if (matrix[newRow][newCol] != Tetrominoes.NoShape) {
                return true;
            }
        }
        return false;
            
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // paintBoard(g);
        paintFrame(g);
        paintMatrix(g);
        paintShape(g);
        Toolkit.getDefaultToolkit().sync();
    }
    
    private void paintMatrix(Graphics g) {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                drawSquare(g, row, col, matrix[row][col]);
            }
        }
    }
    
    private void paintShape(Graphics g) {
        for(int i = 0; i < 4; i++) {
            int row = currentRow + currentShape.getY(i);
            int col = currentCol + currentShape.getX(i);
            drawSquare(g, row, col, currentShape.getShape());
        }
    }
    private void paintFrame(Graphics g) {
        g.setColor(Color.blue);
        int width = squareWidth() * NUM_COLS;
        int height = squareHeight() * NUM_ROWS;
        g.drawRect(0, 0, width, height);
    }

    private void drawSquare(Graphics g, int row, int col,
            Tetrominoes shape) {
        Color colors[] = {new Color(0, 0, 0),
            new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
            new Color(204, 204, 102), new Color(204, 102, 204),
            new Color(102, 204, 204), new Color(218, 170, 0)
        };
        int x = col * squareWidth();
        int y = row * squareHeight();
        Color color = colors[shape.ordinal()];
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2,
                squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1,
                y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
