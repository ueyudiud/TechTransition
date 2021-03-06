package ttr.api.collection;

public class Stack<E>
{
	public E element;
	public long size;

	public Stack(E element)
	{
		this(element, 1L);
	}
	public Stack(E element, long size)
	{
		this.element = element;
		this.size = size;
	}
	
	public boolean same(Stack stack)
	{
		return stack.element.equals(element);
	}
	
	public Stack<E> split(long size)
	{
		Stack<E> stack = new Stack<E>(element, size);
		this.size -= size;
		return stack;
	}
	
	@Override
	public Stack<E> clone()
	{
		return new Stack<E>(element, size);
	}
	
	@Override
	public String toString()
	{
		return "(" + element + ")x" + size;
	}
	
	@Override
	public int hashCode()
	{
		return element.hashCode() * 31 + Long.hashCode(size);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj == this ? true :
			!(obj instanceof Stack) ? false :
				((Stack) obj).element.equals(element) && ((Stack) obj).size == size;
	}
}