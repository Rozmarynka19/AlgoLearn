package algolearn.gui;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
public class FXMLDocumentController implements Initializable{
    FileNames fileNames = FileNames.getInstance();

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

    private int realId;

    /**
     * 
     * @param event : Button handler.
     * @throws Exception : Handle button error.
     * 
     *  Handle choosing algorithm from list in main window.
     */
    @FXML /* Expand window */
    private void handleButtonAction(ActionEvent event) throws Exception {
        Button tmp = (Button)event.getSource(); // dwie liniee zamienne
        Stage stage = (Stage) tmp.getScene().getWindow();
        //Stage stage = (Stage) btn.getScene().getWindow(); Dla Olafa - wywaliłem to stwierdzając że lepiej będzie się uniezależnić od konkretnego
                                                            // fx:id gdy z tej funkcji korzysta ok 13-16 przycisków
        this.stage = stage;
        Button clicked_btn = (Button)event.getSource();
        String btn_val = clicked_btn.getId();
        realId=Integer.parseInt(clicked_btn.getId());
        System.out.println(realId);
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
     * @param event Button responsible for creating the visualization window.
     * @throws Exception : Error that occurred during swap into visualization window.
     *
     * Swaping into visualization window from visualisation_fxml.fxml
     */
    @FXML
    public void pressButtonVisualization(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileNames.paths.get(realId).get(2)));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileNames.paths.get(realId).get(0)));
        AnchorPane anchorPane = loader.load();
        setScreen(anchorPane);
    }

    /**
     * @param event Button responsible for creating the description window.
     * @throws Exception : Error that occurred during swap into description window.
     * 
     * Swaping into descrption window from description_fxml.fxml
     */
    @FXML
    public void pressButtonDescription(ActionEvent event) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(fileNames.paths.get(realId).get(1)));
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

	@FXML
    public void BackToMainStage(ActionEvent event) {
    	loadMenu();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

class FileNames{
    private static FileNames instance;
    //instrukcja podpięcia własnych plików:
    //1.Nadaj plikom nazwy unikalne dla algorytmu (struktury danych czy co tam to jest)
    //2.dodaj same nazwy plików do poniższych list w wiersz zgodny z oznaczeniem
    //3.Niczym się nie przejmuj
    //Uwaga, z pewnych powodów nazwy tak długie jak linked_list_description_fxml.fxml powodują błąd więc sugeruję krótsze
    public List<List<String>> paths = addToList(
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // kopiec binarny
            asList("linked_list_introduction.fxml","linked_list_description.fxml","linked_list_visualization.fxml"), //lista jednokierunkowa
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // lista dwukierunkowa
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // sortowanie kubełkowe
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // Sortowanie przez zliczanie
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // Sortowanie przez kopcowanie
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // fft
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // lista z przeskokami
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // bst
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // tablica haszująca o xd xd mieszająca
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // floyd warshall
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // Union Find
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // algorytm grahama
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml") //  red-black tree
    );

    private static <String> List<String> asList(String ... items) {
        List<String> list = new ArrayList();
        for (String item : items) {
            list.add((String) ("fxml/"+item));
        }
        return list;
    }
    private static <String> List<String> addToList(String ... items) {
        List<String> list = new ArrayList();
        for (String item : items) {
            list.add(item);
        }
        return list;
    }

    public static FileNames getInstance() {
        if (instance == null) {
            instance = new FileNames();
        }
        return instance;
    }
}