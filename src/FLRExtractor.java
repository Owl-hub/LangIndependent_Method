import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.math.*;


public class FLRExtractor implements IAspectCandidateWeighter {
	
	private static double getFrequency(Aspect asp, Collection<PreprocessedReview> reviews) {
		
		int counter = 0;
		for (PreprocessedReview review : reviews) {
			if (review.getWords().containsAll(asp.getName())) {
				counter++;
			}
		}
//		System.out.println("Freq: " + counter);
		return counter;
		
	}
	
	private static double lr(Aspect asp, int i) {
		
		int l = i;
		int r = asp.getName().size() - i;
		double res = Math.sqrt(l*r);
		
		return res;
	}
	
	
	private static double getLR(Aspect asp, Collection<PreprocessedReview> reviews) {
		
		double LR = 0;
		for (int i=0; i<asp.getName().size(); i++) {
			LR *= lr(asp, i);
		}
		LR = Math.pow(LR, 1/((double) asp.getName().size()));
		
		return LR;
		
	}

	private static double getFLR(Aspect asp, Collection<PreprocessedReview> reviews) {
		
		double f = getFrequency(asp, reviews);
		double LR = getLR(asp, reviews);
		double FLR = f * LR;
		
		return FLR;
		
		
	}
	
	@Override
	public ArrayList<Pair<Aspect, Double>> weight(
			Collection<PreprocessedReview> reviews, ArrayList<Aspect> aspects) {
		ArrayList< Pair<Aspect, Double> > pairs = new ArrayList<Pair<Aspect, Double>>();
		
		for (Aspect asp : aspects) {
			if (asp.getName().size() > 1) {
				double FLR = getFLR(asp, reviews);
				pairs.add(new Pair<Aspect, Double> (asp, FLR));
//				System.out.println(asp.getNameAsString() + " " + FLR);
			}
		}
		
	    Collections.sort(pairs, new Comparator<Pair<Aspect, Double>>() {
	    	public int compare(Pair<Aspect, Double> a, Pair<Aspect, Double> b)
	    	{
	    		return a.second.compareTo(b.second);
	    	}
	    }); 
	    
		for (int i=0; i<pairs.size(); i++) {
			System.out.println(pairs.get(i).getFirst().getNameAsString()+
					"; FLR: "+pairs.get(i).getSecond()); 
		}	
		
//	    System.out.println();
		
		return pairs;
	}

}
