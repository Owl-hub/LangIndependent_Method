import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Corpus
{
	private ArrayList<PreprocessedReview> reviews = new ArrayList<PreprocessedReview>();
	private ArrayList<Review> collection = new ArrayList<Review>();
	private String filename = "";

	public ArrayList<PreprocessedReview> getReviews()
	{
		return reviews;
	}

	public ArrayList<Review> getCollection()
	{
		return collection;
	}

	public Corpus(String nameOfProduct)
	{
		try
		{
			filename = nameOfProduct.concat(".txt");
			System.out.println(filename);
			load(filename);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		IReviewPreprocessor rp = new TexterraBased();

		try
		{
			reviews = rp.process(collection);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void load(String filename) throws IOException,
			FileNotFoundException
	{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		int counter = 0;
		while (br.ready() && (counter < 40))
		{ // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			String temp = br.readLine();
			if (!temp.equals("#"))
			{
				collection.add(new Review(temp));
				counter++;
			}
		}
		br.close();
	}
}
