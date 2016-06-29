import java.util.ArrayList;
import java.util.Collection;

public interface IAspectCandidateWeighter
{

	public ArrayList<Pair<Aspect, Double>> weight(
			Collection<PreprocessedReview> corpus, ArrayList<Aspect> aspects);
}
