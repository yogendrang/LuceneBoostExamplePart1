package com.imaginea.scoring;

import java.util.ArrayList;

import com.imaginea.boost.AbstractBoostType;
import com.imaginea.boost.BoostAtIndexingImpl;

public class ScoringExamples {
	public static void main(String args[]) {
		ArrayList<AbstractScoreType> scoreTypes = new ArrayList<AbstractScoreType>();
		if (args.length == 0) {
			printProgramUsage();
		} else {
			String boostType = args[0];
			boolean printExplanation = false; 
			if (args.length != 1 && "true".equalsIgnoreCase(args[1])) {
				printExplanation = true;
			}
			switch (boostType) {
			case "customscorequery": scoreTypes.add(new CustomScoreQueryProviderImpl(printExplanation)); break;
			default: System.out.println("Invalid scoring Option"); printProgramUsage();
			         System.exit(1);
			}
			
			for (AbstractScoreType boostTypeAtHand : scoreTypes) {
				boostTypeAtHand.populateIndex();
				boostTypeAtHand.demoCustomScoring();
			}
			
			
		}
	}
	
	private static void printProgramUsage() {
		System.out.println("--------------------------");
		System.out.println("Program Usage: ");
		System.out.println("--------------------------");
		System.out.println("Param 1: Type of scoring: ");
		System.out.println("customscorequery -- Custom Score Query and Custom Score Provider Demo ");
		System.out.println("--------------------------");
	}


}
