package com.oop.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.awt.Polygon;

public class Triangle extends Line {
    ArrayList<Point> points;
    
    public Triangle(){
    	
    }
    public Triangle(ArrayList<Point> pts) {
    	selected = false;
    	points = (ArrayList<Point>)pts.clone();
        this.color = null;
        this.center = new Point((pts.get(0).x+pts.get(1).x+pts.get(2).x)/3 , (pts.get(0).y+pts.get(1).y+pts.get(2).y)/3);
        this.shapeName = new String("Triangle");
        startDrag = points.get(0);
        endDrag = points.get(1);
    }
    public void draw(Graphics g){
    	if(color==null){
    		g.setColor(Color.BLACK);
    		System.out.println("triangle ....");
            g.drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
            g.drawLine(points.get(1).x, points.get(1).y, points.get(2).x, points.get(2).y);
            g.drawLine(points.get(0).x, points.get(0).y, points.get(2).x, points.get(2).y);
    	} else{
    		g.setColor(color);
            g.fillPolygon((Polygon)this.makeShapeToJava());
    	}
    	
        if(selected){
        	g.setColor(Color.BLUE);
        	g.drawRect(this.center.x-2, this.center.y-2, 4, 4);
        	g.drawRect(points.get(0).x-2, points.get(0).y-2, 4, 4);
            g.drawRect(points.get(1).x-2, points.get(1).y-2, 4, 4);
            g.drawRect(points.get(2).x-2, points.get(2).y-2, 4, 4);
        }
    }
    
    public void color(Color chosenColor){
        this.color = chosenColor;
    }
    
    public Shape resize(Point from, Point to) {
    	Shape newShape = null;
    	ArrayList<Point> newPoints = new ArrayList<Point>();
    	if(points.get(0).equals(from)){
    		newPoints.add(to); 
    		newPoints.add(points.get(1));
    		newPoints.add(points.get(2));
    	}
    	if(points.get(1).equals(from)){
    		newPoints.add(points.get(0));
    		newPoints.add(to); 
    		newPoints.add(points.get(2));
    	}
    	if(points.get(2).equals(from)){
    		newPoints.add(points.get(0));
    		newPoints.add(points.get(1));
    		newPoints.add(to); 
    	}
    	newShape = new Triangle(newPoints);
    	newShape.color = this.color;
    	return newShape;
	}

	@Override
	public Point detectResizingPoint(Point resize) {
		if(new java.awt.Rectangle(points.get(0).x-2, points.get(0).y-2, 4, 4).contains(resize)){
        	return points.get(0);
        }
		if(new java.awt.Rectangle(points.get(1).x-2, points.get(1).y-2, 4, 4).contains(resize)){
        	return points.get(1);
        }
		if(new java.awt.Rectangle(points.get(2).x-2, points.get(2).y-2, 4, 4).contains(resize)){
        	return points.get(2);
        }
		return null;
	}

    
    //can't find equations for center
    public Shape move(Point newCenter){
    	Shape newShape;
    	int dx ,dy;
    	dx = newCenter.x - center.x;
    	dy = newCenter.y - center.y;
    	points.get(0).setLocation(new Point(points.get(0).x+dx, points.get(0).y+dy));
    	points.get(1).setLocation(new Point(points.get(1).x+dx, points.get(1).y+dy));
    	points.get(2).setLocation(new Point(points.get(2).x+dx, points.get(2).y+dy));
    	this.center = new Point(newCenter);
    	newShape = new Triangle((ArrayList<Point>)points);
    	newShape.color = this.color;
    	return newShape;
    }
    
    public java.awt.Shape makeShapeToJava(){
    	Polygon curr = new Polygon();
    	curr.addPoint((int)points.get(0).getX(), (int)points.get(0).getY());
    	curr.addPoint((int)points.get(1).getX(), (int)points.get(1).getY());
    	curr.addPoint((int)points.get(2).getX(), (int)points.get(2).getY());
    	return curr;
    }
    
    public Shape makeColor(Color color){
    	Shape newShape = new Triangle((ArrayList<Point>)this.points);
    	newShape.color = color;
    	return newShape;
    }
    
    public List<Point> getPoints() {
        return points;
    }
    public void setPoints(List<Point> points) {
        this.points = (ArrayList<Point>)points;
    }
}
