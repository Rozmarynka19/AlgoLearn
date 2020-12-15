package algolearn.gui;

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


public class IntroductionControllerBST implements Initializable {

    @FXML
    private void minimalizeWindow(ActionEvent event) {
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).setIconified(true);
    }
    
    @FXML
    //id for each top AnchorPane element in every window-fxmlFile
    //necessary for switching between windows
    private AnchorPane anchorPaneRoot; 

    
    @FXML /* Expand window */
    private void handleCloseWindowAction(ActionEvent event) throws Exception {
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
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
        String btn_val = clicked_btn.getId();
        //System.out.println(clicked_btn.getText());
        Path path;
        final String script;
        path= Paths.get("src/algolearn/gui/Html/BST.html");
        if(clicked_btn.getText().equals("Dodawanie Węzła")){
            script="test1()";
        }
        else{
            script="test2()";
        }
        //System.out.println(script);
        engine = introText.getEngine();
        engine.setJavaScriptEnabled(true);
        engine.load( "file:///"+path.toAbsolutePath());
        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        //System.out.println("oldValue: " + oldValue);
                        //System.out.println("newValue: " + newValue);

                        if (newValue != Worker.State.SUCCEEDED) {
                            return;
                        }
                        //System.out.println("Succeeded!");
                        String hello2 = (String) engine.executeScript("renev()");
                        String hello = (String) engine.executeScript(script);

                        //System.out.println("hello: " + hello);
                    }
                }
        );


    }
}