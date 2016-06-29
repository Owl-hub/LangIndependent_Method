import java.util.ArrayList;
import java.util.List;

import ru.ispras.texterra.core.nlp.datamodel.pos.IPOSTag;

public class PreprocessedReview
{
	private List<String> words;
	private List<IPOSTag> tags;

	public List<String> getWords()
	{
		return words;
	}

	public void setWords(List<String> words)
	{
		this.words = words;
	}

	public void setWords(int i, String word)
	{
		this.words.set(i, word);
	}

	public List<IPOSTag> getTags()
	{
		return tags;
	}

	public void setTags(List<IPOSTag> tags)
	{
		this.tags = tags;
	}

	public void setTags(int i, IPOSTag tag)
	{
		this.tags.set(i, tag);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((words == null) ? 0 : words.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PreprocessedReview other = (PreprocessedReview) obj;
		if (words == null)
		{
			if (other.words != null)
				return false;
		} else if (!words.equals(other.words))
			return false;
		return true;
	}

	public PreprocessedReview(List<String> w, List<IPOSTag> t)
	{
		words = new ArrayList<String>();
		tags = new ArrayList<IPOSTag>();

		words = w;
		tags = t;
	}

}
