package algolearn.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import algolearn.gui.controllers.BST_controller;
import algolearn.gui.info.errors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BucketSortVisualisationController extends FXMLDocumentController  implements Initializable {
	
    List<Button> childrenButtonListUnsorted = new ArrayList<>();
    List<Button> childrenButtonListSorted = new ArrayList<>();
    
    @FXML TextField addTextBS, deleteTextBS, findTextBS;
    @FXML Text Information;
    @FXML private Button previousSearchedChildBS;
    @FXML HBox BucketHBox;
    @FXML HBox TopHBox;
    @FXML HBox BottomHBox;
    @FXML AnchorPane anchorPane, anchorPane2, anchorPane3, anchorPane4;
    private errors errorMSG = new errors();
    
    private List<String> numbersToSortString = new ArrayList<>();
    private List<String> numbersToSortStringBackup = new ArrayList<>();
    private List<String> numbersSortedString = new ArrayList<>();
    private List<String> numbersSortedStringBackup = new ArrayList<>();
    private int idInList=0;
    
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
        System.exit(0);
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
            TextValue = addTextBS.getText();   		
    	}
    	else if(btn_val.equals("2")) {
    		TextValue = deleteTextBS.getText();
    	}
    	else if(btn_val.equals("3")) {
    		TextValue = findTextBS.getText();
    	}
        return TextValue;
    }
    public void restartWindowBS(ActionEvent actionEvent) {
    	TopHBox.getChildren().clear();
    	BottomHBox.getChildren().clear();
    	childrenButtonListUnsorted.clear();
        numbersToSortString.clear();
        numbersSortedString.clear();
        clearBuckets();
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
        for(int i=0;i<childrenButtonListUnsorted.size();i++){
            if(Integer.parseInt(childrenButtonListUnsorted.get(i).getText())==value){
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

    public void generateButtonHandlerBS(ActionEvent event){
    	Button clicked_btn = (Button)event.getSource();
    	String btn_val = clicked_btn.getId();
    			if(btn_val.equals("1")) {
                	addNewNodeBS(btn_val);  				
    			}
    			if(btn_val.equals("2")) {
    				deleteValue(btn_val);
    			}
    			if(btn_val.equals("3")) {
    				findValue(btn_val);
    			}
    }
    
    private void reloadVisualisationBox(){
    	TopHBox.getChildren().clear();
        for(int i=0;i<childrenButtonListUnsorted.size();i++){
        	TopHBox.getChildren().add(childrenButtonListUnsorted.get(i));
        }
        BottomHBox.getChildren().clear();
        clearBuckets();
        if(numbersToSortString.size() == 0) {
        	numbersToSortString = new ArrayList<>(numbersToSortStringBackup);
        	numbersSortedStringBackup.clear();
		}
        else if(childrenButtonListSorted.size() > 0) {
        	numbersSortedString = new ArrayList<>(numbersSortedString);
        	numbersSortedStringBackup.clear();
        } 
        int listSize = numbersToSortString.size();
        for(int i=0;i<listSize;i++) {
       	 	addToBucket();        		
        }   	
   }
    
    private void deleteValue(String btn_val) {
    	String numberToDelete = getTextFromBox(btn_val);
    	numbersToSortString.remove(numberToDelete);
    	numbersToSortStringBackup.remove(numberToDelete);
    	numbersSortedString.remove(numberToDelete);
    	numbersSortedStringBackup.remove(numberToDelete);
    	boolean flag = false;
    	for(int i=0;i<childrenButtonListUnsorted.size();i++) {
    		if(childrenButtonListUnsorted.get(i).getText().equals(numberToDelete)){
    			childrenButtonListUnsorted.remove(i);
                reloadVisualisationBox();
                flag = true;
                break;
            }
    	 }
    	 if(!flag){
             CreateError(errorMSG.notFoundInUnsortedList);
             return;
         }
    	 Information.setText("Informacja: " + errorMSG.isRemoved + numberToDelete + errorMSG.isReloaded);
    }
    
    
    private void findValue(String btn_val) {
    	idInList = 0;
        boolean isFound[] = new boolean[4];
    	String numberToFind = getTextFromBox(btn_val);
    	for (Node node : BucketHBox.getChildren()) {
            if(node instanceof AnchorPane) {
            	if(node.getId().equals("1")) {
            		isFound[0] = findInBucket(anchorPane,numberToFind);
            	}
            	else if(node.getId().equals("2")) {
            		isFound[1] = findInBucket(anchorPane2,numberToFind);
            	}
            	else if(node.getId().equals("3")) {
            		isFound[2] = findInBucket(anchorPane3,numberToFind);
            	}
            	else if(node.getId().equals("4")) {
            		isFound[3] = findInBucket(anchorPane4,numberToFind);
            	}
            }
        }
    	if(!isFound[0] && !isFound[1] && !isFound[2] && !isFound[3]) {
    		CreateError(errorMSG.notFoundInBucket);
    	}
    }
    
    private boolean findInBucket(AnchorPane anchorPane, String numberToFind) {
    	boolean isFound = false;
    	for (Node nodeAnchor : anchorPane.getChildren()) {
    		if(nodeAnchor instanceof TextField) {
    			if(((TextField) nodeAnchor).getText().equals(numberToFind)) {
    				((TextField)nodeAnchor).requestFocus();
    				Information.setText("Informacja: " + errorMSG.isFound + numberToFind);
    				isFound = true;
    			}
    			if(idInList < 16) {
    				idInList=idInList+1;
    			}
    		}
		}
    	return isFound;
    }
    
    private void clearBuckets() {
        for (Node node : BucketHBox.getChildren()) {
            if(node instanceof AnchorPane) {
            	if(node.getId().equals("1")) {
            		clearInBucket(anchorPane);
            	}
            	else if(node.getId().equals("2")) {
            		clearInBucket(anchorPane2);
            	}
            	else if(node.getId().equals("3")) {
            		clearInBucket(anchorPane3);
            	}
            	else if(node.getId().equals("4")) {
            		clearInBucket(anchorPane4);
            	}
            }
        }
    }
    
    private int addValueToBucket(AnchorPane anchorPane) {
    	int flag = 0;
    	for (Node nodeAnchor : anchorPane.getChildren()) {
    		if(nodeAnchor instanceof TextField) {
    			if(!((TextField) nodeAnchor).getText().equals("")) {
    				continue;
    			}
    			else {
    				for(int i=0; i < numbersToSortString.size();i++) {
    					if(anchorPane.getId().equals("1")) {
    						if(Integer.parseInt(numbersToSortString.get(i)) <= 4 && nodeAnchor.getId() != null) {
    							((TextField)nodeAnchor).setText(numbersToSortString.get(i));
    					        Information.setText("Informacja: " + errorMSG.addedToBucket + anchorPane.getId() + errorMSG.addedValueToBucket + numbersToSortString.get(i));
    							numbersToSortString.remove(i);
    							flag = 1;
    							break;
    						}
    					}
    					else if(anchorPane.getId().equals("2")) {
    						if(Integer.parseInt(numbersToSortString.get(i)) > 4 && Integer.parseInt(numbersToSortString.get(i)) <= 8 && nodeAnchor.getId() != null) {
    							((TextField)nodeAnchor).setText(numbersToSortString.get(i));
    							Information.setText("Informacja: " + errorMSG.addedToBucket + anchorPane.getId() + errorMSG.addedValueToBucket + numbersToSortString.get(i));
    							numbersToSortString.remove(i);
    							flag = 1;
    							break;
    						}
    					}
    					else if(anchorPane.getId().equals("3")) {
    						if(Integer.parseInt(numbersToSortString.get(i)) > 8 && Integer.parseInt(numbersToSortString.get(i)) <= 12 && nodeAnchor.getId() != null) {
    							((TextField)nodeAnchor).setText(numbersToSortString.get(i));
    							Information.setText("Informacja: " + errorMSG.addedToBucket + anchorPane.getId() + errorMSG.addedValueToBucket + numbersToSortString.get(i));
    							numbersToSortString.remove(i);
    							flag = 1;
    							break;
    						}
    					}
    					else if(anchorPane.getId().equals("4")) {
    						if(Integer.parseInt(numbersToSortString.get(i)) > 12 && Integer.parseInt(numbersToSortString.get(i)) <= 16 && nodeAnchor.getId() != null) {
    							((TextField)nodeAnchor).setText(numbersToSortString.get(i));
    							Information.setText("Informacja: " + errorMSG.addedToBucket + anchorPane.getId() + errorMSG.addedValueToBucket + numbersToSortString.get(i));
    							numbersToSortString.remove(i);
    							flag = 1;
    							break;
    						}
    					}
    				}
    				break;
    			}
    		}
    	}
    	return flag;
    }
    
    private void SortInBucket(AnchorPane anchorPane) {
    	int flag = 0;
    	for (Node nodeAnchor : anchorPane.getChildren()) {
    		if(nodeAnchor instanceof TextField) {
    					if(anchorPane.getId().equals("1")) {
    						if(Integer.parseInt(numbersSortedString.get(idInList)) <= 4 && nodeAnchor.getId() != null) {
    							((TextField)nodeAnchor).setText(numbersSortedString.get(idInList).toString());
    							flag = 1;
    						}
    					}
    					else if(anchorPane.getId().equals("2")) {
    						if(Integer.parseInt(numbersSortedString.get(idInList)) > 4 && Integer.parseInt(numbersSortedString.get(idInList)) <= 8 && !nodeAnchor.getId().equals("0")) {
    							((TextField)nodeAnchor).setText(numbersSortedString.get(idInList).toString());
    							flag = 1;
    						}
    					}
    					else if(anchorPane.getId().equals("3")) {
    						if(Integer.parseInt(numbersSortedString.get(idInList)) > 8 && Integer.parseInt(numbersSortedString.get(idInList)) <= 12 && !nodeAnchor.getId().equals("0")) {
    							((TextField)nodeAnchor).setText(numbersSortedString.get(idInList).toString());
    							flag = 1;
    						}
    					}
    					else if(anchorPane.getId().equals("4")) {
    						if(Integer.parseInt(numbersSortedString.get(idInList)) > 12 && Integer.parseInt(numbersSortedString.get(idInList)) <= 16 && !nodeAnchor.getId().equals("0")) {
    							((TextField)nodeAnchor).setText(numbersSortedString.get(idInList).toString());
    							flag = 1;
    						}
    					}
    					if(idInList < numbersSortedString.size()-1 && flag == 1) {
    						idInList=idInList+1;
    						flag = 0;
    					}
    					else {
    						break;
    					}
    				}
		}
	}
    
    private void clearInBucket(AnchorPane anchorPane) {
    	for (Node nodeAnchor : anchorPane.getChildren()) {
    		if(nodeAnchor instanceof TextField) {
    			((TextField) nodeAnchor).clear();
    		}
		}
    }
 
    private void addNewNodeBS(String btn_val) {
        String numberToSort = getTextFromBox(btn_val);
        if(numberToSort.equals("") || !isInt(numberToSort)) {
    		CreateError(errorMSG.InputIsString);
    	}
        else if(Integer.parseInt(numberToSort) > 16 || Integer.parseInt(numberToSort) <= 0) {
        	CreateError(errorMSG.exceedValueBS);
    	}
    	else if(CheckForDuplicates(Integer.parseInt(numberToSort))) {
    		CreateError(errorMSG.HaveDuplicate);
    	}
    	else {
            Button btnNumber = new Button();
            numbersToSortString.add(numberToSort);
            numbersToSortStringBackup = new ArrayList<>(numbersToSortString);
            numbersSortedString.add(numberToSort);
            numbersSortedStringBackup = new ArrayList<>(numbersSortedString);
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
            addTextBS.setText("");
        	Information.setText("Informacja: " + errorMSG.addedTo + numberToSort + errorMSG.toUnsortedList);
    	}
    }
    
    public void addToBucket() {
    	if(numbersToSortString.size() == 0) {
    		CreateError(errorMSG.addedToBucketNotExist);
    	}
    	else {
        	int flag = 0;
            for (Node node : BucketHBox.getChildren()) {
                if(node instanceof AnchorPane) {
                	if(node.getId().equals("1") && flag == 0 && Integer.parseInt(numbersToSortString.get(0)) < 5) {
                    	flag = addValueToBucket(anchorPane);
                	}
                	if(node.getId().equals("2") && flag == 0 && Integer.parseInt(numbersToSortString.get(0)) > 4 && Integer.parseInt(numbersToSortString.get(0)) <= 8) {
                    	flag = addValueToBucket(anchorPane2);
                	}
                	if(node.getId().equals("3") && flag == 0 && Integer.parseInt(numbersToSortString.get(0)) > 8 && Integer.parseInt(numbersToSortString.get(0)) <= 12) {
                    	flag = addValueToBucket(anchorPane3);
                	}
                	if(node.getId().equals("4") && flag == 0 && Integer.parseInt(numbersToSortString.get(0)) > 12) {
                    	flag = addValueToBucket(anchorPane4);
                	}
                }
            }
    	}
    }
    
    public void sortBS() {
    	if(numbersSortedString.size() == 0) {
    		CreateError(errorMSG.isEmptyList);
    		return;
    	}
    	idInList = 0;
    	Collections.sort(numbersSortedString);
        for (Node node : BucketHBox.getChildren()) {
            if(node instanceof AnchorPane) {
            	if(node.getId().equals("1")) {
            		SortInBucket(anchorPane);
            	}
            	else if(node.getId().equals("2")) {
            		SortInBucket(anchorPane2);
            	}
            	else if(node.getId().equals("3")) {
                	SortInBucket(anchorPane3);
            	}
            	else if(node.getId().equals("4")) {
            		SortInBucket(anchorPane4);
            	}
            }
        }
        
    	Information.setText("Informacja: " + errorMSG.bucketsAreSorted);
        
        BottomHBox.getChildren().clear();
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
    }
}