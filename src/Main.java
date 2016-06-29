import java.io.IOException;
import java.util.ArrayList;

public class Main
{

	public static void main(String[] args) throws IOException
	{
		String nameOfProduct = "Rest";
//		cameras == cars, mp3 == restaurants
		Corpus corpus = new Corpus(nameOfProduct);
		Data data = new Data(nameOfProduct);

		IAspectExtractor ae = new AspectExtractor();
		ArrayList<Aspect> aspects = ae.extract(corpus, data);

	}
}
