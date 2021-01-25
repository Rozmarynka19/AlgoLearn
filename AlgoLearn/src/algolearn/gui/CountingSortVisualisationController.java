package algolearn.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import algolearn.gui.info.errors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CountingSortVisualisationController extends FXMLDocumentController  implements Initializable {
	
	List<Button> childrenButtonListUnsorted = new ArrayList<>();
    List<Button> childrenButtonListSorted = new ArrayList<>();
    
    @FXML TextField addTextCS, deleteTextCS, findTextCS;
    @FXML Text Information;
    @FXML HBox CountHBox;
    @FXML HBox TopHBox;
    @FXML HBox BottomHBox;
    private errors errorMSG = new errors();
    private List<String> numbersToSortString = new ArrayList<>();
    private List<Integer> numbersSortedString = new ArrayList<>();
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
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }
    
    private String getTextFromBox(String btn_val) {
    	String TextValue = "";
    	if(btn_val.equals("1")) {
            TextValue = addTextCS.getText();   		
    	}
    	else if(btn_val.equals("2")) {
    		TextValue = deleteTextCS.getText();
    	}
    	else if(btn_val.equals("3")) {
    		TextValue = findTextCS.getText();
    	}
        return TextValue;
    }
    
    public void restartWindowCS(ActionEvent actionEvent) {
    	TopHBox.getChildren().clear();
    	BottomHBox.getChildren().clear();
    	childrenButtonListUnsorted.clear();
        numbersToSortString.clear();
 	    resetCountedValues();
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
    
    static boolean isInt(String str) {
        try {
            int i = Integer.parseInt(str);
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
    
    public void generateButtonHandlerCS(ActionEvent event){
    	Button clicked_btn = (Button)event.getSource();
    	String btn_val = clicked_btn.getId();
    			if(btn_val.equals("1")) {
                	addNewNode(btn_val);
    			}
    			else if(btn_val.equals("2")) {
    				deleteValue(btn_val);
    			}
    }
    
   private void reloadVisualisationBox(){
	   TopHBox.getChildren().clear();
	   for(int i=0;i<childrenButtonListUnsorted.size();i++){
		   TopHBox.getChildren().add(childrenButtonListUnsorted.get(i));
	   }
	   BottomHBox.getChildren().clear();
   }
   
   private void addNewNode(String btn_val) {
	   String numberToSort = getTextFromBox(btn_val);
	   if(numberToSort.equals("") || !isInt(numberToSort)) {
		   CreateError(errorMSG.InputIsString);
		   return;
	   }
	   else if(childrenButtonListUnsorted.size() > 16) {
		   CreateError(errorMSG.exceedValueCS);
		   return;
	   }
	   else if(Integer.parseInt(numberToSort) > 10 || Integer.parseInt(numberToSort) < 0) {
		   CreateError(errorMSG.exceedValueCS);
		   return;
	   }
	   BottomHBox.getChildren().clear();
       Button btnNumber = new Button();
       numbersToSortString.add(numberToSort);
       btnNumber.setText(numberToSort);
       btnNumber.setStyle(
    		   "-fx-min-width: 30px; " +
    		   "-fx-min-height: 30px; " +
               "-fx-max-width: 30px; " +
               "-fx-max-height: 30px; " +
               "-fx-border-width: 20px; "
       );
       childrenButtonListUnsorted.add(btnNumber);
       TopHBox.getChildren().add(btnNumber);
       addTextCS.setText("");
       Information.setText("Informacja: " + errorMSG.addedTo + numberToSort + errorMSG.toUnsortedList);
	   resetCountedValues();
   }
   
   private void deleteValue(String btn_val) {
    	String numberToDelete = getTextFromBox(btn_val);
    	numbersToSortString.remove(numberToDelete);
    	boolean flag = false;
    	for(int i=0;i<childrenButtonListUnsorted.size();i++) {
    		if(childrenButtonListUnsorted.get(i).getText().equals(numberToDelete)){
    			childrenButtonListUnsorted.remove(i);
                reloadVisualisationBox();
                flag = true;
                break;
            }
    	 }
    	deleteTextCS.setText("");
    	if(!flag){
            CreateError(errorMSG.notFoundInUnsortedList);
            return;
       }
       Information.setText("Informacja: " + errorMSG.isRemoved + numberToDelete);
  	   resetCountedValues();
    }
   	
   public void generateRandomList() {
	   TopHBox.getChildren().clear();
	   BottomHBox.getChildren().clear();
	   childrenButtonListUnsorted.clear();
	   numbersToSortString.clear();
	   resetCountedValues();

	   Random rand = new Random();
	   for(int i = rand.nextInt((10 - 0) + 1); i<16; i++){
		   if(childrenButtonListUnsorted.size()<16) {
			   int randomNum = rand.nextInt((16 - 1) + 1) + 1;
			   if(randomNum <= 10) {
				   addTextCS.setText(Integer.toString(randomNum));
				   addNewNode("1");				   
			   }
			   else {
				   i--;
			   }

		   }
		   else
			   break;
	   }
	   Information.setText("Informacja: " + errorMSG.isGenerated);
   }
   
   public void countValues() {
	   int i = 0;
	   if(numbersToSortString.size() == 0) {
		   CreateError(errorMSG.isEmptyList);
		   return;
	   }
	   ArrayList<Integer> CountedArray = new ArrayList<>();
	   for(int j = 0; j < 11;j++) {
		   int count = Collections.frequency(numbersToSortString, Integer.toString(j));
		   CountedArray.add(count);
	   }		   
	   for(Node node : CountHBox.getChildren()) {
		   if(node instanceof TextField) {
			   if (node.getId().equals(Integer.toString(i))) {
				   ((TextField)node).setText(Integer.toString(CountedArray.get(i)));
				   i++;
			   }
		   }
	   }
	   Information.setText("Informacja: " + errorMSG.isCounted);
   }
   
   public void sortCS() {
	   BottomHBox.getChildren().clear();
   	   numbersSortedString.clear();
	   int j = 0;
	   for(Node node : CountHBox.getChildren()) {
		   if(node instanceof TextField) {
			   if (node.getId().equals(Integer.toString(j))) {
				   String text = ((TextField)node).getText();
				   if(text.equals("")) {
					   CreateError(errorMSG.notCounted);
					   return;
				   }
				   else {
					   for(int i = 0; i < Integer.parseInt(text);i++) {
						   numbersSortedString.add(j);					   
					   }					   
				   }

				   j++;
			   }
		   }
	   }
	   for(int i = 0; i < numbersSortedString.size();i++) {
		   Button btnNumber = new Button();
		   btnNumber.setText(numbersSortedString.get(i).toString());
		   childrenButtonListSorted.add(btnNumber);
		   BottomHBox.getChildren().add(btnNumber);
		   btnNumber.setStyle(
				   "-fx-min-width: 30px; " +
				   "-fx-min-height: 30px; " +
				   "-fx-max-width: 30px; " +
				   "-fx-max-height: 30px; " +
				   "-fx-border-width: 20px; "
		   );
	   } 
	   Information.setText("Informacja: " + errorMSG.isSorted);
   }
   public void resetCountedValues() {
	   int j = 0;
	   for(Node node : CountHBox.getChildren()) {
		   if(node instanceof TextField) {
			   if (node.getId().equals(Integer.toString(j))) {
				   ((TextField)node).setText("");
				   j++;
			   }
		   }
	   }
   }
}