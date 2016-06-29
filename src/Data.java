import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import ru.ispras.texterra.core.nlp.datamodel.pos.IPOSTag;

import com.javanetworkframework.rb.util.AbstractWebTranslator;

public class Data
{
	private static String trueAspectsPath = "";
	private static String extraWordsPath = "Pruning.txt";
	private static ArrayList<Aspect> trueAspects = new ArrayList<Aspect>();
	private static ArrayList<Aspect> extraWords = new ArrayList<Aspect>();

	public Data(String nameOfProduct)
	{
		trueAspectsPath = nameOfProduct.concat("_true.txt");
		try
		{
			trueAspects.addAll(calculateTrueAspects());
			extraWords.addAll(readExtraWords());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public ArrayList<Aspect> getTrueAspects()
	{
		return trueAspects;
	}

	public ArrayList<Aspect> getExtraWords()
	{
		return extraWords;
	}

	private ArrayList<Aspect> readExtraWords() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(extraWordsPath));
		ArrayList<Aspect> extraWordsList = new ArrayList<Aspect>();

		while (br.ready())
		{
			String str = br.readLine();
			String[] strArray = str.split(" ");
			ArrayList<String> tmp = new ArrayList<String>();
			for (int i = 0; i < strArray.length; i++)
			{
				tmp.add(strArray[i]);
			}
			extraWordsList.add(new Aspect(tmp, new ArrayList<IPOSTag>()));
		}

		br.close();

		// AspectExtractor.print(extraWordsList);

		// if (!extraWordsPath.contains("_en")) {
		// return translate(pruneWordsList);
		// } else
		return extraWordsList;
	}

	private ArrayList<Aspect> calculateTrueAspects() throws IOException
	{

		BufferedReader br = new BufferedReader(new FileReader(trueAspectsPath));
		ArrayList<Aspect> trueAsp = new ArrayList<Aspect>();

		while (br.ready())
		{
			String str = br.readLine();
			String[] strArray = str.split(" ");
			ArrayList<String> tmp = new ArrayList<String>();
			for (int i = 0; i < strArray.length; i++)
			{
				tmp.add(strArray[i]);
			}
			trueAsp.add(new Aspect(tmp, new ArrayList<IPOSTag>()));
		}

		br.close();

		return trueAsp;
	}

	private ArrayList<Aspect> translate(ArrayList<Aspect> words)
			throws UnsupportedEncodingException
	{
		ArrayList<Aspect> translateWords = new ArrayList<Aspect>();
		Locale srcLoc = new Locale("en");
		Locale dstLoc = new Locale("ru");

		AbstractWebTranslator res = (AbstractWebTranslator)

		ResourceBundle.getBundle(
				"com.javanetworkframework.rb.webtranslator.WebTranslator",
				dstLoc);
		for (Aspect asp : words)
		{
			String str = res.getString(asp.getNameAsString(), srcLoc);
			String strB = new String(str.getBytes("ISO-8859-1"), "CP1251");
			translateWords.add(new Aspect(strB, asp.getTag()));
		}

		return null;
	}
}
