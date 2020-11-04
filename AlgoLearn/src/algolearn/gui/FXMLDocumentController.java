package algolearn.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
	private double [] scene_base = {300, 300}; 
	public double [] scene_max = {300, 1105};
	private boolean resize_locker = false;
    @FXML
    private Button btn;

    @FXML /* Expand window */
    private void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage = (Stage) btn.getScene().getWindow();
        if(stage.getWidth() < scene_max[1] && !this.resize_locker) {
        	resize(stage, scene_max[1], (double)5, 1, 3);
        	this.resize_locker = false;
        }
        else if (stage.getWidth() >= scene_max[1] && !this.resize_locker) {
        	resize(stage, scene_base[1], (double)5, 1, 3);
        	this.resize_locker = false;
        }
    }
    

    @FXML /* Expand window */
    private void handleCloseWindowAction(ActionEvent event) throws Exception {
    	Stage stage = (Stage) btn.getScene().getWindow();
    	stage.close();
    }
    
    /* Resize window */
    private void resize(Stage stage, double width, double speed, int delay, int wait) {
    	this.resize_locker = true;
    	Timer animTimer = new Timer();
        animTimer.scheduleAtFixedRate(new TimerTask() {
        	double inc_widht = (width - stage.getWidth())/speed;
            @Override
            public void run() {
                if ((stage.getWidth() != width )) {
                    stage.setWidth(stage.getWidth()+(double)inc_widht);
                }
                else {
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
