import java.util.ArrayList;
import java.util.Collection;

public class Bootstrapping implements IUnsupervizedMethod
{

	private static double getFrequency(Aspect asp,
			Collection<PreprocessedReview> corpus)
	{

		int counter = 0;
		for (PreprocessedReview review : corpus)
		{
			if (review.getWords().containsAll(asp.getName()))
			{
				counter++;
			}
		}
		return counter;

	}

	private static double getFrequency(Aspect asp, Aspect b,
			Collection<PreprocessedReview> corpus)
	{

		int counter = 0;
		for (PreprocessedReview review : corpus)
		{
			if (review.getWords().containsAll(asp.getName())
					& review.getWords().containsAll(b.getName()))
			{
				counter++;
			}
		}
		// System.out.println("Freq: " + counter);
		return counter;

	}

	private static double log2(double a)
	{
		return Math.log(a) / Math.log(2);
	}

	private static double getAScore(Aspect w, ArrayList<Aspect> aspects,
			ArrayList<Aspect> seeds, ArrayList<PreprocessedReview> corpus)
	{

		double aScore = 0;
		// System.out.println("w: "+w.getNameAsString());
		for (Aspect a : seeds)
		{
			if (!a.getName().equals(w.getName()))
			{
				double inner = getFrequency(w, a, corpus)
						/ (getFrequency(w, corpus) * getFrequency(a, corpus));
				inner = inner * corpus.size() + 1;
				aScore += log2(inner);
			} else
			{
				// System.out.println("No: "+w.getNameAsString());
			}
		}
		aScore *= getFrequency(w, corpus);
		return aScore;
	}

	private ArrayList<Aspect> setSeeds(ArrayList<Aspect> seeds)
	{
		ArrayList<Aspect> tmp = new ArrayList<Aspect>();
		tmp.addAll(seeds);
		for (Aspect asp : seeds)
		{
			if (asp.getName().size() > 1)
			{
				for (Aspect asp2 : seeds)
				{
					if ((asp2.getName().size() == 1)
							&& (asp2.getName(0).equals(asp.getName(0)) || asp2
									.getName(0).equals(asp.getName(1))))
					{
						tmp.remove(asp2);
						System.out.println("remove: " + asp2.getNameAsString());
					}
				}
			}
		}
		return tmp;
	}

	@Override
	public ArrayList<Aspect> run(ArrayList<Aspect> seeds,
			ArrayList<Aspect> aspects, ArrayList<PreprocessedReview> corpus)
	{
		double aScore = 0;
		ArrayList<Pair<Aspect, Double>> pairs = new ArrayList<Pair<Aspect, Double>>();
		ArrayList<Aspect> tmp = new ArrayList<Aspect>();
		for (int i = 0; i < 30; i++)
		{
			for (Aspect asp : aspects)
			{
				aScore = getAScore(asp, aspects, seeds, corpus);
				pairs.add(new Pair(asp, aScore));
			}
			AspectExtractor.sort(pairs);
			tmp.clear();
			for (int k = 0; k < pairs.size(); k++)
			{
				tmp.add(pairs.get(k).first);
			}
			tmp.removeAll(seeds);
			System.out.println("First: " + tmp.get(0).getNameAsString());
			seeds.add(tmp.get(0));
			pairs.clear();
		}

		tmp.clear();
		tmp.addAll(setSeeds(seeds));
		seeds.clear();
		seeds.addAll(tmp);
		System.out.println("-------------------------");
		for (Aspect asp : seeds)
		{
			System.out.println(asp.getNameAsString());
		}
		return seeds;
	}

}
