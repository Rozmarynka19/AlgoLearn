package algolearn.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLDocumentController implements Initializable {
	private double [] scene_base = {300, 300}; 
	public double [] scene_max = {300, 1105};
	private boolean resize_locker = false;
    @FXML
    private Button btn;
    private String btn_id = "NULL";
    @FXML
    private Text txtMainTitle;
    private String txtMainTitleString = "Algolearn - ";
    @FXML /* Expand window */
    private void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage = (Stage) btn.getScene().getWindow();
        Button clicked_btn = (Button)event.getSource();
        String btn_val = clicked_btn.getId();
        if(this.btn_id == "NULL") this.btn_id = btn_val;
        if(stage.getWidth() < scene_max[1] && this.resize_locker == false && clicked_btn.getId() == btn_id) {
        	resize(stage, scene_max[1], (double)15, 1, 3);
        	txtMainTitle.setText(txtMainTitleString + clicked_btn.getText());
        }
        else if (stage.getWidth() >= scene_max[1] && this.resize_locker == false && clicked_btn.getId() == btn_id)  {
        	resize(stage, scene_base[1], (double)-15, 1, 3);
        }else if(clicked_btn.getId() != btn_id) {
        	txtMainTitle.setText(txtMainTitleString + clicked_btn.getText());
        }
        this.btn_id = clicked_btn.getId();
    }
    
    @FXML
    private ProgressBar progressBar;
    
    @FXML
    private Button wpr, opi, wiz;

    @FXML /* Expand window */
    private void handleCloseWindowAction(ActionEvent event) throws Exception {
    	Stage stage = (Stage) btn.getScene().getWindow();
    	stage.close();
    }
    
    private long time_exec = 0;
    
    /* Resize window */
    private void resize(Stage stage, double width, double speed, int delay, int wait) {
    	this.resize_locker = true;
    	Timer animTimer = new Timer();
        animTimer.scheduleAtFixedRate(new TimerTask() {
        double inc_widht = speed;
        @Override
        public void run() {
        	if ((stage.getWidth() != width )) {
                	if(inc_widht > 0 && stage.getWidth() + inc_widht > width  ||
                			inc_widht < 0 && stage.getWidth() - inc_widht < width)
                		inc_widht = width - stage.getWidth();
                    stage.setWidth((double)stage.getWidth()+(double)inc_widht);
                }
                else {
                    this.cancel();
                }
            }

        }, delay, wait);
        time_exec = System.nanoTime();
    	this.resize_locker = false;
    }

    public void handleProgress(ActionEvent event) {
    	Button clicked_btn = (Button)event.getSource();
    	String id = clicked_btn.getId();
    	if(id.equals(wpr.getId()))
    			setProgress(0.33);
    	else if(id.equals(opi.getId()))
			setProgress(0.66);
    	else if(id.equals(wiz.getId()))
			setProgress((double)1);
    }
    
    private void setProgress(double progress) {
    	Timeline timeline = new Timeline();
    	KeyValue keyValue = new KeyValue(progressBar.progressProperty(), progress);
    	KeyFrame keyFrame = new KeyFrame(new Duration(1000), keyValue);
    	timeline.getKeyFrames().add(keyFrame);

    	timeline.play();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}