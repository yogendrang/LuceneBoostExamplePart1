package com.imaginea.scoring;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.util.Version;

import com.imaginea.scoring.customscorequery.ImagineaDemoCustomScoreQuery;

public class CustomScoreQueryProviderImpl extends AbstractScoreType {
	
	private boolean printExplanation;

	public CustomScoreQueryProviderImpl(boolean printExplanation) {
		this.printExplanation = printExplanation;
	}

	@Override
	public void demoCustomScoring() {
		performRoutineQuerying();
		performCustomQuerying();
	}
	
	private void performCustomQuerying() {	
		System.out.println("-----Performing Custom Querying-----");
		try {
			IndexReader idxReader = DirectoryReader.open(ramDirectory);
			IndexSearcher idxSearcher = new IndexSearcher(idxReader);	
			Query queryToSearch = new QueryParser(Version.LUCENE_46, "itemType", analyzer)
			                                                           .parse(queryToRun);
			
			CustomScoreQuery customQuery = new ImagineaDemoCustomScoreQuery(queryToSearch); 

		    ScoreDoc[] hitsTop = idxSearcher.search(customQuery, 10).scoreDocs;
		    
		    System.out.println("Search produced " + hitsTop.length + " hits.");
		    System.out.println("----------");	
		    for(int i=0;i<hitsTop.length;++i) {
		      int docId = hitsTop[i].doc;
		      Document docAtHand = idxSearcher.doc(docId);
		      System.out.println(docAtHand.get("itemName") + "\t" + 
                                        docAtHand.get("originOfItem") 
                      		   + "\t" + docAtHand.get("itemColour")
                      		   + "\t" + docAtHand.get("itemPrice")
                               + "\t" + docAtHand.get("itemType") + "\t"   +  
                                        hitsTop[i].score);
		      
		      if (printExplanation) {
			      Explanation explanation = idxSearcher.explain(customQuery, hitsTop[i].doc); 
			      System.out.println("----------");
			      System.out.println(explanation.toString());
			      System.out.println("----------");	
		      }	    
		    }
		} catch (IOException | ParseException ex) {
			System.out.println("Something went wrong in this sample code -- " 
                    + ex.getLocalizedMessage());
		}  finally {
			ramDirectory.close();
		}	
		
	}
	
	public void performRoutineQuerying() {
		System.out.println("-----Performing simple Querying-----");
		
		try {
			IndexReader idxReader = DirectoryReader.open(ramDirectory);
			IndexSearcher idxSearcher = new IndexSearcher(idxReader);
			
			Query queryToSearch = new QueryParser(Version.LUCENE_46, "itemType", analyzer)
			                                                           .parse(queryToRun);
			
			TopScoreDocCollector collector = TopScoreDocCollector.create(10, true);
			idxSearcher.search(queryToSearch, collector);
		    ScoreDoc[] hitsTop = collector.topDocs().scoreDocs;
		    
		    System.out.println("Search produced " + hitsTop.length + " hits.");
		    System.out.println("----------");	
		    for(int i=0;i<hitsTop.length;++i) {
		      int docId = hitsTop[i].doc;
		      Document docAtHand = idxSearcher.doc(docId);
		      System.out.println(docAtHand.get("itemName") + "\t" + 
		                                              docAtHand.get("originOfItem") 
		    		  + "\t" + docAtHand.get("itemColour") + "\t" + 
		                                              docAtHand.get("itemPrice")
		    		  + "\t" + docAtHand.get("itemType") + "\t"   +  
		                                              hitsTop[i].score);
		      
		      if (printExplanation) {
			      Explanation explanation = idxSearcher.explain(queryToSearch, hitsTop[i].doc); 
			      System.out.println("----------");
			      System.out.println(explanation.toString());
			      System.out.println("----------");	
		      }	    
		    }
		} catch (IOException | ParseException ex) {
			System.out.println("Something went wrong in this sample code -- " 
                    + ex.getLocalizedMessage());
		}

	}
}
