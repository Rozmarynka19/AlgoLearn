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


public class DescriptionControllerRBT extends FXMLDocumentController implements Initializable {


    @FXML
    public void BackToMainStage(ActionEvent event) {
        loadMenu();
    }

    @FXML
    WebView introText;
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
//		engine = introText.getEngine();
	}
	
	
	@FXML
	public void onLangButtonClickAction(ActionEvent event) throws IOException {
		//set styles of all buttons to the default
		cppButton.getStyleClass().remove("langBTNselected");
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
		System.out.println("Button selected: "+selectedLangButton);
	}

//    @FXML
//    public void loadText(ActionEvent event) throws IOException {
//        Button clicked_btn = (Button)event.getSource();
//        Path path;
//        final String script;
//        path= Paths.get("src/algolearn/gui/Html/RBT.html");
//        if(clicked_btn.getText().equals("Wstawianie Węzła")){
//            script="test1()";
//        }
//        else if(clicked_btn.getText().equals("Rotacje")){
//            script="test2()";
//        }
//        else if(clicked_btn.getText().equals("Wyszukiwanie Węzła")){
//            script="test3()";
//        }
//        else if(clicked_btn.getText().equals("Wprowadzenie")){
//            script="test4()";
//        }
//        else {
//            script = "test5()";
//        }
//        engine = introText.getEngine();
//        engine.setJavaScriptEnabled(true);
//        engine.load( "file:///"+path.toAbsolutePath());
//        engine.getLoadWorker().stateProperty().addListener(
//                new ChangeListener() {
//                    @Override
//                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                        if (newValue != Worker.State.SUCCEEDED) {
//                            return;
//                        }
//                        String hello2 = (String) engine.executeScript("renev()");
//                        String hello = (String) engine.executeScript(script);
//                    }
//                }
//        );
//    }
}