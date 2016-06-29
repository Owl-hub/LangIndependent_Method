import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.ispras.modisapi.IModisAPIClient;
import ru.ispras.modisapi.JAXRSClient;
import ru.ispras.modisapi.urlmodel.InstanceDescriptor;
import ru.ispras.texterra.core.nlp.ITexterraNLP.Options;
import ru.ispras.texterra.core.nlp.datamodel.INLPDocument;
import ru.ispras.texterra.core.nlp.datamodel.Lemma;
import ru.ispras.texterra.core.nlp.datamodel.pos.IPOSTag;
import ru.ispras.texterra.core.nlp.datamodel.pos.POSToken;
import ru.ispras.texterra.core.nlp.pipelines.IPipeline;
import ru.ispras.texterra.core.nlp.pipelines.LemmatizationPipeline;
import ru.ispras.texterra.core.nlp.pipelines.POSTaggingPipeline;

public class TexterraBased implements IReviewPreprocessor
{

	private final static String key = "70ccd21d6a1ccb65266029fcec0cca11026cc006";

	public ArrayList<PreprocessedReview> lemmatization(
			Collection<PreprocessedReview> col, String text)
	{

		ArrayList<PreprocessedReview> collect = new ArrayList<PreprocessedReview>();
		collect.addAll(col);
		// text = text.replaceAll("#", " ");

		IModisAPIClient client = new JAXRSClient(key);

		INLPDocument doc = client.createDocument(text,
				new InstanceDescriptor<IPipeline>(LemmatizationPipeline.class),
				Options.make(Options.FilteringOptions.KEEPING, Lemma.class));
		int start = 0;
		int end = -1;
		for (PreprocessedReview pr : collect)
		{
			start = end + 1;
			end = start + pr.getWords().size();
			// System.out.println("Size: " + pr.getWords().size());
			// System.out.println("Start: " + pr.getWords().get(start) +
			// " ; end: " + pr.getWords().get(end));

			for (int i = start; i < end; i++)
			{
				Lemma lemma = doc.getAnnotations(Lemma.class).get(i + 1);
				if (!lemma.getText().equals("#"))
				{
					if (!pr.getTags().get(i - start).isPunctuation())
					{
						// System.out.println(pr.getWords().get(i-start) +
						// " --> " + lemma.getValue());
						pr.setWords(i - start, lemma.getValue());
					}
				}
			}
			// for (String s : pr.getWords()) {
			// System.out.print(s + "_");
			// }
			// System.out.println();
		}
		return collect;
	}

	public ArrayList<PreprocessedReview> process(Collection<Review> collect)
	{
		// String text = r.getText();
		String text = "";
		for (Review r : collect)
		{
			text = text.concat(" ").concat(r.getText()).concat("\n");
		}
		List<String> words = new ArrayList<String>();
		List<IPOSTag> tags = new ArrayList<IPOSTag>();
		ArrayList<PreprocessedReview> pr = new ArrayList<PreprocessedReview>();

		IModisAPIClient client = new JAXRSClient(key);

		INLPDocument doc = client.createDocument(text,
				new InstanceDescriptor<IPipeline>(POSTaggingPipeline.class),
				Options.make(Options.FilteringOptions.KEEPING, POSToken.class));

		for (POSToken posToken : doc.getAnnotations(POSToken.class))
		{
			if (!posToken.getText().equals("#"))
			{
				words.add(posToken.getText().toLowerCase());
				tags.add(posToken.getValue());
				// System.out.println(posToken.getText().toLowerCase() + " -> "
				// + posToken.getValue());
			} else
			{
				if (!words.isEmpty())
				{
					pr.add(new PreprocessedReview(words, tags));
				}
				words = new ArrayList<String>();
				tags = new ArrayList<IPOSTag>();
			}
		}
		ArrayList<PreprocessedReview> lem = new ArrayList<PreprocessedReview>();
		lem.addAll(lemmatization(pr, text));

		return lem;
	}
}