package algolearn.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import algolearn.gui.info.errors;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * @author Algolearn Team
 *
 * JavaFX handler class that controls the buttons in main window and sub-windows.
 * 
 */
public class FXMLVisualisationController implements Initializable {
	private double [] scene_base = {300, 300}; 
	public double [] scene_max = {300, 1123};
	private boolean resize_locker = false;
    @FXML
    private Stage stage, secStage;
    @FXML
    private Button btn, wpr, opi, wiz;
    @FXML
    private TextArea infoTextField;
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
    
    @FXML
    WebView introText;
    private WebEngine engine;

    /**
     * 
     * @param event : Button handler.
     * @throws Exception : Handle button error.
     * 
     *  Handle choosing algorithm from list in main window.
     */
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
    
    /**
     * @param event - Button handler
     * @throws Exception - Error that occurred during minimalize window
     * 
     *  Minimalize application window
     */
    @FXML private void minimalizeWindow(ActionEvent event) {
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
    @FXML private ProgressBar generateBar;

    
    /**
     * Swaping background categories based on category_data variable.
     * For example when bool equals true tail with introdution changes color form red to green.
     * It happends becouse of changing style class in the background
     */
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
    
    /**
     * @param event - Button handler
     * @throws Exception - Error that occurred during closing window
     * 
     *  Closing application window
     */
    @FXML /* Expand window */
    private void handleCloseWindowAction(ActionEvent event) throws Exception {
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    
    /**
     * @param event - Q&A - Authors section
     * Modify the content of infoTextField variable by passing into data about authors of the application.
     */
    @FXML
    private void Creators(ActionEvent event){
    	infoTextField.setText(
    			"Twórcy aplikacji:\n"
    			+ "	Monika Rozmarynowska\n"
    			+ "	Jakub Kucharski\n"
    			+ "	Olaf Maliszewski\n"
    			+ "	Piotr Wojdalski\n"
    			+ "	Krzysztof Bieniek\n"
    			+ "	Krzysztof Kubiś"
    	);
    }
    
    /**
     * @param event - Q&A - Requirements section
     * Modify the content of infoTextField variable by passing into data about requirements of the application.
     */
    @FXML
    private void Requirements(ActionEvent event){
    	infoTextField.setText(
    			"Wymagania systemowe: \n"
    			+ " Zostaną wylistowane po ukończeniu aplikacji...\n\n\n"
    			+ "Wymagania systemowe (minimalne): \n"
    			+ " Zostaną wylistowane po ukończeniu aplikacji...\n\n\n"
    	);
    }
    
    /**
     * 
     * @param stage: Main window stage
     * @param width: Width to which window is resizing
     * @param speed: Speed of resizing the window
     * @param delay: Delay of start animation.
     * @param wait: Waiting time
     * 
     *  Function of resizing window.
     */
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

    /**
     * 
     * @param  Button handler.
     * 
     * 	Setup progress of the progressbar.
     */
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
    
    /**
     * Calculate progress of currennt algorithm
     * @return progress value
     */
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
    
    /**
     * @param progress : Progress of currennt alghoritm
     * 
     * Animated progressbar based on Timeline
     */
    private void setProgress(double progress) {
    	Timeline timeline = new Timeline();
    	KeyValue keyValue = new KeyValue(progressBar.progressProperty(), progress);
    	KeyFrame keyFrame = new KeyFrame(new Duration(1000), keyValue);
    	timeline.getKeyFrames().add(keyFrame);

    	timeline.play();
    }
    
    /**
     * @param event Button responsible for creating the description window.
     * @throws Exception : Error that occurred during swap into description window.
     * 
     * Swaping into descrption window from description_fxml.fxml
     */
    @FXML
    public void pressButtonDescription(ActionEvent event) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/description_fxml.fxml"));
    	AnchorPane anchorPane = loader.load();
    	setScreen(anchorPane);
    }

    /**
     * @param event Button responsible for creating the introduction window.
     * @throws Exception : Error that occurred during swap into introduction window.
     * 
     * Swaping into introduction window from introduction_fxml.fxml
     */
    @FXML
    public void pressButtonIntroduction(ActionEvent event) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/introduction_fxml.fxml"));
    	AnchorPane anchorPane = loader.load();
    	setScreen(anchorPane);
    }
    
    /**
     * @param event Button responsible for creating the visualization window.
     * @throws Exception : Error that occurred during swap into visualization window.
     * 
     * Swaping into visualization window from visualisation_fxml.fxml
     */
    @FXML
    public void pressButtonVisualization(ActionEvent event) throws Exception {
      	FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/visualisation_fxml.fxml"));
    	AnchorPane anchorPane = loader.load();
    	setScreen(anchorPane);
    }

    /**
     * @param root Handle of Parent
     * @param stage Handle of Stage
     * 
     * Setup draging of window without border.
     */
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
    
    /**
     * @param stage Handle of Stage
     * 
     * Seting up basic style of window. Window will be undecorated, unresizable and Centerd.
     * Also application icon will be attached.
     */
    public void setStyle(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("img/app_ico.png")));
        /* Start position of window */
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene_max[1]) / 2);
        stage.setY(screenBounds.getHeight()/2 - scene_max[0]);
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
    
    /**
     * @param anchorPane - AnchorPane to add into root anchorPane
     * 
     * Remove and add specific anchorPane to the sceen
     */
	public void setScreen(AnchorPane anchorPane) {
		anchorPaneRoot.getChildren().clear();
		anchorPaneRoot.getChildren().add(anchorPane);
	}
	
	/**
	 * @param event - Questionmark handler in main_menu
	 * 
	 *  Create new window with informations about project
	 *  
	 *  -- Still need to keep track of opened windows ( possible multiple windows to open )
	 */
	@FXML
    public void CreateQuestionmark(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/questionmark.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            setStyle(stage);
            setMouse(root1, stage);
            stage.setScene(new Scene(root1));  
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		if(introText == null)
//			System.out.println("no null!!!");
//		else
//			engine = introText.getEngine();
	}

	@FXML
    public void BackToMainStage(ActionEvent event) {
    	loadMenu();
    }
	
    public void loadText(ActionEvent actionEvent) throws IOException {
//      OutputStream out = new BufferedOutputStream(System.out);
//
//      File file = new File("lista-jednokierunkowa.docx");
//      File file2 = new File("lista-jednokierunkowa.html");
//      FileInputStream fis = new FileInputStream(file.getAbsolutePath());
//
//      XWPFDocument document = new XWPFDocument(fis);
//
//      List<XWPFParagraph> paragraphs = document.getParagraphs();

      String s="<html><body>";
//      for (XWPFParagraph para : paragraphs) {
//          System.out.println(para.getText());
//          s+=para.getText()+"\n";
//      }

      s+="</body></html>";
      engine = introText.getEngine();
//      engine.load( "file:///D:/studia/projekt_inzynierski/d/AlgoLearn/AlgoLearn/lista-jednokierunkowa.html");
      engine.load("file:///D:/semestr5/IPZ1/repo/lista-jednokierunkowa.html");
//
//      fis.close();
//      out.flush();
  }
    
    // =================================	VISUALISATION	=====================================//
	@FXML private AnchorPane Visualisation_anchorPane;
	@FXML private TextArea errorTextArea;
	@FXML private Button hiddenValues;
	//private Map<Circle,Text> arrayCircles = new HashMap<Circle,Text>();

	private ArrayList<Circle> arrayCircles = new ArrayList<Circle>();
	private ArrayList<Text> arrayTexts = new ArrayList<Text>();
	private ArrayList<Line[]> arrayLines = new ArrayList<Line[]>();
	private Circle hintCricle = null;
	private String [] selectedObjectData = new String[2];
	private errors errorMSG = new errors();
	private boolean pathTransitionDone = true;
	private boolean generateDone = true;
	private String selectedCircle = null;
	private int selectedCircleID = 0;
	
	private boolean randomized = false;
	private String [] randomizedIDs = new String[2];
	
	@FXML Button addBTN, deleteBTN, searchBTN, unknownBTN, restartBTN, backBTN;
	@FXML Slider timeSlider;
	
	static int left_offset = 30, rigth_offset = 30, down_offset = 60;
	
	private int bst_height = 0;
	
	static int board_width = 800;
	static int radius = 20;
	static int max_height = 4;
	
	static double [] def_pos = {board_width/2, 50};
	private double x = def_pos[0], y = def_pos[1];
	
	private Color randomColor() {
		Random rand = new Random();
	    double red = rand.nextDouble();
	    double green = rand.nextDouble();
	    double blue = rand.nextDouble();
		return new Color(red, green, blue, 1);
	}
	
	@FXML private void restartVisualisation(ActionEvent event) {
		if(pathTransitionDone == false) {
			bstTEXT.setText(errorMSG.setupInformation(errorMSG.finishAnimation));
			return;
		}
		
		for(Circle arr : arrayCircles)
			Visualisation_anchorPane.getChildren().remove(arr);
		
		for(Text arr : arrayTexts)
			Visualisation_anchorPane.getChildren().remove(arr);
		
		for(Line[] arr : arrayLines)
			for(Line line : arr)
				Visualisation_anchorPane.getChildren().remove(line);
		
		if(hintCricle != null) {
			Visualisation_anchorPane.getChildren().remove(hintCricle);
			hintCricle = null;
		}
		
		arrayCircles = new ArrayList<Circle>();
		arrayTexts = new ArrayList<Text>();
		arrayLines = new ArrayList<Line[]>();
		
		setHiddenValues("?", "?");
		selectedCircleID = 0;
		
		randomizedIDs[0] = null;
		randomizedIDs[1] = null;
		
		if(pathInit == true) {
			StepByStepCheckBox.setDisable(false);
			pPath = new Path();
			dPath = new ArrayList<double[]>();
			pathInit = false;
		}
		
		if(randomized) {
        	deleteBTN.setDisable(false);
        	searchBTN.setDisable(false);
        	randomized = false;
		}
		
		selectedCircle = null;
		rootBST = null;
		bstTEXT.setText(errorMSG.setupInformation(""));
	}
	
	private void drawHintCircle() {
		hintCricle = new Circle(def_pos[0],def_pos[1],radius+5, new Color(0, 0, 0, 0));
		hintCricle.setStroke(Color.RED);
		hintCricle.setStrokeWidth(3.0);
		Visualisation_anchorPane.getChildren().add(hintCricle);
	}
	
	@FXML private void searchValue(ActionEvent event) {
		if(!pathTransitionDone) {
			CreateError(errorMSG.pathTNotDone);
			return;
		}
		
		String getValue = searchField.getText();
		if(getValue.equals(randomizedIDs[0]) || getValue.equals(randomizedIDs[1])) {
			handleFinishOperation(14);
			return;
		}
		searchField.setText("");
		
		if(!analizeInput(getValue)) {
			return;
		}
		int value = Integer.parseInt(getValue);
		ArrayList<double[]> arr = bstFindPath(rootBST, value);
	}
	
	private void setHiddenValues(String a, String b) {
		hiddenValues.setText("Ukryte liczby: [ " + a + ", "+b+" ]");
	}
	
	private void randomNode() {
		Random rand = new Random();
		
		if(arrayCircles.size() != 0) {
			restartVisualisation(null);	
		}
		insert(rootBST, rand.nextInt(20)+50, false);
		for(int i = 1; i<10;) {
			int rnd_value = rand.nextInt(89) + 10;
			if(insert(rootBST, rnd_value, false)) {
				i++;
			}
		}
		
		int [] ids = new int[2];
		ids[0] = rand.nextInt(9) + 1;
		
		do {
			ids[1] = rand.nextInt(9) + 1;
		}while(ids[1] == ids[0]);
		
		for(int i = 0; i<2; i++) {
			updateCirlceToolip(arrayCircles.get(ids[i]), "?");
			updateTextToolip(arrayTexts.get(ids[i]), "?");
		}
		
		randomized = true;
		randomizedIDs[0] = arrayTexts.get(ids[0]).getText();
		randomizedIDs[1] = arrayTexts.get(ids[1]).getText();
		
		setHiddenValues(arrayTexts.get(ids[0]).getText(), arrayTexts.get(ids[1]).getText());
		
		arrayTexts.get(ids[0]).setText("  ?");
		arrayTexts.get(ids[1]).setText("  ?");
		
		bstTEXT.setText(errorMSG.setupInformation(errorMSG.generateTree));
    	deleteBTN.setDisable(true);
		
	}
	
	private int getCircleID(String value) {
		for(int i = 0; i<arrayTexts.size(); i++) {
			if(arrayTexts.get(i).getText().equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	private void removeCircleByID(int id) {
		// Remove shapes
		Visualisation_anchorPane.getChildren().remove(arrayTexts.get(id));
		Visualisation_anchorPane.getChildren().remove(arrayCircles.get(id));
		arrayTexts.remove(id);
		arrayCircles.remove(id);
		
		// Remove lines
		if( id > 0) {
			Line [] line = arrayLines.get(id - 1);
			for(int i = 0; i < 3; i++)
				Visualisation_anchorPane.getChildren().remove(line[i]);
			arrayLines.remove(id-1);
		}

		// Remove hintCircle
		if(hintCricle != null) {
			Visualisation_anchorPane.getChildren().remove(hintCricle);
			hintCricle = null;
			if(arrayTexts.size() > 0)
				drawHintCircle();
		}
	}
	
	@FXML TextField addField, deleteField, searchField, unknownField;
	
	@FXML private void addValue(ActionEvent event) {
		if(!pathTransitionDone) {
			CreateError(errorMSG.pathTNotDone);
			return;
		}
		String getValue = addField.getText();
		addField.setText("");
		if(!analizeInput(getValue)) {
			return;
		}
		
		int value = Integer.parseInt(getValue);
		insert(rootBST, value, true);
	}
	
	int removeTracker = 0;
	
	@FXML private void deleteValue(ActionEvent event) {
		if(!pathTransitionDone) {
			CreateError(errorMSG.pathTNotDone);
			return;
		}
		
		String getValue = deleteField.getText();
		deleteField.setText("");
		if(!analizeInput(getValue)) {
			return;
		}
		// Get node to remove
		BSTNode newRoot = findValueToDelete(rootBST, Integer.parseInt(getValue));
		
		
		// No childrens - just delate it
		if(newRoot.left == null && newRoot.right == null) {
			System.out.println("NULL");
			
		}
		
		// Get keys from subtree
		ArrayList<Integer> keys = new ArrayList<Integer>();
		getKeys(newRoot, keys);
		keys.remove((Object)Integer.parseInt(getValue));
		
		// One or two childrens - rebuild tree
		if((newRoot.left != null && newRoot.right == null) || (newRoot.left == null && newRoot.right != null)) {
			HashMap<String, double[]> before_remove = getPositions(keys);
			
			rootBST = deleteRec(rootBST, Integer.parseInt(getValue));
			
			HashMap<String, double[]> after_remove = getPositions(keys);
			
			
			for(Integer k : keys) {
				Path path = new Path();
				path.getElements().add(new MoveTo(before_remove.get(Integer.toString(k))[0], before_remove.get(Integer.toString(k))[1]));
				path.getElements().add(new LineTo(after_remove.get(Integer.toString(k))[0], after_remove.get(Integer.toString(k))[1]));
				Circle circle = getCircle(Integer.toString(k));
				Text text = getText(Integer.toString(k));
				removeTransition(path, circle, null);
				removeTransition(path, null, text);
				
			}
			
			int circleID = getID(getValue, true, false, false);
			int textID = getID(getValue, false, true, false);
			

			Path path = new Path();
			path.getElements().add(new MoveTo(def_pos[0]-1, def_pos[1]+1));
			path.getElements().add(new LineTo(def_pos[0], def_pos[1]));
			removeTransition(path, hintCricle, null);

			Visualisation_anchorPane.getChildren().remove(arrayCircles.get(circleID));
			Visualisation_anchorPane.getChildren().remove(arrayTexts.get(textID));
			arrayTexts.remove(textID);
			arrayCircles.remove(circleID);
			if(Integer.parseInt(getValue) == rootBST.key)
				keys.add(Integer.parseInt(getValue));
			
			ArrayList<Line[]> lines = linesToRemove(keys);
			for(Line[] l : lines) {
				for(int index = 0; index <3; index++) {
					Visualisation_anchorPane.getChildren().remove(l[index]);
				}
				arrayLines.remove(l);
				System.out.println("Lines count: " + arrayLines.size());
			}
			
			// Lines to draw

			if(Integer.parseInt(getValue) == rootBST.key)
				keys.remove(Integer.parseInt(getValue));
			ArrayList<double[]> line = linesPositions(rootBST, keys);
			System.out.println("Line size: "+line.size());
			for(double[] l : line) {
				boolean left = ((int)l[4] == 1)? true:false;
				drawArrow(l[0], l[1], l[2], l[3], true, "test");
				for(int i = 0; i<6; i++)
					System.out.print("\t"+l[i]);
				System.out.println();
			}			
		}
		// Two childrens - rebuid find a node and rebuid tree
		if(newRoot.left != null && newRoot.right != null){
			
		}
	}
	
	private void removeTransition(Path path, Circle circle, Text text) {
		//pathTransitionDone = false;
		PathTransition pathT = new PathTransition();
		pathT.setDuration(Duration.millis(timeSlider.getValue() * 2));
		pathT.setPath(path);
		if(circle != null)
			pathT.setNode(circle);
		else if(text != null)
			pathT.setNode(text);
		pathT.setCycleCount(1);
		pathT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathT.setAutoReverse(false);
		pathT.setOnFinished(event ->{
			
			removeTracker++;
			System.out.println(removeTracker);
		});
		pathT.play();
	}
	
	private Circle getCircle(String key) {
		for(Circle c : arrayCircles) {
			if(c.getId().equals(key)) {
				return c;
			}
		}
		return null;
	}
	
	private Text getText(String key) {
		for(Text t : arrayTexts) {
			if(t.getId().equals(key)) {
				return t;
			}
		}
		return null;
	}
	
	private ArrayList<Line[]> linesToRemove(ArrayList<Integer> keys){
		ArrayList<Line[]> line = new ArrayList<Line[]>();
		for(Integer k : keys) {
			for(Line[] l : arrayLines) {
				if(l[0].getId().equals(Integer.toString(k))) {
					line.add(l);
				}
			}
		}
		return line;
	}
	private int getID(String key, boolean circle, boolean text, boolean line) {
		if(line) {
			for(int i = 0; i<arrayLines.size();i++){
				Line[] arr = arrayLines.get(i);
				if(arr[0].getId().equals(key))
					return i;
			}
		}else if(text) {
			for(int i = 0; i<arrayTexts.size();i++)
				if(arrayTexts.get(i).getId().equals(key))
					return i;
		}else if(circle) {
			for(int i = 0; i<arrayCircles.size();i++)
				if(arrayCircles.get(i).getId().equals(key))
					return i;
		}
			
		return -1;
	}
	
	@FXML private void unknownValue(ActionEvent event) {
		if(!pathTransitionDone) {
			CreateError(errorMSG.pathTNotDone);
			return;
		}
		String getValue = unknownField.getText();
		unknownField.setText("");
		if(!analizeInput(getValue))
			return;
		
		if(selectedCircle == null) {
			CreateError(errorMSG.nodeNotSelected);
			return;
		}
		
		if(arrayTexts.get(selectedCircleID).getText().equalsIgnoreCase("  ?")) {
			if(arrayTexts.get(selectedCircleID).getId().equalsIgnoreCase(getValue)) {
				arrayTexts.get(selectedCircleID).setText(getValue);
				hintCricle.setStroke(Color.GREEN);
				if(randomizedIDs[0].equalsIgnoreCase(getValue))
					randomizedIDs[0] = "?";
				
				else if(randomizedIDs[1].equalsIgnoreCase(getValue)) 
					randomizedIDs[1] = "?";
				
				if(randomizedIDs[0] == "?" && randomizedIDs[1] == "?") {
		        	deleteBTN.setDisable(false);
				}

				updateCirlceToolip(arrayCircles.get(selectedCircleID), getValue);
				updateTextToolip(arrayTexts.get(selectedCircleID), getValue);
				setHiddenValues(randomizedIDs[0], randomizedIDs[1]);
			}
		}
	}
	
	private int findSelected(String node) {
		for(int i = 0; i<arrayCircles.size(); i++) {
			if(arrayCircles.get(i).getId() == node) {
				return i;
			}
		}
		return 0;
	}
	private void draw(double x, double y, String nodeValue) {
		Circle circle = new Circle(x,y,radius, randomColor());
		
		Text text = new Text(nodeValue);
		text.setBoundsType(TextBoundsType.VISUAL);
		text.setX(circle.getCenterX() - radius/2 - 2);
		text.setY(circle.getCenterY() + radius/2 - 2);
		
    	circle.getStyleClass().add("circle");
    	circle.setId(nodeValue);
    	circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
    		if(!pathTransitionDone) {
    			bstTEXT.setText(errorMSG.setupInformation(errorMSG.finishAnimation));
    			return;
    		}
    		int id = Integer.parseInt(circle.getId());
    		ArrayList<double[]> arr = bstFindPath(rootBST, id);
    		selectedCircle = nodeValue;
    		selectedCircleID = findSelected(nodeValue);
    		selectedObjectData[0] = circle.getId();
    		selectedObjectData[1] = text.getText();
    	});
    	
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
    	
    	text.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
    		if(!pathTransitionDone) {
    			bstTEXT.setText(errorMSG.setupInformation(errorMSG.finishAnimation));
    			return;
    		}
    		int id = Integer.parseInt(text.getId());
    		selectedCircle = nodeValue;
    		selectedCircleID = findSelected(nodeValue);
    		ArrayList<double[]> arr = bstFindPath(rootBST, id);
    		selectedObjectData[0] = text.getId();
    		selectedObjectData[1] = text.getText();
    	});

    	int val = Integer.parseInt(nodeValue);
    	
    	String sTooltip = new String();
    	if(rootBST != null)
    		if(rootBST.key == val)
    			sTooltip = "Korzeń BST \n Klucz: "+nodeValue;
    		else
    			sTooltip = "Węzeł BST \n Klucz: "+nodeValue;
    	
    	Tooltip tt = new Tooltip();
    	tt.setText(sTooltip);
    	tt.setStyle("-fx-font-size: 16px;");
    	tt.setShowDelay(Duration.millis(500));
    	Tooltip.install(circle, tt);
    	Tooltip.install(text, tt);
    	
		arrayCircles.add(circle);
		arrayTexts.add(text);
		
		Visualisation_anchorPane.getChildren().addAll(circle, text);
	}
	
	private void updateCirlceToolip(Circle circle, String s) {
		String sTooltip = new String();
    	if(rootBST != null)
    		sTooltip = "Węzeł BST \n Klucz: "+s;
    	
    	Tooltip tt = new Tooltip();
    	tt.setText(sTooltip);
    	tt.setStyle("-fx-font-size: 16px;");
    	tt.setShowDelay(Duration.millis(500));
    	Tooltip.install(circle, tt);
	}
	
	private void updateTextToolip(Text text, String s) {
		String sTooltip = new String();
    	if(rootBST != null)
    		sTooltip = "Węzeł BST \n Klucz: "+s;
    	
    	Tooltip tt = new Tooltip();
    	tt.setText(sTooltip);
    	tt.setStyle("-fx-font-size: 16px;");
    	tt.setShowDelay(Duration.millis(500));
    	Tooltip.install(text, tt);
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
					CreateError(errorMSG.OnlyNumeric);
					return false;
				}
			}
			return true;
		}
		CreateError(errorMSG.WrongInput);
		return false;
	}
	
	
    public void CreateError(String msg) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/error_fxml.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            FXMLVisualisationController controller = (FXMLVisualisationController)fxmlLoader.getController();
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
    
    @FXML public void pressRandomBar() {
    	if(generateDone == false)
    		return;

    	generateBar.setProgress(0);
    	addBTN.setDisable(true);
    	deleteBTN.setDisable(true);
    	unknownBTN.setDisable(true);
    	restartBTN.setDisable(true);
    	backBTN.setDisable(true);
    	searchBTN.setDisable(true);
    	generateDone = false;
    	Timeline timeline = new Timeline();
    	KeyValue keyValue = new KeyValue(generateBar.progressProperty(), 1);
    	KeyFrame keyFrame = new KeyFrame(new Duration(1500), keyValue);
    	timeline.getKeyFrames().add(keyFrame);
    	timeline.setOnFinished(event->{
        	addBTN.setDisable(false);
        	unknownBTN.setDisable(false);
        	restartBTN.setDisable(false);
        	backBTN.setDisable(false);
        	searchBTN.setDisable(false);
    		randomNode();
        	generateDone = true;
    	});
    	timeline.play();
    }
	
    @FXML private Text bstTEXT;
    @FXML private CheckBox StepByStepCheckBox;
    private boolean pathInit = false;
    private Path pPath = new Path();
    private ArrayList<double[]> dPath = new ArrayList<double[]>();
    
    private int node_value = 0;
    private double [] node_cords = new double[2];
    private double [] arrow_cords = new double[4];
    private boolean arrow_type;
    
    private void handleFinishOperation(int opt) {
    	switch(opt) {
    		case 0: return;
	    	case 1: // add root
	            drawHintCircle();
	    		draw(node_cords[0], node_cords[1], String.valueOf(node_value));
	    		break;
	    	case 2: // add node
	    		draw(node_cords[0], node_cords[1], String.valueOf(node_value));
	    		drawArrow(arrow_cords[0], arrow_cords[1], arrow_cords[2], arrow_cords[3], arrow_type, String.valueOf(node_value));
	    		
	    		break;
	    	case 3: // add error - value already exists
	    		msg(errorMSG.nodeAlredyExists);
	    		break;
	    	case 4: // add error - height max
	    		msg(errorMSG.maxHeightBST);
	    		break;
	    	case 5: //  add node - success msg
	    		optMsg(errorMSG.addNode, String.valueOf(node_value));
	    		break;
	    		
	    		
	    	case 11: // find root
	    		Visualisation_anchorPane.getChildren().remove(hintCricle);
	    		drawHintCircle();
	    		optMsg(errorMSG.searchRoot, String.valueOf(node_value));
	    		break;
	    	case 12: // find node
	    		if(String.valueOf(node_value).equals(randomizedIDs[0]) || String.valueOf(node_value).equals(randomizedIDs[1]))
		    		optMsg(errorMSG.searchNode, "??");
	    		else
		    		optMsg(errorMSG.searchNode, String.valueOf(node_value));
	    		break;
	    	case 13: // find node don't exists
	    		optMsg(errorMSG.searchNotFound, String.valueOf(node_value));
	    		System.out.println(randomizedIDs[0] + " " + randomizedIDs[1]);
	    		break;
	    	case 14: // find node is hidden
	    		msg(errorMSG.searchHidden);
	    		break;
    	}
    }
	private void multipleAnimations(Path path, int opt) {
		if(hintCricle == null) return;
		hintCricle.setStroke(Color.GREEN);
		pathTransitionDone = false;
		PathTransition pathT = new PathTransition();
		pathT.setDuration(Duration.millis(timeSlider.getValue() * dPath.size()));
		pathT.setPath(path);
		pathT.setNode(hintCricle);
		pathT.setCycleCount(1);
		pathT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathT.setAutoReverse(false);
		pathT.setOnFinished(event ->{
			hintCricle.setStroke(Color.RED);
			StepByStepCheckBox.setDisable(false);
			pPath = new Path();
			dPath = new ArrayList<double[]>();
			pathInit = false;
			pathTransitionDone = true;
			handleFinishOperation(opt);
		});
		pathT.play();
	}
	private void singleAnimations(Path path, int opt) {
		if(hintCricle == null) return;
		hintCricle.setStroke(Color.GREEN);
		pathTransitionDone = false;
		PathTransition pathT = new PathTransition();
		pathT.setDuration(Duration.millis(timeSlider.getValue() * 2));
		pathT.setPath(path);
		pathT.setNode(hintCricle);
		pathT.setCycleCount(1);
		pathT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathT.setAutoReverse(false);
		pathT.setOnFinished(event ->{
			hintCricle.setStroke(Color.RED);
			if(dPath.size() <= 1) {
				pPath = new Path();
				dPath = new ArrayList<double[]>();
				pathInit = false;
			}
			StepByStepCheckBox.setDisable(false);
			handleFinishOperation(opt);
			pathTransitionDone = true;
		});
		pathT.play();
	}
    
    private void initializePath(ArrayList<double[]> path, int opt) {
    	if(path.size() < 2) return;
    	if(pathInit == false) {
	    	if(dPath.size() == 0) {
	    		pPath.getElements().add(new MoveTo(def_pos[0], def_pos[1]));
	    		for(double[] c : path) {
	    			pPath.getElements().add(new LineTo(c[0], c[1]));
	    			dPath.add(c);
	    		}
	    		pathInit = true;
	    	}
    	}
    	StepByStepCheckBox.setDisable(true);
    	handleAnimation(!StepByStepCheckBox.isSelected(), opt);
    }
    
    private void handleAnimation(boolean state, int opt) {
    	if(dPath.size() == 0) return;
    	if(pathTransitionDone == false) return;
    	if(pathInit == false) return;
    	
    	if(state) {
    		Path path = new Path();
    		path.getElements().add(new MoveTo(dPath.get(0)[0], dPath.get(0)[1]));
    		for(int i = 1; i<dPath.size(); i++) {
    			path.getElements().add(new LineTo(dPath.get(i)[0], dPath.get(i)[1]));
    		}
    		multipleAnimations(path, opt);
    	}else {
    		if(dPath.size() > 1) {
	    		Path stepPath = new Path();
	    		stepPath.getElements().add(new MoveTo(dPath.get(0)[0], dPath.get(0)[1]));
	    		stepPath.getElements().add(new LineTo(dPath.get(1)[0], dPath.get(1)[1]));
	    		dPath.remove(0);
	    		singleAnimations(stepPath, opt);
    		}
    	}
    	
    }
    
    private void msg(String errorMSG) {
    	bstTEXT.setText(this.errorMSG.setupInformation(errorMSG));
    }
    
    private void optMsg(String errorMSG, String value) {
    	bstTEXT.setText(this.errorMSG.setupOperationInfo(errorMSG, value));
    }
    
    @FXML private void next(ActionEvent event) {
    	handleAnimation(!StepByStepCheckBox.isSelected(), 0);
    }
    
    @FXML private void skip(ActionEvent event) {
    	handleAnimation(StepByStepCheckBox.isSelected(), 0);
    }
    // =================================	Binnary search tree =================================//
    class BSTNode extends FXMLVisualisationController{
    	int key;
    	BSTNode left, right;
    }
    
    BSTNode newNodeBST(int data) { 
    	BSTNode temp = new BSTNode(); 
        temp.key = data; 
        temp.left = null; 
        temp.right = null; 
        return temp; 
    }
    private boolean insert(BSTNode root, int key, boolean animate) { 
    	BSTNode newnode = newNodeBST(key); 
    	BSTNode x = root; 
    	BSTNode y = null; 
    	bst_height = 1;
    	double xx = def_pos[0], yy = def_pos[1];
    	double xx_prev = 0;
    	double xx_add = def_pos[0];
    	ArrayList<double[]> cPath = new ArrayList<double[]>();
    	double [] arr_base = {xx, yy};
    	cPath.add(arr_base);
        while (x != null) {
        	if(bst_height >= max_height) {
        		if(animate)
        			handleFinishOperation(4);
        		return false;
        	}
        	yy += down_offset;
        	xx_prev = xx;
        	xx_add /= 2;
            y = x;
            if (key < x.key) {
                x = x.left;
                xx -= xx_add;
            }
            else if (key > x.key){
                x = x.right; 
                xx += xx_add;
            }
            else {
            	handleFinishOperation(3);
            	return false;
            }
            double [] arr = {xx, yy};
            cPath.add(arr);
            bst_height++;
        }
        
        node_value = key;
        node_cords[0] = xx;
        node_cords[1] = yy;

        arrow_cords[1] = yy - down_offset + radius/2;
        arrow_cords[3] = yy - radius;
        
        
        
        if (y == null) {
        	rootBST = newnode;
        	handleFinishOperation(1);
            return true;
        }
        else if (key < y.key) {
            y.left = newnode;
            arrow_cords[0] = xx_prev - radius;
            arrow_cords[2] = xx + 10;
            arrow_type = true;
            if(animate) {
            	initializePath(cPath, 2);
            	handleFinishOperation(5);
            }else 
            	handleFinishOperation(2);
            return true;
        }
        else if (key > y.key) {
            y.right = newnode;
            arrow_cords[0] = xx_prev + radius;
            arrow_cords[2] = xx - 10;
            arrow_type = false;
            if(animate) {
            	initializePath(cPath, 2);
            	handleFinishOperation(5);
            }else
            	handleFinishOperation(2);
            return true;
        }
        return false; 
    } 
    private BSTNode findValueToDelete(BSTNode root, int key) {
    	BSTNode x = root;
    	
    	while(x != null) {
    		if(key < x.key)
    			x = x.left;
    		else if(key > x.key)
    			x = x.right;
    		else
    			return x;
    	}
    	
    	return x;
    }
    
    private void getKeys(BSTNode root, ArrayList<Integer> keys){
    	if(root == null) return;
    	
    	getKeys(root.left, keys);
    	getKeys(root.right, keys);
    	keys.add(root.key);
    }
    
    private HashMap<String, double[]> getPositions(ArrayList<Integer> keys){
    	HashMap<String, double[]> pos = new HashMap<String, double[]>();
    	
    	for(int i = 0; i<keys.size(); i++) {
    		Integer key = keys.get(i);
    		double [] cords = new double [2];
    		double xx_add = def_pos[0];
    		cords[0] = def_pos[0];
    		cords[1] = def_pos[1];
    		BSTNode x = rootBST;
    		
    		while(x != null) {
        		xx_add /= 2;
        		if(key < x.key) {
        			x = x.left;
        			cords[0] -= xx_add;
        		}else if(key > x.key) {
        			x = x.right;
        			cords[0] += xx_add;
        		}else {
        			pos.put(Integer.toString(key), cords);
        			break;
        		}
        		cords[1] += down_offset;
    		}
    	}
    	return pos;
    }
    
    private ArrayList<double[]> linesPositions(BSTNode root, ArrayList<Integer> keys) { 
    	ArrayList<double[]> lines = new ArrayList<double[]>();
    	for(Integer key : keys) {
        	BSTNode x = root; 
        	BSTNode y = null; 
        	
        	double xx = def_pos[0], yy = def_pos[1];
        	double xx_prev = 0;
        	double xx_add = def_pos[0];
	        while (x != null) {
	        	yy += down_offset;
	        	xx_prev = xx;
	        	xx_add /= 2;
	            y = x;
	            if (key < x.key) {
	                x = x.left;
	                xx -= xx_add;
	            }
	            else if (key > x.key){
	                x = x.right; 
	                xx += xx_add;
	            }
	            else {
	            	break;
	            }
	        }
		        double x1 = (key < y.key) ?  xx_prev - radius : xx_prev + radius;
		        double x2 = yy - down_offset + radius/2;
		        double x3 = (key < y.key) ? xx + 10 : xx - 10;
		        double x4 = yy - radius;
		        double x5 = (key < y.key) ? 1 : 0;
		        double keySet = (double)key;
	        	double [] arr = {x1, x2, x3, x4, x5, keySet};
	            lines.add(arr);
    	}
        return lines;
    } 
    
    private ArrayList<double[]> bstFindPath(BSTNode root, int key) {
    	ArrayList<double[]> path = new ArrayList<double[]>();
    	double xx = def_pos[0], yy = def_pos[1];
    	double xx_add = def_pos[0];
    	BSTNode x = root;
    	double [] arr_base = {xx, yy};
    	
    	node_value = key;
    	
    	if( root.key == key) {
    		handleFinishOperation(11);
    		return path;
    	}else {
    		path.add(arr_base);
    	}
    	
    	while(x != null) {
    		yy += down_offset;
    		xx_add /= 2;
    		if(key < x.key) {
    			x = x.left;
    			xx -= xx_add;
    			double[] arr = {xx, yy};
    			path.add(arr);
    		}else if(key > x.key) {
    			x = x.right;
    			xx += xx_add;
    			double[] arr = {xx, yy};
    			path.add(arr);
    		}else {
        		initializePath(path, 12);
        		return path;
    		}
    	}
    	path.remove(path.size()-1);
    	if(path.size() == 1) {
    		double [] arr = {def_pos[0]+1, def_pos[1]};
    		path.add(arr);
    	}
		initializePath(path, 13);
    	return path;
    }
    
    BSTNode deleteRec(BSTNode root, int key)
    {	
        if (root == null)
            return root;
 
        if (key < root.key)
            root.left = deleteRec(root.left, key);
        else if (key > root.key)
            root.right = deleteRec(root.right, key);
 
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            
            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
 
        return root;
    }
    
    int minValue(BSTNode root)
    {
        int minv = root.key;
        while (root.left != null) 
        {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }
    
    BSTNode rootBST = null;
}