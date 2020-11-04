package algolearn.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
	private double [] scene_base = {300, 514}; 
	private double [] scene_max = {300, 1115};
    @FXML
    private Button btn;

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        //Parent root;
        stage = (Stage) btn.getScene().getWindow();
        stage.setWidth(scene_base[0]);
        stage.setHeight(scene_base[1]);
        //root = FXMLLoader.load(getClass().getResource("fxml/main_fxml2.fxml"));
        //Scene scene = new Scene(root);
        //stage.setScene(scene);
        //stage.show();
        resize(stage, scene_max[1], (double)50, 1, 10, true);
    }
    
    /* Resize window */
    private void resize(Stage stage, double width, double speed, int delay, int wait, boolean keep_center) {
        Timer animTimer = new Timer();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        animTimer.scheduleAtFixedRate(new TimerTask() {
        	double inc_widht = (width - stage.getWidth())/speed;
            @Override
            public void run() {
                if (stage.getWidth()<width) {
                    stage.setWidth(stage.getWidth()+(double)inc_widht);

                    if(keep_center) {
                    	stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
                    }
                } else {
                    this.cancel();
                }
            }

        }, delay, wait);
        
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
