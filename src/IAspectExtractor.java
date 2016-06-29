import java.io.IOException;
import java.util.ArrayList;

public interface IAspectExtractor
{
	public ArrayList<Aspect> extract(Corpus corpus, Data data)
			throws IOException;

}
