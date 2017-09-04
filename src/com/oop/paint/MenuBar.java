package com.oop.paint;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends PaintArea{

    public JMenuBar menuBarMaker() {
        
        JMenuBar menuBar = new JMenuBar();
        
        
        JMenu fileMenu = new JMenu(" File ");
        JMenu aboutMenu = new JMenu(" About ");

        
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setActionCommand("Save");
        saveMenuItem.setPreferredSize(new Dimension(100, saveMenuItem.getPreferredSize().height));
        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.setActionCommand("Load");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("Exit");
        JMenuItem aboutusMenuItem = new JMenuItem("about us");
        aboutusMenuItem.setPreferredSize(new Dimension(100, aboutusMenuItem.getPreferredSize().height));
        aboutusMenuItem.setActionCommand("Load");
        JMenuItem userguideMenuItem = new JMenuItem("user guide");
        userguideMenuItem.setActionCommand("user guide");

        MenuItemListener menuItemListener = new MenuItemListener();

        saveMenuItem.addActionListener(menuItemListener);
        loadMenuItem.addActionListener(menuItemListener);
        exitMenuItem.addActionListener(menuItemListener);
        aboutusMenuItem.addActionListener(menuItemListener);
        userguideMenuItem.addActionListener(menuItemListener);

        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        aboutMenu.add(aboutusMenuItem);
        aboutMenu.add(userguideMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);

        return menuBar;
    }
    class MenuItemListener extends PaintArea implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String seletedItem = e.getActionCommand();
            
        }
    }
}