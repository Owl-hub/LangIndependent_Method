import java.util.ArrayList;

public interface IAspectCandidateFilter
{
	public ArrayList<Aspect> filter(ArrayList<Aspect> aspects,
			ArrayList<Aspect> extraWords);
}
