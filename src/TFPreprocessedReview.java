import java.util.*;

public class TFPreprocessedReview extends PreprocessedReview
{
	private List<Double> tf;
	private List<Double> freq;
	private double tfMax;

	public List<Double> getFreq()
	{
		return freq;
	}

	public void setFreq(int i, double freq)
	{
		System.out.println(this.freq.get(i) + " " + freq);
		this.freq.set(i, this.freq.get(i) + freq);
	}

	public List<Double> getTf()
	{
		return tf;
	}

	public void setTf(List<Double> tf)
	{
		this.tf = tf;
	}

	public double getTfMax()
	{
		return tfMax;
	}

	public void setTfMax(double tfMax)
	{
		this.tfMax = tfMax;
	}

	public TFPreprocessedReview(PreprocessedReview r, List<Double> fr)
	{
		super(r.getWords(), r.getTags());
		tf = new ArrayList<Double>();
		freq = new ArrayList<Double>();

		tf = fr;
		for (Double a : tf)
		{
			tfMax = Math.max(a, tfMax);
		}
	}

}
