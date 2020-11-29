package algolearn.gui;

import algolearn.gui.main_window;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;



public class VisualisationController extends FXMLDocumentController  implements Initializable {
    /**
     * @param event - Button handler
     * @throws Exception - Error that occurred during minimalize window
     *
     *  Minimalize application window
     */
    @FXML
    private void minimalizeWindow(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).setIconified(true);
    }

    @FXML
    //id for each top AnchorPane element in every window-fxmlFile
    //necessary for switching between windows
    private AnchorPane anchorPaneRoot;

    /**
     * @param event - Button handler
     * @throws Exception - Error that occurred during closing window
     *
     *  Closing application window
     */
    @FXML /* Expand window */
    private void handleCloseWindowAction(ActionEvent event) throws Exception {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }


    /**
     * Swaping into menu window from main_fxml.fxml by remove and add anchorPane
     */
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

    /**
     * @param anchorPane - AnchorPane to add into root anchorPane
     *
     * Remove and add specific anchorPane to the sceen
     */
    public void setScreen(AnchorPane anchorPane) {
        anchorPaneRoot.getChildren().clear();
        anchorPaneRoot.getChildren().add(anchorPane);
    }



    @FXML
    public void BackToMainStage(ActionEvent event) {
        loadMenu();
    }

    @FXML HBox MainVBox;
    @FXML TextField AddNodeText;
    @FXML TextField DeleteNodeText;
    @FXML TextField FindNodeText;

    List<Button> childrenList = new ArrayList<>();;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    private Button previousSerchedChild;
    @FXML
    public void generateButtonHandler(ActionEvent event){
        if(previousSerchedChild!=null){
            previousSerchedChild.setStyle("-fx-background-color: #ffffff");
        }
        Alert a = new Alert(Alert.AlertType.NONE);
        if(!DeleteNodeText.getText().equals("Wprowadź wartość:") && !DeleteNodeText.getText().equals("")){
            if(Integer.parseInt(DeleteNodeText.getText())>=childrenList.size() || Integer.parseInt(DeleteNodeText.getText())<=0) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Błędna wartość, upewnij się że nie przekraczasz przedziału wartości do jakich możesz się odnieść");
                a.show();
                DeleteNodeText.setText("");
            }
            else {
                deleteNode();
                DeleteNodeText.setText("");
            }
        }
        if(!AddNodeText.getText().equals("Wprowadź wartość:") && !AddNodeText.getText().equals("")) {
            Button btnNumber = new Button();
            btnNumber.setText(getTextFromAddBox());
            childrenList.add(btnNumber);
            MainVBox.getChildren().add(btnNumber);
            AddNodeText.setText("");
        }
        if(!FindNodeText.getText().equals("Wprowadź wartość:") && !FindNodeText.getText().equals("")){
            if(Integer.parseInt(FindNodeText.getText())>=childrenList.size() || Integer.parseInt(FindNodeText.getText())<=0) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Błędna wartość, upewnij się że nie przekraczasz przedziału wartości do jakich możesz się odnieść");
                a.show();
                FindNodeText.setText("");
            }
            else {
                findNode();
                FindNodeText.setText("");
            }
        }
    }

    private String getTextFromAddBox() {
            String TextValue = AddNodeText.getText();
            return TextValue;
    }

    public void restartWindow(ActionEvent actionEvent) {
        for(int i=0;i<childrenList.size();i++){
            MainVBox.getChildren().remove(childrenList.get(i));
        }

    }

    private void deleteNode(){
        MainVBox.getChildren().remove(childrenList.get(Integer.parseInt(DeleteNodeText.getText())-1));
        childrenList.remove(Integer.parseInt(DeleteNodeText.getText())-1);
    }

    private void findNode() {
        Button tmp = childrenList.get(Integer.parseInt(FindNodeText.getText())-1);
        tmp.setStyle("-fx-background-color: #00c928");
        previousSerchedChild = tmp;

    }

}