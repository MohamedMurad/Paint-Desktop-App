package com.oop.paint;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class GUI extends JFrame {
     
    Point startDrag,endDrag;
    boolean dragging;
    JLabel currentPoint; 
    
    public GUI() {
        initialize();
    }
    
    private void initialize() {
        startDrag = endDrag = null;
        dragging = false;
        this.setVisible(true);
        this.setSize(800,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PaintArea bar = new PaintArea(){};
        this.add(bar);
        MenuBar menuBar = new MenuBar();
        this.setJMenuBar(menuBar.menuBarMaker());
        this.setResizable(false);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                GUI gui = new GUI();
            }
        });
    }
}