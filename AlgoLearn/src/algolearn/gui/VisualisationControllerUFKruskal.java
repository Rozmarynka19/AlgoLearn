package algolearn.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.ResourceBundle;

import algolearn.gui.VisualisationControllerGrahamScan.AlgoNode;
import algolearn.gui.VisualisationControllerGrahamScan.NodeStatus;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

public class VisualisationControllerUFKruskal extends FXMLDocumentController {
	
	public class Edge
	{
		public int startNodeIndex,targetNodeIndex;
		public int cost;
		public EdgeStatus status;
		
		public Edge()
		{
			startNodeIndex=0;
			targetNodeIndex=0;
			cost=0;
			status = EdgeStatus.EXCLUDED_IN_MST;
		}
		
		public Edge(int start, int target)
		{
			startNodeIndex=start;
			targetNodeIndex=target;
			Random rand = new Random();
			cost = rand.nextInt(maxEdgeCost-1)+1;
			status = EdgeStatus.EXCLUDED_IN_MST;
		}
	}
	public enum EdgeStatus
	{
		INCLUDED_IN_MST,EXCLUDED_IN_MST;		
	}
	
	public class UnionFind
	{
		public int[] parentArray;
		int unionCounter;
		long findCounter;

		public UnionFind(int numberOfNodes)
		{
			unionCounter = 0; 
			findCounter = 0;
			parentArray = new int[numberOfNodes];

			for (int i = 0; i < numberOfNodes; i++)
				parentArray[i]=i;
		}
		public void Union(int index1, int index2)
		{
			parentArray[index1] = index2;
			unionCounter++;
		}
		public int Find(int index)
		{
			findCounter++;
			if (index == parentArray[index]) 
				return index;
			return Find(parentArray[index]);
		}
	};
	
	private boolean generateDone = true;
	private boolean deleteInProgress = false;
	private boolean pathTransitionDone = true;
	private errors.grahamScanMsg msg = new errors.grahamScanMsg();
	
    private final int minInputRange = 1;
    private final int maxInputRange = 99;
    private final int maxEdgeCost = 100;
    private final double padding = 40;
    private final int verticalBias = 20;
    private final int horizontalBias = 20;
    private ArrayList<Button> nodes = new ArrayList<Button>();
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private int nextButtonClickedIterator=0;
    private UnionFind uf;
    private int edgeIterator=0;

	@FXML AnchorPane visAnchorPane;
	@FXML Button addButton;
	@FXML Button deleteButton;
	@FXML Button restartButton;
	@FXML Button backButton;
	@FXML Button nextButton;
	@FXML Button bindEdgesButton;
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
	
	//---------- Finding free place ----------
	private final int maxRandomNodes = 9;
    private double[] widthArray = {0,0,0};
    private double[] heightArray = {0,0,0};
    private boolean[][] occupiedPlaces = {{false,false,false},
								    	  {false,false,false},
								    	  {false,false,false}};
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		if (infoScrollPane == null)
			return;
		infoScrollPane.getStyleClass().clear();
		infoScrollPane.getStyleClass().add("infoScrollPane");
		
		nextButton.setDisable(true);
		bindEdgesButton.setDisable(true);
		startAlgoButton.setDisable(true);
		
		widthArray[0] = padding;
		widthArray[1] = (visAnchorPane.getPrefWidth()-padding)/2;
		widthArray[2] = visAnchorPane.getPrefWidth()-padding;
		
		heightArray[0] = padding;
		heightArray[1] = (visAnchorPane.getPrefHeight()-padding)/2;
		heightArray[2] = visAnchorPane.getPrefHeight()-padding;
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
				info.setText(msg.setupInformation(msg.nodeAlredyExistsPart1+String.valueOf(key)+msg.nodeAlredyExistsPart2));
				return;
			}
				
			Button newNode = new Button();
			if (findPlace(newNode))
			{
				newNode.setText(String.valueOf(key));
				nodes.add(newNode);
				System.out.println("insert: udalo sie dodac wezel o kluczu "+String.valueOf(key));
				info.setText(msg.setupInformation(msg.nodeInserted+String.valueOf(key)));
			}
			
			if(bindEdgesButton.isDisabled() && nodes.size()>=4)
				bindEdgesButton.setDisable(false);
				
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
		
		if(bindEdgesButton.isDisabled() && nodes.size()>=4)
			bindEdgesButton.setDisable(false);
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
		for(Edge edge: edges)
		{
			Button startNode = nodes.get(edge.startNodeIndex);
			Button targetNode = nodes.get(edge.targetNodeIndex);
			Line line = new Line(startNode.getLayoutX()+horizontalBias, startNode.getLayoutY()+verticalBias, 
					targetNode.getLayoutX()+horizontalBias,targetNode.getLayoutY()+verticalBias);
			if(edge.status.equals(EdgeStatus.INCLUDED_IN_MST))
			{
				line.setStroke(Color.GREEN);
				line.setStrokeWidth(2.);
//				System.out.println("display: current strokeWidth="+String.valueOf(line.getStrokeWidth()));
			}
				
			//TODO: add label with edge cost
			Text label = new Text(String.valueOf(edge.cost));
			label.setLayoutX((startNode.getLayoutX()+targetNode.getLayoutX())/2+horizontalBias+5);
			label.setLayoutY((startNode.getLayoutY()+targetNode.getLayoutY())/2+verticalBias-5);
			visAnchorPane.getChildren().addAll(line,label);
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
		for(int i=0;i<maxRandomNodes/3;i++)
			for(int j=0;j<maxRandomNodes/3;j++)
				if(occupiedPlaces[i][j] == false)
				{
					occupiedPlaces[i][j] = true;
					
					newNode.setLayoutX(widthArray[i]);
					newNode.setLayoutY(heightArray[j]);
					
					return true;
				}
		
		System.out.println("insert: za duzo wezlow w grafie!");
		createMessageBox(msg.msgErrorHeader, msg.tooManyNodes, msg.msgTypeError);
		return false;
	}
	
	@FXML
	public void delete(ActionEvent event)
	{

		int key = validateInput(deleteTextField);
		
		if (nodes.size()==0)
		{
			info.setText(msg.setupInformation(msg.emptyGraph));
			return;
		}
		
		if(key<0)
			return;
		else
        {
			if (!isSuchNode(key,true))
			{
				System.out.println("delete: nie ma w grafie wezla o kluczu "+String.valueOf(key));
				info.setText(msg.setupInformation(msg.noSuchNode+String.valueOf(key)));
				return;
			}
			
			System.out.println("delete: udalo sie usunac wezel o kluczu "+String.valueOf(key));
			info.setText(msg.setupInformation(msg.nodeDeletedPart1+String.valueOf(key)+msg.nodeDeletedPart2));
			display();            
        }
		
		if(!bindEdgesButton.isDisabled() && nodes.size()<4)
			bindEdgesButton.setDisable(true);
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
            else
            {
            	i--;
            	continue;
            }
        		
    	}
    }
    
    private void blockButtons(boolean areButtonsBlocked)
    {
    	addButton.setDisable(areButtonsBlocked);
    	deleteButton.setDisable(areButtonsBlocked);
    	restartButton.setDisable(areButtonsBlocked);
    	backButton.setDisable(areButtonsBlocked);
    	bindEdgesButton.setDisable(areButtonsBlocked);
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
    	edges.clear();
    	for(int i=0;i<maxRandomNodes/3;i++)
    		for(int j=0;j<maxRandomNodes/3;j++)
    			occupiedPlaces[i][j]=false;
    	this.display();
    	bindEdgesButton.setDisable(true);
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void bindEdges()
    {
    	//binding edges;
    	int numberOfEdgesInFullGraph = nodes.size()*(nodes.size()-1)/2;
    	binding:
    	for(int i=0;i<(int)numberOfEdgesInFullGraph/2;i++)
    	{
    		Random rand = new Random();
    		int startNodeIndex = rand.nextInt(nodes.size());
    		int targetNodeIndex = rand.nextInt(nodes.size());
    		
    		if(startNodeIndex == targetNodeIndex)
    		{
    			i--;
    			continue;
    		}
    		
    		
    		for(Edge edge: edges)
    		{
    			if( (edge.startNodeIndex==startNodeIndex && edge.targetNodeIndex==targetNodeIndex) || 
    					(edge.startNodeIndex==targetNodeIndex && edge.targetNodeIndex==startNodeIndex) )
    			{
    				i--;
    				continue binding;
    			}
    		}
    		
    		edges.add(new Edge(startNodeIndex, targetNodeIndex));
    	}
    	
    	//check if all nodes have min 1 edge
    	boolean hasEdge = false;
    	for(int i=0;i<nodes.size();i++)
    	{
    		for(Edge edge: edges)
    		{
    			if(edge.startNodeIndex==i || edge.targetNodeIndex==i)
    			{
    				hasEdge = true;
    				break;
    			}
    		}
    		
    		if(!hasEdge)
    		{
    			System.out.println("bindEdges: Node without edge found! Reparing...");
    			
    			if(i!=nodes.size()-1)
    				edges.add(new Edge(i,nodes.size()-1));
    			else
    				edges.add(new Edge(i,nodes.size()-2));
    		}
    	}
    	
    	
//    	printEdges();
    	display();
    	
    	bindEdgesButton.setDisable(true);
    	startAlgoButton.setDisable(false);
    }
    
    private void printEdges()
    {
    	for(int i=0;i<edges.size();i++)
    	{
    		System.out.println("printEdges: i="+String.valueOf(i)+
    				", startNodeKey="+nodes.get(edges.get(i).startNodeIndex).getText()+
    				", targetNodeKey="+nodes.get(edges.get(i).targetNodeIndex).getText()+
    				", cost="+String.valueOf(edges.get(i).cost));
    	}
    }
    
    @FXML
    private void next()
    {
    	nextButtonClickedIterator++;
    	kruskal();
    }
    
    @FXML
    public void startAlgorithm()
    {
    	if(nodes.size()==0)
    	{
    		info.setText(msg.setupInformation(msg.emptyGraph));
    		System.out.println("startAlgorithm: graf jest pusty!");
    		return;
    	}
    		
    	addButton.setDisable(true);
    	deleteButton.setDisable(true);
    	bindEdgesButton.setDisable(true);
    	startAlgoButton.setDisable(true);
    	nextButton.setDisable(false);
    	
    	nextButtonClickedIterator=1;
    	kruskal();
    }
    
    private void kruskal()
    {
    	switch(nextButtonClickedIterator)
    	{
    	case 1:
        	//step 1. init UF structure
    		System.out.println("kruskal: step1");
    		uf = new UnionFind(nodes.size());
    		    		
    		//TODO: message - uf structure initialized
    		display();
    		return;
    	case 2:
        	//step 2. sort (ascending) edges by cost
    		System.out.println("kruskal: step2");
    		
    		Collections.sort(edges,costCOMPARE);
    		printEdges();
    		//TODO: message - edges sorted
    		display();
    		return;
    	default:
        	//step 3. for every edge ...
    		System.out.println("kruskal: default step");
    		
    		if(uf.unionCounter>=nodes.size()-1)
    			break;
    		
    		Edge currentEdge = edges.get(edgeIterator);    		
    		int firstParent = uf.Find(currentEdge.startNodeIndex); 
    		int secondParent = uf.Find(currentEdge.targetNodeIndex);
    		
    		if (firstParent != secondParent)
    		{
    			edges.get(edgeIterator).status = EdgeStatus.INCLUDED_IN_MST;
    			
    			//Casual union
    			uf.Union(firstParent, secondParent);    			
    		}
    		edgeIterator++;
    		
    		//TODO: message - edge added to MST
    		display();
    		return;
    	}
    	
    	//step 4. return MST
    	System.out.println("kruskal: Algorithm has finished!");
    	//TODO: message - algorithm has finished
    	nextButton.setDisable(true);
    	display();
	}
    
    public static Comparator<Edge> costCOMPARE = new Comparator<Edge>() {
		@Override
		public int compare(Edge o1, Edge o2) {
			return o1.cost-o2.cost;
		}
    };
	
}