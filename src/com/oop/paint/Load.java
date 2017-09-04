package com.oop.paint;

import java.util.List;
import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Load extends PaintArea {
    JFileChooser chooser;
    //LoadDynamic loadDynamic = null;

    public Load() {
        chooser = new JFileChooser();
    }

    public String getLoadPath() {
        String path;
        int status = chooser.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            path = file.getAbsolutePath();
        } else {
            path = null;
        }
        return path;
    }
    
    public File getDynamicFile(){
    	String path;
        int status = chooser.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            return file;
        }
        return null;
    }
    public List<Shape> loadAsJson() {
        String path = getLoadPath();
        if (path == null) {
            return null;
        }
        List<Shape> list = new ArrayList<Shape>();
        JSONParser jsonParser = new JSONParser();
        File file = new File(path);

        try {
            Object object = jsonParser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) object;
            int size = Integer.parseInt(((String) jsonObject.get("size")));
            JSONArray jsonArray = new JSONArray();
            for (int i = 1; i <= size; i++) {
                jsonArray = (JSONArray) jsonObject.get(i + "");
                String state = new String((String) jsonArray.get(0));
                Shape shape = null;
                if (state.equals("line")) {
                    Point first = new Point(
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(1)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(2)));
                    Point second = new Point(
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(3)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(4)));
//                    loadDynamic = new LoadDynamic(first, second, null, "Line");
//                    shape = loadDynamic.getShapee();
                    shape = new Line(first, second);
//                    addShape(shape);
                    if(jsonArray.size()>5){
                    	int rgbValues[] = {(int) Double.parseDouble(
                                (String) jsonArray.get(5)),
                        (int) Double.parseDouble(
                                (String) jsonArray.get(6)) , (int) Double.parseDouble(
                                        (String) jsonArray.get(7))};
                        shape.setColor(new Color(rgbValues[0], rgbValues[1], rgbValues[2]));
                    }
                    else{
                    	shape.color = null;
                    }
                } else if (state.equals("circle")) {
                    Point center = new Point(
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(1)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(2)));
                    Point lowerRight = new Point(
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(3)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(4)));
//                    loadDynamic = new LoadDynamic(center, lowerRight, null, "Circle");
//                    shape = loadDynamic.getShapee();
//                    addShape(shape);
                    //shape = new Circle(center, lowerRight);
                    DynamicLoad dynamicLoad = new DynamicLoad(center, lowerRight, null, "Circle");
                    if(dynamicLoad.checkException()){
                    	JOptionPane.showMessageDialog(this, "Circle Not uploaded");
                    }
                    else{
                    	shape = dynamicLoad.getShapee();
                    	if(jsonArray.size()>5){
                        	int rgbValues[] = {(int) Double.parseDouble(
                                    (String) jsonArray.get(5)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(6)) , (int) Double.parseDouble(
                                            (String) jsonArray.get(7))};
                            shape.setColor(new Color(rgbValues[0], rgbValues[1], rgbValues[2]));
                        }
                        else{
                        	shape.color = null;
                        }
                    }
                    
                } else if (state.equals("oval")) {
                    Point upperLeft = new Point(
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(1)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(2)));
                    Point lowerRight = new Point(
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(3)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(4)));
//                    loadDynamic = new LoadDynamic(upperLeft, lowerRight, null, "Oval");
//                    shape = loadDynamic.getShapee();
//                    addShape(shape);
                    shape = new Oval(upperLeft, lowerRight);
                    if(jsonArray.size()>5){
                    	int rgbValues[] = {(int) Double.parseDouble(
                                (String) jsonArray.get(5)),
                        (int) Double.parseDouble(
                                (String) jsonArray.get(6)) , (int) Double.parseDouble(
                                        (String) jsonArray.get(7))};
                        shape.setColor(new Color(rgbValues[0], rgbValues[1], rgbValues[2]));
                    }
                    else{
                    	shape.color = null;
                    }
                } else if (state.equals("rectangle")) {
                    Point upperLeft = new Point(
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(1)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(2)));
                    Point downRight = new Point(
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(3)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(4)));
//                    loadDynamic = new LoadDynamic(upperLeft, downRight, null, "Rectangle");
//                    shape = loadDynamic.getShapee();
//                    addShape(shape);
                    shape = new Rectangle(upperLeft, downRight);
                    if(jsonArray.size()>5){
                    	int rgbValues[] = {(int) Double.parseDouble(
                                (String) jsonArray.get(5)),
                        (int) Double.parseDouble(
                                (String) jsonArray.get(6)) , (int) Double.parseDouble(
                                        (String) jsonArray.get(7))};
                        shape.setColor(new Color(rgbValues[0], rgbValues[1], rgbValues[2]));
                    }
                    else{
                    	shape.color = null;
                    }
                } else if (state.equals("square")) {
                    Point center = new Point(
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(1)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(2)));
                    Point edgePoint = new Point(
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(3)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(4)));
//                    loadDynamic = new LoadDynamic(center, edgePoint, null, "Square");
//                    shape = loadDynamic.getShapee();
//                    addShape(shape);
//                    shape = new Square(center, edgePoint);
                    DynamicLoad dynamicLoad = new DynamicLoad(center, edgePoint, null, "Square");
                    if(dynamicLoad.checkException()){
                    	JOptionPane.showMessageDialog(this, "Square Not uploaded");
                    }
                    else{
                    	shape = dynamicLoad.getShapee();
                    	try{
                        	int rgbValues[] = {(int) Double.parseDouble(
                                    (String) jsonArray.get(5)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(6)) , (int) Double.parseDouble(
                                            (String) jsonArray.get(7))};
                            System.out.println(rgbValues[0]+" " + rgbValues[1] + " " + rgbValues[2]);
                        	shape.setColor(new Color(rgbValues[0], rgbValues[1], rgbValues[2]));
                        	shape.color(new Color(rgbValues[0], rgbValues[1], rgbValues[2]));
                        	System.out.println(shape.getColor());
                    	}catch (Exception e){
                    		shape.setColor(null);
                    	}
//                    	if(jsonArray>5){
//                        	int rgbValues[] = {(int) Double.parseDouble(
//                                    (String) jsonArray.get(5)),
//                            (int) Double.parseDouble(
//                                    (String) jsonArray.get(6)) , (int) Double.parseDouble(
//                                            (String) jsonArray.get(7))};
//                            shape.setColor(new Color(rgbValues[0], rgbValues[1], rgbValues[2]));
//                        }
//                        else{
//                        	shape.color = null;
//                        }
                    }

                } else if (state.equals("triangle")) {
                    Point point1 = new Point(
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(1)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(2)));
                    Point point2 = new Point(
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(3)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(4)));
                    Point point3 = new Point(
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(5)),
                            (int) Double.parseDouble(
                                    (String) jsonArray.get(6)));
                    ArrayList<Point> pts = new ArrayList<Point>();
//                    LoadDynamic loadDynamic = new LoadDynamic( point1, point2, point3, "Triangle");
//                    list.add(loadDynamic.getShapee());
                    shape = new Triangle(pts);
                    if(jsonArray.size() > 7){
                    	int rgbValues[] = {(int) Double.parseDouble(
                                (String) jsonArray.get(7)),
                        (int) Double.parseDouble(
                                (String) jsonArray.get(8)) , (int) Double.parseDouble(
                                        (String) jsonArray.get(9))};
                        shape.setColor(new Color(rgbValues[0], rgbValues[1], rgbValues[2]));
                    }
                    else{
                    	shape.color = null;
                    }
                }
                if(shape!=null){
                	list.add(shape);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list;
    }

    public List<Shape> loadAsXml() {
        String path = getLoadPath();
        if (path == null) {
            return null;
        }
        List<Shape> list = new ArrayList<Shape>();
        Document document;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory
                    .newDocumentBuilder();
            document = documentBuilder.parse(path);

            Element root = document.getDocumentElement();
            
            NodeList lines = root.getElementsByTagName("line");
            NodeList circles = root.getElementsByTagName("circle");
            NodeList ovals = root.getElementsByTagName("oval");
            NodeList rectangles = root.getElementsByTagName("rectangle");
            NodeList squares = root.getElementsByTagName("square");
            NodeList triangles = root.getElementsByTagName("triangle");

            Shape shape = null;

            
            Node node = null ;
            
            for (int i = 0; i < lines.getLength(); i++) {
                node = lines.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    shape = generateShape(element, "line");
                    if(shape!=null){
                    	list.add(shape);
                    }
                }
            }
            for (int i = 0; i < circles.getLength(); i++) {
                node = circles.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    shape = generateShape(element, "circle");
                    if(shape!=null){
                    	list.add(shape);
                    }
                }
            }
            for (int i = 0; i < ovals.getLength(); i++) {
                node = ovals.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    shape = generateShape(element, "oval");
                    if(shape!=null){
                    	list.add(shape);
                    }
                }
            }
            for (int i = 0; i < rectangles.getLength(); i++) {
                node = rectangles.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    shape = generateShape(element, "rectangle");
                    if(shape!=null){
                    	list.add(shape);
                    }
                }
            }
            for (int i = 0; i < squares.getLength(); i++) {
                node = squares.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    shape = generateShape(element, "square");
                    if(shape!=null){
                    	list.add(shape);
                    }
                }
            }
            for (int i = 0; i < triangles.getLength(); i++) {
                node = triangles.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    shape = generateShape(element, "triangle");
                    if(shape!=null){
                    	list.add(shape);
                    }
                }
            }
        }catch(Exception e){
            throw new RuntimeException();
        }
        return list;
    }

    private Shape generateShape(Element element, String shapeName) {
        Shape shape = null;
        Color color = null;
        int x1, x2, y1, y2, x3, y3 , r,g,b;
        x1 = (int) Double.parseDouble(element.getElementsByTagName("x1").item(0)
                .getTextContent());
        y1 = (int) Double.parseDouble(element.getElementsByTagName("y1").item(0)
                .getTextContent());
        x2 = (int) Double.parseDouble(element.getElementsByTagName("x2").item(0)
                .getTextContent());
        y2 = (int) Double.parseDouble(element.getElementsByTagName("y2").item(0)
                .getTextContent());
        try{
        	r = (int) Double.parseDouble(element.getElementsByTagName("r").item(0)
                .getTextContent());
        	g = (int) Double.parseDouble(element.getElementsByTagName("g").item(0)
                .getTextContent());
        	b = (int) Double.parseDouble(element.getElementsByTagName("b").item(0)
                .getTextContent());
        	color = new Color(r, g, b);
        }
        catch (Exception e){
        	color = null;
        }
        if (shapeName.equals("line")) {
//            loadDynamic = new LoadDynamic(new Point(x1, y1), new Point(x2, y2), null, "Line");
//            shape = loadDynamic.getShapee();
        	shape = new Line(new Point(x1, y1), new Point(x2, y2));
        	shape.setColor(color);
        } else if (shapeName.equals("circle")) {
//            loadDynamic = new LoadDynamic(, new Point(x2, y2), null, "Circle");
//            shape = loadDynamic.getShapee();
        	//shape = new Circle(new Point(x1, y1), new Point(x2, y2));
        	DynamicLoad dynamicLoad = new DynamicLoad(new Point(x1, y1), new Point(x2, y2), null, "Circle");
        	if(dynamicLoad.checkException()){
            	JOptionPane.showMessageDialog(this, "Circle Not uploaded");
            	shape = null;
        	}
            else{
            	shape = dynamicLoad.getShapee();
            	shape.setColor(color);
            }
        } else if (shapeName.equals("oval")) {
//            loadDynamic = new LoadDynamic(new Point(x1, y1), new Point(x2, y2), null, "Oval");
//            shape = loadDynamic.getShapee();
        	shape = new Oval(new Point(x1, y1), new Point(x2, y2));
        	shape.setColor(color);
        } else if (shapeName.equals("rectangle")) {
//            loadDynamic = new LoadDynamic(new Point(x1, y1), new Point(x2, y2), null, "Rectangle");
//            shape = loadDynamic.getShapee();
        	shape = new Rectangle(new Point(x1, y1), new Point(x2, y2));
        	shape.setColor(color);
        } else if (shapeName.equals("square")) {
//            loadDynamic = new LoadDynamic(new Point(x1, y1), new Point(x2, y2), null, "Square");
//            shape = loadDynamic.getShapee();
//        	shape = new Square(new Point(x1, y1), new Point(x2, y2));
        	DynamicLoad dynamicLoad = new DynamicLoad(new Point(x1, y1), new Point(x2, y2), null, "Square");
        	if(dynamicLoad.checkException()){
            	JOptionPane.showMessageDialog(this, "Square Not uploaded");
            	shape = null;
        	}
            else{
            	shape = dynamicLoad.getShapee();
            	shape.setColor(color);
            }
        } else if (shapeName.equals("triangle")) {
            x3 = (int) Double.parseDouble(element.getElementsByTagName("x3")
                    .item(0).getTextContent());
            y3 = (int) Double.parseDouble(element.getElementsByTagName("y3")
                    .item(0).getTextContent());
//            loadDynamic = new LoadDynamic(new Point(x1, y1), new Point(x2, y2), new Point(x3, y3), "Triangle");
//            shape = loadDynamic.getShapee();
            ArrayList<Point> pts = new ArrayList<Point>();
            pts.add(new Point(x1,y1));
            pts.add(new Point(x2,y2));
            pts.add(new Point(x3,y3));
            shape = new Triangle(pts);
            shape.setColor(color);
        }
        return shape;
    }

    private void getProperty(Document doc, String id) {
        Element element = doc.getElementById(id);
    }

    private static Node setProperty(Document doc, String name,
            String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}