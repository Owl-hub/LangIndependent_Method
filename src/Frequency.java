import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Frequency implements IAspectCandidateWeighter {
	
	public static int getFrequency(Collection<PreprocessedReview> corpus, Aspect aspect) {
		int counter = 0;
		for (PreprocessedReview review : corpus) {
			if (review.getWords().containsAll(aspect.getName())) {
				counter++;
			}
		}
		return counter;
	}

	@Override
	public ArrayList<Pair<Aspect, Double>> weight(
			Collection<PreprocessedReview> corpus, ArrayList<Aspect> aspects) {
		
		Map map = new HashMap<Aspect, Double>();
		for (Aspect asp : aspects) {
			int counter = 0;
			for (PreprocessedReview review : corpus) {
				if (review.getWords().containsAll(asp.getName())) {
					counter++;
				}
			}
			map.put(asp, counter);
		}
		
		return null;
	}

}
