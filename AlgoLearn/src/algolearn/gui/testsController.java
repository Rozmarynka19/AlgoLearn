/**
 * 
 */
package algolearn.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import algolearn.gui.info.errors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Algolearn Team
 *
 */
public class testsController extends FXMLDocumentController implements Initializable{
	testsData testdata = testsData.getInstance();
	SavedValues savedValues = SavedValues.getInstance();
	private errors errorMSG = new errors();
	private boolean examStarted = false;
	private int correctAnwers = 0;
	private int currentQuestion = 0;
	private int questionsCount = getQuestionsCount();
	
	@FXML private Button nextQuest;
	@FXML private WebView engine;
//	WebEngine webEngine;
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
		
		boolean [] points = getPoints();
		
		if(!isMarked(points)) {
			CreateError(errorMSG.examAnswerNotChecked);
			return;	
		}
		
		if(checkAnswers(points))
			this.correctAnwers++;
		
		
		
		if(currentQuestion == questionsCount-1) {
			CreateScore();
			return;
		}
		
		this.currentQuestion++;
		loadHTML();
	}
	
	@FXML private void finishTest(ActionEvent event) {
		if(!examStarted) {
			CreateError(errorMSG.testDidntStart);
			return;
		}
		
		boolean [] points = getPoints();
		if(isMarked(points))
			if(checkAnswers(points))
				this.correctAnwers++;
		
		CreateScore();
	}
	
	@FXML private void beginExam(ActionEvent event) {
		startExam.setDisable(true);
		examStarted = true;
		nextQuest.setDisable(false);
		loadHTML();
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
	
	private boolean checkAnswers(boolean [] arr) {
		for(int i = 0; i<4; i++) 
			if(arr[i] != testsData.correctAnswers[savedValues.savedRealId][currentQuestion][i])
				return false;
		return true;
	}
	
	private boolean isMarked(boolean [] arr) {
		for(int i =0; i<4; i++)
			if(arr[i])
				return true;
		return false;
	}
	
	private int getQuestionsCount() {
		int qHTML = testsData.questionsHTML[savedValues.savedRealId].length;
		int qAnswers = testsData.correctAnswers[savedValues.savedRealId].length;
		if(qHTML != qAnswers)
			return (qAnswers < qHTML) ? qAnswers : qHTML;
		
		return qHTML;
	}
	

    public void CreateScore() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/testScore.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            setStyle(stage);
            Text txt = (Text) root1.lookup("#scoreTXT");
            if(txt != null)
            	txt.setText("Wynik testu: " + Integer.toString(correctAnwers) + "/" + Integer.toString(questionsCount));
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
    
//    @Override
//    public void initialize(URL arg0, ResourceBundle arg1) {
//    	webEngine = engine.getEngine();
//    }
    
    @FXML private void closeScore(ActionEvent event) throws Exception {
    	savedValues.TestController.BackToMainStage(event);
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    
    private void loadHTML() {
    	WebEngine webEngine = engine.getEngine();
    	webEngine.load(""+getClass().getResource(testsData.questionsHTML[savedValues.savedRealId][currentQuestion]));
    }
    
    public void CreateError(String msg) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/error_fxml.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            BST_controller controller = (BST_controller)fxmlLoader.getController();
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
	
}
