/**
 * 
 */
package algolearn.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Algolearn Team
 *
 */
public class testsController extends FXMLDocumentController{
	testsData testdata = testsData.getInstance();
	SavedValues savedValues = SavedValues.getInstance();
	
	private boolean examStarted = false;
	private int correctAnwers = 0;
	
	@FXML private CheckBox checkBoxA, checkBoxB, checkBoxC, checkBoxD;
	@FXML private Button startExam;
	
	@FXML private void selectAnswer(MouseEvent event) {
		Node btn = (Node) event.getSource();
		String bid = btn.getId();

		if(bid.equals("textA") || bid.equals("paneA"))
			checkBoxA.setSelected(!checkBoxA.isSelected());
		else if(bid.equals("textB") || bid.equals("paneB"))
			checkBoxB.setSelected(!checkBoxB.isSelected());
		else if(bid.equals("textC") || bid.equals("paneC"))
			checkBoxC.setSelected(!checkBoxC.isSelected());
		else
			checkBoxD.setSelected(!checkBoxD.isSelected());
	}
	
	@FXML private void nextQuestion(ActionEvent event) {
		if(!examStarted)
			return;
		
		
		boolean [] arr = getPoints();
	}
	
	@FXML private void finishTest(ActionEvent event) {
		CreateScore();
	}
	
	@FXML private void beginExam(ActionEvent event) {
		startExam.setDisable(true);
		examStarted = true;
	}
	
	private boolean [] getPoints() {
		boolean [] arr = new boolean[4];
		arr[0] = checkBoxA.isSelected();
		arr[1] = checkBoxB.isSelected();
		arr[2] = checkBoxC.isSelected();
		arr[3] = checkBoxD.isSelected(); 
		

		checkBoxA.setSelected(false);
		checkBoxB.setSelected(false);
		checkBoxC.setSelected(false);
		checkBoxD.setSelected(false);
		return arr;
	}
	

    public void CreateScore() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/testScore.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            setStyle(stage);
            Text txt = (Text) root1.lookup("#scoreTXT");
            if(txt != null)
            	txt.setText("Wynik testu: " + Integer.toString(correctAnwers) + "/" + testdata.questionsCount[savedValues.savedRealId]);
            setMouse(root1, stage);
            stage.centerOnScreen();
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));  
            stage.show();
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML private void closeScore(ActionEvent event) throws Exception {
    	savedValues.TestController.BackToMainStage(event);
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
	
}
