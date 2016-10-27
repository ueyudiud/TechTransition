package ttr.api.util;

public interface ISubTagContainer
{
	void add(SubTag...tags);
	
	boolean contain(SubTag tag);
}