package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DictionaryLinkedList {
	
	private List<String> dictionary = new LinkedList<String>();
	
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
				dictionary.add(word.toLowerCase());
			}
			br.close();
			
		} catch (IOException e) {
			System.out.println("Errore nell lettura del file");
		}	
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList) {
		List<RichWord> richWords = new LinkedList<RichWord>();
		
		for(String word : inputTextList) {
			boolean isCorrect = false;
			
			if(dictionary.contains(word))
				isCorrect = true;
			
			richWords.add(new RichWord(word, isCorrect));
		}
		
		return richWords;
	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		List<RichWord> richWords = new LinkedList<RichWord>();
		
		for(String word : inputTextList) {
			boolean isCorrect = false;
			
			for(int i=0; i<dictionary.size(); i++) {
				if(dictionary.get(i).equals(word)) {
					isCorrect = true;
					break;
				}
			}
			
			richWords.add(new RichWord(word, isCorrect));
		}
		
		return richWords;
	}
	
	public List<RichWord> spellCheckTextDicotomic(List<String> inputTextList) {
		List<RichWord> richWords = new LinkedList<RichWord>();
		
		for(String word : inputTextList) {
			boolean isCorrect = false;
			
			int low = 0;
			int high = dictionary.size()-1;
			while(low <= high) {
				int mid = (low+high)/2;
				if(word.compareTo(dictionary.get(mid)) == 0) {
					isCorrect = true;
					break;
				}
				else if(word.compareTo(dictionary.get(mid)) > 0)
					low = mid+1;
				else if(word.compareTo(dictionary.get(mid)) < 0)
					high = mid-1;
			}
			
			richWords.add(new RichWord(word, isCorrect));
		}
		
		return richWords;
	}
	
	public void resetDictionary() {
		dictionary.clear();
	}

}
