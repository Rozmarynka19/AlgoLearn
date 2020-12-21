package algolearn.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BucketSortVisualisationController extends FXMLDocumentController  implements Initializable {
	
    List<Button> childrenButtonList = new ArrayList<>();
//    List<Arrow> childrenArrowList = new ArrayList<>();
    
    @FXML TextField AddNodeTextBS;
    @FXML private Button previousSearchedChildBS;
    @FXML HBox BucketHBox;
    @FXML HBox MainHBox;
    @FXML AnchorPane anchorPane, anchorPane2, anchorPane3, anchorPane4; 
    
    private List<String> numbersToSortString = new ArrayList<>();
    private List<Integer> numbersSortedString = new ArrayList<>();
    private int i=0;
    
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
    
    private String getTextFromAddBox() {
        String TextValue = AddNodeTextBS.getText();
        return TextValue;
    }
    public void restartWindowBS(ActionEvent actionEvent) {
    	MainHBox.getChildren().clear();
        childrenButtonList.clear();
        numbersToSortString.clear();
    }

    public void generateButtonHandlerBS(ActionEvent event){
        Alert a = new Alert(Alert.AlertType.NONE);
        if(!AddNodeTextBS.getText().equals("")) {
            if(childrenButtonList.size()<11) {
            	addNewNodeBS();
            }
            else {
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Maksymalna ilość elementów to 11.");
                a.show();
            }
        }
    }
    
    private void AddToBucket(AnchorPane anchorPane) {
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
    							numbersToSortString.remove(i);
    						}
    					}
    					else if(anchorPane.getId().equals("2")) {
    						if(Integer.parseInt(numbersToSortString.get(i)) > 4 && Integer.parseInt(numbersToSortString.get(i)) <= 8 && nodeAnchor.getId() != null) {
    							((TextField)nodeAnchor).setText(numbersToSortString.get(i));
    							numbersToSortString.remove(i);
    						}
    					}
    					else if(anchorPane.getId().equals("3")) {
    						if(Integer.parseInt(numbersToSortString.get(i)) > 8 && Integer.parseInt(numbersToSortString.get(i)) <= 12 && nodeAnchor.getId() != null) {
    							((TextField)nodeAnchor).setText(numbersToSortString.get(i));
    							numbersToSortString.remove(i);
    						}
    					}
    					else if(anchorPane.getId().equals("4")) {
    						if(Integer.parseInt(numbersToSortString.get(i)) > 12 && Integer.parseInt(numbersToSortString.get(i)) <= 16 && nodeAnchor.getId() != null) {
    							((TextField)nodeAnchor).setText(numbersToSortString.get(i));
    							numbersToSortString.remove(i);
    						}
    					}
    				}
    				break;
    			}
    		}
    	}
    }
    
    private void SortInBucket(AnchorPane anchorPane) {
    	int flag = 0;
    	for (Node nodeAnchor : anchorPane.getChildren()) {
    		if(nodeAnchor instanceof TextField) {
    					if(anchorPane.getId().equals("1")) {
    						if(numbersSortedString.get(i) <= 4 && nodeAnchor.getId() != null) {
    							((TextField)nodeAnchor).setText(numbersSortedString.get(i).toString());
    							flag = 1;
    						}
    					}
    					else if(anchorPane.getId().equals("2")) {
    						if(numbersSortedString.get(i) > 4 && numbersSortedString.get(i) <= 8 && !nodeAnchor.getId().equals("0")) {
    							((TextField)nodeAnchor).setText(numbersSortedString.get(i).toString());
    							flag = 1;
    						}
    					}
    					else if(anchorPane.getId().equals("3")) {
    						if(numbersSortedString.get(i) > 8 && numbersSortedString.get(i) <= 12 && !nodeAnchor.getId().equals("0")) {
    							((TextField)nodeAnchor).setText(numbersSortedString.get(i).toString());
    							flag = 1;
    						}
    					}
    					else if(anchorPane.getId().equals("4")) {
    						if(numbersSortedString.get(i) > 12 && numbersSortedString.get(i) <= 16 && !nodeAnchor.getId().equals("0")) {
    							((TextField)nodeAnchor).setText(numbersSortedString.get(i).toString());
    							flag = 1;
    						}
    					}
    					if(i < numbersSortedString.size()-1 && flag == 1) {
    						i=i+1;
    						flag = 0;
    					}
    					else {
    						break;
    					}
    				}
		}
	}
    
    private void addNewNodeBS() {
        String numberToSort = getTextFromAddBox();
    	if(Integer.parseInt(numberToSort) > 16 || Integer.parseInt(numberToSort) <= 0) {
    		Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Zakres wartości od 1-16");
            a.show();
    	}
    	else {
            Button btnNumber = new Button();
            numbersToSortString.add(numberToSort);
            numbersSortedString.add(Integer.parseInt(numberToSort));
            btnNumber.setText(numberToSort);
            btnNumber.setStyle(
                    "-fx-min-width: 30px; " +
                    "-fx-min-height: 30px; " +
                    "-fx-max-width: 30px; " +
                    "-fx-max-height: 30px; " +
                    "-fx-border-width: 20px; "

            );
            childrenButtonList.add(btnNumber);
            MainHBox.getChildren().add(btnNumber);
            AddNodeTextBS.setText("");
            for (Node node : BucketHBox.getChildren()) {
                if(node instanceof AnchorPane) {
                	if(node.getId().equals("1")) {
                    	AddToBucket(anchorPane);
                    	continue;
                	}
                	if(node.getId().equals("2")) {
                    	AddToBucket(anchorPane2);
                    	continue;
                	}
                	if(node.getId().equals("3")) {
                    	AddToBucket(anchorPane3);
                    	continue;
                	}
                	if(node.getId().equals("4")) {
                    	AddToBucket(anchorPane4);
                    	continue;
                	}
                }
            }
    	}        
    }
    
    public void sortBS() {
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
    }
}
