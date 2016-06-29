import java.util.ArrayList;
import java.util.List;

import ru.ispras.texterra.core.nlp.datamodel.pos.IPOSTag;

public class Aspect
{

	private ArrayList<String> name;
	private ArrayList<IPOSTag> tag;

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aspect other = (Aspect) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName(int i)
	{
		return name.get(i);
	}

	public ArrayList<String> getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name.add(name);
	}

	public void setName(ArrayList<String> name)
	{
		this.name.addAll(name);
	}

	public ArrayList<IPOSTag> getTag()
	{
		return tag;
	}

	public IPOSTag getTag(int i)
	{
		return tag.get(i);
	}

	public void setTag(IPOSTag tag)
	{
		this.tag.add(tag);
	}

	public void setTag(ArrayList<IPOSTag> tag)
	{
		this.tag.addAll(tag);
	}

	public String getNameAsString()
	{
		String str = this.getName().toString().replaceAll(", ", " ")
				.replace('[', ' ').replace(']', ' ').replaceFirst(" ", "");
		return str;
	}

	public Aspect(String s, IPOSTag t)
	{
		name = new ArrayList<String>();
		name.add(s);
		tag = new ArrayList<IPOSTag>();
		tag.add(t);
	}

	public Aspect(ArrayList<String> s, ArrayList<IPOSTag> t)
	{
		name = new ArrayList<String>();
		name.addAll(s);
		tag = new ArrayList<IPOSTag>();
		tag.addAll(t);
	}

	public Aspect(String s, ArrayList<IPOSTag> t)
	{
		name = new ArrayList<String>();
		String[] strArray = s.split(" ");
		for (int i = 0; i < strArray.length; i++)
		{
			name.add(strArray[i]);
		}
		tag = new ArrayList<IPOSTag>();
		tag.addAll(t);
	}
}
