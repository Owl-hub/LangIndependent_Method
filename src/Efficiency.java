import java.io.IOException;
import java.util.ArrayList;

public class Efficiency
{
	private static double recall = 0;
	private static double precision = 0;
	private static double fScore = 0;
	private static ArrayList<Aspect> trueAspList = new ArrayList<Aspect>();

	public Efficiency(ArrayList<Aspect> aspects, Data data) throws IOException
	{
		trueAspList.clear();
		trueAspList.addAll(data.getTrueAspects());
		recall = recall(aspects) * 100;
		precision = precision(aspects) * 100;
		fScore = FScore(precision, recall);

		System.out.println(aspects.size()+":  ");
		System.out.printf(" %.2f %.2f %.2f", precision, recall, fScore);
		System.out.println();
//		System.out.println("Точность: " + precision);
//		System.out.println("Полнота: " + recall);
//		System.out.println("F-мера: " + fScore);
//		System.out.printf("  %.2f", fScore);
	}

	private static double recall(ArrayList<Aspect> aspects) throws IOException
	{
		ArrayList<String> trueAsp = new ArrayList<String>();
		for (int i = 0; i < trueAspList.size(); i++)
		{
			trueAsp.add(trueAspList.get(i).getNameAsString());
		}

		int match = 0;
		int tAsp = trueAsp.size();

		for (Aspect asp : aspects)
		{
			if (trueAsp.contains(asp.getNameAsString()))
			{
				match++;
			}
		}
		double res = (double) match / tAsp;
		return res;
	}

	private static double precision(ArrayList<Aspect> aspects)
			throws IOException
	{
		ArrayList<String> trueAsp = new ArrayList<String>();
		for (int i = 0; i < trueAspList.size(); i++)
		{
			trueAsp.add(trueAspList.get(i).getNameAsString());
		}
		int match = 0;
		for (Aspect asp : aspects)
		{
			if (trueAsp.contains(asp.getNameAsString()))
			{
//				System.out.println("Совпали: " + asp.getNameAsString());
				match++;
			}
		}
//		System.out.println("precision match: " + match);
		double res = (double) match / aspects.size();
		return res;
	}

	private static double FScore(double p, double r)
	{
		double res;
		res = (double) (2 * p * r) / (p + r);
		return res;
	}

}
