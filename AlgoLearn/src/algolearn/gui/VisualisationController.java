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

    List<Button> childrenButtonList = new ArrayList<>();
    List<Arrow> childrenArrowList = new ArrayList<>();;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    private Button previousSerchedChild;
    private Button previousButton;

    @FXML
    public void generateButtonHandler(ActionEvent event){

        if(previousSerchedChild!=null){
            previousSerchedChild.setStyle(
                    " -fx-background-color: " +
                            "linear-gradient(#ff0000, #990000 100%)" +
                            "linear-gradient(#3a3a3a, #020b02); " +
                            "-fx-background-radius: 5em; " +
                            "-fx-min-width: 30px; " +
                            "-fx-min-height: 30px; " +
                            "-fx-max-width: 30px; " +
                            "-fx-max-height: 30px; "
            );
        }

        Alert a = new Alert(Alert.AlertType.NONE);

        if(!DeleteNodeText.getText().equals("")){
            if(Integer.parseInt(DeleteNodeText.getText())>childrenButtonList.size() || Integer.parseInt(DeleteNodeText.getText())<=0) {
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

        if(!AddNodeText.getText().equals("")) {
            if(childrenButtonList.size()<11) {
                Button btnNumber = new Button();
                previousButton = btnNumber;
                btnNumber.setText(getTextFromAddBox());
                btnNumber.setStyle(
                        " -fx-background-color: " +
                                "linear-gradient(#ff0000, #990000 100%)" +
                                "linear-gradient(#3a3a3a, #020b02); " +
                                "-fx-background-radius: 5em; " +
                                "-fx-min-width: 30px; " +
                                "-fx-min-height: 30px; " +
                                "-fx-max-width: 30px; " +
                                "-fx-max-height: 30px; "
                );
                if (childrenButtonList.size() >= 1) {
                    Arrow arrow = new Arrow();
                    MainVBox.getChildren().add(arrow);
                    arrow.setEndX(previousButton.getLayoutX() + 40);
                    arrow.setEndY(previousButton.getLayoutY());
                    childrenArrowList.add(arrow);
                }
                childrenButtonList.add(btnNumber);
                MainVBox.getChildren().add(btnNumber);
                AddNodeText.setText("");
            }
            else {
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Maksymalna ilość elementów to 11.");
                a.show();
            }
        }

        if(!FindNodeText.getText().equals("")){
            if(Integer.parseInt(FindNodeText.getText())>childrenButtonList.size() || Integer.parseInt(FindNodeText.getText())<=0) {
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
        int size1, size2;
        size1=childrenButtonList.size();
        size2=
        for(int i=0;i<childrenButtonList.size();i++){
            MainVBox.getChildren().remove(childrenButtonList.get(i));
            childrenButtonList.remove(i);
        }
        for(int i=0;i<childrenArrowList.size();i++){
            MainVBox.getChildren().remove(childrenArrowList.get(i));
        }

    }

    private void deleteNode(){
        MainVBox.getChildren().remove(childrenButtonList.get(Integer.parseInt(DeleteNodeText.getText())-1));
        childrenButtonList.remove(Integer.parseInt(DeleteNodeText.getText())-1);
        if(childrenArrowList.size()>1) {
            MainVBox.getChildren().remove(Integer.parseInt(DeleteNodeText.getText()));
            childrenArrowList.remove(Integer.parseInt(DeleteNodeText.getText()) - 2);
        }
        if(childrenButtonList.size()==1){
            MainVBox.getChildren().remove(1);
            childrenArrowList.remove(0);
        }
    }

    private void findNode() {
        Button tmp = childrenButtonList.get(Integer.parseInt(FindNodeText.getText())-1);
        tmp.setStyle(
                " -fx-background-color: " +
                        "linear-gradient(#3BFF72, #05B336 100%)" +
                        "linear-gradient(#3a3a3a, #020b02); " +
                        "-fx-background-radius: 5em; " +
                        "-fx-min-width: 30px; " +
                        "-fx-min-height: 30px; " +
                        "-fx-max-width: 30px; " +
                        "-fx-max-height: 30px; "
        );
        previousSerchedChild = tmp;

    }

}