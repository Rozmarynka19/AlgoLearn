package algolearn.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import algolearn.gui.info.errors;
import algolearn.gui.main_window;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Algolearn Team
 *
 * JavaFX handler class that controls the buttons in main window and sub-windows.
 * 
 */
public class BST_controller extends FXMLDocumentController implements Initializable {

    
    // =================================	VISUALISATION	=====================================//
	@FXML private AnchorPane Visualisation_anchorPane;
	@FXML
	public TextArea errorTextArea;
	@FXML private Button hiddenValues;

	private boolean test = false;
	//private Map<Circle,Text> arrayCircles = new HashMap<Circle,Text>();

	private ArrayList<Circle> arrayCircles = new ArrayList<Circle>();
	private ArrayList<Text> arrayTexts = new ArrayList<Text>();
	private ArrayList<Line[]> arrayLines = new ArrayList<Line[]>();
	private Circle hintCricle = null;
	private String [] selectedObjectData = new String[2];
	private errors errorMSG = new errors();
	private boolean pathTransitionDone = true;
	private boolean generateDone = true;
	private String selectedCircle = null;
	private int selectedCircleID = 0;
	
	private boolean randomized = false;
	private String [] randomizedIDs = new String[2];
	
	@FXML Button addBTN, deleteBTN, searchBTN, unknownBTN, restartBTN, backBTN;
	@FXML Slider timeSlider;
	
	static int left_offset = 30, rigth_offset = 30, down_offset = 60;
	
	private int bst_height = 0;
	
	static int board_width = 800;
	static int radius = 20;
	static int max_height = 4;
	
	static double [] def_pos = {board_width/2, 50};
	
	private Color randomColor() {
		Random rand = new Random();
	    double red = rand.nextDouble();
	    double green = rand.nextDouble();
	    double blue = rand.nextDouble();
		return new Color(red, green, blue, 1);
	}
	
	@FXML private void restartVisualisation(ActionEvent event) {
		if(deleteInProgress) {
			bstTEXT.setText(errorMSG.setupInformation(errorMSG.delInProgress));
			return;
		}
		
		if(pathTransitionDone == false) {
			bstTEXT.setText(errorMSG.setupInformation(errorMSG.finishAnimation));
			return;
		}
		
		for(Circle arr : arrayCircles)
			Visualisation_anchorPane.getChildren().remove(arr);
		
		for(Text arr : arrayTexts)
			Visualisation_anchorPane.getChildren().remove(arr);
		
		for(Line[] arr : arrayLines)
			for(Line line : arr)
				Visualisation_anchorPane.getChildren().remove(line);
		
		if(hintCricle != null) {
			Visualisation_anchorPane.getChildren().remove(hintCricle);
			hintCricle = null;
		}
		
		arrayCircles = new ArrayList<Circle>();
		arrayTexts = new ArrayList<Text>();
		arrayLines = new ArrayList<Line[]>();
		
		setHiddenValues("?", "?");
		selectedCircleID = 0;
		
		randomizedIDs[0] = null;
		randomizedIDs[1] = null;
		
		if(pathInit == true) {
			StepByStepCheckBox.setDisable(false);
			pPath = new Path();
			dPath = new ArrayList<double[]>();
			pathInit = false;
		}
		
		if(randomized) {
			addBTN.setDisable(false);
        	deleteBTN.setDisable(false);
        	searchBTN.setDisable(false);
        	randomized = false;
		}
		
		selectedCircle = null;
		rootBST = null;
		bstTEXT.setText(errorMSG.setupInformation(""));
	}
	
	private void drawHintCircle() {
		hintCricle = new Circle(def_pos[0],def_pos[1],radius+5, new Color(0, 0, 0, 0));
		hintCricle.setStroke(Color.RED);
		hintCricle.setStrokeWidth(3.0);
		Visualisation_anchorPane.getChildren().add(hintCricle);
	}
	
	@FXML private void searchValue(ActionEvent event) {
		if(deleteInProgress) {
			bstTEXT.setText(errorMSG.setupInformation(errorMSG.delInProgress));
			return;
		}
		
		if(!pathTransitionDone) {
			CreateError(errorMSG.pathTNotDone);
			return;
		}
		
		String getValue = searchField.getText();
		if(getValue.equals(randomizedIDs[0]) || getValue.equals(randomizedIDs[1])) {
			handleFinishOperation(14);
			return;
		}
		searchField.setText("");
		
		if(!analizeInput(getValue)) {
			return;
		}
		int value = Integer.parseInt(getValue);
		ArrayList<double[]> arr = bstFindPath(rootBST, value, 0, null);
	}
	
	private void setHiddenValues(String a, String b) {
		hiddenValues.setText("Ukryte liczby: [ " + a + ", "+b+" ]");
	}
	
	private void randomNode() {
		Random rand = new Random();
		
		if(arrayCircles.size() != 0) {
			restartVisualisation(null);	
		}
		if(test == false) {
			insert(rootBST, rand.nextInt(20)+50, false);
			for(int i = 1; i<10;) {
				int rnd_value = rand.nextInt(89) + 10;
				if(insert(rootBST, rnd_value, false)) {
					i++;
				}
			}
			
			int [] ids = new int[2];
			ids[0] = rand.nextInt(9) + 1;
			
			do {
				ids[1] = rand.nextInt(9) + 1;
			}while(ids[1] == ids[0]);
			
			for(int i = 0; i<2; i++) {
				updateCirlceToolip(arrayCircles.get(ids[i]), arrayTexts.get(ids[i]), "?", arrayTexts.get(ids[i]).getId());
			}
			
			randomized = true;
			randomizedIDs[0] = arrayTexts.get(ids[0]).getText();
			randomizedIDs[1] = arrayTexts.get(ids[1]).getText();
			
			setHiddenValues(arrayTexts.get(ids[0]).getText(), arrayTexts.get(ids[1]).getText());
			
			arrayTexts.get(ids[0]).setText("  ?");
			arrayTexts.get(ids[1]).setText("  ?");
			
			bstTEXT.setText(errorMSG.setupInformation(errorMSG.generateTree));
			addBTN.setDisable(true);
	    	deleteBTN.setDisable(true);
		}else {
			addBTN.setDisable(false);
	    	deleteBTN.setDisable(false);
			int [] arr = {55, 44, 33, 22, 36, 48, 51, 46, 60, 70, 80};
			for(int i : arr) {
				insert(rootBST, i, false);
			}
		}
		
	}
	
	@FXML TextField addField, deleteField, searchField, unknownField;
	
	@FXML private void addValue(ActionEvent event) {
		if(deleteInProgress) {
			bstTEXT.setText(errorMSG.setupInformation(errorMSG.delInProgress));
			return;
		}
		
		if(!pathTransitionDone) {
			CreateError(errorMSG.pathTNotDone);
			return;
		}
		String getValue = addField.getText();
		addField.setText("");
		if(!analizeInput(getValue)) {
			return;
		}
		
		int value = Integer.parseInt(getValue);
		insert(rootBST, value, true);
	}
	
	int removeTracker = 0;
	
	private BSTNode newRoot = null;
	private String delValue = null;
	private int delMin = 0;
	

	private boolean deleteInProgress = false;
	
	@FXML private void deleteValue(ActionEvent event) {
		if(deleteInProgress) {
			bstTEXT.setText(errorMSG.setupInformation(errorMSG.delInProgress));
			return;
		}
		
		if(!pathTransitionDone) {
			CreateError(errorMSG.pathTNotDone);
			return;
		}
		
		String getValue = deleteField.getText();
		deleteField.setText("");
		if(!analizeInput(getValue)) {
			return;
		}
		deleteInProgress = true;
		optMsg(errorMSG.searchToDelete, getValue);
		
		// Get node to remove
		delValue = getValue;
		newRoot = findValueToDelete(rootBST, Integer.parseInt(getValue));
		
		if(newRoot == null) {
			bstFindPath(rootBST, Integer.parseInt(getValue), 0, null);
			return;
		}
		
		if((newRoot.left != null && newRoot.right == null) || (newRoot.left == null && newRoot.right != null)) {
			bstFindPath(rootBST, Integer.parseInt(getValue), 20, null);
		}else if((newRoot.left == null && newRoot.right == null)) {
			bstFindPath(rootBST, Integer.parseInt(getValue), 20, null);
		}else {
			bstFindPath(rootBST, Integer.parseInt(getValue), 21, null);
		}
	}
	
	private void FindMin() {
		// Two childrens - find min
		msg(errorMSG.delMin);
		if(newRoot.left != null && newRoot.right != null){
			delMin = minValue(newRoot.right);
			
			ArrayList<Integer> path = minValues(newRoot);
			ArrayList<double[]> cords = new ArrayList<double[]>();
			
			for(Integer p : path) {
				cords.add(getNodePositions(rootBST, p));

			}
			initSwapPath(cords, 21);
		}
	}
	
    private void initSwapPath(ArrayList<double[]> path, int opt) {
	    if(dPath.size() == 0) {
	    	pPath.getElements().add(new MoveTo(path.get(0)[0], path.get(0)[1]));
	    	dPath.add(path.get(0));
	    	for(int i = 1; i<path.size();i++) {
	    		pPath.getElements().add(new LineTo(path.get(i)[0], path.get(i)[1]));
	    		dPath.add(path.get(i));
	    	}
	    	pathInit = true;
	    }
    	selectedOption = opt;
    	StepByStepCheckBox.setDisable(true);
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.err.println("Thread.sleep error!");
		}
    	handleSwap(!StepByStepCheckBox.isSelected(), selectedOption);
    }
    
    private void handleSwap(boolean state, int opt) {
    	if(state) {
    		Path path = new Path();
    		path.getElements().add(new MoveTo(dPath.get(0)[0], dPath.get(0)[1]));
    		for(int i = 1; i<dPath.size(); i++) {
    			path.getElements().add(new LineTo(dPath.get(i)[0], dPath.get(i)[1]));
    		}
    		selectedOption = 22;
    		multipleAnimations(path, 22);
    	}else {
    		if(dPath.size() > 1) {
	    		Path path = new Path();
	    		path.getElements().add(new MoveTo(dPath.get(0)[0], dPath.get(0)[1]));
	    		path.getElements().add(new LineTo(dPath.get(1)[0], dPath.get(1)[1]));
	    		dPath.remove(0);
	    		selectedOption = 22;
	    		singleAnimations(path, 22);
    		}
    	}
    	
    }
    
    
    ArrayList<Integer> minValues(BSTNode root){
    	ArrayList<Integer> mins = new ArrayList<Integer>();
    	BSTNode x = root;
    	int minv = root.key;
    	mins.add(minv);
    	
    	x = root.right;
    	int right_key = x.key;
    	mins.add(right_key);
    	
    	
    	while(x.left != null) {
    		int min = x.left.key;
    		x = x.left;
    		mins.add(min);
    	}
    	return mins;
    }
    
    HashMap<String, double[]> before_multiple;
    HashMap<String, double[]> after_multiple;
    ArrayList<Integer> keys_multiple;
    private int SwapCount = 0;
    
	private void Swap() {
		msg(errorMSG.delSwap);
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			System.out.println("Thread.sleep error");
		}
		SwapCount = 0;
		int deleteNodeValue = Integer.parseInt(delValue);
		
		double [] deleteNodePos = getNodePositions(rootBST, deleteNodeValue);
		double [] swapNodePos = getNodePositions(rootBST, delMin);
		
		swapNodes(deleteNodePos, swapNodePos, deleteNodeValue);
		swapNodes(swapNodePos, deleteNodePos, delMin);

		
	}
	
	private void reCreateTooltips() {
		ArrayList<Integer> keys = new ArrayList<Integer>();
		getKeys(rootBST, keys);
		
		for(Integer k : keys) {
			Circle circle = getCircle(Integer.toString(k));
			Text text = getText(Integer.toString(k));
			
			if(text.getText().equals("  ?"))
				updateCirlceToolip(circle, text, "?", text.getId());
			else
				updateCirlceToolip(circle, text, text.getText(), null);
		}
	}
	
	private void reCreateTexts() {
		ArrayList<Integer> keys = new ArrayList<Integer>();
		getKeys(rootBST, keys);
		
		for(Integer k : keys) {
			double [] pos = getNodePositions(rootBST, k);
			Circle circle = getCircle(Integer.toString(k));
			Text text = getText(Integer.toString(k));
			
			Visualisation_anchorPane.getChildren().remove((Object)circle);
			Visualisation_anchorPane.getChildren().remove((Object)text);
			
			arrayCircles.remove((Object)circle);
			arrayTexts.remove((Object)text);
			
			draw(pos[0], pos[1], Integer.toString(k));
		}
	}
	
	private void Delete(BSTNode newRoot, String getValue, int deleteTime, int multiChilds, String bonus, boolean AlreadyDeleted) throws InterruptedException {
		optMsg(errorMSG.deleteNode, getValue);
		Thread.sleep(deleteTime);
		if(newRoot.left == null && newRoot.right == null) {
			if(rootBST.key == Integer.parseInt(getValue)  && multiChilds == 0) {
				rootBST = null;
				for(Circle c : arrayCircles)
					Visualisation_anchorPane.getChildren().remove(c);
				for(Text t : arrayTexts)
					Visualisation_anchorPane.getChildren().remove(t);
				
				arrayCircles = new ArrayList<Circle>();
				arrayTexts = new ArrayList<Text>();
				arrayLines = new ArrayList<Line[]>();
				if(hintCricle != null) {
					Visualisation_anchorPane.getChildren().remove(hintCricle);
					hintCricle = null;
				}
				
			}else {
				if(multiChilds == 0) {
					int circleID = getID(getValue, true, false, false);
					int textID = getID(getValue, false, true, false);
					int linesID = getID(getValue, false, false, true);
					
					Visualisation_anchorPane.getChildren().remove(arrayCircles.get(circleID));
					Visualisation_anchorPane.getChildren().remove(arrayTexts.get(textID));
					for(int index =0; index<3; index++) {
						Visualisation_anchorPane.getChildren().remove(arrayLines.get(linesID)[index]);
		
					}
					arrayLines.remove(linesID);
					arrayTexts.remove(textID);
					arrayCircles.remove(circleID);
					
					rootBST = deleteRec(rootBST, Integer.parseInt(getValue));
					
					Path path = new Path();
					path.getElements().add(new MoveTo(def_pos[0]-1, def_pos[1]+1));
					path.getElements().add(new LineTo(def_pos[0], def_pos[1]));
					removeTransition(path, hintCricle, null, 1,0);
					
				}else {
					int circleID = getID(delValue, true, false, false);
					int textID = getID(delValue, false, true, false);

					Visualisation_anchorPane.getChildren().remove(arrayCircles.get(circleID));
					Visualisation_anchorPane.getChildren().remove(arrayTexts.get(textID));
					arrayTexts.remove(textID);
					arrayCircles.remove(circleID);
					
					rootBST = deleteRec(rootBST, Integer.parseInt(delValue));
					for(Line[] l : arrayLines) {
						for(int index = 0; index < 3; index++) {
							Visualisation_anchorPane.getChildren().remove(l[index]);
						}
					}
					arrayLines = new ArrayList<Line[]>();

					ArrayList<Integer> keys = new ArrayList<Integer>();
					keys = new ArrayList<Integer>();
					getKeys(rootBST, keys);
					
					ArrayList<double[]> line = linesPositions(rootBST, keys);
					for(double[] l : line) {
						boolean left = ((int)l[4] == 1)? true:false;
						drawArrow(l[0], l[1], l[2], l[3], left, Integer.toString((int)l[5]));
					}

					Path path = new Path();
					path.getElements().add(new MoveTo(def_pos[0]-1, def_pos[1]+1));
					path.getElements().add(new LineTo(def_pos[0], def_pos[1]));
					removeTransition(path, hintCricle, null, 1, 0);
				}
			}
			deleteInProgress = false;
			return;
		}
		
		// One or two childrens - rebuild tree
		if((newRoot.left != null && newRoot.right == null) || (newRoot.left == null && newRoot.right != null)) {
			if(multiChilds ==0) {
				ArrayList<Integer> keys = new ArrayList<Integer>();
				getKeys(newRoot, keys);
				keys.remove((Object)Integer.parseInt(getValue));
				
				HashMap<String, double[]> before_remove = getPositions(keys);
				rootBST = deleteRec(rootBST, Integer.parseInt(getValue));
				HashMap<String, double[]> after_remove = getPositions(keys);
				for(Integer k : keys) {
					Path path = new Path();
					path.getElements().add(new MoveTo(before_remove.get(Integer.toString(k))[0], before_remove.get(Integer.toString(k))[1]));
					path.getElements().add(new LineTo(after_remove.get(Integer.toString(k))[0], after_remove.get(Integer.toString(k))[1]));
					Circle circle = getCircle(Integer.toString(k));
					Text text = getText(Integer.toString(k));
					removeTransition(path, circle, null, 2, keys.size() * 2);
					removeTransition(path, null, text, 2, keys.size() * 2);
					
				}
				
				int circleID = getID(getValue, true, false, false);
				int textID = getID(getValue, false, true, false);
				
	
				Path path = new Path();
				path.getElements().add(new MoveTo(def_pos[0]-1, def_pos[1]+1));
				path.getElements().add(new LineTo(def_pos[0], def_pos[1]));
				removeTransition(path, hintCricle, null, 0, 0);
	
				Visualisation_anchorPane.getChildren().remove(arrayCircles.get(circleID));
				Visualisation_anchorPane.getChildren().remove(arrayTexts.get(textID));
				arrayTexts.remove(textID);
				arrayCircles.remove(circleID);
				
				
				for(Line[] l : arrayLines) {
					for(int index = 0; index < 3; index++) {
						Visualisation_anchorPane.getChildren().remove(l[index]);
					}
				}
				arrayLines = new ArrayList<Line[]>();
	
				keys = new ArrayList<Integer>();
				getKeys(rootBST, keys);
				ArrayList<double[]> line = linesPositions(rootBST, keys);
				for(double[] l : line) {
					boolean left = ((int)l[4] == 1)? true:false;
					drawArrow(l[0], l[1], l[2], l[3], left, Integer.toString((int)l[5]));
				}
	
				if(hintCricle != null) {
					Visualisation_anchorPane.getChildren().remove(hintCricle);
					hintCricle = null;
					drawHintCircle();
				}
			}
			else {
				int deleteNodeValue = Integer.parseInt(delValue);
				BSTNode swap = findValueToDelete(rootBST, delMin);
				keys_multiple = new ArrayList<Integer>();
				getKeys(swap, keys_multiple);
				keys_multiple.remove((Object)Integer.parseInt(delValue));
				
				before_multiple = getPositions(keys_multiple);
				
				rootBST = deleteRec(rootBST, deleteNodeValue);
				
				after_multiple = getPositions(keys_multiple);
				
				int circleID = getID(getValue, true, false, false);
				int textID = getID(getValue, false, true, false);
				
				for(Integer k : keys_multiple) {
					if(k == delMin)
						continue;
					Path path = new Path();
					path.getElements().add(new MoveTo(before_multiple.get(Integer.toString(k))[0], before_multiple.get(Integer.toString(k))[1]));
					path.getElements().add(new LineTo(after_multiple.get(Integer.toString(k))[0], after_multiple.get(Integer.toString(k))[1]));
					Circle circle = getCircle(Integer.toString(k));
					Text text = getText(Integer.toString(k));
					removeTransition(path, circle, null, 12, (keys_multiple.size()-1) *2);
					removeTransition(path, null, text, 12, (keys_multiple.size() -1) * 2);
					
				}
				
				
				Path path = new Path();
				path.getElements().add(new MoveTo(def_pos[0]-1, def_pos[1]+1));
				path.getElements().add(new LineTo(def_pos[0], def_pos[1]));
				removeTransition(path, hintCricle, null, 1, 0);
	
				Visualisation_anchorPane.getChildren().remove(arrayCircles.get(circleID));
				Visualisation_anchorPane.getChildren().remove(arrayTexts.get(textID));
				arrayTexts.remove(textID);
				arrayCircles.remove(circleID);
				
				
				for(Line[] l : arrayLines) {
					for(Line l2 : l) {
						Visualisation_anchorPane.getChildren().remove(l2);
					}
				}
				
				arrayLines = new ArrayList<Line[]>();
	
				keys_multiple = new ArrayList<Integer>();
				getKeys(rootBST, keys_multiple);
				ArrayList<double[]> line = linesPositions(rootBST, keys_multiple);
				
				for(double[] l : line) {
					boolean left = ((int)l[4] == 1)? true:false;
					drawArrow(l[0], l[1], l[2], l[3], left, Integer.toString((int)l[5]));
				}
			}
		}
		selectedOption = 0;
		deleteInProgress = false;
	}

	private void swapNodes(double [] before, double [] after, int value) {
		Path path = new Path();
		path.getElements().add(new MoveTo(before[0], before[1]));
		path.getElements().add(new LineTo(after[0], after[1]));
		Circle circle = getCircle(Integer.toString(value));
		Text text = getText(Integer.toString(value));
		removeTransition(path, circle, null, 10, 2);
		removeTransition(path, null, text, 10, 2);
	}
	
	private void removeTransition(Path path, Circle circle, Text text, int opt, int max) {
		//pathTransitionDone = false;
		PathTransition pathT = new PathTransition();
		pathT.setDuration(Duration.millis(timeSlider.getValue() * 2));
		pathT.setPath(path);
		if(circle != null)
			pathT.setNode(circle);
		else if(text != null)
			pathT.setNode(text);
		pathT.setCycleCount(1);
		pathT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathT.setAutoReverse(false);
		pathT.setOnFinished(event ->{
			// SWAP
			if(opt == 10) {
				++SwapCount;
				if(SwapCount == max) {
					String swapValue = Integer.toString(delMin);
					BSTNode newdel = findValueToDelete(rootBST, delMin);
					try {
						Delete(newdel, delValue, 1000, 1, swapValue, false);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			reCreateTexts();
		});
		pathT.play();
	}
	
	private Circle getCircle(String key) {
		for(Circle c : arrayCircles) {
			if(c.getId().equals(key)) {
				return c;
			}
		}
		return null;
	}
	
	private Text getText(String key) {
		for(Text t : arrayTexts) {
			if(t.getId().equals(key)) {
				return t;
			}
		}
		return null;
	}
	
	private int getID(String key, boolean circle, boolean text, boolean line) {
		if(line) {
			for(int i = 0; i<arrayLines.size();i++){
				Line[] arr = arrayLines.get(i);
				if(arr[0].getId().equals(key))
					return i;
			}
		}else if(text) {
			for(int i = 0; i<arrayTexts.size();i++)
				if(arrayTexts.get(i).getId().equals(key))
					return i;
		}else if(circle) {
			for(int i = 0; i<arrayCircles.size();i++)
				if(arrayCircles.get(i).getId().equals(key))
					return i;
		}
			
		return -1;
	}
	
	@FXML private void unknownValue(ActionEvent event) {
		if(deleteInProgress) {
			bstTEXT.setText(errorMSG.setupInformation(errorMSG.delInProgress));
			return;
		}
		
		if(!pathTransitionDone) {
			CreateError(errorMSG.pathTNotDone);
			return;
		}
		String getValue = unknownField.getText();
		unknownField.setText("");
		if(!analizeInput(getValue))
			return;
		
		if(selectedCircle == null) {
			CreateError(errorMSG.nodeNotSelected);
			return;
		}
		
		if(arrayTexts.get(selectedCircleID).getText().equalsIgnoreCase("  ?")) {
			if(arrayTexts.get(selectedCircleID).getId().equalsIgnoreCase(getValue)) {
				arrayTexts.get(selectedCircleID).setText(getValue);
				hintCricle.setStroke(Color.GREEN);
				if(randomizedIDs[0].equalsIgnoreCase(getValue)) {
					optMsg(errorMSG.successUnknownValue, getValue);
					randomizedIDs[0] = "?";
				}
				
				else if(randomizedIDs[1].equalsIgnoreCase(getValue)) {
					optMsg(errorMSG.successUnknownValue, getValue);
					randomizedIDs[1] = "?";
				}
				
				if(randomizedIDs[0] == "?" && randomizedIDs[1] == "?") {
					addBTN.setDisable(false);
		        	deleteBTN.setDisable(false);
				}

				//updateCirlceToolip(arrayCircles.get(selectedCircleID),arrayTexts.get(selectedCircleID), getValue, null);
				setHiddenValues(randomizedIDs[0], randomizedIDs[1]);
				reCreateTooltips();
			}
		}
	}
	
	private int findSelected(String node) {
		for(int i = 0; i<arrayCircles.size(); i++) {
			if(arrayCircles.get(i).getId() == node) {
				return i;
			}
		}
		return 0;
	}
	private Color[] colors = new Color[100];
	
	private void draw(double x, double y, String nodeValue) {
		Color color;
		if(colors[Integer.parseInt(nodeValue)] != null) {
			color = colors[Integer.parseInt(nodeValue)];
		}else {
			color = randomColor();
			colors[Integer.parseInt(nodeValue)] = color;
		}
		
		Circle circle = new Circle(x,y,radius, color);
		
		Text text = new Text(nodeValue);
		text.setBoundsType(TextBoundsType.VISUAL);
		text.setX(circle.getCenterX() - radius/2 - 2);
		text.setY(circle.getCenterY() + radius/2 - 2);
		
    	circle.getStyleClass().add("circle");
    	circle.setId(nodeValue);
    	circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
    		if(!pathTransitionDone) {
    			bstTEXT.setText(errorMSG.setupInformation(errorMSG.finishAnimation));
    			return;
    		}
    		if(selectedOption > 15)
    			return;
    		int id = Integer.parseInt(circle.getId());
    		bstFindPath(rootBST, id, 0, null);
    		selectedCircle = nodeValue;
    		selectedCircleID = findSelected(nodeValue);
    		selectedObjectData[0] = circle.getId();
    		selectedObjectData[1] = text.getText();
    	});
    	
    	text.getStyleClass().add("textBST");
    	text.setId(nodeValue);
    	text.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, e->{
    		String id = text.getId();
    		for(Circle c : arrayCircles)
    			if(c.getId() == id) {
    				c.setOpacity(0.65);
    			}
    	});
    	text.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, e->{
    		String id = text.getId();
    		for(Circle c : arrayCircles)
    			if(c.getId() == id) {
    				c.setOpacity(1);
    			}
    	});

    	text.addEventHandler(MouseEvent.MOUSE_PRESSED, e->{
    		String id = text.getId();
    		for(Circle c : arrayCircles)
    			if(c.getId() == id) {
    				c.setOpacity(0.45);
    			}
    	});
    	
    	text.addEventHandler(MouseEvent.MOUSE_RELEASED, e->{
    		String id = text.getId();
    		for(Circle c : arrayCircles)
    			if(c.getId() == id) {
    				c.setOpacity(1);
    			}
    	});
    	
    	text.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
    		if(!pathTransitionDone) {
    			bstTEXT.setText(errorMSG.setupInformation(errorMSG.finishAnimation));
    			return;
    		}
    		if(selectedOption > 15)
    			return;
    		int id = Integer.parseInt(text.getId());
    		selectedCircle = nodeValue;
    		selectedCircleID = findSelected(nodeValue);
    		bstFindPath(rootBST, id, 0, null);
    		selectedObjectData[0] = text.getId();
    		selectedObjectData[1] = text.getText();
    	});

		arrayCircles.add(circle);
		arrayTexts.add(text);
		Visualisation_anchorPane.getChildren().addAll(circle, text);
		
		updateCirlceToolip(circle, text, nodeValue, null);
	}
	
	private void updateCirlceToolip(Circle circle,Text text, String s, String realValue) {
		String sTooltip = new String();
		String nodeType = "";
		String key = "Klucz: ";
		String leftChild = "Lewy potomek: ";
		String rightChild = "Prawy potomek: ";
		String noneChilds = "brak!";
		String nodeHidden = "węzeł ukryty!";
		
		int [] childsKeys;
		
		if(realValue == null) {
			childsKeys = getChildrens(rootBST, Integer.parseInt(s));
			key += Integer.parseInt(s);
			if(rootBST.key == Integer.parseInt(s))
				nodeType +="Korzeń BST";
			else
				nodeType +="Węzeł BST";
		}else {
			childsKeys = getChildrens(rootBST, Integer.parseInt(realValue));
			key += "?";	
			if(rootBST.key == Integer.parseInt(realValue))
				nodeType +="Korzeń BST";
			else
				nodeType +="Węzeł BST";
		}
		
		if(childsKeys[0] > -1 && !String.valueOf(childsKeys[0]).equals(randomizedIDs[1])
				&& !String.valueOf(childsKeys[0]).equals(randomizedIDs[0]))
			leftChild += Integer.toString(childsKeys[0]);
		else if(childsKeys[0] > -1 && (String.valueOf(childsKeys[0]).equals(randomizedIDs[1])
				|| String.valueOf(childsKeys[0]).equals(randomizedIDs[0])))
			leftChild += nodeHidden;
		else
			leftChild += noneChilds;
		
		if(childsKeys[1] > -1 && !String.valueOf(childsKeys[1]).equals(randomizedIDs[1])
				&& !String.valueOf(childsKeys[1]).equals(randomizedIDs[0]))
			rightChild += Integer.toString(childsKeys[1]);
		else if(childsKeys[1] > -1 && (String.valueOf(childsKeys[1]).equals(randomizedIDs[1])
				|| String.valueOf(childsKeys[1]).equals(randomizedIDs[0])))
			rightChild += nodeHidden;
		else
			rightChild += noneChilds;
		
		
    	sTooltip = nodeType
    			+ "\n" + key
    			+ "\n" + leftChild
    			+ "\n" + rightChild;
    	
    	Tooltip tt = new Tooltip();
    	tt.setText(sTooltip);
    	tt.setStyle("-fx-font-size: 16px;");
    	tt.setShowDelay(Duration.millis(500));
    	Tooltip.install(circle, tt);
    	Tooltip.install(text, tt);
	}
	
	private void drawArrow(double x_start, double y_start, double x_end, double y_end, boolean left, String value) {
		Line [] line = new Line[3];

		line[0] = new Line(x_start, y_start, x_end, y_end);
		if(left) {
			line[1] = new Line(x_end,  y_end - 10, x_end, y_end);
			line[2] = new Line(x_end + 10, y_end + 3, x_end, y_end);
		}else {
			line[1] = new Line(x_end,  y_end - 10, x_end, y_end);
			line[2] = new Line(x_end - 10, y_end + 3, x_end, y_end);
		}
		
		for(Line l : line) {
			l.getStyleClass().add("line");
			l.setId(value);
		}
		
		
		arrayLines.add(line);
		Visualisation_anchorPane.getChildren().addAll(line);
	}
	
	private boolean isCharNum(char x) {
		if(x == '0' || x == '1'|| x == '2'|| x == '3'|| x == '4'|| x == '5'|| x == '6'|| x == '7'|| x == '8'|| x == '9')
			return true;
		return false;
	}
	
	private boolean analizeInput(String in) {
		int len = in.length();
		if( len > 0 && len <= 2) {
			for(int i = 0; i<len; i++) {
				if(!isCharNum(in.charAt(i))) {
					CreateError(errorMSG.OnlyNumeric);
					return false;
				}
			}
			return true;
		}
		CreateError(errorMSG.WrongInput);
		return false;
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
    @FXML ProgressBar generateBar;
    @FXML public void pressRandomBar() {
    	if(generateDone == false)
    		return;

		if(deleteInProgress) {
			bstTEXT.setText(errorMSG.setupInformation(errorMSG.delInProgress));
			return;
		}
		
		if(!pathTransitionDone) {
			CreateError(errorMSG.pathTNotDone);
			return;
		}
    	
    	generateBar.setProgress(0);
    	addBTN.setDisable(true);
    	deleteBTN.setDisable(true);
    	unknownBTN.setDisable(true);
    	restartBTN.setDisable(true);
    	backBTN.setDisable(true);
    	searchBTN.setDisable(true);
    	generateDone = false;
    	Timeline timeline = new Timeline();
    	KeyValue keyValue = new KeyValue(generateBar.progressProperty(), 1);
    	KeyFrame keyFrame = new KeyFrame(new Duration(1500), keyValue);
    	timeline.getKeyFrames().add(keyFrame);
    	timeline.setOnFinished(event->{
        	addBTN.setDisable(false);
        	unknownBTN.setDisable(false);
        	restartBTN.setDisable(false);
        	backBTN.setDisable(false);
        	searchBTN.setDisable(false);
    		randomNode();
    		reCreateTooltips();
        	generateDone = true;
    	});
    	timeline.play();
    }
	
    @FXML private Text bstTEXT;
    @FXML private CheckBox StepByStepCheckBox;
    private boolean pathInit = false;
    private Path pPath = new Path();
    private ArrayList<double[]> dPath = new ArrayList<double[]>();
    
    private int node_value = 0;
    private double [] node_cords = new double[2];
    private double [] arrow_cords = new double[4];
    private boolean arrow_type;
    
    private void handleFinishOperation(int opt) {
    	switch(opt) {
    		case 0: return;
	    	case 1: // add root
	            drawHintCircle();
	    		draw(node_cords[0], node_cords[1], String.valueOf(node_value));
	    		reCreateTexts();
	    		break;
	    	case 2: // add node
	    		draw(node_cords[0], node_cords[1], String.valueOf(node_value));
	    		drawArrow(arrow_cords[0], arrow_cords[1], arrow_cords[2], arrow_cords[3], arrow_type, String.valueOf(node_value));
	    		reCreateTexts();
	    		break;
	    	case 3: // add error - value already exists
	    		msg(errorMSG.nodeAlredyExists);
	    		break;
	    	case 4: // add error - height max
	    		msg(errorMSG.maxHeightBST);
	    		break;
	    	case 5: //  add node - success msg
	    		optMsg(errorMSG.addNode, String.valueOf(node_value));
	    		break;
	    	case 11: // find root
	    		Visualisation_anchorPane.getChildren().remove(hintCricle);
	    		drawHintCircle();
	    		if(String.valueOf(node_value).equals(randomizedIDs[0]) || String.valueOf(node_value).equals(randomizedIDs[1]))
		    		optMsg(errorMSG.searchNode, "??");
	    		else
		    		optMsg(errorMSG.searchNode, String.valueOf(node_value));
	    		break;
	    	case 12: // find node
	    		if(String.valueOf(node_value).equals(randomizedIDs[0]) || String.valueOf(node_value).equals(randomizedIDs[1]))
		    		optMsg(errorMSG.searchNode, "??");
	    		else
		    		optMsg(errorMSG.searchNode, String.valueOf(node_value));
	    		break;
	    	case 13: // find node don't exists
	    		optMsg(errorMSG.searchNotFound, String.valueOf(node_value));
	    		selectedOption = 0;
	    		deleteInProgress = false;
	    		break;
	    	case 14: // find node is hidden
	    		msg(errorMSG.searchHidden);
	    		break;
	    	case 19: // node to delete is root
	    		Visualisation_anchorPane.getChildren().remove(hintCricle);
	    		drawHintCircle();
				try {
					Delete(rootBST, delValue, 1000, 0, null, false);
				} catch (InterruptedException e) {
					System.out.println("Thread.sleep error!");
				}
	    		break;
	    	case 20:
	    		FindMin();
	    		break;
    	}
    }
    
	private void multipleAnimations(Path path, int opt) {
		if(hintCricle == null) return;
		hintCricle.setStroke(Color.GREEN);
		pathTransitionDone = false;
		PathTransition pathT = new PathTransition();
		pathT.setDuration(Duration.millis(timeSlider.getValue() * dPath.size()));
		pathT.setPath(path);
		pathT.setNode(hintCricle);
		pathT.setCycleCount(1);
		pathT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathT.setAutoReverse(false);
		pathT.setOnFinished(event ->{
			hintCricle.setStroke(Color.RED);
			StepByStepCheckBox.setDisable(false);
			pPath = new Path();
			dPath = new ArrayList<double[]>();
			pathInit = false;
			pathTransitionDone = true;
			handleFinishOperation(opt);
			if(opt == 20) {
				if((newRoot.left != null && newRoot.right == null)
				|| (newRoot.left == null && newRoot.right != null)
				|| (newRoot.left == null && newRoot.right == null))
				try {
					Delete(newRoot, delValue, 1000, 0 , null, false);
				} catch (InterruptedException e) {
					System.out.println("Thread sleep error!");
				}
				selectedOption = 20;
			}else if(opt == 21) {
				selectedOption = 21;
				FindMin();
			}else if(opt == 22) {
				selectedOption = 22;
				Swap();
			}else {
				selectedOption = 0;
			}
		});
		pathT.play();
	}
	
	private void singleAnimations(Path path, int opt) {
		if(hintCricle == null) return;
		hintCricle.setStroke(Color.GREEN);
		pathTransitionDone = false;
		PathTransition pathT = new PathTransition();
		pathT.setDuration(Duration.millis(timeSlider.getValue() * 2));
		pathT.setPath(path);
		pathT.setNode(hintCricle);
		pathT.setCycleCount(1);
		pathT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathT.setAutoReverse(false);
		pathT.setOnFinished(event ->{
			hintCricle.setStroke(Color.RED);
			if(dPath.size() <= 1) {
				pPath = new Path();
				dPath = new ArrayList<double[]>();
				pathInit = false;
				handleFinishOperation(opt);
				// delete case
				if(opt == 20) {
					if(newRoot == null) {
						return;
					}
					if((newRoot.left != null && newRoot.right == null)
					|| (newRoot.left == null && newRoot.right != null)
					|| (newRoot.left == null && newRoot.right == null))
					try {
						Delete(newRoot, delValue, 1000, 0 , null, false);
					} catch (InterruptedException e) {
						System.out.println("Thread sleep error!");
					}
					selectedOption = 20;
				}else if(opt == 21) {
					selectedOption = 21;
					FindMin();
				}else if(opt == 22) {
					selectedOption = 22;
					Swap();
				}
				else {
					selectedOption = 0;
				}
			}
			StepByStepCheckBox.setDisable(false);
			pathTransitionDone = true;
		});
		pathT.play();
	}
    
	private int selectedOption = 0;
	
    private void initializePath(ArrayList<double[]> path, int opt) {
    	//if(path.size() < 2) return;
    	if(pathInit == false) {
	    	if(dPath.size() == 0) {
	    		pPath.getElements().add(new MoveTo(def_pos[0]+1, def_pos[1]-1));
	    		for(double[] c : path) {
	    			pPath.getElements().add(new LineTo(c[0], c[1]));
	    			dPath.add(c);
	    		}
	    		pathInit = true;
	    	}
    	}
    	selectedOption = opt;
    	StepByStepCheckBox.setDisable(true);
    	handleAnimation(!StepByStepCheckBox.isSelected(), selectedOption);
    }
    
    private void handleAnimation(boolean state, int opt) {
    	if(dPath.size() == 0) return;
    	if(pathTransitionDone == false) return;
    	if(pathInit == false) return;
    	
    	if(state) {
    		Path path = new Path();
    		path.getElements().add(new MoveTo(dPath.get(0)[0], dPath.get(0)[1]));
    		for(int i = 1; i<dPath.size(); i++) {
    			path.getElements().add(new LineTo(dPath.get(i)[0], dPath.get(i)[1]));
    		}
    		multipleAnimations(path, opt);
    	}else {
    		if(dPath.size() > 1) {
	    		Path stepPath = new Path();
	    		stepPath.getElements().add(new MoveTo(dPath.get(0)[0], dPath.get(0)[1]));
	    		stepPath.getElements().add(new LineTo(dPath.get(1)[0], dPath.get(1)[1]));
	    		dPath.remove(0);
	    		singleAnimations(stepPath, opt);
    		}
    	}
    	
    }
    
    private void msg(String errorMSG) {
    	bstTEXT.setText(this.errorMSG.setupInformation(errorMSG));
    }
    
    private void optMsg(String errorMSG, String value) {
    	bstTEXT.setText(this.errorMSG.setupOperationInfo(errorMSG, value));
    }
    
    @FXML private void next(ActionEvent event) {
    	if(pathTransitionDone)
    		handleAnimation(!StepByStepCheckBox.isSelected(), selectedOption);
    }
    
    @FXML private void skip(ActionEvent event) {
    	if(pathTransitionDone)
    		handleAnimation(StepByStepCheckBox.isSelected(), selectedOption);
    }
    // =================================	Binnary search tree =================================//
    class BSTNode extends BST_controller{
    	int key;
    	BSTNode left, right;
    }
    
    BSTNode newNodeBST(int data) { 
    	BSTNode temp = new BSTNode(); 
        temp.key = data; 
        temp.left = null; 
        temp.right = null; 
        return temp; 
    }
    private boolean insert(BSTNode root, int key, boolean animate) { 
    	BSTNode newnode = newNodeBST(key); 
    	BSTNode x = root; 
    	BSTNode y = null; 
    	bst_height = 1;
    	double xx = def_pos[0], yy = def_pos[1];
    	double xx_prev = 0;
    	double xx_add = def_pos[0];
    	ArrayList<double[]> cPath = new ArrayList<double[]>();
    	double [] arr_base = {xx, yy};
    	cPath.add(arr_base);
        while (x != null) {
        	if(bst_height >= max_height) {
        		if(animate)
        			handleFinishOperation(4);
        		return false;
        	}
        	yy += down_offset;
        	xx_prev = xx;
        	xx_add /= 2;
            y = x;
            if (key < x.key) {
                x = x.left;
                xx -= xx_add;
            }
            else if (key > x.key){
                x = x.right; 
                xx += xx_add;
            }
            else {
            	handleFinishOperation(3);
            	return false;
            }
            double [] arr = {xx, yy};
            cPath.add(arr);
            bst_height++;
        }
        
        node_value = key;
        node_cords[0] = xx;
        node_cords[1] = yy;

        arrow_cords[1] = yy - down_offset + radius/2;
        arrow_cords[3] = yy - radius;
        
        if (y == null) {
        	rootBST = newnode;
        	handleFinishOperation(1);
            return true;
        }
        else if (key < y.key) {
            y.left = newnode;
            arrow_cords[0] = xx_prev - radius;
            arrow_cords[2] = xx + 10;
            arrow_type = true;
            if(animate) {
            	initializePath(cPath, 2);
            	handleFinishOperation(5);
            }else 
            	handleFinishOperation(2);
            return true;
        }
        else if (key > y.key) {
            y.right = newnode;
            arrow_cords[0] = xx_prev + radius;
            arrow_cords[2] = xx - 10;
            arrow_type = false;
            if(animate) {
            	initializePath(cPath, 2);
            	handleFinishOperation(5);
            }else
            	handleFinishOperation(2);
            return true;
        }
        return false; 
    } 
    private BSTNode findValueToDelete(BSTNode root, int key) {
    	BSTNode x = root;
    	while(x != null) {
    		if(key < x.key)
    			x = x.left;
    		else if(key > x.key)
    			x = x.right;
    		else
    			return x;
    	}
    	return x;
    }
    
    private void getKeys(BSTNode root, ArrayList<Integer> keys){
    	if(root == null) return;
    	
    	getKeys(root.left, keys);
    	getKeys(root.right, keys);
    	keys.add(root.key);
    }
    
    private HashMap<String, double[]> getPositions(ArrayList<Integer> keys){
    	HashMap<String, double[]> pos = new HashMap<String, double[]>();
    	
    	for(int i = 0; i<keys.size(); i++) {
    		Integer key = keys.get(i);
    		double [] cords = new double [2];
    		double xx_add = def_pos[0];
    		cords[0] = def_pos[0];
    		cords[1] = def_pos[1];
    		BSTNode x = rootBST;
    		
    		while(x != null) {
        		xx_add /= 2;
        		if(key < x.key) {
        			x = x.left;
        			cords[0] -= xx_add;
        		}else if(key > x.key) {
        			x = x.right;
        			cords[0] += xx_add;
        		}else {
        			pos.put(Integer.toString(key), cords);
        			break;
        		}
        		cords[1] += down_offset;
    		}
    	}
    	return pos;
    }
    
    private int [] getChildrens(BSTNode root, int key) {
    	BSTNode x = root;
    	
    	int [] values = new int[2];
    	values[0] = -1;
    	values[1] = -1;
    	
    	while(x!=null) {
    		if(key < x.key)
    			x = x.left;
    		else if(key > x.key)
    			x = x.right;
    		else {
    			if(x.left != null)
    				values[0] = x.left.key;
    			
    			if(x.right != null)
    				values[1] = x.right.key;
    				
    			return values;
    		}
    	}
    	
    	
    	return values;
    }
    
    private double[] getNodePositions(BSTNode root, int key) {
    	double [] pos = new double[2];
		double xx_add = def_pos[0];
		pos[0] = def_pos[0];
		pos[1] = def_pos[1];
		BSTNode x = rootBST;
		
		if(x == null)
			return null;
		
		if(x.key == key) {
			return pos;
		}
		
		while(x != null) {
    		xx_add /= 2;
    		if(key < x.key) {
        		pos[1] += down_offset;
    			pos[0] -= xx_add;
    			x = x.left;
    		}else if(key > x.key) {
        		pos[1] += down_offset;
    			pos[0] += xx_add;
    			x = x.right;
    		}else 
    			return pos;
		}
    	
    	return pos;
    }
    
    private ArrayList<double[]> linesPositions(BSTNode root, ArrayList<Integer> keys) { 
    	ArrayList<double[]> lines = new ArrayList<double[]>();
    	
    	key_loop:for(Integer key : keys) {
        	BSTNode x = root; 
        	BSTNode y = null; 
        	int k = (int)key;
        	double xx = def_pos[0], yy = def_pos[1];
        	double xx_prev = 0;
        	double xx_add = def_pos[0];
        	
	        key_while:while (x != null) {
	            if (k < x.key) {
		            y = x;
		        	xx_prev = xx;
		        	xx_add /= 2;
	                x = x.left;
	                xx -= xx_add;
		        	yy += down_offset;
	            }
	            else if (k > x.key){
		            y = x;
		        	xx_prev = xx;
		        	xx_add /= 2;
	                x = x.right; 
	                xx += xx_add;
		        	yy += down_offset;
	            }
	            else {
	            	break key_while;
	            }
	        }
        	double x1 =400, x2 = yy - down_offset + radius/2, x3=400, x4 = yy - radius, x5=400, x6 = (double)k;
        	if(y==null) {
        		continue key_loop;
        	}else if(k < y.key) {
        		x1 = xx_prev - radius;
        		x3 = xx +10;
        		x5 = (double)1;
        	}else if(k > y.key) {
        		x1 = xx_prev + radius;
        		x3 = xx -10;
        		x5 = (double)0;
        	}
        	
     
        	double [] arr = {x1, x2, x3, x4, x5, x6};
        	lines.add(arr);
    	}
        return lines;
    }
    
    private ArrayList<double[]> bstFindPath(BSTNode root, int key, int opt, double [] cords) {
    	ArrayList<double[]> path = new ArrayList<double[]>();
    	BSTNode x = root;
    	
    	double xx, yy, xx_add;
    	if(cords == null) {
    		xx = def_pos[0];
    		yy = def_pos[1];
    		xx_add = xx;
    	}else {
    		xx = cords[0];
    		yy = cords[1];
    		xx_add = xx;
    	}
    	double [] arr_base = {xx, yy};
    	
    	node_value = key;
    	
    	if( root.key == key) {

			if(opt == 20)
				handleFinishOperation(19);
			else if(opt == 21)
				handleFinishOperation(20);
			else
    			handleFinishOperation(11);
    		return path;
    	}else {
    		path.add(arr_base);
    	}
    	
    	while(x != null) {
    		yy += down_offset;
    		xx_add /= 2;
    		if(key < x.key) {
    			x = x.left;
    			xx -= xx_add;
    			double[] arr = {xx, yy};
    			path.add(arr);
    		}else if(key > x.key) {
    			x = x.right;
    			xx += xx_add;
    			double[] arr = {xx, yy};
    			path.add(arr);
    		}else {
    			if(opt >= 20)
    				initializePath(path, opt);
    			else
    				initializePath(path, 12);
        		return path;
    		}
    	}
    	path.remove(path.size()-1);
    	if(path.size() == 1) {
    		double [] arr = {def_pos[0], def_pos[1]};
    		path.add(arr);
    	}
		initializePath(path, 13);
    	return path;
    }
    
    BSTNode deleteRec(BSTNode root, int key)
    {	
        if (root == null)
            return root;
 
        if (key < root.key)
            root.left = deleteRec(root.left, key);
        else if (key > root.key)
            root.right = deleteRec(root.right, key);
 
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            
            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
 
        return root;
    }
	
	
    int minValue(BSTNode root)
    {
        int minv = root.key;
        while (root.left != null) 
        {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }
    BSTNode rootBST = null;
}