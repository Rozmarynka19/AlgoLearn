package algolearn.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
 
public class main_window extends Application {
	
	private int [] scene_base = {300, 514}; 
	public double [] scene_max = {300, 1105};
	private double window_x_offset = 0, window_y_offset = 0;
	
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
         Parent root = FXMLLoader.load(getClass().getResource("fxml/main_fxml.fxml"));

         root.setOnMousePressed(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
            	 window_x_offset = event.getSceneX();
            	 window_y_offset = event.getSceneY();
             }
         });
         
         root.setOnMouseDragged((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 stage.setX(event.getScreenX() - window_x_offset);
                 stage.setY(event.getScreenY() - window_y_offset);
             }
         });
         
         Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
         
         Scene scene = new Scene(root, scene_base[0], scene_base[1]);
         stage.setResizable(false);
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setX((screenBounds.getWidth() - scene_max[1]) / 2);
         stage.getIcons().add(new Image(this.getClass().getResourceAsStream("img/app_ico.png")));
         stage.setY(screenBounds.getHeight()/2 - scene_max[0]);
         stage.setScene(scene);
         stage.show();
         
         
     }
}
