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

public class LemmatizationBased implements IReviewPreprocessor{

	private final static String key = "70ccd21d6a1ccb65266029fcec0cca11026cc006";
	
	public ArrayList<PreprocessedReview> process(Collection<Review> collect) {
//		String text = r.getText();
		String text = "";
		for (Review r : collect) {
			text = text.concat(" ").concat(r.getText()).concat("\n");
		}
		List<String> words = new ArrayList<String>();
		List<IPOSTag> tags = new ArrayList<IPOSTag>();
		ArrayList<PreprocessedReview> pr = new ArrayList<PreprocessedReview>();
		
		IModisAPIClient client = new JAXRSClient(key);

		INLPDocument doc = client.createDocument(text,
				new InstanceDescriptor<IPipeline>(LemmatizationPipeline.class),
				Options.make(Options.FilteringOptions.KEEPING, Lemma.class));
		for (Lemma lemma : doc.getAnnotations(Lemma.class)) {
			if (!lemma.getText().equals("#")) {
				words.add(lemma.getText());
				System.out.println(lemma.getText() + " [" + lemma.getStart()
						+ "; " + lemma.getEnd() + "] -> " + lemma.getValue());
			} else {
				pr.add(new PreprocessedReview(words, tags));
				for (int i=0; i<words.size(); i++) {
				}
				words = new ArrayList<String>();
				tags = new ArrayList<IPOSTag>();
			}
		}
		return pr;
	}
}
