package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SpellCheckerController {
	
	Dictionary model = new Dictionary();

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxLanguage;

    @FXML
    private TextArea txtInput;

    @FXML
    private TextArea txtOutput;

    @FXML
    private Label lblErrors;

    @FXML
    private Label lblPerformance;

    @FXML
    void doClearText(ActionEvent event) {
    	this.txtInput.clear();
    	this.txtOutput.clear();
    	
    	this.lblErrors.setVisible(false);
    	this.lblPerformance.setVisible(false);
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
	    this.txtOutput.clear();
	   	
	   	String languageSelected = this.boxLanguage.getValue();
	   	if(languageSelected == (null)) {
	   		this.txtOutput.setText("Error: choose a language!");
	 	
    	} else {	    		
    		long startTime = System.nanoTime();
	    		
		   		model.loadDictionary(languageSelected);
		    	
		    	String inputText = this.txtInput.getText();
		    	String[] inputTextVector = inputText.split(" +");
			    	
		    	List<String> inputTextList = new ArrayList<String>();			    	
		    	for(String s : inputTextVector) {
			    	s = s.toLowerCase();
			    	s = s.replaceAll("[.,\\/#!?$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");
			   		inputTextList.add(s);
			   	}
			    	
			   	int countErrors = 0;
			   	List<RichWord> richWords = model.spellCheckText(inputTextList);
		    	for(RichWord rw : richWords) {
		    		if(rw.isCorrect() == false) {
		    			this.txtOutput.appendText(rw.getWord()+"\n");			    			
		    			countErrors++;
			    	}
			    }
			    	
			   	this.lblErrors.setText("The text contains " + countErrors + " errors");
			   	this.lblErrors.setVisible(true);
			    	
			long estimatedTime = System.nanoTime() - startTime;
			
				this.lblPerformance.setText("Spell check completed in " + ((double)estimatedTime/(double)1000000000) + " seconds");
				this.lblPerformance.setVisible(true);
	    	}
    }

    @FXML
    void initialize() {
        assert boxLanguage != null : "fx:id=\"boxLanguage\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblErrors != null : "fx:id=\"lblErrors\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblPerformance != null : "fx:id=\"lblPerformance\" was not injected: check your FXML file 'SpellChecker.fxml'.";

    }

	public void setModel(Dictionary model) {
		this.model = model;
		boxLanguage.getItems().addAll("Italian", "English");
	}
}
