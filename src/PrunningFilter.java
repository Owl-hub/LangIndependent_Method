import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ru.ispras.texterra.core.nlp.datamodel.pos.IPOSTag;

public class PrunningFilter implements IAspectCandidateFilter
{

	public ArrayList<Aspect> filter(ArrayList<Aspect> aspects,
			ArrayList<Aspect> extraWords)
	{
		return remove(aspects, extraWords);
	}

	private ArrayList<Aspect> remove(ArrayList<Aspect> aspects,
			ArrayList<Aspect> extraWords)
	{
		ArrayList<Aspect> tmp = new ArrayList<Aspect>();
//		System.out.println("ExtraWords: ");
//		AspectExtractor.print(extraWords);
		tmp.addAll(aspects);
		for (Aspect asp : aspects)
		{
			for (Aspect extra : extraWords)
			{
				if (asp.getName(0).equals(extra.getName(0)))
				{
					tmp.remove(asp);
					System.out.println("prun: " + asp.getNameAsString());
				}
			}
		}

		return tmp;
	}

}
