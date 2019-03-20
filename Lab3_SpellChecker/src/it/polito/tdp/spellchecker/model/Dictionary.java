package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
	
	private List<String> dictionary = new ArrayList<String>();
	
	public void loadDictionary(String language) {
		String filePath = "";
		if(language.equals("Italian"))
			filePath += "rsc/Italian.txt";
		else if(language.equals("English"))
			filePath += "rsc/English.txt";
		
		try {
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			
			String word;
			while((word = br.readLine()) != null) {
				dictionary.add(word);
			}
			br.close();
			
		} catch (IOException e) {
			System.out.println("Errore nell lettura del file");
		}	
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList) {
		List<RichWord> richWords = new ArrayList<RichWord>();
		
		for(String word : inputTextList) {
			boolean isCorrect = false;
			if(dictionary.contains(word))
				isCorrect = true;
			
			richWords.add(new RichWord("word", isCorrect));
		}
		
		return richWords;
	}

}
