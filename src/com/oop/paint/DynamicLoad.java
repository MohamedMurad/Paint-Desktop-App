package com.oop.paint;

import java.awt.Point;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class DynamicLoad {
    Shape shape;
    private boolean exception;
    DynamicLoad(Point first, Point second, Point third,
            final String fileName) {

    	final String className = "com.oop.paint." + fileName;
        exception = false;
    	try {
        	System.out.println("1");
            shape = (Shape) Class.forName(className).newInstance();
            System.out.println("2");
            shape.points.add(0, first);
            shape.points.add(1, second);
            shape.points.add(2, third);
            shape.shapeName = fileName;
            System.out.println("3");
        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException e) {
            exception = true;
        }
    }

    public Shape getShapee() {
        return shape;
    }
    
    public boolean checkException(){
    	return exception;
    }
}
