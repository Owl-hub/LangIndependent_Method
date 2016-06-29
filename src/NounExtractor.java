import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.ispras.texterra.core.nlp.datamodel.pos.IPOSTag;

public class NounExtractor implements IAspectCandidateExtractor
{

	public ArrayList<Aspect> extract(Collection<PreprocessedReview> corpus)
	{
		ArrayList<Aspect> aspects = new ArrayList<Aspect>();

		for (PreprocessedReview preproc : corpus)
		{
			List<String> words = preproc.getWords();
			List<IPOSTag> tags = preproc.getTags();
			int i = 0;

			for (String word : words)
			{
				i = words.indexOf(word);
				// Для русского языка:
				if (tags.get(i).isNoun() & (word.length() > 4))
				{
					aspects.add(new Aspect(word, tags.get(words.indexOf(word))));
				}
				// Для англ. языка:
				// if (tags.get(i).isNoun()) {
				// aspects.add(new Aspect(word, tags.get(words.indexOf(word))));
				// }
			}
		}

		return aspects;
	}

}
