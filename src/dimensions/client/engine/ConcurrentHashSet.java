package dimensions.client.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashSet<E> implements Set<E>
{
	private final Set<E> set;
	
	public ConcurrentHashSet()
	{
		 set = Collections.newSetFromMap(new ConcurrentHashMap<E, Boolean>());
	}
	
	public ConcurrentHashSet(int initialCapacity)
	{
		set = Collections.newSetFromMap(new ConcurrentHashMap<E, Boolean>(initialCapacity));
	}
	
	public ConcurrentHashSet(int initialCapacity, float loadFactor)
	{
		set = Collections.newSetFromMap(new ConcurrentHashMap<E, Boolean>(initialCapacity, loadFactor));
	}

	@Override
	public boolean add(E e)
	{
		return set.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		return set.addAll(c);
	}

	@Override
	public void clear()
	{
		set.clear();
	}

	@Override
	public boolean contains(Object o)
	{
		return set.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return c.containsAll(c);
	}

	@Override
	public boolean isEmpty()
	{
		return set.isEmpty();
	}

	@Override
	public Iterator<E> iterator()
	{
		return set.iterator();
	}

	@Override
	public boolean remove(Object o)
	{
		return set.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		return set.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		return set.retainAll(c);
	}

	@Override
	public int size()
	{
		return set.size();
	}

	@Override
	public Object[] toArray()
	{
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return set.toArray(a);
	}

}
