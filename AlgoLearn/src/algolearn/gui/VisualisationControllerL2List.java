package algolearn.gui;

import algolearn.gui.HelpClass.Arrow;
import algolearn.gui.info.errors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


public class VisualisationControllerL2List extends FXMLDocumentController implements Initializable {

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
    @FXML HBox MainVBox;
    @FXML TextField AddNodeText;
    @FXML TextField DeleteNodeText;
    @FXML TextField FindNodeText;

    List<Button> childrenButtonList = new ArrayList<>();
    List<Arrow> childrenArrowList = new ArrayList<>();
    List<VBox> ArrowBoxList = new ArrayList<>();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    private Button previousSerchedChild;
    private Button previousButton;

    private void coloringButtons(){
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
        if(previousButton!=null){
            previousButton.setStyle(
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
    }

    @FXML
    public void generateButtonHandler(ActionEvent event){
        //rand.nextInt((max - min) + 1) + min; to find random value
        Random rand = new Random();
        for(int i = rand.nextInt((10 - 0) + 1); i<11; i++){
            if(childrenButtonList.size()<11) {
                coloringButtons();
                int randomNum = rand.nextInt((99 - 1) + 1) + 1;
                if(!CheckForDuplicates(randomNum)) {
                    AddNodeText.setText("" + randomNum);
                    addNewNode();
                }
                else
                    i--;
            }
            else
                break;
        }
        coloringButtons();
    }

    @FXML
    public void addNodeButton(ActionEvent event){
        coloringButtons();
        if(!AddNodeText.getText().equals("")) {
            if(!isInt(AddNodeText.getText())){
                CreateError(errorMSG.OnlyNumeric);
            }
            else {
                if(Integer.parseInt(AddNodeText.getText())<=0 || Integer.parseInt(AddNodeText.getText())>=100) {
                    CreateError(errorMSG.OnlyNumeric);
                }
                else{
                    if (childrenButtonList.size() < 11) {
                        if(!CheckForDuplicates(Integer.parseInt(AddNodeText.getText())))
                            addNewNode();
                        else
                            CreateError(errorMSG.HaveDuplicate);
                    } else {
                        CreateError(errorMSG.IsFull);
                    }
                }
            }
        }
    }

    @FXML
    public void deleteNodeButton(ActionEvent event){
        coloringButtons();
        if(!DeleteNodeText.getText().equals("")){
            if(isInt(DeleteNodeText.getText())) {
                if (childrenButtonList.size() == 0) {
                    CreateError(errorMSG.IsEmpty);
                    DeleteNodeText.setText("");
                } else {
                    deleteNode();
                    DeleteNodeText.setText("");
                }
            }
            else
                CreateError(errorMSG.InputIsString);
        }
    }

    @FXML
    public void findNodeButton(ActionEvent event){
        coloringButtons();
        if(!FindNodeText.getText().equals("")){
            if(childrenButtonList.size()==0) {
                CreateError(errorMSG.IsEmpty);
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
        MainVBox.getChildren().clear();
        childrenButtonList.clear();
        //childrenArrowList.clear();
        ArrowBoxList.clear();
    }

    private void deleteNode(){
        int valueToDelete=Integer.parseInt(DeleteNodeText.getText());
        boolean flag = false;
        for(int i=0;i<childrenButtonList.size();i++){
            if(Integer.parseInt(childrenButtonList.get(i).getText())==valueToDelete){
                childrenButtonList.remove(i);
                //childrenArrowList.remove(i);
                ArrowBoxList.remove(i);
                reloadVisualisationBox();
                flag = true;
                break;
            }
        }
        if(!flag){
            CreateError(errorMSG.IsNotExisting);
        }
    }

    private void findNode() {
        int searchedValue = Integer.parseInt(FindNodeText.getText());
        boolean flag=false;
        Button tmp = null;
        for (Button button : childrenButtonList) {
            if (Integer.parseInt(button.getText()) == searchedValue) {
                tmp = button;
                flag = true;
                break;
            }
        }
        if(flag){
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
        else{
            CreateError(errorMSG.CannotBeFound);
        }
    }

    private void addNewNode(){
        Button btnNumber = new Button();
        previousButton = btnNumber;
        btnNumber.setText(getTextFromAddBox());
        int keyValue = Integer.parseInt(btnNumber.getText());
        btnNumber.setStyle(
                " -fx-background-color: " +
                        "linear-gradient(#00ff00, #009900 100%)" +
                        "linear-gradient(#3a3a3a, #020b02); " +
                        "-fx-background-radius: 5em; " +
                        "-fx-min-width: 30px; " +
                        "-fx-min-height: 30px; " +
                        "-fx-max-width: 30px; " +
                        "-fx-max-height: 30px; "
        );

        addSortButtonList(btnNumber, keyValue);
        VBox box = new VBox();
        Arrow arrow = new Arrow();
        //arrow.setEndX(previousButton.getLayoutX() + 40);
        arrow.setEndX(previousButton.getLayoutX() + 40);
        arrow.setEndY(previousButton.getLayoutY());
        Arrow arrow2 = new Arrow();
        arrow2.setEndX(btnNumber.getLayoutX() - 40);
        arrow2.setEndY(btnNumber.getLayoutY());
        //childrenArrowList.add(arrow);
        //childrenArrowList.add(arrow2);
        //box.setSpacing(10);
        box.setMargin(arrow, new Insets(45, 0, 0, 0));
        box.setMargin(arrow2, new Insets(5, 0, 0, 0));
        box.getChildren().addAll(arrow, arrow2);
        ArrowBoxList.add(box);
        reloadVisualisationBox(); //function to add elements from list
        AddNodeText.setText("");
    }

    private void checklist(){ //funkcja do testowania sortowania listy
       childrenButtonList.forEach((temp) -> System.out.println(temp.getText()));
        System.out.println("---------------------------");
    }

    //function to
    private void reloadVisualisationBox(){
        MainVBox.getChildren().clear();
        for(int i=0, k=0;i<childrenButtonList.size();i++, k+=2){
            MainVBox.getChildren().add(childrenButtonList.get(i));
            ArrowBoxList.get(i).getChildren().get(0).setVisible(i != childrenButtonList.size() - 1);
            ArrowBoxList.get(i).getChildren().get(1).setVisible(i != childrenButtonList.size() - 1);
            MainVBox.getChildren().add(ArrowBoxList.get(i));
            //MainVBox.getChildren().add(childrenArrowList.get(k+1));
        }
    }

    private void addSortButtonList(Button btnNumber, int keyValue){
        int tmp=childrenButtonList.size();
        if(childrenButtonList.size()==0){
            childrenButtonList.add(btnNumber);
        }
        else{
            for(int i=0;i<tmp;i++){
                if((Integer.parseInt(childrenButtonList.get(i).getText())>keyValue) || Integer.parseInt(childrenButtonList.get(i).getText())==keyValue){
                    childrenButtonList.add(i,btnNumber);
                    break;
                }
                else if (i==tmp-1){
                    childrenButtonList.add(btnNumber);
                    break;
                }
            }
        }
    }

    @FXML private TextArea errorTextArea;

    public void CreateError(String msg) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/error_fxml.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            BST_controller controller = (BST_controller) fxmlLoader.getController();
            Stage stage = new Stage();
            setStyle(stage);
            setMouse(root1, stage);
            stage.centerOnScreen();
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
            controller.errorTextArea.setText(msg);
            controller.errorTextArea.setStyle("-fx-text-fill: RED;-fx-font-weight:bold;");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean CheckForDuplicates(int value){
        for(int i=0;i<childrenButtonList.size();i++){
            if(Integer.parseInt(childrenButtonList.get(i).getText())==value){
                return true;
            }
        }
        return false;
    }

    static boolean isInt(String str) {
        try {
            int i = Integer.parseInt(str);
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }

}

