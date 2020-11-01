package algolearn.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
	private double [] scene_base = {300, 557}; 
	private double [] scene_max = {300, 1140};
    @FXML
    private Button btn;

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;
        stage = (Stage) btn.getScene().getWindow();
        stage.setWidth(scene_base[0]);
        stage.setHeight(scene_base[1]);
        root = FXMLLoader.load(getClass().getResource("fxml/main_fxml2.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        resize_window(stage, 0.01);
    }
    /* Not finished */
    private void resize_window(Stage stage, double speed) {
    	for(double i = scene_base[1]; i<scene_max[1]; i = i + (1 * speed)) {
    		stage.setWidth((int)i);
    	}
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
