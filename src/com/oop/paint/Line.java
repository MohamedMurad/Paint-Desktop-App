package com.oop.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Line extends Shape{
    private Point first ,second;
    
    public Line() {
	
    }
    
    public Line(Point first, Point second){
    	shapeName = new String("Line");
    	selected = false;
    	this.first = first;
        this.second = second;
        startDrag = first;
        endDrag = second;
        this.center = new Point((first.x + second.x)/2 , (first.y + second.y)/2); 
        this.color = null;
    }
    
    public Point getFirst() {
        return first;
    }
    
    public void setFirst(Point first) {
        this.first = first;
    }
    
    public Point getSecond() {
        return second;
    }
    
    public void setSecond(Point second) {
        this.second = second;
    }
    
    public void draw(Graphics g) {
    	if(color==null){
    		g.setColor(Color.BLACK);
            g.drawLine(first.x, first.y , second.x, second.y);
    	} else{
    		g.setColor(color);
            g.drawLine(first.x, first.y , second.x, second.y);
    	}
                
        if(selected){
        	g.setColor(Color.BLUE);
            g.drawRect(center.x-2, center.y-2, 4, 4);
            g.drawRect(first.x-2, first.y-2, 4, 4);
            g.drawRect(second.x-2, second.y-2, 4, 4);
        }
    }
    
    public void color(Color chosenColor){
        this.color = chosenColor;
    }
    
    public Shape resize(Point from, Point to){
    	Shape newShape = null;
    	if(first.equals(from)){
    		newShape = new Line(to,second);
    	}
    	if(second.equals(from)){
    		newShape = new Line(first,to);
    	}
    	newShape.color = this.color;
    	return newShape;
    }
    
    public Shape move(Point newCenter){
        int dx ,dy;
        dx = newCenter.x - center.x;
        dy = newCenter.y - center.y;
    	Shape newShape = new Line(new Point(first.x+dx, first.y+dy), new Point(second.x+dx, second.y+dy));
        newShape.color = this.color;
    	return newShape;
    }
    
    
	public Point detectResizingPoint(Point resize) {
        if(new Rectangle(first.x-2, first.y-2, 4, 4).contains(resize)){
        	return first;
        }
        if(new Rectangle(second.x-2, second.y-2, 4, 4).contains(resize)){
        	return second;
        }
		return null;
	}
	
	public java.awt.Shape makeShapeToJava(){
    	Polygon curr = new Polygon();
    	curr.addPoint((int)first.getX(), (int)first.getY());
    	curr.addPoint((int)second.getX(), (int)second.getY());
    	return curr;
	}
	
	public Shape makeColor(Color color){
    	Shape newShape = new Line(this.first,this.second);
    	newShape.color = color;
    	return newShape;
    }
}
