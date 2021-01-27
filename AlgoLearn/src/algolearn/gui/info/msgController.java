package algolearn.gui.info;

import algolearn.gui.FXMLDocumentController;
import algolearn.gui.HelpClass.Arrow;
//import algolearn.gui.FXMLDocumentController;
import algolearn.gui.HelpClass.HashTable;
import algolearn.gui.info.errors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;



public class msgController extends FXMLDocumentController implements Initializable {

    @FXML
    //id for each top AnchorPane element in every window-fxmlFile
    //necessary for switching between windows
    private AnchorPane anchorPaneRoot;

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
    private errors errorMSG = new errors();


    @FXML public TextArea msgTextArea;
    public void CreateMsg(String msg) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/valueMsg_fxml.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            msgController controller = (msgController) fxmlLoader.getController();
            Stage stage = new Stage();
            setStyle(stage);
            setMouse(root1, stage);
            stage.centerOnScreen();
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
            controller.msgTextArea.setText(msg);
            controller.msgTextArea.setStyle("-fx-text-fill: RED;-fx-font-weight:bold;");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



}

