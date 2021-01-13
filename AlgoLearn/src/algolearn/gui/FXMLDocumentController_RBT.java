package algolearn.gui;

import algolearn.gui.main_window;
import algolearn.gui.algorithms.rbt.RBPane;
import algolearn.gui.algorithms.rbt.RedBlackTree;
import algolearn.gui.algorithms.rbt.TreeNode;
import algolearn.gui.info.errors;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.StrokeTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class FXMLDocumentController_RBT extends FXMLDocumentController {
	
	private boolean generateDone = true;
	private boolean deleteInProgress = false;
	private boolean pathTransitionDone = true;
	private errors errorMSG = new errors();
	
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
//    private int correctCurrentUnknownValue=0;
    private Button selectedButton=null;
    
	private Circle hintCricle = null;
	
//	@FXML
//	public void generate()
//	{
////	    public void start(Stage primaryStage){
////		visPane.setBackground(new Background(new BackgroundFill(Color.web("#" + "FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
//	     	tree 
//	     	nodes
////	        BorderPane pane = new BorderPane();
////	        RBPane view = new RBPane(tree);
////	        setPane(pane, view, tree);
////	        setStage(pane, primaryStage, "RedBlackTree Visualisation");
//	        
////	        Alert alert = new Alert(Alert.AlertType.INFORMATION,"This is a RedBlackTree Visualiser created by Ankit Sharma. This demonstrates the operations of insertion and deletion.\n\n" +
////	                "Insert button inserts a node, delete button deletes a node", ButtonType.OK);
////	        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
////	        alert.show();
//	 }
    
	//	private Path createMovePath() {
	//	Path path = new Path();
	//	path.getElements().add(new MoveTo(tree.getRoot().x, tree.getRoot().y));
	//	return path;
	//}
	
//    public void highlightNode(double nodeX, double nodeY, Color color,int e)
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
		
//		PathTransition pathT = new PathTransition();
//		pathT.setDuration(Duration.millis(5000));
//		pathT.setPath(tree.path);
//		pathT.setNode(hintCricle);
//		pathT.setCycleCount(1);
//		pathT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//		pathT.setAutoReverse(false);
//		pathT.play();
    }

	
	public void animateSearch(boolean isNodeFound) {
	//	if(hintCricle == null) return;
	//	Path path = createMovePath();
	//	for(int i=0;i<nodes.size();i++)
	//		path.getElements().add(new LineTo(c[0], c[1]));
		
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
		
//		visPane.getChildren().remove(hintCricle);
	}
	
	
	@FXML
    public void insert(ActionEvent event) {
		 if(insertTextField.getText().length() == 0) {
	            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You haven't entered anything!", ButtonType.OK);
	            alert.getDialogPane().setMinHeight(80);
	            alert.show();
	        }
	        else {
	            int key = Integer.parseInt(insertTextField.getText());
	            nodes.add(key);
	            if (tree.search(key)) {
	                displayTree();
//	                view.setStatus(key + " is already present!");
	            } else {
//	            	System.out.println("Inserting...");
	                tree.insert(key);
	                displayTree();
//	                tree.path.getElements().clear();
//	                tree.search(key);
//		            animateSearch();
	                
//	                setStatus(key + " is inserted!");
	            }
	            insertTextField.clear();
	            
	        }
    }
	
	@FXML
	public void delete(ActionEvent event) {
         int key = Integer.parseInt(deleteTextField.getText());
         if(!tree.search(key)){
             displayTree();
//	             view.setStatus(key +" is not present!");
         }
         else{
             tree.delete(key);
             displayTree();
//	             view.setStatus(key+" is replaced by its predecessor and is deleted!");
         }
         deleteTextField.clear();

     }
	
	@FXML
	public void search(ActionEvent event) {
         int key = Integer.parseInt(searchTextField.getText());
         boolean isNodeFound;
         tree.path.getElements().clear();
         if(!tree.search(key)){
        	 isNodeFound=false;
//	             view.setStatus(key +" is not present!");
         }
         else{
        	 isNodeFound=true;
//	             view.setStatus(key +" is present!");
         }
         displayTree();
         searchTextField.clear();
         
         System.out.println("Path size: " + String.valueOf(tree.path.getElements().size()));
         for(int i=0;i<tree.path.getElements().size();i++)
        	 System.out.println(tree.path.getElements().get(i));
         animateSearch(isNodeFound);     

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
	
	    Button node = new Button();//new Circle(x, y, radius);
	    node.setLayoutX(x-bias);
	    node.setLayoutY(y-bias);
	    
//	    circle.setStroke(Color.BLACK);
//	    if(tree.getRed(root))
//	        circle.setFill(Color.INDIANRED);
//	    else circle.setFill(Color.GRAY);
	    
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
	    	}
		);
	    visAnchorPane.getChildren().add(node);
	}
	
    @FXML public void pressRandomBar() {
    	if(generateDone == false)
    		return;

//		if(deleteInProgress) {
//			bstTEXT.setText(errorMSG.setupInformation(errorMSG.delInProgress));
//			return;
//		}
		
//		if(!pathTransitionDone) {
//			CreateError(errorMSG.pathTNotDone);
//			return;
//		}
    	
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
    	});
    	timeline.play();
    }
	
    @FXML
    public void restartVis()
    {
    	restartVis(false);
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
    	int input = Integer.parseInt(unknownTextField.getText());
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
    			//info - brawo!
    		}
    		else
    		{
    			System.out.println("tu nie powinna byc liczba "+input);
    			//info - tu nie powinna byc xyz liczba
    		}
    	}
    	else
    	{
    		System.out.println("najpierw zaznacz wezel, ktore wartosc chcesz odgadnac");
    		//messagebox - najpierw zaznacz wezel, ktore wartosc chcesz odgadnac
    	}
    	unknownTextField.clear();
    	
    	if(hiddenNodes.size()<=0)
    	{
    		switchToTheGameMode(false);
        	
        	System.out.println("brawo mistrzu za odgadniecie wartosci wszystkich wezlow!");
        	//messagebox - brawo mistrzu za odgadniecie wartosci wszystkich wezlow!
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

}