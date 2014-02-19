package com.imaginea.scoring.customscorequery;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.CustomScoreProvider;
import org.apache.lucene.util.Bits;

public class ImagineaDemoCustomScoreProvider extends CustomScoreProvider {
	
	private static AtomicReader atomicReader;

	public ImagineaDemoCustomScoreProvider(AtomicReaderContext context) {
		super(context);
		atomicReader = context.reader();
	}
	
    @Override
    public float customScore(int doc, float subQueryScore, float valSrcScore)
                    throws IOException {
    	Document docAtHand = atomicReader.document(doc);
    	String[] itemOrigin = docAtHand.getValues("originOfItem");
    	boolean originIndia = false;
    	for (int counter=0; counter<itemOrigin.length; counter++) {
    		if (itemOrigin[counter] != null && 
    			itemOrigin[counter].equalsIgnoreCase("India")) {
    			originIndia = true;
    			break;
    		}
    	}
    	if (originIndia) {
    		return 3.0f;
    	} else {
    		return 1.0f;
    	}
    	
    }

}
