package com.oop.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class Circle extends Oval{
	private Point lowerRight, top, down, left, right;
    private int width, heigh, rad;
    private Point upperLeft;
	
    public Circle(){
    	
    }
    public Circle(Point center, Point lowerRight) {
    	initialize(center, lowerRight);
    }
	private void initialize(Point center, Point lowerRight) {
		selected = false;
		this.center = center;
        this.lowerRight = lowerRight;
        startDrag = center;
        endDrag = lowerRight;
        rad = (int) Math
                .sqrt(Math.pow((center.x - lowerRight.x), 2.0)
                        + Math.pow((center.y - lowerRight.y), 2.0));
        //this.color = null;
        this.top = new Point(center.x , center.y - rad);
        this.down = new Point(center.x , center.y + rad);
        this.left = new Point(center.x - rad , center.y);
        this.right = new Point(center.x + rad, center.y);
        this.upperLeft = new Point(center.x-rad, center.y-rad);
        this.shapeName = new String("Circle");
	}
    
    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Point getLowerRight() {
        return lowerRight;
    }

    public void draw(Graphics g){
        if(this.center ==null && this.lowerRight==null){
        	initialize(points.get(0), points.get(1));
        }
    	if(this.color ==null){
        	g.setColor(Color.BLACK);
            
            g.drawOval(center.x - rad, center.y - rad, 2 * rad,
                    2 * rad);
        } else{
        	g.setColor(color);
            g.fillOval(center.x - rad, center.y - rad, 2 * rad,
                    2 * rad);
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
    	Shape newShape = new Circle(this.center, new Circle(this.center,to).top);
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
        Shape newShape = new Circle(newCenter, new Point(newCenter.x,newCenter.y+rad));
        newShape.color = this.color;
    	return newShape;
    }
    
    public java.awt.Shape makeShapeToJava(){
    	Ellipse2D curr = new Ellipse2D.Float();
    	curr.setFrame((int)(center.x - rad), (int)(center.y - rad), (int)(2*rad), (int)(2*rad));
    	return curr;
    }
    
    public Shape makeColor(Color color){
    	Shape newShape = new Circle(this.center,this.lowerRight);
    	newShape.color = color;
    	return newShape;
    }
}
