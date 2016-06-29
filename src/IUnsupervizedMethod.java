import java.util.ArrayList;

public interface IUnsupervizedMethod
{
	public ArrayList<Aspect> run(ArrayList<Aspect> seeds,
			ArrayList<Aspect> aspects, ArrayList<PreprocessedReview> corpus);
}
