package algolearn.gui;

import algolearn.gui.info.errors;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class VisualisationHeapSort extends FXMLDocumentController implements Initializable {
    private boolean minHeap = false;
    private boolean generateInProgress = false;
    private boolean restartInProgress = false;
    private boolean backInProgress = false;
    private boolean nextInProgress = false;
    private boolean sortInProgress = false;
    
    private boolean buildingHeap = false;
    private boolean sortingHeap = false;
    
    private errors msg = new errors();
    
    private static final int radius = 20;
    private static final int small_radius = radius * 3 / 4;
    private static final boolean enable_repeating = true;
    
    private boolean operationInProgress() {
    	if(generateInProgress || restartInProgress || backInProgress ||
    	nextInProgress || sortInProgress) {
        	bhTEXT.setText(msg.setupInformation(msg.waitUntilDone));
    		return true;
    	}
    	return false;
    }
    
    private static final double [][] nodePositions = {
    		{400, 50+25},
    		{200, 110+25}, {600, 110+25},
    		{100, 170+25}, {300, 170+25}, {500, 170+25}, {700, 170+25},
    		{50, 230+25}, {150, 230+25}, {250, 230+25}, {350, 230+25}, {450, 230+25}, {550, 230+25}, {650, 230+25}, {750, 230+25}
    };
    
    private static final double [][] linesPositions = {
    		{380, 60+25, 210, 90+25, 1},
    		{420, 60+25, 590, 90+25, 0},
    		{180, 120+25, 110, 150+25, 1},
    		{220, 120+25, 290, 150+25, 0},
    		{580, 120+25, 510, 150+25, 1},
    		{620, 120+25, 690, 150+25, 0},
    		{80, 180+25, 60, 210+25, 1},
    		{120, 180+25, 140, 210+25, 0},
    		{280, 180+25, 260, 210+25, 1},
    		{320, 180+25, 340, 210+25, 0},
    		{480, 180+25, 460, 210+25, 1},
    		{520, 180+25, 540, 210+25, 0},
    		{680, 180+25, 660, 210+25, 1},
    		{720, 180+25, 740, 210+25, 0},
    };
    
    private static final double [][] nodeListPostions = {
    		{60, 310}, {110, 310}, {160, 310}, {210, 310}, {260, 310},
    		{310, 310}, {360, 310}, {410, 310}, {460, 310}, {510, 310},
    		{560, 310}, {610, 310}, {660, 310}, {710, 310}, {760, 310}
    };
    
    private static final double [][] nodeUnsortedListPostions = {
    		{60, 23}, {110, 23}, {160, 23}, {210, 23}, {260, 23},
    		{310, 23}, {360, 23}, {410, 23}, {460, 23}, {510, 23},
    		{560, 23}, {610, 23}, {660, 23}, {710, 23}, {760, 23}
    };
    
    @FXML private Button addBTN, sortBTN, heapsortBTN, minBTN, maxBTN, backBTN, minimalizeBTN, closeBTN;
    @FXML private TextField addField;
    @FXML private ProgressBar generateBar, restartBTN, next, prev; 
    @FXML private Text bhTEXT;
    @FXML private AnchorPane anchorPaneRoot, Visualisation_anchorPane;
    @FXML private Slider timeSlider;
    
    @FXML private void addValue(ActionEvent event) {
    	String getValue = addField.getText();
		addField.setText("");
		if(!analizeInput(getValue) || operationInProgress()) {
			return;
		}
		
		int value = Integer.parseInt(getValue);
		boolean duplicate = checkDuplicate(value);
		
		if((enable_repeating && duplicate) || !duplicate) {
			addUnsortedValue(value, false);
			drawTree();
			searchValues(value);
        	bhTEXT.setText(msg.setupOperationInfo(msg.addUnsorted, getValue));
		}else
        	bhTEXT.setText(msg.setupInformation(msg.HaveDuplicate));
    }
    private ArrayList<Integer> unsortedArray = new ArrayList<Integer>();
    
    private void addUnsortedValue(int value, boolean multiple) {
    	if(!enable_repeating) {
			if (checkDuplicate(value) == true)
				return;
		}
    	if(unsortedArray.size() >= heapMaxSize)
    		return;
    	
    	unsortedArray.add(value);
    	if(unsortedArray.size() >= 2 && !sortInProgress && !multiple)
    		sortBTN.setDisable(false);
    }
    
    private boolean checkDuplicate(int value) {
    	for(Integer x : unsortedArray)
    		if(x == value)
    			return true;
    	return false;
    }
    
    private int buildHeapStep = 0;
    private ArrayList<ArrayList<Integer>> steps = null;
    
    private void preBuildHeap() {
    	buildHeapStep = 0;
    	steps = new ArrayList<ArrayList<Integer>>();
    	for(int i = 0; i<unsortedArray.size(); i++)
    		steps.add(new ArrayList<Integer>());
    	
    	for(int i = 0; i<unsortedArray.size(); i++) {
    		insert(unsortedArray.get(i));
    		for(int j = 0; j<heapSize; j++) {
    			steps.get(i).add(heap[j]);
    		}
    	}
    	
    	for(int i = 0; i<heapMaxSize; i++)
    		heap[i] = 0;
    	
    	heapSize = 0;
    	
    }
    
    @FXML private void sortArray(ActionEvent event) {
		if(operationInProgress() || unsortedArray.size() < 2)
			return;
		buildingHeap = true;
		addBTN.setDisable(true);
		next.setDisable(false);
		sortBTN.setDisable(true);
		preBuildHeap();
		insert(unsortedArray.get(buildHeapStep++));
		drawTree();
		searchValues(unsortedArray.get(0));
    	bhTEXT.setText(msg.setupOperationInfo(msg.beginHeapBuild, msg.getStep(buildHeapStep, unsortedArray.size())));
	}
    
    int sortingDoneIN = 0;
    int sortingStep = 0;
    int [] sorted_heap = null;
    ArrayList<ArrayList<Integer>> sortResults = null;
    
    private boolean isSorted(ArrayList<Integer> arr) {
    	boolean sorted = true;        
        for (int i = 1; i < arr.size(); i++) 
            if (arr.get(i-1).compareTo(arr.get(i)) > 0) sorted = false;
    	
    	return sorted;
    }
    
    private void preCalculateSorting() {
    	sortingStep = 0;
    	sortResults = new ArrayList<ArrayList<Integer>>();
    	sorted_heap = new int [heapSize];
    	for(int i = 0; i<heapSize;i++)
    		sorted_heap[i] = heap[i];
    	
    	heapSort(sorted_heap, heapSize);
    	

        for(int i = 0; i<sortResults.size(); i++)
        	if(isSorted(sortResults.get(i)))
        		sortingDoneIN = i;
    	
    }
    
    @FXML private void sortHeap(ActionEvent event) {
		if(operationInProgress() || unsortedArray.size() < 2)
			return;
    	preCalculateSorting();
		sortingHeap = true;
		addBTN.setDisable(true);
		next.setDisable(false);
		heapsortBTN.setDisable(true);

		int ids = sortingStep;
		sortingStep++;
		for(int i = 0; i<heapSize; i++)
			heap[i] = sortResults.get(ids).get(i);
		
		drawTree();
		
    	bhTEXT.setText(msg.setupOperationInfo(msg.beginSort, msg.getStep(sortingStep, sortingDoneIN)));
   	
    }
    
    private void searchValues(int value) {
    	ArrayList<Integer> ids = new ArrayList<Integer>();
    	ArrayList<Integer> unsorted_ids = new ArrayList<Integer>();
    	
    	for(int i = 0; i<unsortedArray.size(); i++)
    		if(value == unsortedArray.get(i))
    			unsorted_ids.add(i);
    	
    	for(int i = 0; i<heapSize; i++)
    		if(value == heap[i])
    			ids.add(i);
    	
    	for(Integer i : ids) {
    		drawHighlightCircle(nodePositions[i][0], nodePositions[i][1], radius);
    		drawHighlightCircle(nodeListPostions[i][0], nodeListPostions[i][1], small_radius);
    	}
    	for(Integer i: unsorted_ids)
    		drawHighlightCircle(nodeUnsortedListPostions[i][0], nodeUnsortedListPostions[i][1], small_radius);
    	
    }
    
    private void drawHighlightCircle(double xx, double yy, int r) {
		Circle hintCricle = new Circle(xx, yy, r+5, new Color(0, 0, 0, 0));
		hintCricle.setStroke(Color.RED);
		hintCricle.setStrokeWidth(3.0);
		Visualisation_anchorPane.getChildren().add(hintCricle);
    }
    
    @FXML private void restartVisualisation() {
    	if(restartBTN.isDisabled() || operationInProgress())
    		return;
    	restartInProgress = true;
    	restartBTN.setProgress(0);
    	disableBTNs(false);
    	restartBTN.setDisable(false);
    	sortBTN.setDisable(true);
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
        	buildingHeap = false;
        	sortingHeap = false;
        	freeArrays();
        	bhTEXT.setText(msg.setupInformation(msg.restartHeapSort));
    	});
    	timeline.play();
    	
    }
    
    @FXML private void clickBack() {
    	if(prev.isDisabled() || operationInProgress())
    		return;
		prev.setProgress(0);
		prev.setDisable(false);
		backInProgress = true;
		if(buildingHeap) {
			buildHeapStep--;
			heapSize--;
			for(int i = 0; i<heapSize; i++)
				heap[i] = steps.get(buildHeapStep-1).get(i);
		}
		if(sortingHeap) {
    		int ids = sortingStep;
    		sortingStep--;
			for(int i = 0; i<heapSize; i++)
				heap[i] = sortResults.get(sortingStep-1).get(i);
		}
		Timeline timeline = new Timeline();
		KeyValue keyValue = new KeyValue(prev.progressProperty(), 1);
		KeyFrame keyFrame = new KeyFrame(new Duration(750), keyValue);
		timeline.getKeyFrames().add(keyFrame);
		timeline.setOnFinished(event->{
			backInProgress = false;
			if(buildingHeap) {
		    	drawTree();
		    	searchValues(unsortedArray.get(buildHeapStep-1));
				if(buildHeapStep < 2)
					prev.setDisable(true);
				if(next.isDisable())
					next.setDisable(false);
				bhTEXT.setText(msg.setupOperationInfo(msg.heapBuildInProgress, msg.getStep(buildHeapStep, unsortedArray.size())));
			}

	    	if(sortingHeap) {
				drawTree();
				if(sortingStep < 2)
					prev.setDisable(true);
				if(next.isDisable())
					next.setDisable(false);
				bhTEXT.setText(msg.setupOperationInfo(msg.sortInProgress, msg.getStep(sortingStep, sortingDoneIN)));
	    	}
		});
		timeline.play();
	}
    @FXML private void clickNext() {
    	if(next.isDisabled() || operationInProgress())
    		return;
    	
    	next.setProgress(0);
		next.setDisable(false);
		nextInProgress = true;
		Timeline timeline = new Timeline();
		KeyValue keyValue = new KeyValue(next.progressProperty(), 1);
		KeyFrame keyFrame = new KeyFrame(new Duration(750), keyValue);
		timeline.getKeyFrames().add(keyFrame);
		timeline.setOnFinished(event->{
			nextInProgress = false;
	    	if(buildingHeap) {
				insert(unsortedArray.get(buildHeapStep++));
				drawTree();
				searchValues(unsortedArray.get(buildHeapStep-1));
				if(buildHeapStep >= 2)
					prev.setDisable(false);
				
				if(buildHeapStep < unsortedArray.size())
					bhTEXT.setText(msg.setupOperationInfo(msg.heapBuildInProgress, msg.getStep(buildHeapStep, unsortedArray.size())));
				else {
					bhTEXT.setText(msg.setupOperationInfo(msg.endHeapSort, msg.getStep(buildHeapStep, unsortedArray.size())));
					next.setDisable(true);
					prev.setDisable(true);
					heapsortBTN.setDisable(false);
					buildingHeap = false;
				}
	    	}
	    	if(sortingHeap) {
	    		int ids = sortingStep;
	    		sortingStep++;
				for(int i = 0; i<heapSize; i++)
					heap[i] = sortResults.get(ids).get(i);
				
				drawTree();
				
				if(sortingStep >= 2)
					prev.setDisable(false);
				
				if(sortingStep < sortingDoneIN)
					bhTEXT.setText(msg.setupOperationInfo(msg.sortInProgress, msg.getStep(sortingStep, sortingDoneIN)));
				else {
					bhTEXT.setText(msg.setupOperationInfo(msg.endSort, msg.getStep(sortingStep, sortingDoneIN)));
					next.setDisable(true);
					prev.setDisable(true);
					heapsortBTN.setDisable(false);
					sortingHeap = false;
				}
	    	}
				

		});
		timeline.play();
	}
    
    @FXML private void pressRandomBar() {
    	if(generateBar.isDisabled() || operationInProgress())
    		return;
    	generateBar.setProgress(0);
    	disableBTNs(false);
    	generateBar.setDisable(false);
    	generateInProgress = true;
        freeArrays();
        randomNodes();
    	Timeline timeline = new Timeline();
    	KeyValue keyValue = new KeyValue(generateBar.progressProperty(), 1);
    	KeyFrame keyFrame = new KeyFrame(new Duration(1500), keyValue);
    	timeline.getKeyFrames().add(keyFrame);
    	timeline.setOnFinished(event->{
    		disableBTNs(true);
        	generateInProgress = false;
        	drawTree();
        	sortBTN.setDisable(false);
        	heapsortBTN.setDisable(true);
        	bhTEXT.setText(msg.setupInformation(msg.generateUnsortedArray));
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
					//CreateError(msg.OnlyNumeric);
					return false;
				}
			}
			return true;
		}
		//CreateError(msg.WrongInput);
		return false;
	}
    
    void disableBTNs(boolean disable) {
    	addBTN.setDisable(!disable);
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
		for(int i = 0; i<unsortedArray.size(); i++) {
			drawCircle(nodeUnsortedListPostions[i][0], nodeUnsortedListPostions[i][1], Integer.toString(unsortedArray.get(i)), small_radius);
			if(i>0)
				drawListArrow(nodeUnsortedListPostions, i, unsortedArray.get(i));
		}
		for(int i = 0, j = -1; i < heapSize; i++, j++) {
			drawCircle(nodePositions[i][0], nodePositions[i][1], Integer.toString(heap[i]), radius);
			drawCircle(nodeListPostions[i][0], nodeListPostions[i][1], Integer.toString(heap[i]), radius/4 *3);
			if(i>0) {
				drawArrow(linesPositions[j][0], linesPositions[j][1], linesPositions[j][2], linesPositions[j][3], (linesPositions[j][4] == 1)? true:false, Integer.toString(heap[j]));
				drawListArrow(nodeListPostions, i, heap[i]);
			}
		}
	}
	
    private void drawCircle(double x, double y, String nodeValue, double radius) {
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
    	if(radius == VisualisationHeapSort.radius) {
    		text.getStyleClass().add("textBST");
        	circle.getStyleClass().add("circle");
    	}else{
    		text.getStyleClass().add("textBST_small");
        	circle.getStyleClass().add("circle_small");
    	}
    		
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
    
    private void drawListArrow(double arr[][], int id, int value) {
    	int r = small_radius;
    	double xx = arr[id][0] -r, yy = arr[id][1];
    	double xx_prev = arr[id-1][0] +r, yy_prev = arr[id-1][1];
    	Line [] line = new Line[3];

    	line[0] = new Line(xx_prev, yy_prev, xx, yy);
    	line[1] = new Line(xx-8, yy-6, xx, yy);
    	line[2] = new Line(xx-8, yy+6, xx, yy);
    	
    	for(Line l : line) {
    		l.getStyleClass().add("line_small");
    		l.setId(Integer.toString(value));
    	}
		arrayLines.add(line);
		Visualisation_anchorPane.getChildren().addAll(line);
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
	    		addUnsortedValue(rnd_value, true);
	    	}
    	}
    	
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
    	unsortedArray = new ArrayList<Integer>();
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

    public void CreateError(String msg) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/error_fxml.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            BST_controller controller = (BST_controller) fxmlLoader.getController();
            Stage stage = new Stage();
            setStyle(stage);
            setMouse(root1, stage);
            stage.centerOnScreen();
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
            controller.errorTextArea.setText(msg);
            controller.errorTextArea.setStyle("-fx-text-fill: RED;-fx-font-weight:bold;");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //==================================  Binary heap implementation ==================================//
    
    private static final int d= 2;
    private static final int heapMaxSize = 15;
    private int[] heap = new int [heapMaxSize+1];
    private int heapSize = 0;
    
    private int parent(int i){
        return (i-1)/d;
    }
    
    private int LEFT(int i)
    {
        return (2 * i + 1);
    }
 
    private int RIGHT(int i)
    {
        return (2 * i + 2);
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
        if(minHeap)
        	heapify_down_min_it(x);
        else
        	heapify_down_max(x);
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
    
    private void heapify_down_max(int i) {
        int left = LEFT(i);
        int right = RIGHT(i);
        int largest = i;
        
        if (left < heapSize && heap[left] > heap[i])
            largest = left;
 
        if (right < heapSize && heap[right] > heap[largest])
            largest = right;
 
        if (largest != i){
            swap(heap, i, largest);
			heapify_down_max(largest);
        }
    }
    
    private void heapify_down_min_recur(int i) {
        int left = LEFT(i);
        int right = RIGHT(i);
        int smallest = i;

        if (left < heapSize && heap[left] < heap[i])
            smallest = left;

        if (right < heapSize && heap[right] < heap[smallest])
            smallest = right;
 
        if (smallest != i){
            swap(heap, i, smallest);
            heapify_down_min_recur(smallest);
        }
    }
    
    private void heapify_down_min_it(int i) {
        while(true){
	        int left = LEFT(i);
	        int right = RIGHT(i);
	        int smallest = i;
	        if (left < heapSize && heap[left] < heap[i])
	            smallest = left;
	
	        if (right < heapSize && heap[right] < heap[smallest])
	            smallest = right;
	 
	        if (smallest != i) {
	            swap(heap, i, smallest);
	            i = smallest;
	            drawTree();
	        }
	        else
	        	return;
        }
    }
    
    private void swap(int [] arr, int x,int y) {
    	int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
     
    void heapify(int arr[], int n, int i) 
    { 
        int smallest = i; 
        int l = 2 * i + 1;
        int r = 2 * i + 2;
      
        if (l < n && arr[l] > arr[smallest]) 
            smallest = l; 
      
        if (r < n && arr[r] > arr[smallest]) 
            smallest = r; 
        
        if (smallest != i) { 
            swap(arr, i, smallest); 
            heapify(arr, n, smallest); 
        } 
    } 
      
    void heapSort(int arr[], int n) 
    { 
        for (int i = n / 2 - 1; i >= 0; i--) 
            heapify(arr, n, i); 
      
        for (int i = n - 1, j = 0; i >= 0; i--, j++) {
            swap(arr, 0, i); 
            heapify(arr, i, 0);
            
            sortResults.add(new ArrayList<Integer>());
            for(int k = 0; k<n; k++)
            	sortResults.get(j).add(arr[k]);
            
        }
    } 

}