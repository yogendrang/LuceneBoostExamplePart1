package com.imaginea.scoring.customscorequery;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.CustomScoreProvider;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.search.Query;


public class ImagineaDemoCustomScoreQuery extends CustomScoreQuery {

	public ImagineaDemoCustomScoreQuery(Query subQuery) {
		super(subQuery);
	} 
	
	 @Override
	    public CustomScoreProvider getCustomScoreProvider(final AtomicReaderContext atomicContext) {
		 return new ImagineaDemoCustomScoreProvider(atomicContext);
	 }	 

}
