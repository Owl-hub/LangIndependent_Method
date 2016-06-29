import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class AspectExtractor implements IAspectExtractor
{

	public ArrayList<Aspect> getFirstN(ArrayList<Aspect> aspects, int n)
	{
		ArrayList<Aspect> tmp = new ArrayList<Aspect>();
		for (int i = 0; i < n; i++)
		{
			tmp.add(aspects.get(i));
		}
		return tmp;
	}

	private ArrayList<Aspect> removeEquals(ArrayList<Aspect> aspects)
	{
		ArrayList<Aspect> noneEqual = new ArrayList<Aspect>();
		noneEqual.addAll(aspects);
		for (Aspect asp : aspects)
		{
			if (asp.getName().size() > 1)
			{
				for (int i = 0; i < asp.getName().size(); i++)
				{
					for (Aspect asp2 : aspects)
					{
						if (asp2.getName(0).equals(asp.getName(i)))
						{
							noneEqual.remove(asp2);
							break;
						}
					}
				}
			}
		}

		return noneEqual;
	}

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

	public static ArrayList<Pair<Aspect, Double>> sort(
			ArrayList<Pair<Aspect, Double>> pairs)
	{

		Collections.sort(pairs, new Comparator<Pair<Aspect, Double>>()
		{
			public int compare(Pair<Aspect, Double> a, Pair<Aspect, Double> b)
			{
				return b.second.compareTo(a.second);
			}
		});
		return pairs;
	}

	private static ArrayList<Aspect> sortbyFreq(ArrayList<Aspect> aspects,
			Collection<PreprocessedReview> corpus)
	{

		ArrayList<Pair<Aspect, Double>> pairs = new ArrayList<Pair<Aspect, Double>>();
		for (Aspect asp : aspects)
		{
			pairs.add(new Pair<Aspect, Double>(asp, getFrequency(asp, corpus)));
		}

		Collections.sort(pairs, new Comparator<Pair<Aspect, Double>>()
		{
			public int compare(Pair<Aspect, Double> a, Pair<Aspect, Double> b)
			{
				return a.second.compareTo(b.second);
			}
		});

		ArrayList<Aspect> sortAspects = new ArrayList<Aspect>();
		for (int i = pairs.size() - 1; i > -1; i--)
		{
			sortAspects.add(pairs.get(i).first);
		}

		for (int i = 0; i < sortAspects.size(); i++)
		{
			// System.out.println(sortAspects.get(i).getNameAsString() + " " +
			// pairs.get(sortAspects.size()-1-i).second);
		}

		return sortAspects;

	}

	private static ArrayList<Aspect> sort(ArrayList<Aspect> aspects,
			Collection<PreprocessedReview> corpus)
	{

		ArrayList<Pair<Aspect, Double>> pairs = new ArrayList<Pair<Aspect, Double>>();
		for (Aspect asp : aspects)
		{
			pairs.add(new Pair<Aspect, Double>(asp, getFrequency(asp, corpus)));
		}

		Collections.sort(pairs, new Comparator<Pair<Aspect, Double>>()
		{
			public int compare(Pair<Aspect, Double> a, Pair<Aspect, Double> b)
			{
				return a.second.compareTo(b.second);
			}
		});

		ArrayList<Aspect> sortAspects = new ArrayList<Aspect>();
		for (int i = pairs.size() - 1; i > -1; i--)
		{
			sortAspects.add(pairs.get(i).first);
		}

		return sortAspects;

	}

	public static void print(ArrayList<Aspect> aspects)
	{
		for (Aspect asp : aspects)
		{
			System.out.println(asp.getNameAsString());
		}
		System.out.println();
	}

	public static void print(Aspect asp)
	{
		System.out.println(asp.getNameAsString());
	}

	public ArrayList<Aspect> extract(Corpus corpus, Data data)
			throws IOException
	{
		ArrayList<Aspect> aspects = new ArrayList<Aspect>();

		// Шаг 1. Предобработка
		IAspectCandidateExtractor ne = new NounExtractor();
		aspects = ne.extract(corpus.getReviews());
		System.out.println("<---------->");

		IAspectCandidateExtractor nge = new NPhrasesExtractor();
		aspects.addAll(nge.extract(corpus.getReviews()));

		aspects = new ArrayList<Aspect>(new HashSet<Aspect>(aspects)); // Убрать
																		// повторяющиеся

		ArrayList<Aspect> tmp = new ArrayList<Aspect>();
		tmp.addAll(aspects);
		aspects.clear();
		aspects.addAll(sortbyFreq(tmp, corpus.getReviews()));
		System.out.println("Для 20 существительных по частоте появления:");
		 print(getFirstN(aspects, 20));
		Efficiency prf = new Efficiency(getFirstN(aspects, 20), data);
		System.out.println();

		// Шаг 2. Алгоритм загрузки
		IAspectCandidateWeighter acw = new Cvalue();
		ArrayList<Pair<Aspect, Double>> pairs = new ArrayList<Pair<Aspect, Double>>();
		ArrayList<Aspect> cvalAsp = new ArrayList<Aspect>();
		pairs.addAll(acw.weight(corpus.getReviews(), aspects));
		for (int j = 0; j < pairs.size(); j++)
		{
			cvalAsp.add(pairs.get(j).first);
		}
		System.out.println("Для первых N по C-value:");
//		Для русского N=18, 10
		int N = 10;
		prf = new Efficiency(getFirstN(cvalAsp, 10), data);
		print(getFirstN(cvalAsp, 18));

		IUnsupervizedMethod unsm = new Bootstrapping();
		ArrayList<Aspect> seeds = new ArrayList<Aspect>();
		ArrayList<Aspect> bootsAsp = new ArrayList<Aspect>();
		seeds.addAll(getFirstN(cvalAsp, N));

		bootsAsp.addAll(unsm.run(seeds,
				new ArrayList<Aspect>(cvalAsp.subList(N+1, cvalAsp.size())),
				corpus.getReviews()));
		// System.out.println("============");
		// print(bootsAsp);
		// System.out.println("============");
		 for (int i = 10; i < 30; i++)
		 {
			 prf = new Efficiency(new ArrayList<Aspect>(bootsAsp.subList(0, i)),
					 data);
			 System.out.println();
		 }
		 System.out.println("Число характеристик: " + bootsAsp.size());
		 System.out.println();

		// Шаг 3. Удаление избыточных слов

		IAspectCandidateFilter acf = new PrunningFilter();
		ArrayList<Aspect> pruningAsp = new ArrayList<Aspect>();
		pruningAsp.addAll(acf.filter(bootsAsp, data.getExtraWords()));
		System.out.println("vvvvvvvvvvvv");
		// print(pruningAsp);
		// print(new ArrayList<Aspect>(pruneAsp.subList(0, 14)));

		for (int i = 1; i < pruningAsp.size(); i++)
		{
			prf = new Efficiency(getFirstN(pruningAsp, i), data);
			System.out.println();
		}

		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		return aspects;
	}

}
