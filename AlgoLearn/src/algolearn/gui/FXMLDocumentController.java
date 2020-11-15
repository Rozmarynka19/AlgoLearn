package algolearn.gui;

import algolearn.gui.main_window;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class FXMLDocumentController implements Initializable {
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
    private AnchorPane anchorRoot;
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
<<<<<<< HEAD
    public void pressButton(ActionEvent event) throws Exception {     
=======
    public void pressButtonDescription(ActionEvent event) throws Exception {
>>>>>>> master
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/description_fxml.fxml"));
    	Parent root1 = (Parent) fxmlLoader.load();
    	Stage stage = new Stage();
    	setMouse(root1, stage);
    	setStyle(stage);
    	stage.setScene(new Scene(root1));  
    	stage.show();

    }
<<<<<<< HEAD
=======

    @FXML
    public void pressButtonIntroduction(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/introduction_fxml.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        setMouse(root1, stage);
        setStyle(stage);
        stage.setScene(new Scene(root1));
        stage.show();

    }

    @FXML
    public void pressButtonVisualization(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/visualisation_fxml.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        setMouse(root1, stage);
        setStyle(stage);
        stage.setScene(new Scene(root1));
        stage.show();

    }
>>>>>>> master
    
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
    
    @FXML
    public void BackToMainStage(ActionEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}