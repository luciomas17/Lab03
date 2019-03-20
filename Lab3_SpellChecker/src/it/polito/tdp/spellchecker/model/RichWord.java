package it.polito.tdp.spellchecker.model;

public class RichWord {
	
	private String word;
	private boolean isCorrect;
	
	public RichWord(String word, boolean isCorrect) {
		super();
		this.word = word;
		this.isCorrect = isCorrect;
	}
}
