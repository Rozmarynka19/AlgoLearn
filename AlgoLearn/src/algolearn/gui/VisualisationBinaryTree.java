package algolearn.gui;

import algolearn.gui.info.errors;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


public class VisualisationBinaryTree extends FXMLDocumentController implements Initializable {
    private boolean minHeap = false, generateInProgress = false, restartInProgress = false;
    private errors msg = new errors();
    
    private static final double [] def_pos = {400, 50};
    private static final int radius = 20;
    private static final double [][] nodePositions = {
    		{400, 50},
    		{200, 110}, {600, 110},
    		{100, 170}, {300, 170}, {500, 170}, {700, 170},
    		{50, 230}, {150, 230}, {250, 230}, {350, 230}, {450, 230}, {550, 230}, {650, 230}, {750, 230}
    };
    
    private static final double [][] linesPositions = {
    		{380, 60, 210, 90, 1}, // 44
    		{420, 60, 590, 90, 0}, // 60
    		{180, 120, 110, 150, 1}, // 33
    		{220, 120, 290, 150, 0}, // 48
    		{580, 120, 510, 150, 1}, // 57
    		{620, 120, 690, 150, 0}, // 80
    		{80, 180, 60, 210, 1}, // 22
    		{120, 180, 140, 210, 0}, // 36
    		{280, 180, 260, 210, 1}, // 46
    		{320, 180, 340, 210, 0}, // 51
    		{480, 180, 460, 210, 1}, // 56
    		{520, 180, 540, 210, 0}, // 58
    		{680, 180, 660, 210, 1}, // 66
    		{720, 180, 740, 210, 0}, // null
    };
    
    @FXML private Button addBTN, deleteBTN, searchBTN, minBTN, maxBTN, backBTN, minimalizeBTN, closeBTN;
    @FXML private TextField addField, deleteField, searchField;
    @FXML private ProgressBar generateBar, restartBTN; 
    @FXML private Text bhTEXT;
    @FXML private AnchorPane anchorPaneRoot, Visualisation_anchorPane;
    
    @FXML private void addValue(ActionEvent event) {
    	String getValue = addField.getText();
		addField.setText("");
		if(!analizeInput(getValue) || generateInProgress || restartInProgress) {
			return;
		}
		
		int value = Integer.parseInt(getValue);
		insert(value);
		drawTree();
    }
    
    @FXML private void deleteValue(ActionEvent event) {
    	String getValue = deleteField.getText();
		deleteField.setText("");
		if(!analizeInput(getValue) || generateInProgress || restartInProgress) {
			return;
		}
		
		int value = Integer.parseInt(getValue);
		int id = findID(value);
		if(id > -1) {
			delete(id);
			drawTree();
		}
	}
    
    @FXML private void searchValue(ActionEvent event) {}
    
    @FXML private void restartVisualisation() {
    	if(restartBTN.isDisabled() || generateInProgress || restartInProgress)
    		return;
    	restartInProgress = true;
    	restartBTN.setProgress(0);
    	disableBTNs(false);
    	restartBTN.setDisable(false);
    	
    	Timeline timeline = new Timeline();
    	KeyValue keyValue = new KeyValue(restartBTN.progressProperty(), 1);
    	KeyFrame keyFrame = new KeyFrame(new Duration(1500), keyValue);
    	timeline.getKeyFrames().add(keyFrame);
    	timeline.setOnFinished(event->{
    		disableBTNs(true);
    		restartInProgress = false;
        	minBTN.setDisable(false);
        	maxBTN.setDisable(false);
        	disableBTNs(false);
        	freeArrays();
        	bhTEXT.setText(msg.setupInformation(msg.restartHeap));
    	});
    	timeline.play();
    	
    }
    
    @FXML private void pressRandomBar() {
    	if(generateBar.isDisabled() || generateInProgress || restartInProgress)
    		return;
    	generateBar.setProgress(0);
    	disableBTNs(false);
    	generateBar.setDisable(false);
    	generateInProgress = true;
    	Timeline timeline = new Timeline();
    	KeyValue keyValue = new KeyValue(generateBar.progressProperty(), 1);
    	KeyFrame keyFrame = new KeyFrame(new Duration(1500), keyValue);
    	timeline.getKeyFrames().add(keyFrame);
    	timeline.setOnFinished(event->{
    		disableBTNs(true);
        	generateInProgress = false;
        	freeArrays();
        	randomNodes();
        	drawTree();
        	bhTEXT.setText(msg.setupInformation(msg.generateHeap));
    	});
    	timeline.play();
    }
    
    @FXML private void selectTree(ActionEvent event) {
    	if(event.getSource().equals(minBTN))
    		minHeap = true;
    	else
    		minHeap = false;
    	
    	String treeType = minHeap ? msg.minTree : msg.maxTree;
    	
    	bhTEXT.setText(msg.setupOperationInfo(msg.selectedBinnaryTree, treeType));
    	disableBTNs(true);
    	minBTN.setDisable(true);
    	maxBTN.setDisable(true);
    }

	private boolean isCharNum(char x) {
		if(x == '0' || x == '1'|| x == '2'|| x == '3'|| x == '4'|| x == '5'|| x == '6'|| x == '7'|| x == '8'|| x == '9')
			return true;
		return false;
	}
    
    private boolean analizeInput(String in) {
		int len = in.length();
		if( len > 0 && len <= 2) {
			for(int i = 0; i<len; i++) {
				if(!isCharNum(in.charAt(i))) {
					//CreateError(errorMSG.OnlyNumeric);
					return false;
				}
			}
			return true;
		}
		//CreateError(errorMSG.WrongInput);
		return false;
	}
    
    void disableBTNs(boolean disable) {
    	addBTN.setDisable(!disable);
    	deleteBTN.setDisable(!disable);
    	searchBTN.setDisable(!disable);
    	generateBar.setDisable(!disable);
    	restartBTN.setDisable(!disable);
    }
    
    private Color[] colors = new Color[100];
    
    private Color randomColor() {
		Random rand = new Random();
	    double red = rand.nextDouble();
	    double green = rand.nextDouble();
	    double blue = rand.nextDouble();
		return new Color(red, green, blue, 1);
	}
    
	private ArrayList<Circle> arrayCircles = new ArrayList<Circle>();
	private ArrayList<Text> arrayTexts = new ArrayList<Text>();
	private ArrayList<Line[]> arrayLines = new ArrayList<Line[]>();
    
	private void drawTree() {
		freeDrawArrays();
		for(int i = 0, j = -1; i < heapSize; i++, j++) {
			drawCircle(nodePositions[i][0], nodePositions[i][1], Integer.toString(heap[i]));
			if(i>0)
				drawArrow(linesPositions[j][0], linesPositions[j][1], linesPositions[j][2], linesPositions[j][3], (linesPositions[j][4] == 1)? true:false, Integer.toString(heap[j]));
		}
	}
	
    private void drawCircle(double x, double y, String nodeValue) {
		Color color;
		if(colors[Integer.parseInt(nodeValue)] != null) {
			color = colors[Integer.parseInt(nodeValue)];
		}else {
			color = randomColor();
			colors[Integer.parseInt(nodeValue)] = color;
		}
		
		Circle circle = new Circle(x,y,radius, color);
		
		Text text = new Text(nodeValue);
		text.setBoundsType(TextBoundsType.VISUAL);
		text.setX(circle.getCenterX() - radius/2 - 2);
		text.setY(circle.getCenterY() + radius/2 - 2);
		
    	circle.getStyleClass().add("circle");
    	circle.setId(nodeValue);
    	
    	text.getStyleClass().add("textBST");
    	text.setId(nodeValue);
    	text.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, e->{
    		String id = text.getId();
    		for(Circle c : arrayCircles)
    			if(c.getId() == id) {
    				c.setOpacity(0.65);
    			}
    	});
    	text.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, e->{
    		String id = text.getId();
    		for(Circle c : arrayCircles)
    			if(c.getId() == id) {
    				c.setOpacity(1);
    			}
    	});

    	text.addEventHandler(MouseEvent.MOUSE_PRESSED, e->{
    		String id = text.getId();
    		for(Circle c : arrayCircles)
    			if(c.getId() == id) {
    				c.setOpacity(0.45);
    			}
    	});
    	
    	text.addEventHandler(MouseEvent.MOUSE_RELEASED, e->{
    		String id = text.getId();
    		for(Circle c : arrayCircles)
    			if(c.getId() == id) {
    				c.setOpacity(1);
    			}
    	});

		arrayCircles.add(circle);
		arrayTexts.add(text);
		Visualisation_anchorPane.getChildren().addAll(circle, text);
    }
    
    private void drawArrow(double x_start, double y_start, double x_end, double y_end, boolean left, String value) {
		Line [] line = new Line[3];

		line[0] = new Line(x_start, y_start, x_end, y_end);
		if(left) {
			line[1] = new Line(x_end,  y_end - 10, x_end, y_end);
			line[2] = new Line(x_end + 10, y_end + 3, x_end, y_end);
		}else {
			line[1] = new Line(x_end,  y_end - 10, x_end, y_end);
			line[2] = new Line(x_end - 10, y_end + 3, x_end, y_end);
		}
		
		for(Line l : line) {
			l.getStyleClass().add("line");
			l.setId(value);
		}
		
		
		arrayLines.add(line);
		Visualisation_anchorPane.getChildren().addAll(line);
	}
    
    private void randomNodes() {
    	Random rand = new Random();
    	boolean [] arr = new boolean[100];
    	Arrays.fill(arr, false);
    	for(int i = 0; i<10; i++) {
	    	int rnd_value = rand.nextInt(89) + 10;
	    	if(arr[rnd_value] == false) {
	    		arr[rnd_value] = true;
	    		insert(rnd_value);
	    	}
    	}
    	
    }
    
    private int findID(int val) {
    	for(int i = 0; i<heapSize; i++)
    		if(val == heap[i])
    			return i;
    	return -1;
    }
    
    private void freeDrawArrays() {
    	Visualisation_anchorPane.getChildren().clear();
    	arrayCircles = new ArrayList<Circle>();
    	arrayTexts = new ArrayList<Text>();
    	arrayLines = new ArrayList<Line[]>();
    }
    
    private void freeArrays() {
    	Visualisation_anchorPane.getChildren().clear();
    	arrayCircles = new ArrayList<Circle>();
    	arrayTexts = new ArrayList<Text>();
    	arrayLines = new ArrayList<Line[]>();
    	heap = new int[heapMaxSize+1];
    	heapSize = 0;
    }
    
    /**
     * Swaping into menu window from main_fxml.fxml by remove and add anchorPane
     */
    public void loadMenu()
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("fxml/main_fxml.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setScreen(anchorPane);
    }

    //==================================  Binary heap implementation ==================================//
    
    private static final int d= 2;
    private static final int heapMaxSize = 15;
    private int[] heap = new int [heapMaxSize+1];
    private int heapSize = 0;
    
    private int parent(int i){
        return (i-1)/d;
    }
     
    private int iChild(int i,int k){
        return d*i+k;
    }
     
    public void insert(int x){
    	if(heapSize == heap.length -1)
    		return;
        heap[heapSize++] = x;
        heapifyUp(heapSize-1);
    }
     
    public int delete(int x){
        int key = heap[x];
        heap[x] = heap[heapSize -1];
        heapSize--;
        heapifyDown(x);
        return key;
    }
    private boolean ifHeapUp(int i, int temp) {
    	if(minHeap == false)
    		return (temp > heap[parent(i)]);
    	else
    		return (temp < heap[parent(i)]);
    }
    
    private void heapifyUp(int i) {
        int temp = heap[i];
        while(i>0 && ifHeapUp(i, temp)){
            heap[i] = heap[parent(i)];
            i = parent(i);
        }
        heap[i] = temp;
    }
    
    private boolean ifHeapDown(int i) {
    	if(minHeap == false) 
    		return (iChild(i, 1) < heapSize);
    	else
    		return (iChild(i, 1) > heapSize);
    }
    
    private void heapifyDown(int i){
        int child;
        int temp = heap[i];
        while(ifHeapDown(i)){
            child = (minHeap) ? minChild(i) : maxChild(i);
            if(temp < heap[child]){ heap[i] = heap[child]; }
            else break; i = child; 
            }
        heap[i] = temp;
    }
    
    private int maxChild(int i) { 
    	int leftChild = iChild(i, 1); 
    	int rightChild = iChild(i, 2);
    	return heap[leftChild]>heap[rightChild]?leftChild:rightChild;
    }
    
    private int minChild(int i) {
    	int leftChild = iChild(i, 1); 
    	int rightChild = iChild(i, 2);
    	return heap[leftChild]<heap[rightChild]?leftChild:rightChild;
    }
     
    public void printHeap()
        {
            System.out.print("Heap = ");
            for (int i = 0; i < heapSize; i++)
                System.out.print(heap[i] +" ");
            System.out.println();
        }

}