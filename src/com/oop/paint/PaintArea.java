package com.oop.paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class PaintArea extends JPanel implements ActionListener {

    // buttons keys strings.
    // to detect selected buttons and take actions on them.
    final private String BACK = "back";
    final private String NEXT = "next";
    final private String LINE = "line";
    final private String CIRCLE = "circle";
    final private String OVAL = "oval";
    final private String SQUARE = "square";
    final private String RECTANGLE = "rectangle";
    final private String TRIANGLE = "triangle";
    final private String FILL = "fill";
    final private String DELETE = "delete";
    final private String SAVEJSON = "saveJson";
    final private String LOADJSON = "loadJson";
    final private String SAVEXML = "saveXml";
    final private String LOADXML = "loadXml";
    final private String PLUG_IN = "plug_in";

    // other attributes.
    private String selectedButton = "No";
    private boolean dragging, drawTriangle, resizing, moving;
    private Point startDrag, endDrag, triangle1, triangle2, triangle3, resizingPoint;
    private Shape current, selectedShape;
    private ArrayList<Shape> allShapes;
    JToolBar toolBar ;
    private int currentState, lastState;
    private List<ArrayList<Shape>> allStates;
    Color chosenColor;
    JFileChooser chooser;
    
    public PaintArea() {
        super(new BorderLayout());
        allShapes = new ArrayList<Shape>();
        allStates = new ArrayList<ArrayList<Shape>>();
        allStates.add(new ArrayList<Shape>());
        currentState = lastState = 0;
        selectedShape = null;
        toolBar = new JToolBar("Buttons");
        addButtons(toolBar);
        add(toolBar, BorderLayout.PAGE_START);
        chooser = new JFileChooser();
        
        drawTriangle = dragging = resizing = moving = false;
        startDrag = endDrag = triangle1 = triangle2 = triangle3 = resizingPoint = null;
        chosenColor = null;
        
        mouseMotion();
    }

 // add shapes and actions buttons to the tool bar.
    protected void addButtons(JToolBar toolBar) {
        System.out.println("here1");
    	JButton button = null;
        button = makeNavigationButton("8", BACK, "Back");
        toolBar.add(button);
        button = makeNavigationButton("9", NEXT, "Next");
        toolBar.add(button);
        toolBar.addSeparator();
        toolBar.addSeparator();
        toolBar.addSeparator();
        JLabel jLabel = new JLabel("Shapes");
        toolBar.add(jLabel);
        toolBar.addSeparator();
        button = makeNavigationButton("1", LINE,
                "click then drag to draw a LINE");
        toolBar.add(button);
        button = makeNavigationButton("2", CIRCLE,
                "click then drag to draw a CIRCLE");
        DynamicLoad dynamicLoad = new DynamicLoad(new Point(1, 2),new Point(5, 6) , null, "Circle");
        toolBar.add(button);
        if(dynamicLoad.checkException()){
        	System.out.println("here2");
        	button.setEnabled(false);
        }
        button = makeNavigationButton("3", OVAL,
                "click then drag to draw a OVAL");
        toolBar.add(button);
        button = makeNavigationButton("4", RECTANGLE,
                "click then drag to draw a RECTANGLE");
        toolBar.add(button);
        button = makeNavigationButton("5", SQUARE,
                "click then drag to draw a SQUARE");
        toolBar.add(button);
        dynamicLoad = new DynamicLoad(new Point(1, 2),new Point(5, 6) , null, "Square");
        toolBar.add(button);
        if(dynamicLoad.checkException()){
        	System.out.println("here2");
        	button.setEnabled(false);
        }
        button = makeNavigationButton("6", TRIANGLE,
                "choose three points to draw TRIANGLE");
        toolBar.add(button);
        toolBar.addSeparator();
        button = makeNavigationButton("12", DELETE, "Delete");
        toolBar.add(button);
        toolBar.addSeparator();
        toolBar.addSeparator();
        toolBar.addSeparator();
        jLabel = new JLabel("Color");
        toolBar.add(jLabel);
        toolBar.addSeparator();
        button = makeNavigationButton("7", FILL, "choose a color");
        toolBar.add(button);
        toolBar.addSeparator();
        toolBar.addSeparator();
        jLabel = new JLabel("Json");
        toolBar.add(jLabel);
        toolBar.addSeparator();
        button = makeNavigationButton("13", SAVEJSON, "Save");
        toolBar.add(button);
        button = makeNavigationButton("14", LOADJSON, "Load");
        toolBar.add(button);
        toolBar.addSeparator();
        toolBar.addSeparator();
        jLabel = new JLabel("Xml");
        toolBar.add(jLabel);
        toolBar.addSeparator();
        button = makeNavigationButton("13", SAVEXML, "Save");
        toolBar.add(button);
        button = makeNavigationButton("14", LOADXML, "Load");
        toolBar.add(button);
        toolBar.addSeparator();
        toolBar.addSeparator();
        button = makeNavigationButton("15", PLUG_IN, "import shapes");
        toolBar.add(button);
    }

    protected JButton makeNavigationButton(String imageName,
            String actionCommand, String toolTipText) {

        JButton button = new JButton();
        try {
            String img = "btn" + imageName + ".png";
            ImageIcon btn = new ImageIcon(
                    getClass().getClassLoader().getResource(img));
            button.setIcon(btn);
        } catch (Exception e) {
            button.setText(actionCommand);
        }
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);
        button.setSize(20, 20);
        return button;
    }

    public void actionPerformed(ActionEvent event) {
        selectedButton = event.getActionCommand();
        System.out.println(selectedButton);
        try{
        // Handle each button.
        if (LINE.equalsIgnoreCase(selectedButton)) { // first button clicked
        	//System.out.println("line here");
        	current = new Line(startDrag, endDrag);
            //System.out.println("line here");
        } else if (CIRCLE.equals(selectedButton)) { // second button clicked
            //System.out.println("circle here");
        	DynamicLoad dynamicLoad = new DynamicLoad(startDrag, endDrag, null, "Circle");
        	current = dynamicLoad.getShapee();
        } else if (OVAL.equals(selectedButton)) { // third button clicked
            current = new Oval(startDrag, endDrag);
        } else if (SQUARE.equals(selectedButton)) { // third button clicked
        	DynamicLoad dynamicLoad = new DynamicLoad(startDrag, endDrag, null, "Square");
        	current = dynamicLoad.getShapee();
        } else if (RECTANGLE.equals(selectedButton)) { // third button clicked
        	current = new Rectangle(startDrag, endDrag);
            
        } else if (TRIANGLE.equals(selectedButton)) { // third button clicked
            drawTriangle = true;
            current = null;
            selectedShape = null;
            return;
        } else if (FILL.equals(selectedButton)) {
            chosenColor = selectedColor();
            current= null;
            executeActionOnShape(selectedShape);
        } else if (BACK.equals(selectedButton)) {
            current = null;
        	undo();
        } else if (NEXT.equals(selectedButton)) {
        	current = null;
        	redo();
        } else if (DELETE.equals(selectedButton)) {
        	current = null;
        	executeActionOnShape(selectedShape);
        } else if (SAVEJSON.equals(selectedButton)) {
            Save save = new Save(allShapes);
            save.saveAsJson();
            drawAllShapes();
        } else if (LOADJSON.equals(selectedButton)) {
            Load load2 = new Load();
            List<Shape> list2;
            list2 = load2.loadAsJson();
            if (list2 != null) {
                allShapes = new ArrayList<Shape>(list2);
                currentState = 0;
                lastState = currentState;
                for (ArrayList<Shape> state : allStates) {
                    if (state != null)
                        state.clear();
                }
                ArrayList<Shape> state = (ArrayList<Shape>) allShapes
                        .clone();
                allStates.add(0, state);
            }
            drawAllShapes();
        }else if (SAVEXML.equals(selectedButton)) {
            System.out.println("here1");
            Save save = new Save(allShapes);
            save.saveAsXml();
            drawAllShapes();

        } else if (LOADXML.equals(selectedButton)) {
            Load load2 = new Load();
            List<Shape> list2;
            list2 = load2.loadAsXml();
            if (list2 != null) {
                allShapes = new ArrayList<Shape>(list2);
                currentState = 0;
                lastState = currentState;
                for (ArrayList<Shape> state : allStates) {
                    if (state != null)
                        state.clear();
                }
                ArrayList<Shape> state = (ArrayList<Shape>) allShapes
                        .clone();
                allStates.add(0, state);
            }
            drawAllShapes();

        } else if (PLUG_IN.equals(selectedButton)) {
            MyClassLoader classLoader = new MyClassLoader();
            classLoader.loader();
            DynamicLoad dynamicLoad = new DynamicLoad(new Point(1,2) , new Point(1, 2),null,"Square");
            if(!dynamicLoad.checkException()){
            	toolBar.getComponentAtIndex(11).setEnabled(true);
            }
            dynamicLoad = new DynamicLoad(new Point(1,2) , new Point(1, 2),null,"Circle");
            if(!dynamicLoad.checkException()){
            	toolBar.getComponentAtIndex(8).setEnabled(true);
            }
            
            drawAllShapes();
        }
        selectedShape = null;
        drawTriangle = false;
        triangle1 = triangle2 = triangle3 = null;
        }
        catch (NullPointerException e){
        	
        }
    }
    
    public Color selectedColor() {
        Color initialcolor = Color.RED;
        Color color = JColorChooser.showDialog(this, "Select a color",
                initialcolor);
        return color;
    }

    private void mouseMotion() {
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent m) {
                if(TRIANGLE.equalsIgnoreCase(selectedButton))return;
            	startDrag = endDrag = m.getPoint();
                if(selectedShape!=null&&holdShape(selectedShape,m.getPoint())){
                	moving = true;
                	return;
                }
                if(selectedShape!=null&&holdResizePoints(selectedShape,m.getPoint())){
                	resizing = true;
                	return;
                }
                dragging = true;
            }

            public void mouseReleased(MouseEvent m) {
                if (drawTriangle)
                    return;
                addShape(current);
                if(moving){
                	if(endDrag!=startDrag){
                		allShapes.remove(selectedShape);
                		allStates.add(currentState, (ArrayList<Shape>)allShapes.clone());
                	}
                
                }
                if(resizing){
                	if(endDrag!=startDrag){
                		allShapes.remove(selectedShape);
                		allStates.add(currentState, (ArrayList<Shape>)allShapes.clone());
                	}
                }
                current = null;
                dragging = false;
                moving = false;
                resizing = false;
                startDrag = endDrag = null;
                selectedButton = null;
                selectedShape = null;
            }

            public void mouseClicked(MouseEvent m) {
                if (drawTriangle) {
                    if (triangle1 == null)
                        triangle1 = m.getPoint();
                    else if (triangle2 == null)
                        triangle2 = m.getPoint();
                    else if (triangle3 == null)
                        triangle3 = m.getPoint();
                    System.out.println("detect");
                    detectAction();
                } else{
                	selectShape(m.getPoint());
                }
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!dragging&&!moving&&!resizing)
                    return;
                //System.out.println("mouse dragged");
                endDrag = e.getPoint();
                if(moving){
                	//System.out.println("moving");
                	current = selectedShape.move(endDrag);
                	//System.out.println(current.center);
                
                	drawAllShapes();
                	return;
                }
                if(resizing){
                	//System.out.println("enter resizing");
                	current = selectedShape.resize(resizingPoint,endDrag);
                	//System.out.println(current.center);
                	
                	drawAllShapes();
                	return;
                }
                //System.out.println(current + "here");
                detectAction();
            }
        });
    }

    private void detectAction() {
        if (!dragging && !drawTriangle){
        	current = null;
        	return;
        }
            
        if (LINE.equalsIgnoreCase(selectedButton)) {
            current = new Line(startDrag, endDrag);

        } else if (CIRCLE.equalsIgnoreCase(selectedButton)) {
        	DynamicLoad dynamicLoad = new DynamicLoad(startDrag, endDrag, null, "Circle");
        	current = dynamicLoad.getShapee();
        } else if (OVAL.equalsIgnoreCase(selectedButton)) {
            current = new Oval(startDrag, endDrag);

        } else if (RECTANGLE.equalsIgnoreCase(selectedButton)) {
            current = new Rectangle(startDrag, endDrag);

        } else if (TRIANGLE.equalsIgnoreCase(selectedButton)) {
            if (triangle1 != null && triangle2 != null
                    && triangle3 != null) {
                System.out.println("here in triangle");
            	ArrayList<Point> pts = new ArrayList<Point>();
                pts.add(triangle1);
                pts.add(triangle2);
                pts.add(triangle3);
                current = new Triangle(pts);
                addShape(current);
                current = null;
                triangle1 = triangle2 = triangle3 = null;
                pts.clear();
                drawTriangle = false;
            }
            drawAllShapes();
            return;

        } else if (SQUARE.equalsIgnoreCase(selectedButton)) {
//            current = new Square(startDrag, endDrag);        	
        	DynamicLoad dynamicLoad = new DynamicLoad(startDrag, endDrag, null, "Square");
        	current = dynamicLoad.getShapee();
        }
        drawTriangle = false;
        triangle1 = triangle2 = triangle3 = null;
        repaint();
    }

    public void addShape(Shape curr) {
        System.out.println(curr);
    	if (curr == null) {
    		 System.out.println("aa");
    		repaint();
            return;
        }
        if(!curr.shapeName.equals("Triangle")){
        	 System.out.println("cccc");
        	if(curr.getStartDrag()==null || curr.getEndDrag()==null){
        		System.out.println("bbb");
        		repaint();
        		return;
        	}
        }
        System.out.println("add shape!");
        makeNewState(curr);
        currentState++;
        lastState = currentState;
        repaint();
    }

    private void drawAllShapes() {
        repaint();
    }

    protected void paintComponent(Graphics g) {
        
    	super.paintComponent(g);
    	if (current != null) {
            current.draw(g);
        }
        //System.out.println("here in paint : currentState = " + currentState);
        //int counter = 0;
    	
        for (Shape q : allShapes) {
            if (q == null) {
                allShapes.remove(q);
                continue;
            }
            //counter++;
            if(q!=selectedShape||selectedShape==null){
            	q.selected = false;
            }
            q.draw(g);
            //Graphics2D g2d = (Graphics2D)g;
        	//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
    }

    private void undo() {
    	current = null;
    	System.out.println("current state = " + currentState);
    	if(currentState==0){
    		allShapes = (ArrayList<Shape>)(allStates.get(currentState).clone());
    		drawAllShapes();
    		return;
    	}
    	allShapes = (ArrayList<Shape>)(allStates.get(currentState-1).clone());
    	currentState--;
    	drawAllShapes();
    }

    private void redo() {
    	current = null;
    	System.out.println("current state = " + currentState);
    	System.out.println("last state = " + lastState);
    	if(currentState==lastState){
    		allShapes = (ArrayList<Shape>)(allStates.get(currentState).clone());
    		drawAllShapes();
    		return;
    	}
    	allShapes = (ArrayList<Shape>)(allStates.get(currentState+1).clone());
    	currentState++;
    	drawAllShapes();
    }
    
    private void selectShape(Point curr){
    	if(selectedShape!=null){
    		selectedShape.selected = false;
    	}
    	selectedShape = detectShape(curr);
    	if(selectedShape!=null){
    		selectedShape.selected = true;
    		//System.out.println("in selctShape shape is selected");
    	}
    	drawAllShapes();
    }
    
    private Shape detectShape(Point curr){
    	//System.out.println("in detectShape");
    	//System.out.println("in detectShape size = " + allShapes.size());
    	for(Shape e : allShapes){
    		if(e==null){
    			allShapes.remove(e);
    			continue;
    		}
    		//small rectangle conatins this point
    		if((e.makeShapeToJava()).intersects((curr.getX()-0.5), (curr.getY()-0.5),1, 1)){
    			//System.out.println("in detectShape found shape contains point");
    			return e;
    		}	
    	}
		return null;
    }
    
    private void executeActionOnShape(Shape curr){
    	if(curr==null || selectedButton==null)return;
    	if(selectedButton.equalsIgnoreCase(DELETE)){
    		//System.out.println("deleted!");
    		allShapes.remove(curr);
    		ArrayList<Shape> myCurrentState = (ArrayList<Shape>)(allShapes.clone());
    		allStates.add(currentState+1,myCurrentState);
    		currentState++;
    		lastState = currentState;
    		selectedShape = null;
    		drawAllShapes();
    	
    	} else if(selectedButton.equalsIgnoreCase(FILL)){
    		if(chosenColor==null){
    			curr.selected = false;
    			drawAllShapes();
    			return;
    		}
    		curr.selected = false;
    		allShapes.remove(curr);
    		Shape newShape = curr.makeColor(chosenColor);
    		addShape(newShape);
    		lastState = currentState;
    		selectedShape = null;
    		chosenColor = null;
    		drawAllShapes();
    	} 
    }
    
    void makeNewState(Shape newShape){
    	ArrayList<Shape> myCurrentState = (ArrayList<Shape>)allStates.get(currentState).clone();
    	//list = allStates.get(currentState).clone();
    	myCurrentState.add(newShape);
    	allStates.add(currentState+1,myCurrentState);
    	if(selectedShape!=null&&chosenColor!=null){
    		allStates.get(currentState+1).remove(selectedShape);
    	}
    	allShapes = (ArrayList<Shape>)myCurrentState.clone();
    }

    private boolean holdShape(Shape selectedShape, Point hold){
    	return selectedShape.getCenterRect().contains(hold);
    }
    
    private boolean holdResizePoints(Shape selectedShape, Point hold){
    	resizingPoint = selectedShape.detectResizingPoint(hold);
    	if(resizingPoint==null)return false;
    	return true;
    }
}