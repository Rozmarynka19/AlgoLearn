package algolearn.gui;

import algolearn.gui.FXMLDocumentController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
//import org.jsoup.Jsoup;


public class IntroductionControllerSkipList extends FXMLDocumentController implements Initializable {

    @FXML
    //id for each top AnchorPane element in every window-fxmlFile
    //necessary for switching between windows
    private AnchorPane anchorPaneRoot;

    @FXML
    public void BackToMainStage(ActionEvent event) {
        engine.executeScript("pause_vid()");
        loadMenu();
        // do what you have to do
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
        Path path;
        final String script;
        boolean flagS = false;
        path=Paths.get("src/algolearn/gui/Html/skipList.html");
        if(clicked_btn.getText().equals("Dodawanie Węzła")){
            script="test1()";
            flagS=true;
        }
        else if(clicked_btn.getText().equals("Usuwanie Węzła")){
            script="test2()";
        }
        else if(clicked_btn.getText().equals("Wyszukiwanie Węzła")){
            script="test3()";
        }
        else {
            script = "test4()";
        }
        engine = introText.getEngine();
        engine.setJavaScriptEnabled(true);
        engine.load( "file:///"+path.toAbsolutePath());
        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        if (newValue != Worker.State.SUCCEEDED) {
                            return;
                        }
                        String hello2 = (String) engine.executeScript("renev()");
                        String hello = (String) engine.executeScript(script);
                        String hello3;
                        if(!script.equals("test4()"))
                             hello3 = (String) engine.executeScript("pause_vid()");
                    }
                }
        );


    }
}