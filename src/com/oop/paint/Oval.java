package com.oop.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class Oval extends Shape{
    private Point upperLeft, lowerRight, top, down, left, right ;
    private int width, heigh;
    public Oval(){
    	
    }
    public Oval(Point upperLeft, Point lowerRight) {
    	selected = false;
    	this.upperLeft = new Point(Math.min(upperLeft.x, lowerRight.x),
                Math.min(upperLeft.y, lowerRight.y));
        this.lowerRight = new Point(Math.max(upperLeft.x, lowerRight.x),
                Math.max(upperLeft.y, lowerRight.y));
        startDrag = upperLeft; 
        endDrag = lowerRight;
        width = Math.abs((lowerRight.x - upperLeft.x));
        heigh = Math.abs(lowerRight.y - upperLeft.y);
        this.center = new Point(Math.min(upperLeft.x, lowerRight.x)+(width/2),Math.min(upperLeft.y, lowerRight.y)+(heigh/2));
        this.color = null;
        this.top = new Point(center.x , center.y - heigh/2);
        this.down = new Point(center.x , center.y + heigh/2);
        this.left = new Point(center.x - width/2 , center.y);
        this.right = new Point(center.x + width/2, center.y);
        shapeName = new String("Oval");
    }
    
    public void draw(Graphics g){
    	if(color ==null){
    		g.setColor(Color.BLACK);
            g.drawOval(Math.min(upperLeft.x, lowerRight.x),
                    Math.min(upperLeft.y, lowerRight.y),
                    width, heigh);
    	} else{
    		g.setColor(color);
            g.fillOval(Math.min(upperLeft.x, lowerRight.x),
                    Math.min(upperLeft.y, lowerRight.y),
                    width, heigh);
    	}
    	
        if(selected){
        	g.setColor(Color.BLUE);
            g.drawRect(center.x-2, center.y-2, 4, 4);
            g.drawRect(top.x-2, top.y-2, 4, 4);
        	g.drawRect(down.x-2, down.y-2, 4, 4);
        	g.drawRect(left.x-2, left.y-2, 4, 4);
        	g.drawRect(right.x-2, right.y-2, 4, 4);
        }
    }
    
    public void color(Color chosenColor){
        this.color = chosenColor;
    }
    
    public Shape resize(Point from, Point to) {
    	int wid=0 , hei=0;
    	Shape newShape = null;
    	if(top.equals(from) || down.equals(from)){
    		wid = this.width;
    		hei = Math.abs(this.center.y - to.y)*2;	 
    	}
    	if(left.equals(from) || right.equals(from)){
    		hei = this.heigh;
    		wid = Math.abs(this.center.x - to.x)*2;
    	}
    	newShape = new Oval(new Point(this.center.x  - (wid/2), this.center.y  - (hei/2)),new Point(this.center.x  + (wid/2), this.center.y  + (hei/2)));
    	newShape.color = this.color;
    	return newShape;
	}
    
    @Override
	public Point detectResizingPoint(Point resize) {
		if(new Rectangle(top.x-2, top.y-2, 4, 4).contains(resize)){
        	return top;
        }
        if(new Rectangle(down.x-2, down.y-2, 4, 4).contains(resize)){
        	return down;
        }
        if(new Rectangle(left.x-2, left.y-2, 4, 4).contains(resize)){
        	return left;
        }
        if(new Rectangle(right.x-2, right.y-2, 4, 4).contains(resize)){
        	return right;
        }
		return null;
	}

    public Shape move(Point newCenter){
        Shape newShape = new Oval(new Point(newCenter.x-(width/2), newCenter.y-(heigh/2)),
        		new Point(newCenter.x+(width/2), newCenter.y+(heigh/2)));
        newShape.color = this.color;
    	return newShape;
    }

    public java.awt.Shape makeShapeToJava(){
    	Ellipse2D curr = new Ellipse2D.Float();
    	curr.setFrame(Math.min(upperLeft.x, lowerRight.x), Math.min(upperLeft.y, lowerRight.y),(int)width, (int)heigh);
    	return curr;
    }
    
    public Shape makeColor(Color color){
    	Shape newShape = new Oval(this.upperLeft,this.lowerRight);
    	newShape.color = color;
    	return newShape;
    }
    
    public Point getUpperLeft() {
        return upperLeft;
    }

    public void setUpperLeft(Point upperLeft) {
        this.upperLeft = upperLeft;
    }

    public Point getLowerRight() {
        return lowerRight;
    }

    public void setLowerRight(Point lowerRight) {
        this.lowerRight = lowerRight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigh() {
        return heigh;
    }

    public void setHeigh(int heigh) {
        this.heigh = heigh;
    }

}
