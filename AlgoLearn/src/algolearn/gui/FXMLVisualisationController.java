package algolearn.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.javafx.fxml.FXMLLoaderHelper.FXMLLoaderAccessor;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.ProgressBar;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
	//private Map<Circle,Text> arrayCircles = new HashMap<Circle,Text>();

	private ArrayList<Circle> arrayCircles = new ArrayList<Circle>();
	private ArrayList<Text> arrayTexts = new ArrayList<Text>();
	private ArrayList<Line[]> arrayLines = new ArrayList<Line[]>();
	private Circle hintCricle = null;
	private String [] selectedObjectData = new String[2];
	
	static int left_offset = 30, rigth_offset = 30, down_offset = 60;
	
	static int board_width = 800;
	static int radius = 20;
	
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
		
		rootBST = null;
	}
	
	private void test() {
		hintCricle = new Circle(def_pos[0],def_pos[1],radius+5, new Color(0, 0, 0, 0));
		hintCricle.setStroke(Color.GREEN);
		hintCricle.setStrokeWidth(3.0);
		Visualisation_anchorPane.getChildren().add(hintCricle);
	}
	
	@FXML private void searchValue(ActionEvent event) {
		String getValue = searchField.getText();
		int value = Integer.parseInt(getValue);
		ArrayList<double[]> arr = bstFindPath(rootBST, value);
		animateThroughPath(arr);
	}
	
	@FXML private void randomNode(ActionEvent event) {
		Random rand = new Random();
		if(rootBST != null)
			insert(rootBST, rand.nextInt(89) + 10);
		else
			insert(rootBST, 50);
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
				test();
		}
	}
	
	
	@FXML TextField addField, deleteField, searchField;
	
	@FXML private void addValue(ActionEvent event) {
		String getValue = addField.getText();
		
		int value = Integer.parseInt(getValue);
		insert(rootBST, value);
	}
	
	@FXML private void deleteValue(ActionEvent event) {
		String getValue = deleteField.getText();
		int arr_pos = getCircleID(getValue);
		
		if(arr_pos != -1) {
			removeCircleByID(arr_pos);
		}
	}
	
	private Path createMovePath() {
		Path path = new Path();
		path.getElements().add(new MoveTo(def_pos[0], def_pos[1]));
		return path;
	}
	
	private void animateThroughPath(ArrayList<double[]> cPath) {
		if(hintCricle == null) return;
		Path path = createMovePath();
		for(double[] c : cPath)
			path.getElements().add(new LineTo(c[0], c[1]));
		
		PathTransition pathT = new PathTransition();
		pathT.setDuration(Duration.millis(600 * cPath.size()));
		pathT.setPath(path);
		pathT.setNode(hintCricle);
		pathT.setCycleCount(1);
		pathT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathT.setAutoReverse(false);
		pathT.play();
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
    		int id = Integer.parseInt(circle.getId());
    		ArrayList<double[]> arr = bstFindPath(rootBST, id);
    		animateThroughPath(arr);
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
    		int id = Integer.parseInt(text.getId());
    		ArrayList<double[]> arr = bstFindPath(rootBST, id);
    		animateThroughPath(arr);
    		selectedObjectData[0] = text.getId();
    		selectedObjectData[1] = text.getText();
    	});

    	int val = Integer.parseInt(nodeValue);
    	BSTNode xNode = bstFindNode(rootBST, val);
    	
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
	private void drawArrow(double x_start, double y_start, double x_end, double y_end, boolean left) {
		Line [] line = new Line[3];

		line[0] = new Line(x_start, y_start, x_end, y_end);
		if(left) {
			line[1] = new Line(x_end,  y_end - 10, x_end, y_end);
			line[2] = new Line(x_end + 10, y_end + 3, x_end, y_end);
		}else {
			line[1] = new Line(x_end,  y_end - 10, x_end, y_end);
			line[2] = new Line(x_end - 10, y_end + 3, x_end, y_end);
		}
		
		for(Line l : line)
			l.getStyleClass().add("line");
		
		arrayLines.add(line);
		Visualisation_anchorPane.getChildren().addAll(line);
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
    
    BSTNode insert(BSTNode root, int key) { 
    	BSTNode newnode = newNodeBST(key); 
    	BSTNode x = root; 
    	BSTNode y = null; 
    	
    	double xx = def_pos[0], yy = def_pos[1];
    	double xx_prev = 0;
    	double xx_add = def_pos[0];
    	ArrayList<double[]> cPath = new ArrayList<double[]>();
        while (x != null) {
        	yy += down_offset;
        	xx_prev = xx;
        	xx_add /= 2;
            y = x;
            if (key < x.key) {
                x = x.left;
                xx -= xx_add;
            }
            else {
                x = x.right; 
                xx += xx_add;
            }
            double [] arr = {xx, yy};
            cPath.add(arr);
        }
       
        if (y == null) {
        	rootBST = newnode;
            draw(xx, yy, String.valueOf(key));
            test();
        }
        else if (key < y.key) {
            y.left = newnode;
            draw(xx, yy, String.valueOf(key));
            animateThroughPath(cPath);
            drawArrow(xx_prev - radius, ( yy - down_offset + radius/2), xx + 10, yy - radius, true);
        }
        else if (key > y.key) {
            y.right = newnode;
            draw(xx, yy, String.valueOf(key));
            animateThroughPath(cPath);
            drawArrow(xx_prev + radius, ( yy - down_offset + radius/2 ), xx -10, yy - radius, false);
        }
        return y; 
    } 
    
    private ArrayList<double[]> bstFindPath(BSTNode root, int key) {
    	ArrayList<double[]> path = new ArrayList<double[]>();
    	double xx = def_pos[0], yy = def_pos[1];
    	double xx_add = def_pos[0];
    	BSTNode x = root;
    	
    	if( root.key == key) {
    		double [] arr = {def_pos[0]+1, def_pos[1]};
    		path.add(arr);
    		return path;
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
    		}else
        		return path;
    	}
    	
    	ArrayList<double[]> pathEmpty = new ArrayList<double []>();
    	double [] arr = {def_pos[0]+1, def_pos[1]};
    	pathEmpty.add(arr);
    	return pathEmpty;
    }
    
    BSTNode rootBST = null;
}