package com.imaginea.scoring;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public abstract class AbstractScoreType {
	
	protected Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
	protected IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46, analyzer);
	protected RAMDirectory ramDirectory = new RAMDirectory();
	protected IndexWriter indexWriter;
	protected String queryToRun = "itemType:suv";
	
	public void populateIndex() {		
		try {
			    indexWriter = new IndexWriter(ramDirectory, config);	
				addDocumentToIndex("Lada Niva", "Brown", "2000000", "Russia", "SUV");	
				addDocumentToIndex("Tata Aria", "Red", "1600000", "India", "SUV");	
				addDocumentToIndex("Nissan Terrano", "Blue", "2000000", "Japan", "SUV");	
				addDocumentToIndex("Mahindra XUV500", "Black", "1600000", "India", "SUV");	
				addDocumentToIndex("Ford Ecosport", "White", "1000000", "USA", "SUV");	
				addDocumentToIndex("Mahindra Thar", "White", "1200000", "India", "SUV");	
				indexWriter.close();
				} catch (IOException | NullPointerException ex) {
					System.out.println("Something went wrong in this sample code -- " 
				                                         + ex.getLocalizedMessage());
		        } 
	}
	
	protected void addDocumentToIndex(String itemName, String itemColour,
			String itemPrice, String originOfItem, String itemType)
			throws IOException {
		Document docToAdd = new Document();
		docToAdd.add(new StringField("itemName", itemName,
				Field.Store.YES));
		
		docToAdd.add(new TextField("itemColour", itemColour, Field.Store.YES));
		docToAdd.add(new StringField("itemPrice", itemPrice, Field.Store.YES));
		docToAdd.add(new StringField("originOfItem", originOfItem,
				Field.Store.YES));
		
		TextField itemTypeField = new TextField("itemType", itemType, Field.Store.YES);
		docToAdd.add(itemTypeField);
		indexWriter.addDocument(docToAdd);
	}
	
	abstract public void demoCustomScoring();
	}
