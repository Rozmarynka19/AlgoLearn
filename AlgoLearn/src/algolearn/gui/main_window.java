package algolearn.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class main_window extends Application {
	
	private int [] scene_base = {1123, 514}; 
	
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
         Parent root = FXMLLoader.load(getClass().getResource("fxml/main_fxml.fxml"));
     
         stage.setTitle("Algolearn");
         Scene scene = new Scene(root, scene_base[0], scene_base[1]);
         stage.setScene(scene);
         stage.show();
     }
}
