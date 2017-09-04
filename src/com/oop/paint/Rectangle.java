package com.oop.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Rectangle extends Shape {
    private Point upperLeft, downRight, upperRight, downLeft, midUpper, midDown, midLeft, midRight;
    private int width, heigh;
    
    public Rectangle(){
		
	}
    public Rectangle(Point upperLeft, Point downRight) {
    	selected = false;
    	this.upperLeft = new Point(Math.min(upperLeft.x, downRight.x),
                Math.min(upperLeft.y, downRight.y));
        this.downRight = new Point(Math.max(upperLeft.x, downRight.x),
                Math.max(upperLeft.y, downRight.y));
        this.width = Math.abs((downRight.x - upperLeft.x));
        this.heigh = Math.abs((downRight.y - upperLeft.y));
        startDrag = upperLeft; 
        endDrag = downRight;
        this.center = new Point((Math.min(this.upperLeft.x, this.downRight.x)+(width/2)) , (Math.min(this.upperLeft.y, this.downRight.y)+(heigh/2))); 
        this.color = null;
        this.upperRight = new Point(this.downRight.x,this.upperLeft.y);
        this.downLeft = new Point(this.upperLeft.x, this.downRight.y);
        this.midUpper = new Point(this.upperLeft.x+(width/2),this.upperLeft.y);
        this.midDown = new Point(this.downLeft.x+(width/2),this.downLeft.y);
        this.midRight = new Point(this.upperRight.x,this.upperRight.y+(heigh/2));
        this.midLeft = new Point(this.upperLeft.x,this.upperLeft.y+(heigh/2));
        shapeName = new String("Rectangle");
    }
    public void draw(Graphics g){
    	if(color==null){
    		g.setColor(Color.BLACK);
            g.drawRect(Math.min(this.upperLeft.x, this.downRight.x),
                    Math.min(this.upperLeft.y, this.downRight.y),
                    width,heigh);
    	} else{
    		g.setColor(this.color);
            g.fillRect(Math.min(this.upperLeft.x, this.downRight.x),
                    Math.min(this.upperLeft.y, this.downRight.y),
                    width,heigh);
    	}
    	
        if(selected){
        	g.setColor(Color.BLUE);
        	g.drawRect(this.center.x-2, this.center.y-2, 4, 4);
        	//g.drawRect(this.upperLeft.x-2, this.upperLeft.y-2, 4, 4);
        	//g.drawRect(this.upperRight.x-2, this.upperRight.y-2, 4, 4);
        	//g.drawRect(this.downLeft.x-2, this.downLeft.y-2, 4, 4);
        	//g.drawRect(this.downRight.x-2, this.downRight.y-2, 4, 4);
        	g.drawRect(this.midDown.x-2, this.midDown.y-2, 4, 4);
        	g.drawRect(this.midUpper.x-2, this.midUpper.y-2, 4, 4);
        	g.drawRect(this.midLeft.x-2, this.midLeft.y-2, 4, 4);
        	g.drawRect(this.midRight.x-2, this.midRight.y-2, 4, 4);
        }
    }
    
    public void color(Color chosenColor){
        this.color = chosenColor;
    }
    
    public Shape resize(Point from, Point to) {
    	int wid = 0, hei=0;
    	Shape newShape = null;
    	if(midDown.equals(from) || midUpper.equals(from)){
    		wid = this.width;
    		hei = Math.abs(this.center.y - to.y)*2; 
    	}
    	if(midLeft.equals(from) || midRight.equals(from)){
    		hei = this.heigh;
    		wid = Math.abs(this.center.x - to.x)*2;
    	}
    	newShape = new Rectangle(new Point(this.center.x  - (wid/2), this.center.y  - (hei/2)),new Point(this.center.x  + (wid/2), this.center.y  + (hei/2)));
    	newShape.color = this.color;
    	return newShape;
	}
    
    @Override
	public Point detectResizingPoint(Point resize) {
		if(new java.awt.Rectangle(midUpper.x-2, midUpper.y-2, 4, 4).contains(resize)){
        	return midUpper;
        }
		if(new java.awt.Rectangle(midDown.x-2, midDown.y-2, 4, 4).contains(resize)){
        	return midDown;
        }
		if(new java.awt.Rectangle(midLeft.x-2, midLeft.y-2, 4, 4).contains(resize)){
        	return midLeft;
        }
		if(new java.awt.Rectangle(midRight.x-2, midRight.y-2, 4, 4).contains(resize)){
        	return midRight;
        }
		return null;
	}


    
    public Shape move(Point newCenter){
    	Shape newShape = new Rectangle(new Point(newCenter.x-(width/2), newCenter.y-(heigh/2)),
        		new Point(newCenter.x+(width/2), newCenter.y+(heigh/2)));
        newShape.color = this.color;
    	return newShape;
    }

    public java.awt.Shape makeShapeToJava(){
		java.awt.Rectangle curr = new java.awt.Rectangle(Math.min(upperLeft.x, downRight.x),
                Math.min(upperLeft.y, downRight.y),
                width,heigh);
		return curr;
    }
    
    public Shape makeColor(Color color){
    	Shape newShape = new Rectangle(this.upperLeft,this.downRight);
    	newShape.color = color;
    	return newShape;
    }
    
    public Point getUpperLeft() {
        return upperLeft;
    }
    public void setUpperLeft(Point upperLeft) {
        this.upperLeft = upperLeft;
    }
    public Point getDownRight() {
        return downRight;
    }
    public void setDownRight(Point downRight) {
        this.downRight = downRight;
    }
}
