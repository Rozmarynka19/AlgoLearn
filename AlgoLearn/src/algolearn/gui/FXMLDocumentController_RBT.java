package algolearn.gui;

import algolearn.gui.algorithms.rbt.RedBlackTree;
import algolearn.gui.algorithms.rbt.TreeNode;
import algolearn.gui.info.errors;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.StrokeTransition;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLDocumentController_RBT extends FXMLDocumentController {
	
	private boolean generateDone = true;
	private boolean deleteInProgress = false;
	private boolean pathTransitionDone = true;
	private errors.rbtMsg msg = new errors.rbtMsg();
	
	@FXML AnchorPane visAnchorPane;
	@FXML private ProgressBar generateBar;
	@FXML TextField insertTextField;
	@FXML TextField deleteTextField;
	@FXML TextField searchTextField;
	@FXML TextField unknownTextField;
	@FXML Button addButton;
	@FXML Button deleteButton;
	@FXML Button searchButton;
	@FXML Button unknownButton;
	@FXML Button letsPlayButton;
	@FXML Button hiddenValues;
	@FXML Button restartButton;
	@FXML Button backButton;
	
	//--------- Msg box stuff ----------
	@FXML Text txtMainTitle;
	@FXML TextArea msgTextArea;
	
	//---------- Infoline stuff ----------
	@FXML Text info;
	@FXML ScrollPane infoScrollPane;
	
	
	
    private RedBlackTree<Integer> tree= new RedBlackTree<>(this);
    private ArrayList<Integer> nodes = new ArrayList<>();
    private double radius = 15;
    private double vGap = 50;
    private int minInputRange = 1;
    private int maxInputRange = 99;
    private int maxRandomNodes = 15;
    private ArrayList<Integer> hiddenNodes = new ArrayList<>();
    private ArrayList<Integer> indexesOfHiddenNodes = new ArrayList<>();
    private int maxHiddenValues = 2;
    private double bias = 35/2;
    private Button selectedButton=null;
    
	private Circle hintCricle = null;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		if (infoScrollPane == null)
			return;
		infoScrollPane.getStyleClass().clear();
		infoScrollPane.getStyleClass().add("infoScrollPane");
	}

	public void highlightNode(Integer index,Color color)
    {
    	displayTree();
    	ArrayList<TreeNode<Integer>> tmp = tree.path(index);
    	TreeNode<Integer> node = tmp.get(tmp.size()-1);
    	
    	System.out.println("In highlightNode "+ index +" "+node.x+" "+node.y);
    	tree.search(index);

    	hintCricle = new Circle(node.x,node.y,radius+5, new Color(0, 0, 0, 0));
		hintCricle.setStroke(color);
		hintCricle.setStrokeWidth(3.0);
		visAnchorPane.getChildren().add(hintCricle);
		
		FadeTransition ft = new FadeTransition(Duration.millis(1000),hintCricle);
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.setOnFinished(e->tree.ensureRBTree(index));
		ft.play();
    }

	
	public void animateSearch(boolean isNodeFound) {	
		if(tree.root==null)
			return;
		hintCricle = new Circle(tree.root.x,tree.root.y,radius+10, new Color(0, 0, 0, 0));
		hintCricle.setStroke(Color.GREEN);
		hintCricle.setStrokeWidth(3.0);
		visAnchorPane.getChildren().add(hintCricle);
		
		StrokeTransition ft = new StrokeTransition(Duration.millis(500),hintCricle,Color.GREEN,Color.RED);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		
		PathTransition pathT = new PathTransition();
		pathT.setDuration(Duration.millis(1000));
		if(tree.path.getElements().size()<=2)
			tree.path.getElements().add(new LineTo(406.0,51.0));
		pathT.setPath(tree.path);
		pathT.setNode(hintCricle);
		pathT.setCycleCount(1);
		pathT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathT.setAutoReverse(false);
		if(!isNodeFound)
			pathT.setOnFinished(e -> ft.play());
		pathT.play();
	}
	
	
	@FXML
    public void insert(ActionEvent event) {
		int key = validateInput(insertTextField);
		
		if(key<0)
			return;
		else
        {        	
            nodes.add(key);
            if (tree.search(key)) 
            {
                displayTree();
                info.setText(msg.setupInformation(msg.nodeAlredyExistsPart1+key+msg.nodeAlredyExistsPart2));
            } 
            else 
            {
                tree.insert(key);
                displayTree();
                
                info.setText(msg.setupInformation(msg.nodeInserted+key));
            }
            insertTextField.clear();
            
        }
    }
	
	@FXML
	public void delete(ActionEvent event) {
		int key = validateInput(deleteTextField);
		
		if(key<0)
			return;
		else
        {      
         if(!tree.search(key)){
             displayTree();
             info.setText(msg.setupInformation(msg.nodeNotFound+key));
         }
         else{
             tree.delete(key);
             displayTree();
             info.setText(msg.setupInformation(msg.nodeDeletedPart1+key+msg.nodeDeletedPart2));
         }
         deleteTextField.clear();
        }

     }
	
	@FXML
	public void search(ActionEvent event) {
		int key = validateInput(searchTextField);
		
		if(key<0)
			return;
		else
        {               
		 boolean isNodeFound;
		 tree.path.getElements().clear();
		 if(!tree.search(key)){
			 isNodeFound=false;
			 if(tree.root==null)
				 info.setText(msg.setupInformation(msg.emptyTree));
			 else
				 info.setText(msg.setupInformation(msg.nodeNotFound+key));
		 }
		 else{
			 isNodeFound=true;
			 info.setText(msg.setupInformation(msg.nodeFound+key));
		 }
		 displayTree();
		 searchTextField.clear();
		 
		 System.out.println("Path size: " + String.valueOf(tree.path.getElements().size()));
		 for(int i=0;i<tree.path.getElements().size();i++)
			 System.out.println(tree.path.getElements().get(i));
		 animateSearch(isNodeFound);     
        }
    }
	
	
	public void displayTree(){
		visAnchorPane.getChildren().clear();
	    if(tree.getRoot() != null){
	    	tree.getRoot().x=visAnchorPane.getWidth() / 2;
	    	tree.getRoot().y=vGap;
	        displayTree(tree.getRoot(), tree.getRoot().x, tree.getRoot().y, visAnchorPane.getWidth() / 4);
	    }
	}
	
	private void displayTree(TreeNode<Integer> root, double x, double y, double hGap){
	    if(root.left != null){
	    	root.left.x= x - hGap;
	    	root.left.y= y + vGap;
	    	visAnchorPane.getChildren().add(new Line(x - hGap, y + vGap, x, y));
	        displayTree(root.left, root.left.x, root.left.y, hGap / 2);
	    }
	
	    if (root.right != null){
	    	root.right.x=x + hGap;
	    	root.right.y=y + vGap;
	    	visAnchorPane.getChildren().add(new Line(x + hGap, y + vGap, x, y));
	        displayTree(root.right, root.right.x, root.right.y, hGap / 2);
	    }
	
	    tree.getRed(root);
	
	    Button node = new Button();
	    node.setLayoutX(x-bias);
	    node.setLayoutY(y-bias);
	    
	    node.getStyleClass().clear();
	    if(tree.getRed(root))
	    	node.getStyleClass().add("rbtNodeRed");
	    else
	    	node.getStyleClass().add("rbtNodeBlack");
	    
	    
	    boolean found = false;
	    for(int i=0;i<hiddenNodes.size();i++)
	    {
	    	if(hiddenNodes.get(i)==root.element)
	    		found=true;
	    }
	    
    	if(found)
    	{
	    	node.setText("?");
	    	node.setAccessibleText(Integer.valueOf(root.element).toString());
    	}
    	else
    		node.setText(root.element.toString());

    	node.setOnMouseClicked(e -> 
	    	{
	    		selectedButton = (Button) e.getSource();
	    		setCircle();
	    		
	    		if(selectedButton.getText()=="?")
	    			info.setText(msg.setupInformation(msg.unknownNodeSelected));
	    		else
	    			info.setText(msg.setupInformation(msg.knownNodeSelected+selectedButton.getText()));
	    	}
		);
	    visAnchorPane.getChildren().add(node);
	}
	
    @FXML public void pressRandomBar() {
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
        	switchToTheGameMode(false);

        	restartVis(false);
        	generateTree();
    		this.displayTree();
        	generateDone = true;
        	info.setText(msg.setupInformation(msg.generateDone));
    	});
    	timeline.play();
    }
	
    @FXML
    public void restartVis()
    {
    	restartVis(false);
    	info.setText(msg.setupInformation(msg.restartDone));
    }
    
    private void restartVis(boolean isGameModeOn)
    {
    	switchToTheGameMode(isGameModeOn);
    	this.tree.clear();
    	this.nodes.clear();
    	this.displayTree();
    	hiddenNodes.clear();
    	indexesOfHiddenNodes.clear();
    }
    
    private void generateTree()
    {
    	int randomInteger;
    	for(int i=0;i<maxRandomNodes;i++)
    	{
    		randomInteger = (int)(Math.random()*(maxInputRange-1)+1);
            if (!tree.search(randomInteger))
            {
            	tree.insert(randomInteger);
            	nodes.add(randomInteger);
            }
        		
    	}
    }
    
    @FXML
    public void letsPlay(ActionEvent event)
    {
    	restartVis(true);
    	generateTree();
    	
    	int randomIndex;
    	outer:
    	for(int i=0;i<maxHiddenValues;i++)
    	{
    		randomIndex = (int)(Math.random()*(nodes.size()-1));
    		System.out.println("Index: "+randomIndex+" Value: "+nodes.get(randomIndex));
    		for(int j=0;j<indexesOfHiddenNodes.size();j++)
    			if(indexesOfHiddenNodes.get(j)==randomIndex)
    			{
    				i--;
    				continue outer;
    			}
    		indexesOfHiddenNodes.add(randomIndex);
    		hiddenNodes.add(nodes.get(randomIndex));
    	}
    	
    	displayTree();
    	displayHiddenValues();
    }
    
    private void displayHiddenValues()
    {
    	String hiddenValuesText="Ukryte liczby: [";
    	for(int i=0;i<hiddenNodes.size();i++)
    	{
    		hiddenValuesText+=hiddenNodes.get(i).toString();
    		if(i!=hiddenNodes.size()-1)
    			hiddenValuesText+=",";
    	}
    	hiddenValuesText+="]";
    	hiddenValues.setText(hiddenValuesText);
    }
    
    @FXML
    public void guessValue()
    {    	
		int input = validateInput(unknownTextField);
		
		if(input<0)
			return;
		else
        {   
			if(selectedButton!=null && selectedButton.getAccessibleText()!=null)
	    	{
	    		if(input == Integer.parseInt(selectedButton.getAccessibleText()))
	    		{
	    			selectedButton.setText(selectedButton.getAccessibleText());
	    			selectedButton.setAccessibleText(null);
	    			for(int i=0;i<hiddenNodes.size();i++)
	    				if(hiddenNodes.get(i)==input)
	    				{
	    					hiddenNodes.remove(i);
	    					break;
	    				}
	    			displayHiddenValues();
	    			displayTree();
	    			System.out.println("brawo! tu faktycznie powinna byc liczba "+input);
	    			info.setText(msg.setupInformation(msg.goodCall + input));
	    		}
	    		else
	    		{
	    			System.out.println("tu nie powinna byc liczba "+input);
	    			info.setText(msg.setupInformation(msg.wrongCall + input));
	    		}
	    	}
	    	else
	    	{
	    		System.out.println("najpierw zaznacz wezel, ktore wartosc chcesz odgadnac");
	    		createMessageBox(msg.msgErrorHeader, msg.selectNodeFirst , msg.msgTypeError);
	    		
	    	}
	    	unknownTextField.clear();
	    	
	    	if(hiddenNodes.size()<=0)
	    	{
	    		switchToTheGameMode(false);
	        	
	        	System.out.println("brawo mistrzu za odgadniecie wartosci wszystkich wezlow!");
	        	createMessageBox(msg.msgCongratsHeader, msg.finishedGuessGame , msg.msgTypeCongrats);
	    	}
        }
    	
    }
    
    private void setCircle()
    {
    	System.out.println("setCircle: selectedButton-Text: "+selectedButton.getText()+
    						", selectedButton-AccesibleText: "+selectedButton.getAccessibleText()
    			);
    	
    	int val=0;
    	if(selectedButton.getText()=="?")
    		val = Integer.parseInt(selectedButton.getAccessibleText());
    	else
    		val = Integer.parseInt(selectedButton.getText());
    	System.out.println("setCircle: "+String.valueOf(val));
    	
    	tree.path.getElements().clear();
    	tree.search(val);
        displayTree();
        animateSearch(true);   
    }
    
    private void switchToTheGameMode(boolean isGameModeActivated)
    {
    	if (isGameModeActivated)
    		info.setText(msg.setupInformation(msg.gameModeOn));
    	else
    		info.setText(msg.setupInformation(msg.gameModeOff));
    	
    	letsPlayButton.setDisable(isGameModeActivated);
    	letsPlayButton.setVisible(!isGameModeActivated);
    	unknownTextField.setDisable(!isGameModeActivated);
    	unknownTextField.setVisible(isGameModeActivated);
    	unknownButton.setDisable(!isGameModeActivated);
    	unknownButton.setVisible(isGameModeActivated);
    	
    	addButton.setDisable(isGameModeActivated);
    	deleteButton.setDisable(isGameModeActivated);
    	searchButton.setDisable(isGameModeActivated);
    	
    	hiddenValues.setText("");
    }
    
    private void blockButtons(boolean areButtonsBlocked)
    {
    	addButton.setDisable(areButtonsBlocked);
    	deleteButton.setDisable(areButtonsBlocked);
    	searchButton.setDisable(areButtonsBlocked);
    	unknownButton.setDisable(areButtonsBlocked);
    	restartButton.setDisable(areButtonsBlocked);
    	backButton.setDisable(areButtonsBlocked);
    	letsPlayButton.setDisable(areButtonsBlocked);
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

}