package algolearn.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import algolearn.gui.info.errors;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

public class VisualisationControllerGrahamScan extends FXMLDocumentController {
	
	public class AlgoNode
	{
		public double x,y,angle;
		public NodeStatus status;
		
		public AlgoNode()
		{
			x=0;
			y=0;
			angle=0;
			status = NodeStatus.NOT_VISITED;
		}
		
		public AlgoNode(Button node)
		{
			x=node.getLayoutX();
			y=visAnchorPane.getPrefHeight()-padding-node.getLayoutY();
			angle = 0;
			status = NodeStatus.NOT_VISITED;
		}
	}
	public enum NodeStatus
	{
		VISITED,NOT_VISITED,HULL;		
	}
	
	private boolean generateDone = true;
	private boolean deleteInProgress = false;
	private boolean pathTransitionDone = true;
	private errors.rbtMsg msg = new errors.rbtMsg();
	
    private final int minInputRange = 1;
    private final int maxInputRange = 99;
    private final int maxRandomNodes = 15;
    private final double padding = 40;
    private final int maxSafetyIterations = 1000;
    private int nextButtonClickedIterator=0;
    private final int verticalBias = 20;
    private final int horizontalBias = 20;
    private ArrayList<Button> nodes = new ArrayList<Button>();
    private ArrayList<Integer> indexesOfHullNodes = new ArrayList<Integer>();
    private ArrayList<Pair<Button,AlgoNode>> algoNodes = new ArrayList<Pair<Button,AlgoNode>>();

	@FXML AnchorPane visAnchorPane;
	@FXML Button addButton;
	@FXML Button deleteButton;
	@FXML Button restartButton;
	@FXML Button backButton;
	@FXML Button nextButton;
	@FXML Button startAlgoButton;
	@FXML TextField insertTextField;
	@FXML TextField deleteTextField;
	@FXML ProgressBar generateBar;
	
	//--------- Msg box stuff ----------
	@FXML Text txtMainTitle;
	@FXML TextArea msgTextArea;
	
	//---------- Infoline stuff ----------
	@FXML Text info;
	@FXML ScrollPane infoScrollPane;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		if (infoScrollPane == null)
			return;
		infoScrollPane.getStyleClass().clear();
		infoScrollPane.getStyleClass().add("infoScrollPane");
		
		nextButton.setDisable(true);
		startAlgoButton.setDisable(true);
	}
	
	@FXML
	public void insert(ActionEvent event)
	{
		int key = validateInput(insertTextField);
		
		if(key<0)
			return;
		else
        {
			if (isSuchNode(key,false))
			{
				System.out.println("insert: juz jest węzeł w grafie o kluczu "+String.valueOf(key));
				//TODO: messagebox - juz jest wezel w grafie o takim kluczu
				return;
			}
				
			Button newNode = new Button();
			if (findPlace(newNode))
			{
				newNode.setText(String.valueOf(key));
				nodes.add(newNode);
				System.out.println("insert: udalo sie dodac wezel o kluczu "+String.valueOf(key));
				//TODO: messagebox - udalo sie dodac wezel
			}
			
			if(startAlgoButton.isDisabled() && nodes.size()>=4)
				startAlgoButton.setDisable(false);
				
			display();            
        }
	}
	
	private void insert(int key)
	{
		Button newNode = new Button();
		if (findPlace(newNode))
		{
			newNode.setText(String.valueOf(key));
			nodes.add(newNode);
		}
		
		if(startAlgoButton.isDisabled() && nodes.size()>=4)
			startAlgoButton.setDisable(false);
	}
	
	private void display()
	{
		visAnchorPane.getChildren().clear();
		for(Button node: nodes)
		{
			 node.getStyleClass().clear();
			 node.getStyleClass().add("rbtNodeRed");
			 visAnchorPane.getChildren().add(node);
		}
	}
	
	private void displayGrahamScan()
	{
		visAnchorPane.getChildren().clear();
		for(Pair<Button,AlgoNode> node: algoNodes)
		{
			node.getKey().getStyleClass().clear();
			if(node.getValue().status.equals(NodeStatus.HULL))
				node.getKey().getStyleClass().add("rbtNodeBlack");
			else if (node.getValue().status.equals(NodeStatus.VISITED))
				node.getKey().getStyleClass().add("GrahamScanVisitedNode");
			else
				node.getKey().getStyleClass().add("rbtNodeRed");
			 visAnchorPane.getChildren().add(node.getKey());
		}
		for(int i=1;i<indexesOfHullNodes.size();i++)
		{
			Button prev = algoNodes.get(indexesOfHullNodes.get(i-1)).getKey();
			Button curr = algoNodes.get(indexesOfHullNodes.get(i)).getKey();
			visAnchorPane.getChildren().add(new Line(prev.getLayoutX()+horizontalBias, prev.getLayoutY()+verticalBias, 
					curr.getLayoutX()+horizontalBias,curr.getLayoutY()+verticalBias));
		}
	}
	
	private boolean isSuchNode(int key, boolean remove)
	{
		for(Button node:nodes)
		{
			if(Integer.valueOf(node.getText()) == key)
			{
				if (remove==true)
					nodes.remove(node);
				return true;
			}
				
		}
		return false;
	}

	private boolean findPlace(Button newNode)
	{
		double x,y;
		boolean tooClose;
		int safetyIterator = -1;
		
		do
		{
			x = (Math.random() * (visAnchorPane.getPrefWidth()-2*padding))+padding;
			y = (Math.random() * (visAnchorPane.getPrefHeight()-2*padding))+padding;
			tooClose = false;
			safetyIterator++;
			
			for(Button node: nodes)
			{
				if(x >= node.getLayoutX()-padding && x <= node.getLayoutX()+padding
						&& y >= node.getLayoutY()-padding && y <= node.getLayoutY()+padding)
					tooClose=true;
			}
//			System.out.println("safetyIterator: "+String.valueOf(safetyIterator));
		}
		while(tooClose && safetyIterator<=maxSafetyIterations);
		
		if(safetyIterator>=maxSafetyIterations)
		{
			System.out.println("insert: za duzo wezlow w grafie!");
			//TODO: messagebox - too many nodes
			createMessageBox(msg.msgErrorHeader, msg.acceptableValues, msg.msgTypeError);
			return false;
		}
		
		newNode.setLayoutX(x);
		newNode.setLayoutY(y);
		return true;
	}
	
	@FXML
	public void delete(ActionEvent event)
	{
		int key = validateInput(deleteTextField);
		
		if(key<0)
			return;
		else
        {
			if (!isSuchNode(key,true))
			{
				System.out.println("delete: nie ma w grafie wezla o kluczu "+String.valueOf(key));
				//TODO: messagebox - nie ma w grafie wezla o kluczu x
				return;
			}
			
			System.out.println("delete: udalo sie usunac wezel o kluczu "+String.valueOf(key));
			//TODO: messagebox - udalo sie usunac wezel
			display();            
        }
		
		if(!startAlgoButton.isDisabled() && nodes.size()<4)
			startAlgoButton.setDisable(true);
	}
	
    @FXML 
    public void pressRandomBar() 
    {
    	if(generateDone == false)
    		return;

    	if(deleteInProgress) {
			info.setText(msg.setupInformation(msg.delInProgress));
			return;
    	}
		
    	if(!pathTransitionDone) {
		createMessageBox(msg.msgErrorHeader, msg.pathTNotDone, msg.msgTypeError);
			return;
		}
    	
    	generateBar.setProgress(0);
    	
    	blockButtons(true);
    	
    	generateDone = false;
    	Timeline timeline = new Timeline();
    	KeyValue keyValue = new KeyValue(generateBar.progressProperty(), 1);
    	KeyFrame keyFrame = new KeyFrame(new Duration(1500), keyValue);
    	timeline.getKeyFrames().add(keyFrame);
    	timeline.setOnFinished(event->{
    		blockButtons(false);

        	restartVis();
        	generateGraph();
    		display();
        	generateDone = true;
        	info.setText(msg.setupInformation(msg.generateDone));
    	});
    	timeline.play();
    }

    
    private void generateGraph()
    {
    	int randomInteger;
    	for(int i=0;i<maxRandomNodes;i++)
    	{
    		randomInteger = (int)(Math.random()*(maxInputRange-1)+1);
            if (!isSuchNode(randomInteger,false))
	            insert(randomInteger);
        		
    	}
    }
    
    private void blockButtons(boolean areButtonsBlocked)
    {
    	addButton.setDisable(areButtonsBlocked);
    	deleteButton.setDisable(areButtonsBlocked);
    	restartButton.setDisable(areButtonsBlocked);
    	backButton.setDisable(areButtonsBlocked);
    	startAlgoButton.setDisable(areButtonsBlocked);
    	nextButton.setDisable(true);
    }
	
	@FXML
	public void restartVis(ActionEvent event)
	{
		restartVis();
	}
	
	private void restartVis()
	{
    	this.nodes.clear();
    	nextButtonClickedIterator=0;
    	indexesOfHullNodes.clear();
    	algoNodes.clear();
    	this.display();
    	startAlgoButton.setDisable(true);
    	addButton.setDisable(false);
    	deleteButton.setDisable(false);
    	System.out.println("restartVis: zrestartowano wizualizacje!");
    	info.setText(msg.setupInformation(msg.restartDone));
	}
	
    private int validateInput(TextField inputTextField)
    {
		 if(inputTextField.getText().length() == 0)
		 {
			 	createMessageBox(msg.msgErrorHeader, msg.valueNotGiven, msg.msgTypeError);
			 	inputTextField.clear();
			 	return -1;
		 }

    	int key;
    	try
    	{
    		key = Integer.parseInt(inputTextField.getText());
    	}
    	catch (Exception e)
    	{
    		createMessageBox(msg.msgErrorHeader, msg.acceptableValues, msg.msgTypeError);
    		inputTextField.clear();
    		return -1;
    	}
    	
    	if(key<minInputRange || key>maxInputRange)
    	{
    		createMessageBox(msg.msgErrorHeader, msg.acceptableValues, msg.msgTypeError);
    		inputTextField.clear();
    		return -1;
    	}
    	
    	inputTextField.clear();
    	return key;
    }
    
    private void createMessageBox(String header, String message, String msgBoxStyle)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/msg_fxml.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            FXMLDocumentController_RBT controller = (FXMLDocumentController_RBT)fxmlLoader.getController();
            Stage stage = new Stage();
            setStyle(stage);
            setMouse(root1, stage);
            stage.centerOnScreen();
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));  
            stage.show();
            controller.txtMainTitle.setText(header);
            controller.msgTextArea.setText(message);
            if(msgBoxStyle==msg.msgTypeError)
            {
            	controller.txtMainTitle.getStyleClass().add("errorHeader");
            	controller.msgTextArea.getStyleClass().add("errorText");
            }
            else if(msgBoxStyle==msg.msgTypeCongrats)
            {
            	controller.txtMainTitle.getStyleClass().add("congratsHeader");
            	controller.msgTextArea.getStyleClass().add("congratsText");
            }
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void next()
    {
    	nextButtonClickedIterator++;
    	grahamScan();
    }
    
    @FXML
    public void startAlgorithm()
    {
    	if(nodes.size()==0)
    	{
    		//TODO: messagebox - graf jest pusty
    		System.out.println("startAlgorithm: graf jest pusty!");
    		return;
    	}
    		
    	addButton.setDisable(true);
    	deleteButton.setDisable(true);
    	startAlgoButton.setDisable(true);
    	nextButton.setDisable(false);

    	//make copy of nodes
    	for(Button node:nodes)
    		algoNodes.add(new Pair<Button,AlgoNode>(node,new AlgoNode(node)));
    	
    	nextButtonClickedIterator=1;
    	grahamScan();
    }
    
    public static Comparator<Pair<Button,AlgoNode>> angleCOMPARE = new Comparator<Pair<Button,AlgoNode>>() {
		@Override
		public int compare(Pair<Button, AlgoNode> o1, Pair<Button, AlgoNode> o2) {
//			if(o1.getValue().x<o2.getValue().x && o1.getValue().y<o2.getValue().y)
//				return 1;
//			else if (o1.getValue().x>o2.getValue().x && o1.getValue().y>o2.getValue().y)
//				return -1;
//			else
//				return 0;
			return (int) (o1.getValue().angle-o2.getValue().angle);
		}
    };
    
    private void grahamScan()
    {
    	switch(nextButtonClickedIterator)
    	{
    	case 1:
        	//step 1. find left lowest node ( 0,0 of the screen is upper-left corner)
    		//and make that node as a new starting point of coordinate system
    		System.out.println("grahamScan: step1");
    		
    		int indexOfLowest=0;
    		for(int i=0;i<algoNodes.size();i++)
    		{
    			AlgoNode lowest = algoNodes.get(indexOfLowest).getValue();
    			AlgoNode current = algoNodes.get(i).getValue();
    			
				if(current.y<lowest.y || 
						(current.y==lowest.y && current.x<lowest.x) )
					indexOfLowest=i;
    		}
    		
    		algoNodes.get(indexOfLowest).getValue().status = NodeStatus.HULL;
    		
    		double lowestX = algoNodes.get(indexOfLowest).getValue().x;
    		double lowestY = algoNodes.get(indexOfLowest).getValue().y;
    		
    		for(Pair<Button,AlgoNode> node: algoNodes)
    		{
    			node.getValue().x -= lowestX;
    			node.getValue().y -= lowestY;
    		}
    		
    		displayGrahamScan();
    		return;
    	case 2:
        	//step 2. sort (ascending) nodes by angle from (1,0) vector (P2) y1*1-0*x1
    		System.out.println("grahamScan: step2");
//    		double x2=1,y2=0;
    		//calc angle
    		for(Pair<Button,AlgoNode> node : algoNodes)
    		{
//    			double result = node.getValue().y*x2-y2*node.getValue().x;
    			double angle = (float) Math.toDegrees(Math.atan2(node.getValue().y, node.getValue().x));
    		    if(angle < 0){
    		        angle += 360;
    		    }
    		    if(node.getValue().x<=0.0 && node.getValue().y<=0.0)
    		    	node.getValue().angle=-100.;
    		    else
    		    	node.getValue().angle=angle;
    		}
    		
    		Collections.sort(algoNodes,angleCOMPARE);   		
    		printAngles();
    		
    		displayGrahamScan();
    		return;
    	case 3:
        	//step 3. to the hull add index of starting point and first point from sorted list of nodes
    		System.out.println("grahamScan: step3");
    		algoNodes.get(1).getValue().status=NodeStatus.HULL;
    		indexesOfHullNodes.add(0);
    		indexesOfHullNodes.add(1);
    		displayGrahamScan();
    		return;
		default:
    		//step 4. loop
			System.out.println("grahamScan: default step");
			int indexOfCurrentHullNode=0;
			for(int i=0;i<algoNodes.size();i++)
			{
				if(algoNodes.get(i).getValue().status.equals(NodeStatus.NOT_VISITED))
				{
					algoNodes.get(i).getValue().status = NodeStatus.HULL;
					indexesOfHullNodes.add(i);
					indexOfCurrentHullNode=i;
					break;
				}
			}
			
			boolean turnRight=false;
			
			do
			{
				if(indexesOfHullNodes.size()<=2)
					break;
				int LastIndx = indexesOfHullNodes.get(indexesOfHullNodes.size()-1);
				int SecondToLastIndx = indexesOfHullNodes.get(indexesOfHullNodes.size()-2);
				int ThirdToLastIndx = indexesOfHullNodes.get(indexesOfHullNodes.size()-3);
				
				double LastX = algoNodes.get(LastIndx).getValue().x -  algoNodes.get(SecondToLastIndx).getValue().x;
				double LastY = algoNodes.get(LastIndx).getValue().y -  algoNodes.get(SecondToLastIndx).getValue().y;
				
				double SecondToLastX = algoNodes.get(SecondToLastIndx).getValue().x -  algoNodes.get(ThirdToLastIndx).getValue().x;
				double SecondToLastY = algoNodes.get(SecondToLastIndx).getValue().y -  algoNodes.get(ThirdToLastIndx).getValue().y;
				
    			double LastAngle = (float) Math.toDegrees(Math.atan2(LastY, LastX));
    		    if(LastAngle < 0){
    		    	LastAngle += 360;
    		    }
    		    
       			double SecondToLastAngle = (float) Math.toDegrees(Math.atan2(SecondToLastY, SecondToLastX));
    		    if(SecondToLastAngle < 0){
    		    	SecondToLastAngle += 360;
    		    }
    		    
    		    if(LastAngle>SecondToLastAngle)
    		    {
    		    	turnRight=false;
    		    }
    		    else
    		    {
    		    	System.out.println("grahamScan: shows indexesOfHullNodes before removing");
    		    	for(int i=0;i<indexesOfHullNodes.size();i++)
    		    	{
    		    		System.out.println("i="+String.valueOf(i)+
    		    				", index="+String.valueOf(indexesOfHullNodes.get(i))+
    		    				", value="+algoNodes.get(indexesOfHullNodes.get(i)).getKey().getText()
    		    				);
    		    	}
    		    	turnRight=true;
    		    	algoNodes.get(SecondToLastIndx).getValue().status=NodeStatus.VISITED;
    		    	indexesOfHullNodes.remove(indexesOfHullNodes.size()-2);
    		    	System.out.println("grahamScan: shows indexesOfHullNodes after removing");
    		    	for(int i=0;i<indexesOfHullNodes.size();i++)
    		    	{
    		    		System.out.println("i="+String.valueOf(i)+
    		    				", index="+String.valueOf(indexesOfHullNodes.get(i))+
    		    				", value="+algoNodes.get(indexesOfHullNodes.get(i)).getKey().getText()
    		    				);
    		    	}
    		    	displayGrahamScan();
    		    }
			}
			while(turnRight);
			
			AlgoNode last = algoNodes.get(algoNodes.size()-1).getValue();
			if(last.status.equals(NodeStatus.VISITED) || last.status.equals(NodeStatus.HULL))
				break;	
			
			displayGrahamScan();
			return;
    	}
    	
    	//step 5. return indexes of points which are included in the hull
    	System.out.println("grahamScan: Algorithm has finished!");
    	indexesOfHullNodes.add(0);
    	nextButton.setDisable(true);
    	displayGrahamScan();
    }
    
    private void printAngles()
    {
    	for(int i=0;i<algoNodes.size();i++)
    	{
    		System.out.println("printAngles: i="+String.valueOf(i)+
    				", label="+algoNodes.get(i).getKey().getText()+
    				", angle="+String.valueOf(algoNodes.get(i).getValue().angle)+
    				", x="+String.valueOf(algoNodes.get(i).getValue().x)+", y="+String.valueOf(algoNodes.get(i).getValue().y));
    	}
    }
	
}