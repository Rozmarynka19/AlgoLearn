package algolearn.gui;

import algolearn.gui.FXMLDocumentController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
//import org.jsoup.Jsoup;


public class DescriptionControllerBST extends FXMLDocumentController implements Initializable {


    @FXML
    public void BackToMainStage(ActionEvent event) {
        loadMenu();
    }

    @FXML
    WebView descriptionText;
    private WebEngine engine;
    
    @FXML
    Button cppButton;
    @FXML
    Button javaButton;
    @FXML
    Button pyButton;
    
    private String selectedLangButton; 

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		engine = descriptionText.getEngine();

		cppButton.getStyleClass().clear();
		cppButton.getStyleClass().add("langBTNselected");
		selectedLangButton = cppButton.getText();
	}
	
	
	@FXML
	public void onLangButtonClickAction(ActionEvent event) throws IOException {
		//set styles of all buttons to the default
		cppButton.getStyleClass().clear();
		cppButton.getStyleClass().add("langBTN");
		javaButton.getStyleClass().clear();
		javaButton.getStyleClass().add("langBTN");
		pyButton.getStyleClass().clear();
		pyButton.getStyleClass().add("langBTN");
		
		//set specified style to selected button
		Button clickedButton = (Button)event.getSource();
		clickedButton.getStyleClass().clear();
		clickedButton.getStyleClass().add("langBTNselected");
		
		//save in any variable which button was selected
		selectedLangButton = clickedButton.getText();
	}

    @FXML
    public void loadCodeExample(ActionEvent event) throws IOException {
        Button clicked_btn = (Button)event.getSource();
        final String script;
        if(clicked_btn.getText().equals("Przykład klasy drzewa")){
            script="treeClass_"+selectedLangButton+"()";
        }
        else if(clicked_btn.getText().equals("Przykład klasy węzła")){
        	script="nodeClass_"+selectedLangButton+"()";
        }
        else if(clicked_btn.getText().equals("Przejście drzewa wzdłuż")){
        	script="preOrder_"+selectedLangButton+"()";
        }
        else if(clicked_btn.getText().equals("Przejście d. poprzecznie")){
        	script="inOrder_"+selectedLangButton+"()";
        }
        else if(clicked_btn.getText().equals("Przejście d. wstecznie")){
        	script="postOrder_"+selectedLangButton+"()";
        }
        else if(clicked_btn.getText().equals("Wstawianie węzła")){
        	script="insert_"+selectedLangButton+"()";
        }
        else if(clicked_btn.getText().equals("Wyszukiwanie węzła")){
        	script="search_"+selectedLangButton+"()";
        }
        else {
        	script="delete_"+selectedLangButton+"()";
        }
        engine = descriptionText.getEngine();
        engine.setJavaScriptEnabled(true);
        engine.load(""+getClass().getResource("Html/BSTcodes.html"));
        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        if (newValue != Worker.State.SUCCEEDED) {
                            return;
                        }
                        String hello2 = (String) engine.executeScript("renev()");
                        String hello = (String) engine.executeScript(script);
                    }
                }
        );
    }
}