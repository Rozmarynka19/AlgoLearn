package algolearn.gui;

import algolearn.gui.HelpClass.Arrow;
import algolearn.gui.info.errors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

//import algolearn.gui.FXMLDocumentController;


public class VisualisationControllerSkipList extends FXMLDocumentController implements Initializable {

    @FXML
    //id for each top AnchorPane element in every window-fxmlFile
    //necessary for switching between windows
    private AnchorPane anchorPaneRoot;


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
    private errors errorMSG = new errors();
    @FXML HBox lvl1;
    @FXML HBox lvl2;
    @FXML HBox lvl3;
    @FXML HBox lvl4;
    @FXML HBox lvl5;
    @FXML HBox lvl6;
    List<HBox> levelsList = new ArrayList();
    @FXML TextField AddNodeText;
    @FXML TextField DeleteNodeText;
    @FXML TextField FindNodeText;

    List<Button> childrenButtonList1 = new ArrayList<>();
    List<Button> childrenButtonList2 = new ArrayList<>();
    List<Button> childrenButtonList3 = new ArrayList<>();
    List<Button> childrenButtonList4 = new ArrayList<>();
    List<Button> childrenButtonList5 = new ArrayList<>();
    List<Button> childrenButtonList6 = new ArrayList<>();
    List<List<Button>> buttonList = new ArrayList();
    List<Arrow> childrenArrowList1 = new ArrayList<>();
    List<Arrow> childrenArrowList2 = new ArrayList<>();
    List<Arrow> childrenArrowList3 = new ArrayList<>();
    List<Arrow> childrenArrowList4 = new ArrayList<>();
    List<Arrow> childrenArrowList5 = new ArrayList<>();
    List<Arrow> childrenArrowList6 = new ArrayList<>();
    List<List<Arrow>> arrowList = new ArrayList();
    HashMap<Button,Integer> nodesHeights = new HashMap<>();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        levelsList.add(lvl1);
        levelsList.add(lvl2);
        levelsList.add(lvl3);
        levelsList.add(lvl4);
        levelsList.add(lvl5);
        levelsList.add(lvl6);
        buttonList.add(childrenButtonList1);
        buttonList.add(childrenButtonList2);
        buttonList.add(childrenButtonList3);
        buttonList.add(childrenButtonList4);
        buttonList.add(childrenButtonList5);
        buttonList.add(childrenButtonList6);
        arrowList.add(childrenArrowList1);
        arrowList.add(childrenArrowList2);
        arrowList.add(childrenArrowList3);
        arrowList.add(childrenArrowList4);
        arrowList.add(childrenArrowList5);
        arrowList.add(childrenArrowList6);
        for(int i=0;i<6;i++){
            Arrow ar = new Arrow();
            ar.setStartX(levelsList.get(i).getLayoutX()+1);
            ar.setStartY(levelsList.get(i).getLayoutY());
            levelsList.get(i).getChildren().add(ar);
        }
    }

    private Button previousSerchedChild;
    private Button previousButton;

    private void coloringButtons(){

    }

    @FXML
    public void generateButtonHandler(ActionEvent event){
        //rand.nextInt((max - min) + 1) + min; to find random value
        Random rand = new Random();
        for(int i = rand.nextInt((6 - 0) + 1); i<7; i++){
            if(buttonList.get(0).size()<6) {
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
                if(Integer.parseInt(AddNodeText.getText())<=0) {
                    CreateError(errorMSG.OnlyNumeric);
                }
                else{
                    if (buttonList.get(0).size() < 6) {
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
                if (buttonList.get(0).size() == 0) {
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
            if(buttonList.get(0).size()==0) {
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
        nodesHeights.clear();
        for(int i=0;i<6;i++) {
            levelsList.get(i).getChildren().clear();
            buttonList.get(i).clear();
            arrowList.get(i).clear();
        }
    }

    private void deleteNode(){
        int valueToDelete=Integer.parseInt(DeleteNodeText.getText());
        boolean flag = false;
        for(int j=0;j<buttonList.size();j++){
        for(int i=0;i<buttonList.get(j).size();i++){
            if(Integer.parseInt(buttonList.get(j).get(i).getText())==valueToDelete){
                buttonList.get(j).remove(i);
                arrowList.get(j).remove(i);
                reloadArrows();
                reloadArrows2();
                flag = true;
                break;
            }
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
        for (Button button : buttonList.get(0)) {
            if (Integer.parseInt(button.getText()) == searchedValue) {
                tmp = button;
                flag = true;
                break;
            }
        }
        if(flag){
            tmp.setStyle(
                    " -fx-background-color: " +
                            "linear-gradient(#ff0000, #990000 100%)" +
                            "linear-gradient(#3a3a3a, #020b02); " +
                            "-fx-min-width: 46px; " +
                            "-fx-min-height: 46px; " +
                            "-fx-max-width: 46px; " +
                            "-fx-max-height: 46px; "
            );
            previousSerchedChild = tmp;
        }
        else{
            CreateError(errorMSG.CannotBeFound);
        }
    }

    int levelWidth = 628;
    private void addNewNode(){
        Button btnNumber = new Button();
        previousButton = btnNumber;
        btnNumber.setText(getTextFromAddBox());
        int keyValue = Integer.parseInt(btnNumber.getText());
        int height = randomHeight();
        btnNumber.setStyle(
                " -fx-background-color: " +
                        "linear-gradient(#ff0000, #990000 100%)" +
                        "linear-gradient(#3a3a3a, #020b02); " +
                        "-fx-min-width: 46px; " +
                        "-fx-min-height: 46px; " +
                        "-fx-max-width: 46px; " +
                        "-fx-max-height: 46px; "
        );

        addSortButtonList(btnNumber, keyValue);
        checklist();
        reloadArrows();
        nodesHeights.put(btnNumber, height);
        ShowKeys();
        AddNodeText.setText("");
        System.out.println("wysokość: " + height);
        for(int i=1;i<height;i++) {
            Button test = new Button();
            test.setStyle(
                    " -fx-background-color: " +
                            "linear-gradient(#ff0000, #990000 100%)" +
                            "linear-gradient(#3a3a3a, #020b02); " +
                            "-fx-min-width: 46px; " +
                            "-fx-min-height: 46px; " +
                            "-fx-max-width: 46px; " +
                            "-fx-max-height: 46px; "
            );
            test.setText(btnNumber.getText());
            addSortButtonListLevels(test, keyValue, i);
        }
        reloadArrows2();
    }

    private void ShowKeys(){ for(Map.Entry m:nodesHeights.entrySet()){
        System.out.println(m.getKey()+" "+m.getValue() + "||||");
    }   }



    private void checklist(){ //funkcja do testowania sortowania listy
        buttonList.get(0).forEach((temp) -> System.out.println(temp.getText()));
        System.out.println("---------------------------");
    }

    private void reloadArrows2(){
        int fulldistance = 0;
        for(int i=1;i<6;i++) {
            arrowList.get(i).clear();
            for (int j = 0; j < buttonList.get(i).size()+1; j++) {
                Arrow arrow = new Arrow();
                if(buttonList.get(i).size()!=0 && j<buttonList.get(i).size()) {
                    arrow.setEndX(findBaseX(buttonList.get(i).get(j).getText(), j, i));
                    fulldistance+=46;
                }
                arrowList.get(i).add(arrow);
                fulldistance+=arrow.getEndX();
            }
            arrowList.get(i).get(arrowList.get(i).size()-1).setEndX(levelWidth-fulldistance);
            reloadVisualisationBox(levelsList.get(i), i);
            fulldistance = 0;
        }
    }

    private double findBaseX(String val, int pos, int height){
        int distance = 0;
        for(int i=1;i<buttonList.get(height).size()+1;i++){
            if(val.equals(buttonList.get(height).get(i-1).getText())){
               break;
            }
            else
                distance = 40 * (i);
        }
       for(int i=1;i<buttonList.get(0).size()+1;i++){
           if(val.equals(buttonList.get(0).get(i-1).getText())) {
               if(i==1){
                   return (40) * i - (distance);
               }
               if(pos>0) {
                   return (40 + 46) * i - distance - findBaseX2(buttonList.get(height).get(pos - 1).getText(), pos, height);
               }
               else {
                   return (40 + 46) * i - distance - 46;
               }
           }
       }
       return 0;
    }

    private double findBaseX2(String val, int pos, int height){
        int distance = 0;
        for(int i=1;i<buttonList.get(height).size()+1;i++){
            if(val.equals(buttonList.get(height).get(i-1).getText())){
                break;
            }
            else
                distance = 40 * (i) - distance;
        }
        for(int i=1;i<buttonList.get(0).size()+1;i++){
            if(val.equals(buttonList.get(0).get(i-1).getText())) {
                if(i==1){
                    return (40 + 46) * i - distance;
                }
                return (40+46) * i - distance;
            }
        }
        return 0;
    }

    private void reloadArrows(){
        arrowList.get(0).clear();
        for(int i=0;i<buttonList.get(0).size()+1;i++){
            Arrow arrow = new Arrow();
            arrow.setEndX(0 + 40);
            arrow.setEndY(previousButton.getLayoutY());
            arrowList.get(0).add(arrow);
        }
        arrowList.get(0).get(arrowList.get(0).size()-1).setEndX(levelWidth-buttonList.get(0).size()*(46+41));
        reloadVisualisationBox(levelsList.get(0));

    }

    //function to
    private void reloadVisualisationBox(HBox level){
        if(level!=null)
            level.getChildren().clear();
        for(int i=0;i<buttonList.get(0).size();i++){
            level.getChildren().add(arrowList.get(0).get(i));
            level.getChildren().add(buttonList.get(0).get(i));
        }

        level.getChildren().add(arrowList.get(0).get(arrowList.get(0).size()-1));
    }

    private void reloadVisualisationBox(HBox level, int height){
        if(level!=null)
            level.getChildren().clear();
        for(int i=0;i<buttonList.get(height).size();i++){
            level.getChildren().add(arrowList.get(height).get(i));
            level.getChildren().add(buttonList.get(height).get(i));
        }

        level.getChildren().add(arrowList.get(height).get(arrowList.get(height).size()-1));
    }

    private void addSortButtonList(Button btnNumber, int keyValue){
        int tmp=buttonList.get(0).size();
        if(buttonList.get(0).size()==0){
            buttonList.get(0).add(btnNumber);
        }
        else{
            for(int i=0;i<tmp;i++){
                if((Integer.parseInt(buttonList.get(0).get(i).getText())>keyValue) || Integer.parseInt(buttonList.get(0).get(i).getText())==keyValue){
                    buttonList.get(0).add(i,btnNumber);
                    break;
                }
                else if (i==tmp-1){
                    buttonList.get(0).add(btnNumber);
                    break;
                }
            }
        }
    }

    private void addSortButtonListLevels(Button btnNumber, int keyValue ,int level){
        int tmp=buttonList.get(level).size();
        if(buttonList.get(level).size()==0){
            buttonList.get(level).add(btnNumber);
        }
        else{
            for(int i=0;i<tmp;i++){
                if((Integer.parseInt(buttonList.get(level).get(i).getText())>keyValue) || Integer.parseInt(buttonList.get(level).get(i).getText())==keyValue){
                    buttonList.get(level).add(i,btnNumber);
                    break;
                }
                else if (i==tmp-1){
                    buttonList.get(level).add(btnNumber);
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
        for(int i=0;i<buttonList.get(0).size();i++){
            if(Integer.parseInt(buttonList.get(0).get(i).getText())==value){
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

    private int randomHeight(){
        //rand.nextInt((max - min) + 1) + min; to find random value
        Random rand = new Random();
        int height = 1;
        int i = 10;
        while(i!=0) {
            i = rand.nextInt((1 - 0) + 1) + 0;
            if(i==1){
                height++;
                if(height==6)
                    break;
            }
        }
        return height;
    }

}

