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
import javafx.scene.Node;
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

    @FXML HBox MainVBox;
    @FXML TextField AddNodeText;
    @FXML TextField DeleteNodeText;
    @FXML TextField FindNodeText;

    List<Button> childrenButtonList = new ArrayList<>();
    List<Arrow> childrenArrowList = new ArrayList<>();

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
        Alert a = new Alert(Alert.AlertType.NONE);
        if(!DeleteNodeText.getText().equals("")){
            if(Integer.parseInt(DeleteNodeText.getText())>childrenButtonList.size() || Integer.parseInt(DeleteNodeText.getText())<=0) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Błędna wartość, upewnij się że nie przekraczasz przedziału wartości do jakich możesz się odnieść!");
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
                addNewNode();
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
        MainVBox.getChildren().clear();
        childrenButtonList.clear();
        childrenArrowList.clear();
    }

    private void deleteNode(){
        MainVBox.getChildren().remove(childrenButtonList.get(Integer.parseInt(DeleteNodeText.getText()) - 1));
        MainVBox.getChildren().remove(childrenArrowList.get(Integer.parseInt(DeleteNodeText.getText()) -1));
        childrenButtonList.remove(Integer.parseInt(DeleteNodeText.getText()) - 1); //ok
        childrenArrowList.remove(Integer.parseInt(DeleteNodeText.getText()) - 1);
        if (childrenButtonList.size() >= 1) {
            childrenArrowList.get(childrenArrowList.size()-1).setVisible(false);
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

    private void addNewNode(){
        if (childrenButtonList.size() >= 1) {
            childrenArrowList.get(childrenArrowList.size()-1).setVisible(true);
        }
        Button btnNumber = new Button();
        previousButton = btnNumber;
        btnNumber.setText(getTextFromAddBox());
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
        childrenButtonList.add(btnNumber);
        MainVBox.getChildren().add(btnNumber);
        Arrow arrow = new Arrow();
        arrow.setEndX(previousButton.getLayoutX() + 40);
        arrow.setEndY(previousButton.getLayoutY());
        arrow.setVisible(false);
        MainVBox.getChildren().add(arrow);
        childrenArrowList.add(arrow);
        AddNodeText.setText("");
    }
    
    //bucket sort
    @FXML TextField AddNodeTextBS;
    @FXML private Button previousSearchedChildBS;
    @FXML HBox BucketHBox;
    @FXML HBox MainHBox;
    @FXML AnchorPane anchorPane, anchorPane2, anchorPane3, anchorPane4; 
    private List<String> numbersToSortString = new ArrayList<>();
    private List<Integer> numbersSortedString = new ArrayList<>();
    private int i=0;
    
    public void restartWindowBS(ActionEvent actionEvent) {
    	MainHBox.getChildren().clear();
        childrenButtonList.clear();
        numbersToSortString.clear();
    }

    public void generateButtonHandlerBS(ActionEvent event){
        Alert a = new Alert(Alert.AlertType.NONE);
        if(!AddNodeText.getText().equals("")) {
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
            AddNodeText.setText("");
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