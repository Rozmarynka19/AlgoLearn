package algolearn.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import algolearn.gui.info.errors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VisualisationControllerGrahamScan extends FXMLDocumentController {
	
	private boolean generateDone = true;
	private boolean deleteInProgress = false;
	private boolean pathTransitionDone = true;
	private errors.rbtMsg msg = new errors.rbtMsg();
	
    private final int minInputRange = 1;
    private final int maxInputRange = 99;
    private final int maxRandomNodes = 15;
    private final double padding = 40;
    private final int maxSafetyIterations = 1000;
    private ArrayList<Button> nodes = new ArrayList<Button>();

	@FXML AnchorPane visAnchorPane;
	@FXML Button addButton;
	@FXML Button deleteButton;
	@FXML Button restartButton;
	@FXML TextField insertTextField;
	@FXML TextField deleteTextField;
	@FXML ProgressBar generateBar;
	
	//--------- Msg box stuff ----------
	@FXML Text txtMainTitle;
	@FXML TextArea msgTextArea;
	
	//---------- Infoline stuff ----------
	@FXML Text info;
	@FXML ScrollPane infoScrollPane;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		if (infoScrollPane == null)
			return;
		infoScrollPane.getStyleClass().clear();
		infoScrollPane.getStyleClass().add("infoScrollPane");
	}
	
	@FXML
	public void insert(ActionEvent event)
	{
		int key = validateInput(insertTextField);
		
		if(key<0)
			return;
		else
        {
			if (isSuchNode(key,false))
			{
				System.out.println("insert: juz jest węzeł w grafie o kluczu "+String.valueOf(key));
				//TODO: messagebox - juz jest wezel w grafie o takim kluczu
				return;
			}
				
			Button newNode = new Button();
			if (findPlace(newNode))
			{
				newNode.setText(String.valueOf(key));
				nodes.add(newNode);
				System.out.println("insert: udalo sie dodac wezel o kluczu "+String.valueOf(key));
				//TODO: messagebox - udalo sie dodac wezel
			}
				
			display();            
        }
	}
	
	private void display()
	{
		visAnchorPane.getChildren().clear();
		for(Button node: nodes)
		{
			 node.getStyleClass().clear();
			 node.getStyleClass().add("rbtNodeRed");
			 visAnchorPane.getChildren().add(node);
		}
	}
	
	private boolean isSuchNode(int key, boolean remove)
	{
		for(Button node:nodes)
		{
			if(Integer.valueOf(node.getText()) == key)
			{
				if (remove==true)
					nodes.remove(node);
				return true;
			}
				
		}
		return false;
	}

	private boolean findPlace(Button newNode)
	{
		double x,y;
		boolean tooClose;
		int safetyIterator = -1;
		
		do
		{
			x = (Math.random() * (visAnchorPane.getPrefWidth()-2*padding))+padding;
			y = (Math.random() * (visAnchorPane.getPrefHeight()-2*padding))+padding;
			tooClose = false;
			safetyIterator++;
			
			for(Button node: nodes)
			{
				if(x >= node.getLayoutX()-padding && x <= node.getLayoutX()+padding
						&& y >= node.getLayoutY()-padding && y <= node.getLayoutY()+padding)
					tooClose=true;
			}
//			System.out.println("safetyIterator: "+String.valueOf(safetyIterator));
		}
		while(tooClose && safetyIterator<=maxSafetyIterations);
		
		if(safetyIterator>=maxSafetyIterations)
		{
			System.out.println("insert: za duzo wezlow w grafie!");
			//TODO: messagebox - too many nodes
			createMessageBox(msg.msgErrorHeader, msg.acceptableValues, msg.msgTypeError);
			return false;
		}
		
		newNode.setLayoutX(x);
		newNode.setLayoutY(y);
		return true;
	}
	
	@FXML
	public void delete(ActionEvent event)
	{
		int key = validateInput(deleteTextField);
		
		if(key<0)
			return;
		else
        {
			if (!isSuchNode(key,true))
			{
				System.out.println("delete: nie ma w grafie wezla o kluczu "+String.valueOf(key));
				//TODO: messagebox - nie ma w grafie wezla o kluczu x
				return;
			}
			
			System.out.println("delete: udalo sie usunac wezel o kluczu "+String.valueOf(key));
			//TODO: messagebox - udalo sie usunac wezel
			display();            
        }
	}
	
	@FXML
	public void pressRandomBar(ActionEvent event)
	{
		
	}
	
	@FXML
	public void restartVis(ActionEvent event)
	{
    	this.nodes.clear();
    	this.display();
    	System.out.println("restartVis: zrestartowano wizualizacje!");
    	info.setText(msg.setupInformation(msg.restartDone));
	}
	
    private int validateInput(TextField inputTextField)
    {
		 if(inputTextField.getText().length() == 0)
		 {
			 	createMessageBox(msg.msgErrorHeader, msg.valueNotGiven, msg.msgTypeError);
			 	inputTextField.clear();
			 	return -1;
		 }

    	int key;
    	try
    	{
    		key = Integer.parseInt(inputTextField.getText());
    	}
    	catch (Exception e)
    	{
    		createMessageBox(msg.msgErrorHeader, msg.acceptableValues, msg.msgTypeError);
    		inputTextField.clear();
    		return -1;
    	}
    	
    	if(key<minInputRange || key>maxInputRange)
    	{
    		createMessageBox(msg.msgErrorHeader, msg.acceptableValues, msg.msgTypeError);
    		inputTextField.clear();
    		return -1;
    	}
    	
    	inputTextField.clear();
    	return key;
    }
    
    private void createMessageBox(String header, String message, String msgBoxStyle)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/msg_fxml.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            FXMLDocumentController_RBT controller = (FXMLDocumentController_RBT)fxmlLoader.getController();
            Stage stage = new Stage();
            setStyle(stage);
            setMouse(root1, stage);
            stage.centerOnScreen();
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));  
            stage.show();
            controller.txtMainTitle.setText(header);
            controller.msgTextArea.setText(message);
            if(msgBoxStyle==msg.msgTypeError)
            {
            	controller.txtMainTitle.getStyleClass().add("errorHeader");
            	controller.msgTextArea.getStyleClass().add("errorText");
            }
            else if(msgBoxStyle==msg.msgTypeCongrats)
            {
            	controller.txtMainTitle.getStyleClass().add("congratsHeader");
            	controller.msgTextArea.getStyleClass().add("congratsText");
            }
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}