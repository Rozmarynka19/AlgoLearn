package algolearn.gui;

import algolearn.gui.main_window;
import algolearn.gui.algorithms.rbt.RBPane;
import algolearn.gui.algorithms.rbt.RedBlackTree;
import algolearn.gui.algorithms.rbt.TreeNode;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.Pane;
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

public class FXMLDocumentController_RBT {
	private double [] scene_base = {300, 300}; 
	public double [] scene_max = {300, 1123};
	private boolean resize_locker = false;
    @FXML
    private Stage stage, secStage;
    @FXML
    private Button btn, wpr, opi, wiz;
    private String btn_id = "NULL";
    /* Dane dotyczace progressu - tymczasowo false - wymaga implemntacji zapisu i wczytywania danych z pliku*/
    private boolean[][] category_data =  {
    		{false, false, false},
    		{false, false, false},
    		{false, false, false},
    		{false, false, false},
    		{false, false, false},
    		{false, false, false},
    		{false, false, false},
    		{false, false, false}, 
    		{false, false, false}, 
    };
    private int algo_id = 0;
    @FXML
    private Text txtMainTitle, txtProgress;
    private String txtMainTitleString = "Algolearn - ", txtProgressString = "/100";
    
    
    @FXML /* Expand window */
    private void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage = (Stage) btn.getScene().getWindow();
        this.stage = stage;
        Button clicked_btn = (Button)event.getSource();
        String btn_val = clicked_btn.getId();
        this.algo_id = Integer.parseInt(btn_val);
        if(this.btn_id == "NULL") this.btn_id = btn_val;
        if(stage.getWidth() < scene_max[1] && this.resize_locker == false) {
        	resize(stage, scene_max[1], (double)15, 1, 3);
        	txtMainTitle.setText(txtMainTitleString + clicked_btn.getText());
        	categorySetBackground();
        	setProgress(calculateProgress());
        }
        else if (stage.getWidth() >= scene_max[1] && this.resize_locker == false && clicked_btn.getId() == btn_id)  {
        	resize(stage, scene_base[1], (double)-15, 1, 3);
        }else if(clicked_btn.getId() != btn_id) {
        	txtMainTitle.setText(txtMainTitleString + clicked_btn.getText());
        	categorySetBackground();
        	setProgress(calculateProgress());
        }
        this.btn_id = clicked_btn.getId();
    }
    
    @FXML
    private void minimalizeWindow(ActionEvent event) {
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).setIconified(true);
    }
    
    @FXML
    //id for each top AnchorPane element in every window-fxmlFile
    //necessary for switching between windows
    private AnchorPane anchorPaneRoot; 
    @FXML
    private StackPane parentContainer;
    @FXML
    private ProgressBar progressBar;

    @FXML
    private void categorySetBackground(){
    	wpr.getStyleClass().clear();
    	wiz.getStyleClass().clear();
    	opi.getStyleClass().clear();
    	
    	if(category_data[this.algo_id][0] == false)
    		wpr.getStyleClass().add("wprowadzenie");
    	else if(category_data[this.algo_id][0] == true)
    		wpr.getStyleClass().add("wprowadzenie2");
    	
    	if(category_data[this.algo_id][1] == false)
    		opi.getStyleClass().add("opis");
    	else if(category_data[this.algo_id][1] == true)
    		opi.getStyleClass().add("opis2");
    	
    	if(category_data[this.algo_id][2] == false)
    		wiz.getStyleClass().add("wizualizacja");
    	else if(category_data[this.algo_id][2] == true)
    		wiz.getStyleClass().add("wizualizacja2");	
    	
    }
    
    @FXML /* Expand window */
    private void handleCloseWindowAction(ActionEvent event) throws Exception {
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    
    /* Resize window */
    private void resize(Stage stage, double width, double speed, int delay, int wait) {
    	this.resize_locker = true;
    	Timer animTimer = new Timer();
        animTimer.scheduleAtFixedRate(new TimerTask() {
        double inc_widht = speed;
        @Override
        public void run() {
        	if ((stage.getWidth() != width )) {
                	if(inc_widht > 0 && stage.getWidth() + inc_widht > width  ||
                			inc_widht < 0 && stage.getWidth() - inc_widht < width)
                		inc_widht = width - stage.getWidth();
                    stage.setWidth((double)stage.getWidth()+(double)inc_widht);
                }
                else {
                    this.cancel();
                }
            }

        }, delay, wait);
    	this.resize_locker = false;
    }

    public void handleProgress(ActionEvent event) {
    	Button clicked_btn = (Button)event.getSource();
    	String id = clicked_btn.getId();
    	
    	if(id.equals(wpr.getId())) 
    		category_data[this.algo_id][0] = category_data[this.algo_id][0] ? false : true;
    	else if(id.equals(opi.getId())) 
    		category_data[this.algo_id][1] = category_data[this.algo_id][1] ? false : true;
    	else if(id.equals(wiz.getId())) 
    		category_data[this.algo_id][2] = category_data[this.algo_id][2] ? false : true;
    	
    	setProgress(calculateProgress());
    	int prog = (int)(calculateProgress() * 100);
    	categorySetBackground();
    	txtProgress.setText(Integer.toString(prog)+txtProgressString);
    }
    
    private double calculateProgress() {
    	int count_done = 0;
    	for(int i = 0 ; i < 3; i++)
    		if(category_data[this.algo_id][i] == true) 
    			count_done++;
    	switch(count_done) {
    		case 1:
    			return 0.33;
    		case 2:
    			return 0.66;
    		case 3:
    			return 1.0;
    		default:
    			return 0;
    	}
    }
    
    
    private void setProgress(double progress) {
    	Timeline timeline = new Timeline();
    	KeyValue keyValue = new KeyValue(progressBar.progressProperty(), progress);
    	KeyFrame keyFrame = new KeyFrame(new Duration(1000), keyValue);
    	timeline.getKeyFrames().add(keyFrame);

    	timeline.play();
    }
    
    @FXML
    public void pressButtonDescription(ActionEvent event) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/description_fxml.fxml"));
    	AnchorPane anchorPane = loader.load();
    	setScreen(anchorPane);
    }

    @FXML
    public void pressButtonIntroduction(ActionEvent event) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/introduction_fxml.fxml"));
    	AnchorPane anchorPane = loader.load();
    	setScreen(anchorPane);
    }

    @FXML
    public void pressButtonVisualization(ActionEvent event) throws Exception {
      	FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/visualisation_fxml.fxml"));
    	AnchorPane anchorPane = loader.load();
    	setScreen(anchorPane);
    }
    
    public void setMouse(Parent root, Stage stage) {
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	main_window.window_x_offset = event.getSceneX();
            	main_window.window_y_offset = event.getSceneY();
            }
        });
        
       root.setOnMouseDragged((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - main_window.window_x_offset);
                stage.setY(event.getScreenY() - main_window.window_y_offset);
            }
        });
    }
    
    public void setStyle(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("img/app_ico.png")));
        /* Start position of window */
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene_max[1]) / 2);
        stage.setY(screenBounds.getHeight()/2 - scene_max[0]);
    }
    
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
    
	public void setScreen(AnchorPane anchorPane) {
		anchorPaneRoot.getChildren().clear();
		anchorPaneRoot.getChildren().add(anchorPane);
	}
	
	@FXML
    public void BackToMainStage(ActionEvent event) {
    	loadMenu();
    }
	
	// ------------------------------------- RBT vis --------------------------------------------------------
	
	@FXML
	Pane visPane;
	
    private RedBlackTree<Integer> tree= new RedBlackTree<>(this);
    private ArrayList<Integer> nodes = new ArrayList<>();
    private double radius = 15;
    private double vGap = 50;
    
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
		visPane.getChildren().add(hintCricle);
		
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
		
		hintCricle = new Circle(tree.root.x,tree.root.y,radius+5, new Color(0, 0, 0, 0));
		hintCricle.setStroke(Color.GREEN);
		hintCricle.setStrokeWidth(3.0);
		visPane.getChildren().add(hintCricle);
		
		StrokeTransition ft = new StrokeTransition(Duration.millis(500),hintCricle,Color.GREEN,Color.RED);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		
		PathTransition pathT = new PathTransition();
		pathT.setDuration(Duration.millis(1000));
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
	TextField insertTextField;
	@FXML
	TextField deleteTextField;
	@FXML
	TextField searchTextField;
	
	@FXML
	public void insert(KeyEvent k) {
		 if (k.getCode().equals(KeyCode.ENTER))
         {
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
//		                view.setStatus(key + " is already present!");
		            } else {
//		            	System.out.println("Inserting...");
		                tree.insert(key);
		                displayTree();
//		                tree.path.getElements().clear();
//		                tree.search(key);
//			            animateSearch();
		                
//		                setStatus(key + " is inserted!");
		            }
		            insertTextField.clear();
		            
		        }
         }
    }
	
	@FXML
	public void delete(KeyEvent k){
		if (k.getCode().equals(KeyCode.ENTER))
        {
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
     }
	
	@FXML
	public void search(KeyEvent k)
	{
		if (k.getCode().equals(KeyCode.ENTER))
        {
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
	         animateSearch(isNodeFound);     
        }
    }
	
	
	public void displayTree(){
		visPane.getChildren().clear();
	    if(tree.getRoot() != null){
	    	tree.getRoot().x=visPane.getWidth() / 2;
	    	tree.getRoot().y=vGap;
	        displayTree(tree.getRoot(), tree.getRoot().x, tree.getRoot().y, visPane.getWidth() / 4);
	    }
	}
	
	private void displayTree(TreeNode<Integer> root, double x, double y, double hGap){
	    if(root.left != null){
	    	root.left.x= x - hGap;
	    	root.left.y= y + vGap;
	    	visPane.getChildren().add(new Line(x - hGap, y + vGap, x, y));
	        displayTree(root.left, root.left.x, root.left.y, hGap / 2);
	    }
	
	    if (root.right != null){
	    	root.right.x=x + hGap;
	    	root.right.y=y + vGap;
	    	visPane.getChildren().add(new Line(x + hGap, y + vGap, x, y));
	        displayTree(root.right, root.right.x, root.right.y, hGap / 2);
	    }
	
	    tree.getRed(root);
	
	    Circle circle = new Circle(x, y, radius);
	    circle.setStroke(Color.BLACK);
	    if(tree.getRed(root))
	        circle.setFill(Color.INDIANRED);
	    else circle.setFill(Color.GRAY);
	    visPane.getChildren().addAll(circle, new Text(x - 4, y + 4, root.element + ""));
	}
	

}