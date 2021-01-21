package algolearn.gui;

import algolearn.gui.FXMLDocumentController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
//import org.jsoup.Jsoup;


public class FullscreenController extends FXMLDocumentController implements Initializable {
	
	
// Code to invoke fullscreen window - need to insert into other controller
//	@FXML
//	public void openFullscreen()
//	{
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/fullscreen_fxml.fxml"));
//            Parent root1 = (Parent) fxmlLoader.load();
//            FullscreenController controller = (FullscreenController)fxmlLoader.getController();
//            Stage stage = new Stage();
//            setStyle(stage);
//            setMouse(root1, stage);
////            stage.centerOnScreen();
//            stage.setFullScreen(true);
////            stage.setAlwaysOnTop(true);
////            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.setScene(new Scene(root1));  
//            stage.show();
//            
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//	}


//    @FXML
//    public void BackToMainStage(ActionEvent event) {
//        loadMenu();
//    }
//
//    @FXML
//    WebView introText;
//    private WebEngine engine;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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