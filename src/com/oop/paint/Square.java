package com.oop.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Square extends com.oop.paint.Rectangle {
    private Point edgePoint,upperLeft, downRight, upperRight, downLeft, midUpper, midDown, midLeft, midRight;
    private int halfSideLen;
    
    public Square(){
    	
    }
    
    public Square(Point center, Point edgePoint) {
    	initialize(center, edgePoint);
    }
    
    private void initialize(Point center, Point edgePoint) {
    	selected = false;
    	this.center = center;
        this.edgePoint = edgePoint;
        startDrag = center;
        endDrag = edgePoint;
        this.color = null;
        this.halfSideLen = Math.abs(center.x - edgePoint.x);
        this.upperLeft = new Point(center.x - halfSideLen , center.y - halfSideLen);
        this.downRight = new Point(center.x + halfSideLen , center.y + halfSideLen);
        this.upperRight = new Point(downRight.x,upperLeft.y);
        this.downLeft = new Point(upperLeft.x, downRight.y);
        this.midUpper = new Point(upperLeft.x+(halfSideLen),upperLeft.y);
        this.midDown = new Point(downLeft.x+(halfSideLen),downLeft.y);
        this.midRight = new Point(upperRight.x,upperRight.y+(halfSideLen));
        this.midLeft = new Point(upperLeft.x,upperLeft.y+(halfSideLen));
        shapeName = new String("Square");
    }
    
    public void draw(Graphics g){
    	if(this.center ==null && this.edgePoint==null){
        	initialize(points.get(0), points.get(1));
        }
        if(color ==null){
    		g.setColor(Color.BLACK);
        	g.drawRect(center.x - halfSideLen, center.y - halfSideLen, halfSideLen*2, halfSideLen*2);
    	} else{
    		g.setColor(color);
            g.fillRect(center.x - halfSideLen, center.y - halfSideLen, halfSideLen*2, halfSideLen*2);
    	}
    	
    	if(selected){
    		g.setColor(Color.BLUE);
            g.drawRect(center.x-2, center.y-2, 4, 4);
            //g.drawRect(upperLeft.x-2, upperLeft.y-2, 4, 4);
        	//g.drawRect(upperRight.x-2, upperRight.y-2, 4, 4);
        	//g.drawRect(downLeft.x-2, downLeft.y-2, 4, 4);
        	//g.drawRect(downRight.x-2, downRight.y-2, 4, 4);
        	g.drawRect(midDown.x-2, midDown.y-2, 4, 4);
        	g.drawRect(midUpper.x-2, midUpper.y-2, 4, 4);
        	g.drawRect(midLeft.x-2, midLeft.y-2, 4, 4);
        	g.drawRect(midRight.x-2, midRight.y-2, 4, 4);
    	}
    }

    public void color(Color chosenColor){
        this.color = chosenColor;
    }
    
    public Shape resize(Point from, Point to) {
    	int len =0;
    	Shape newShape = null;
    	if(midDown.equals(from) || midUpper.equals(from)){
    		len = Math.abs(this.center.y - to.y)*2; 
    	}
    	if(midLeft.equals(from) || midRight.equals(from)){
       		len = Math.abs(this.center.x - to.x)*2;
    	}
    	newShape = new Square(this.center,new Point(this.center.x  + (len/2), this.center.y  + (len/2)));
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
    	Shape newShape = new Square(newCenter, new Point(newCenter.x+halfSideLen, newCenter.y+halfSideLen));
        newShape.color = this.color;
    	return newShape;
    }
    
    public java.awt.Shape makeShapeToJava(){
		Rectangle curr = new Rectangle(center.x - halfSideLen, center.y - halfSideLen, halfSideLen*2, halfSideLen*2);
		return curr;
    }
    
    public Shape makeColor(Color color){
    	Shape newShape = new Square(this.center,this.edgePoint);
    	newShape.color = color;
    	return newShape;
    }
    
    public Point getCenter() {
        return center;
    }
    public void setCenter(Point center) {
        this.center = center;
    }
    public Point getEdgePoint() {
        return edgePoint;
    }
    public void setEdgePoint(Point edgePoint) {
        this.edgePoint = edgePoint;
    }
}
