package algolearn.gui;

import algolearn.gui.HelpClass.Arrow;
//import algolearn.gui.FXMLDocumentController;
import algolearn.gui.HelpClass.HashTable;
import algolearn.gui.info.errors;
import algolearn.gui.info.msgController;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;



public class VisualisationControllerHashTab extends FXMLDocumentController implements Initializable {

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
    @FXML TextField AddNodeText;
    @FXML TextField DeleteNodeText;
    @FXML TextField FindNodeText;

    List<Button> childrenButtonList = new ArrayList<>();

    HashTable hashTab = new HashTable();

    String universalButtonStyle = " -fx-background-color: " +
                                        "linear-gradient(#171615, #0A0908 100%)" +
                                        "linear-gradient(#3a3a3a, #020b02); " +
                                        "-fx-min-width: 38px; "  +
                                        "-fx-min-height: 38px; "  +
                                        "-fx-max-width: 38px; "  +
                                        "-fx-max-height: 38px; "  +
                                        "-fx-text-fill: white;"   +
                                         "-fx-font-size: 9;";

    String findButtonStyle = " -fx-background-color: " +
                            "linear-gradient(#0906E7, #0705B3 100%)" +
                            "linear-gradient(#3a3a3a, #020b02); " +
                            "-fx-min-width: 38px; "  +
                            "-fx-min-height: 38px; "  +
                            "-fx-max-width: 38px; "  +
                            "-fx-max-height: 38px; "  +
                            "-fx-text-fill: white;"   +
                            "-fx-font-size: 9;";

    List<Button> childrenButtonList1 = new ArrayList<>();
    List<Button> childrenButtonList2 = new ArrayList<>();
    List<Button> childrenButtonList3 = new ArrayList<>();
    List<Button> childrenButtonList4 = new ArrayList<>();
    List<Button> childrenButtonList5 = new ArrayList<>();
    List<Button> childrenButtonList6 = new ArrayList<>();
    List<Button> childrenButtonList7 = new ArrayList<>();
    List<Button> childrenButtonList8 = new ArrayList<>();
    List<Button> childrenButtonList9 = new ArrayList<>();
    List<Button> childrenButtonList10 = new ArrayList<>();
    List<Button> childrenButtonList11 = new ArrayList<>();
    List<Button> childrenButtonList12 = new ArrayList<>();
    List<Button> childrenButtonList13 = new ArrayList<>();
    List<Button> childrenButtonList14 = new ArrayList<>();
    List<Button> childrenButtonList15 = new ArrayList<>();
    List<Button> childrenButtonList16 = new ArrayList<>();
    List<Button> childrenButtonList17 = new ArrayList<>();
    List<Button> childrenButtonList18 = new ArrayList<>();
    List<Button> childrenButtonList19 = new ArrayList<>();
    List<Button> childrenButtonList20 = new ArrayList<>();
    List<List<Button>> ButtonList = new ArrayList<>();

    List<Button> childrenButtonListba1;
    List<Button> childrenButtonListba2;
    List<Button> childrenButtonListba3;
    List<Button> childrenButtonListba4;
    List<Button> childrenButtonListba5;
    List<Button> childrenButtonListba6;
    List<Button> childrenButtonListba7;
    List<Button> childrenButtonListba8;
    List<Button> childrenButtonListba9;
    List<Button> childrenButtonListba10;
    List<Button> childrenButtonListba11;
    List<Button> childrenButtonListba12;
    List<Button> childrenButtonListba13;
    List<Button> childrenButtonListba14;
    List<Button> childrenButtonListba15;
    List<Button> childrenButtonListba16;
    List<Button> childrenButtonListba17;
    List<Button> childrenButtonListba18;
    List<Button> childrenButtonListba19;
    List<Button> childrenButtonListba20;
    List<List<Button>> ButtonListba;




    List<VBox> BoxList = new ArrayList();
    @FXML VBox box1;
    @FXML VBox box2;
    @FXML VBox box3;
    @FXML VBox box4;
    @FXML VBox box5;
    @FXML VBox box6;
    @FXML VBox box7;
    @FXML VBox box8;
    @FXML VBox box9;
    @FXML VBox box10;
    @FXML VBox box11;
    @FXML VBox box12;
    @FXML VBox box13;
    @FXML VBox box14;
    @FXML VBox box15;
    @FXML VBox box16;
    @FXML VBox box17;
    @FXML VBox box18;
    @FXML VBox box19;
    @FXML VBox box20;

    @FXML HBox ArrowPlace;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        BoxList.add(box1);
        BoxList.add(box2);
        BoxList.add(box3);
        BoxList.add(box4);
        BoxList.add(box5);
        BoxList.add(box6);
        BoxList.add(box7);
        BoxList.add(box8);
        BoxList.add(box9);
        BoxList.add(box10);
        BoxList.add(box11);
        BoxList.add(box12);
        BoxList.add(box13);
        BoxList.add(box14);
        BoxList.add(box15);
        BoxList.add(box16);
        BoxList.add(box17);
        BoxList.add(box18);
        BoxList.add(box19);
        BoxList.add(box20);
        ButtonList.add(childrenButtonList1);
        ButtonList.add(childrenButtonList2);
        ButtonList.add(childrenButtonList3);
        ButtonList.add(childrenButtonList4);
        ButtonList.add(childrenButtonList5);
        ButtonList.add(childrenButtonList6);
        ButtonList.add(childrenButtonList7);
        ButtonList.add(childrenButtonList8);
        ButtonList.add(childrenButtonList9);
        ButtonList.add(childrenButtonList10);
        ButtonList.add(childrenButtonList11);
        ButtonList.add(childrenButtonList12);
        ButtonList.add(childrenButtonList13);
        ButtonList.add(childrenButtonList14);
        ButtonList.add(childrenButtonList15);
        ButtonList.add(childrenButtonList16);
        ButtonList.add(childrenButtonList17);
        ButtonList.add(childrenButtonList18);
        ButtonList.add(childrenButtonList19);
        ButtonList.add(childrenButtonList20);
        initBa();
        Arrow ar = new Arrow();
        ar.setStartX(0-135);
        ArrowPlace.getChildren().add(ar);
        for (int i=0;i<20;i++){
            Button block = new Button();
            block.setStyle(universalButtonStyle);
            block.setText("");
            BoxList.get(i).getChildren().add(block);
        }
    }

    private void initBa(){
         childrenButtonListba1 = new ArrayList<>();
         childrenButtonListba2 = new ArrayList<>();
         childrenButtonListba3 = new ArrayList<>();
         childrenButtonListba4 = new ArrayList<>();
         childrenButtonListba5 = new ArrayList<>();
         childrenButtonListba6 = new ArrayList<>();
         childrenButtonListba7 = new ArrayList<>();
         childrenButtonListba8 = new ArrayList<>();
         childrenButtonListba9 = new ArrayList<>();
         childrenButtonListba10 = new ArrayList<>();
         childrenButtonListba11 = new ArrayList<>();
         childrenButtonListba12 = new ArrayList<>();
         childrenButtonListba13 = new ArrayList<>();
         childrenButtonListba14 = new ArrayList<>();
         childrenButtonListba15 = new ArrayList<>();
         childrenButtonListba16 = new ArrayList<>();
         childrenButtonListba17 = new ArrayList<>();
         childrenButtonListba18 = new ArrayList<>();
         childrenButtonListba19 = new ArrayList<>();
         childrenButtonListba20 = new ArrayList<>();
         ButtonListba = new ArrayList<>();
        ButtonListba.add(childrenButtonListba1);
        ButtonListba.add(childrenButtonListba2);
        ButtonListba.add(childrenButtonListba3);
        ButtonListba.add(childrenButtonListba4);
        ButtonListba.add(childrenButtonListba5);
        ButtonListba.add(childrenButtonListba6);
        ButtonListba.add(childrenButtonListba7);
        ButtonListba.add(childrenButtonListba8);
        ButtonListba.add(childrenButtonListba9);
        ButtonListba.add(childrenButtonListba10);
        ButtonListba.add(childrenButtonListba11);
        ButtonListba.add(childrenButtonListba12);
        ButtonListba.add(childrenButtonListba13);
        ButtonListba.add(childrenButtonListba14);
        ButtonListba.add(childrenButtonListba15);
        ButtonListba.add(childrenButtonListba16);
        ButtonListba.add(childrenButtonListba17);
        ButtonListba.add(childrenButtonListba18);
        ButtonListba.add(childrenButtonListba19);
        ButtonListba.add(childrenButtonListba20);
    }

    private Button previousSearchedButton;
    boolean coloredFlag = false;

    private String Key = "";
    private int value;

    @FXML
    public void generateButtonHandler(ActionEvent event){
        recolorFoundButton();
        Random rand = new Random();
        for(int i = rand.nextInt((10 - 0) + 1); i<30; i++){
            if(hashTab.getCurrentSize()<14) {
                value = rand.nextInt((99 - 1) + 1) + 1;
                Key = RandomKey(5);
                if(!CheckForDuplicates(Key)) {
                    AddNodeText.setText("" + Key);
                    addNewNode();
                    AddNodeText.setText("");
                }
                else
                    i--;
            }
            else
                break;
        }
    }

    @FXML
    public void addNodeButton(ActionEvent event){
        recolorFoundButton();
       Key=getTextFromAddBox();
       if(Key.length()>5){
           CreateError(errorMSG.valueIsTooLong);
           AddNodeText.setText("");
       }
       else
           if(!CheckForDuplicates(Key)) {
               if(hashTab.getCurrentSize()<14) {
                   addNewNode();
                   AddNodeText.setText("");
               }
               else
                   CreateError(errorMSG.visualisationLimit);
           }
           else {
               CreateError(errorMSG.HaveDuplicate);
               AddNodeText.setText("");
           }
    }

    @FXML
    public void deleteNodeButton(ActionEvent event){
        recolorFoundButton();
        if(!DeleteNodeText.getText().equals("")){
            if(DeleteNodeText.getText().length()<=5) {
                if (hashTab.getCurrentSize() == 0) {
                    CreateError(errorMSG.IsEmpty);
                    DeleteNodeText.setText("");
                } else {
                    deleteNode();
                    DeleteNodeText.setText("");
                }
            }
            else
                CreateError(errorMSG.valueIsTooLong);
        }
    }

    private void recolorFoundButton(){
        if(coloredFlag){
            previousSearchedButton.setStyle(universalButtonStyle);
            coloredFlag = false;
        }
    }

    @FXML
    public void findNodeButton(ActionEvent event){
        recolorFoundButton();
        boolean flag = true; //true if all lists are empty
        if(!FindNodeText.getText().equals("")){
            for(int j=0;j<ButtonList.size();j++) {
                if(ButtonList.get(j).size()>0){
                    flag=false;
                }
            }
            if(flag) {
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

    private Button CreateBlock(){
        Button block = new Button();
        block.setStyle(universalButtonStyle);
        block.setText("");
        return block;
    }

    public void restartWindow(ActionEvent actionEvent) {
        for (int i=0;i<20;i++){
            BoxList.get(i).getChildren().clear();
            ButtonList.get(i).clear();
            Button block = new Button();
            block.setStyle(universalButtonStyle);
            block.setText("");
            BoxList.get(i).getChildren().add(CreateBlock());
        }
        hashTab = new HashTable();
        previousMax = 10;
        initBa();
    }

    private void deleteNode(){
        String keyValue = DeleteNodeText.getText();
        int index = hashTab.getIndex(keyValue);
        switch (index){
            case -1:
                CreateError(errorMSG.CannotBeFound);
                break;
            default:
                for(int i=0;i<ButtonList.get(index).size();i++){
                    if(ButtonList.get(index).get(i).getText().equals(keyValue)) {
                        ButtonList.get(index).remove(i);
                        hashTab.DeleteElement(keyValue);
                        reloadVisualisationBox();
                        break;
                    }
                }
        }
    }



    private void findNode() {
        String keyValue = FindNodeText.getText();
        int index = hashTab.getIndex(keyValue);
        System.out.println("ok: " + index + " " + "key: " + keyValue);
        switch (index){
            case -1:
                CreateError(errorMSG.CannotBeFound);
                System.out.println("tutaj");
                break;
            default:
                for(int i=0;i<ButtonList.get(index).size();i++){
                    if(ButtonList.get(index).get(i).getText().equals(keyValue)) {
                        ButtonList.get(index).get(i).setStyle(findButtonStyle);
                        previousSearchedButton = ButtonList.get(index).get(i);
                        coloredFlag = true;
                        System.out.println("found");
                        reloadVisualisationBox();
                        break;
                    }
                }
        }
    }

    private void reloadLists(){
        for(int i=0;i<ButtonList.size();i++){
            for(int j=0;j<ButtonList.get(i).size();j++){
                int index = hashTab.getIndex(ButtonList.get(i).get(j).getText());
                Button ak = ButtonList.get(i).get(j);
                ButtonListba.get(index).add(ak);
            }
        }
        ButtonList = ButtonListba;
    };

    int previousMax = 10;

    private void addNewNode(){
        hashTab.Add(Key, value);
        int max = hashTab.getMaksSize();
        int index = hashTab.getIndex(Key);
        System.out.println("{ dodawany element: " + Key + " " + value + " " + index +" }");
        //hashTab.Print();
        Button btnNumber = new Button();
        btnNumber.setText(""+Key);
        btnNumber.setStyle(universalButtonStyle);
        btnNumber.setOnMouseClicked((MouseEvent event) -> {
            CreateMsg("Key Value: " + btnNumber.getText());
        });
        if(ButtonList.get(index).size()==5){
            hashTab.DeleteElement(Key);
            CreateError(errorMSG.chainToHigh);
            return;
        }
        ButtonList.get(index).add(btnNumber);
        if(max>previousMax) {
            reloadLists();
            previousMax = max;
        }
        reloadVisualisationBox();
    }

    private void checklist(){ //funkcja do testowania sortowania listy
        childrenButtonList.forEach((temp) -> System.out.println(temp.getText()));
        System.out.println("---------------------------");
    }

    private void reloadVisualisationBox(){
        for(int i=0;i<BoxList.size();i++){
                    BoxList.get(i).getChildren().clear();
            for(int j=0;j<ButtonList.get(i).size();j++){
                BoxList.get(i).getChildren().add(ButtonList.get(i).get(j));
            }
            if(BoxList.get(i).getChildren().size()==0){
                BoxList.get(i).getChildren().add(CreateBlock());
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

    @FXML private TextArea msgTextArea;
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
            controller.msgTextArea.setStyle("-fx-text-fill: purple;-fx-font-weight:bold;");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean CheckForDuplicates(String value){
        for(int i=0;i<BoxList.size();i++){
            for(int j=0;j<ButtonList.get(i).size();j++){
                if(ButtonList.get(i).get(j).getText().equals(value))
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

    static String RandomKey(int n) {
        Random rand = new Random();
        String key = "";
        for (int i = 0; i < n; ++i)
        {
            //rand.nextInt((max - min) + 1) + min; to find random value
            int test = rand.nextInt((1 - 0) + 1) + 0;
            if (test == 1) {
                key += (char)(rand.nextInt((122 - 97) + 1) + 97);
            }
            else {
                key += (char)(rand.nextInt((90 - 65) + 1) + 65);
            }
        }
        return key;
    }

}

