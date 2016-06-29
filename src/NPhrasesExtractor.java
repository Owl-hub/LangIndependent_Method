import java.util.ArrayList;
import java.util.Collection;

import ru.ispras.texterra.core.nlp.datamodel.pos.IPOSTag;

public class NPhrasesExtractor implements IAspectCandidateExtractor
{

	public ArrayList<Aspect> extract(Collection<PreprocessedReview> corpus)
	{
		ArrayList<Aspect> aspects = new ArrayList<Aspect>();
		int flag = 0;

		for (PreprocessedReview pr : corpus)
		{
			// String asp = new String();
			flag = 0;
			ArrayList<String> str = new ArrayList<String>();
			ArrayList<IPOSTag> pos = new ArrayList<IPOSTag>();
			for (int i = 0; i < pr.getWords().size(); i++)
			{
				if (pr.getTags().get(i).isNoun())
				{
					int start = i;
					i++;
					while ((i < pr.getWords().size())
							&& (pr.getTags().get(i).isNoun()))
					{
						i++;
					}
					for (int j = start; j < i; j++)
					{
						str.add(pr.getWords().get(j));
						pos.add(pr.getTags().get(j));
					}
					if (str.size() > 1)
					{
						aspects.add(new Aspect(str, pos));
						// System.out.println("NN: "+str.toString());
					}
				}
				str = new ArrayList<String>();
				pos = new ArrayList<IPOSTag>();
			}
		}

		ArrayList<Aspect> tmp = new ArrayList<Aspect>();
		tmp.addAll(aspects);

		for (Aspect asp : aspects)
		{
			for (int i = 0; i < asp.getName().size(); i++)
			{
				if (asp.getName(i).length() < 5)
				{
					tmp.remove(asp);
					break;
				}
			}
		}
		aspects.clear();
		aspects.addAll(tmp);

		return aspects;
	}

}
