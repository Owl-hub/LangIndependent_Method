import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Cvalue implements IAspectCandidateWeighter
{

	private int pSet(Aspect w, ArrayList<Aspect> aspects,
			Collection<PreprocessedReview> corpus)
	{

		int p = 0;
		ArrayList<Aspect> Tw = new ArrayList<Aspect>();
		for (Aspect asp : aspects)
		{
			if (asp.getNameAsString().contains(w.getNameAsString()))
			{
				p++;
				Tw.add(asp);
			}
		}
		return p;
	}

	private int freqSum(Aspect w, ArrayList<Aspect> aspects,
			Collection<PreprocessedReview> corpus)
	{

		ArrayList<Aspect> Tw = new ArrayList<Aspect>();
		for (Aspect asp : aspects)
		{
			if (asp.getName().containsAll(w.getName()))
			{
				Tw.add(asp);
			}
		}

		int sum = 0;
		for (Aspect b : Tw)
		{
			sum += Frequency.getFrequency(corpus, b);
		}

		return sum;
	}

	private static double log2(double a)
	{
		return Math.log(a) / Math.log(2);
	}

	@Override
	public ArrayList<Pair<Aspect, Double>> weight(
			Collection<PreprocessedReview> corpus, ArrayList<Aspect> aspects)
	{

		ArrayList<Pair<Aspect, Double>> pairs = new ArrayList<Pair<Aspect, Double>>();

		for (Aspect asp : aspects)
		{
			int asp_len = asp.getNameAsString().length();
			double c_val = 0;
			if (pSet(asp, aspects, corpus) == 0)
			{
				c_val = log2(asp_len) * Frequency.getFrequency(corpus, asp);
			} else
			{
				c_val = log2(asp_len)
						* (Frequency.getFrequency(corpus, asp) - freqSum(asp,
								aspects, corpus)
								* (1 / pSet(asp, aspects, corpus)));
			}
			pairs.add(new Pair(asp, c_val));
		}

		Collections.sort(pairs, new Comparator<Pair<Aspect, Double>>()
		{
			public int compare(Pair<Aspect, Double> a, Pair<Aspect, Double> b)
			{
				return b.second.compareTo(a.second);
			}
		});

		for (int i = 0; i < pairs.size(); i++)
		{
		}

		return pairs;
	}

}
