package algolearn.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Algolearn Team
 * 
 *  Main window class to initialization main application window
 */
public class main_window extends Application {
	
	private int [] scene_base = {300, 515};
	public static double window_x_offset = 0;
	public static double window_y_offset = 0;
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
    	FXMLDocumentController fxmlDocumentController= new FXMLDocumentController();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/main_fxml.fxml"));
        fxmlDocumentController.setMouse(root, stage);
        Scene scene = new Scene(root, scene_base[0], scene_base[1]);
        fxmlDocumentController.setStyle(stage);
        stage.setScene(scene);
        stage.show();      
     }
}
