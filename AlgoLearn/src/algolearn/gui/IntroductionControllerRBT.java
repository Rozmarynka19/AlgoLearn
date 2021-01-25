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


public class IntroductionControllerRBT extends FXMLDocumentController implements Initializable {


    @FXML
    public void BackToMainStage(ActionEvent event) {
        loadMenu();
    }

    @FXML
    WebView introText;
    private WebEngine engine;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		engine = introText.getEngine();
	}

    @FXML
    public void loadText(ActionEvent event) throws IOException {
        Button clicked_btn = (Button)event.getSource();
        final String script;
        if(clicked_btn.getText().equals("Wstawianie Węzła")){
            script="test1()";
        }
        else if(clicked_btn.getText().equals("Rotacje")){
            script="test2()";
        }
        else if(clicked_btn.getText().equals("Wyszukiwanie Węzła")){
            script="test3()";
        }
        else if(clicked_btn.getText().equals("Wprowadzenie")){
            script="test4()";
        }
        else {
            script = "test5()";
        }
        engine = introText.getEngine();
        engine.setJavaScriptEnabled(true);
        engine.load(""+getClass().getResource("Html/RBT.html"));
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