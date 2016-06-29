import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public interface IReviewPreprocessor
{
	public ArrayList<PreprocessedReview> process(Collection<Review> collect)
			throws IOException;
}
