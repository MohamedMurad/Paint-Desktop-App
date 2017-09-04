package com.oop.paint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Save extends PaintArea {

    JFileChooser chooser;
    List<Shape> all_shapes;

    public Save(List<Shape> myShapes) {
        chooser = new JFileChooser();
        this.all_shapes = myShapes;
    }

    public String getSavePath() {
        String path;
        int status = chooser.showSaveDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            path = file.getAbsolutePath();
        } else {
            path = null;
        }
        return path;
    }

    public void saveAsJson() {
        String path = getSavePath();
        path += ".json";
        if (path == null) {
            return;
        }
        int size = all_shapes.size();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("size", "" + size);
        JSONArray array;
        Shape shape = new Shape() {
        };
        for (int i = 1; i <= size; i++) {
            shape = all_shapes.get(i - 1);
            array = new JSONArray();
            if (shape.shapeName.equals("Line")) {
                array.add("line");
                array.add(((Line) shape).getFirst().getX() + "");
                array.add(((Line) shape).getFirst().getY() + "");
                array.add(((Line) shape).getSecond().getX() + "");
                array.add(((Line) shape).getSecond().getY() + "");
            } else if (shape.shapeName.equals("Circle")) {
                array.add("circle");
                array.add(((Circle) shape).getCenter().getX() + "");
                array.add(((Circle) shape).getCenter().getY() + "");
                array.add(((Circle) shape).getLowerRight().getX() + "");
                array.add(((Circle) shape).getLowerRight().getY() + "");
            } else if (shape.shapeName.equals("Oval")) {
                array.add("oval");
                array.add(((Oval) shape).getUpperLeft().getX() + "");
                array.add(((Oval) shape).getUpperLeft().getY() + "");
                array.add(((Oval) shape).getLowerRight().getX() + "");
                array.add(((Oval) shape).getLowerRight().getY() + "");
            } else if (shape.shapeName.equals("Rectangle")) {
                array.add("rectangle");
                array.add(((Rectangle) shape).getUpperLeft().getX() + "");
                array.add(((Rectangle) shape).getUpperLeft().getY() + "");
                array.add(((Rectangle) shape).getDownRight().getX() + "");
                array.add(((Rectangle) shape).getDownRight().getY() + "");
            } else if (shape.shapeName.equals("Square")) {
                array.add("square");
                array.add(((Square) shape).getCenter().getX() + "");
                array.add(((Square) shape).getCenter().getY() + "");
                array.add(((Square) shape).getEdgePoint().getX() + "");
                array.add(((Square) shape).getEdgePoint().getY() + "");
            } else if (shape.shapeName.equals("Triangle")) {
                array.add("triangle");
                array.add(
                        ((Triangle) shape).getPoints().get(0).getX() + "");
                array.add(
                        ((Triangle) shape).getPoints().get(0).getY() + "");
                array.add(
                        ((Triangle) shape).getPoints().get(1).getX() + "");
                array.add(
                        ((Triangle) shape).getPoints().get(1).getY() + "");
                array.add(
                        ((Triangle) shape).getPoints().get(2).getX() + "");
                array.add(
                        ((Triangle) shape).getPoints().get(2).getY() + "");
            }
            if(shape.getColor()!=null){
            	int rgbValues[] = shape.getColorValue();
                array.add(rgbValues[0]+"");
                array.add(rgbValues[1]+"");
                array.add(rgbValues[2]+"");
            }
            jsonObject.put("" + i, array);
        }
        try {
            FileWriter file = new FileWriter(path);
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAsXml() {
        String path = getSavePath();
        path += ".xml";
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory
                    .newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("Shapes");
            rootElement.setAttribute("size", all_shapes.size() + "");

            document.appendChild(rootElement);

            Shape shape = null;
            int size = all_shapes.size();

            for (int i = 0; i < size; i++) {
                shape = all_shapes.get(i);
                int rgcValues[];
                if(shape.getColor()==null){
                	rgcValues = null;
                }
                else{
                	rgcValues = shape.getColorValue();
                }
                if (shape.shapeName.equals("Line")) {
                    rootElement.appendChild(
                            setShapeProperties(document, "line", i+1 + "",
                                    ((Line) shape).getFirst().getX() + "",
                                    ((Line) shape).getFirst().getY() + "",
                                    ((Line) shape).getSecond().getX() + "",
                                    ((Line) shape).getSecond().getY() + "",
                                    null, null,rgcValues));
                } else if (shape.shapeName.equals("Circle")) {
                    rootElement.appendChild(setShapeProperties(document,
                            "circle", i+1 + "",
                            ((Circle) shape).getCenter().getX() + "",
                            ((Circle) shape).getCenter().getY() + "",
                            ((Circle) shape).getLowerRight().getX() + "",
                            ((Circle) shape).getLowerRight().getY() + "",
                            null, null,rgcValues));
                } else if (shape.shapeName.equals("Oval")) {
                    rootElement.appendChild(setShapeProperties(document,
                            "oval", i+1 + "",
                            ((Oval) shape).getUpperLeft().getX() + "",
                            ((Oval) shape).getUpperLeft().getY() + "",
                            ((Oval) shape).getLowerRight().getX() + "",
                            ((Oval) shape).getLowerRight().getY() + "",
                            null, null,rgcValues));
                } else if (shape.shapeName.equals("Rectangle")) {
                    rootElement.appendChild(setShapeProperties(document,
                            "rectangle", i+1 + "",
                            ((Rectangle) shape).getUpperLeft().getX() + "",
                            ((Rectangle) shape).getUpperLeft().getY() + "",
                            ((Rectangle) shape).getDownRight().getX() + "",
                            ((Rectangle) shape).getDownRight().getY() + "",
                            null, null,rgcValues));
                } else if (shape.shapeName.equals("Square")) {
                    rootElement.appendChild(setShapeProperties(document,
                            "square", i+1 + "",
                            ((Square) shape).getCenter().getX() + "",
                            ((Square) shape).getCenter().getY() + "",
                            ((Square) shape).getEdgePoint().getX() + "",
                            ((Square) shape).getEdgePoint().getY() + "",
                            null, null,rgcValues));
                } else if (shape.shapeName.equals("Triangle")) {
                    rootElement.appendChild(setShapeProperties(document,
                            "triangle", i+1 + "",
                            ((Triangle) shape).getPoints().get(0).getX()
                                    + "",
                            ((Triangle) shape).getPoints().get(0).getY()
                                    + "",
                            ((Triangle) shape).getPoints().get(1).getX()
                                    + "",
                            ((Triangle) shape).getPoints().get(1).getY()
                                    + "",
                            ((Triangle) shape).getPoints().get(2).getX()
                                    + "",
                            ((Triangle) shape).getPoints().get(2).getY()
                                    + "",rgcValues));
                }

            }

            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult file = new StreamResult(new File(path));

            transformer.transform(source, file);

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static Node setShapeProperties(Document doc, String shapeName,
            String shapeId, String x1, String y1, String x2, String y2,
            String x3, String y3,int rgbValues[]) {

        Element element = doc.createElement(shapeName);
        element.setAttribute("id", shapeId);

        element.appendChild(setProperty(doc, "x1", x1));
        element.appendChild(setProperty(doc, "y1", y1));
        element.appendChild(setProperty(doc, "x2", x2));
        element.appendChild(setProperty(doc, "y2", y2));
        if (shapeName.equals("triangle")) {
            element.appendChild(setProperty(doc, "x3", x3));
            element.appendChild(setProperty(doc, "y3", y3));
        }
        if(rgbValues!=null){
        	element.appendChild(setProperty(doc, "r", rgbValues[0]+""));
            element.appendChild(setProperty(doc, "g", rgbValues[1]+""));
            element.appendChild(setProperty(doc, "b", rgbValues[2]+""));
        }
        return element;
    }

    private static Node setProperty(Document doc, String name,
            String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}