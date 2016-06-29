import java.util.ArrayList;
import java.util.Collection;

public interface IAspectCandidateExtractor
{
	public ArrayList<Aspect> extract(Collection<PreprocessedReview> corpus);

}
