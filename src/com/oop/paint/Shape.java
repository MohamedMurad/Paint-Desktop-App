package com.oop.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class Shape {
    Point center, startDrag, endDrag;
    Color color;
    boolean selected;
    public String shapeName = null;
    List<Point> points;
    
    public Shape(){
        points = new ArrayList<Point>();
    }
    
    public void draw(Graphics g){
        selected = false;
    }
    public void color(Color chosenColor){
    	this.color = chosenColor;
    }
    
    public Shape resize(Point from, Point to){
    	return null;
    }
    
    public Shape move(Point newCenter){
    	return null;
    }
    
    public java.awt.Shape makeShapeToJava(){
		return null;
    }
    
    public Point getStartDrag(){
    	return startDrag;
    }
    public Point getEndDrag(){
    	return endDrag;
    }
    
    public Color getColor(){
    	return this.color;
    }
    
    public void setColor(Color chosenColor){
    	this.color = chosenColor;
    }
    
    public java.awt.Shape getCenterRect(){
    	java.awt.Rectangle rec = new java.awt.Rectangle(center.x-2, center.y-2, 4, 4); 
    	return rec;
    }
    
    public Point detectResizingPoint(Point resize){
    	return null;
    }
    
    public Shape makeColor(Color color){
    	return null;
    }
    
    public int[] getColorValue(){
    	if(color==null){
        	color = new Color(255,255,255);
        }
    	int rgbValues[] = {color.getRed(),color.getGreen(),color.getBlue()};
    	return rgbValues;
    }
    
}
