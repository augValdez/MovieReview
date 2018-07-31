import java.util.Iterator;
import java.util.ArrayList;

/*
 * Braxden Hanzelka
 * Feb 9, 2018
 * Implementing set class by using an Array
 * Partner Score 100
 */
public class ArraySet<T> implements Set<T> {
	public static final int DEFAULT_CAPACITY = 15;
	public static final int CAPACITY_MULTIPLIER = 2;

	private int capacity = 0;
	private int numberOfItems = 0;
	private T[] items;

	public ArraySet() {
		this(DEFAULT_CAPACITY);
	}

	public ArraySet(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity must be >= 0");
		}

		this.capacity = capacity;
		items = (T[]) new Object[capacity];
	}

	@Override
	public void add(T element) {
		ensureCapacity();
		if (this.contains(element) == true) {
			// returns nothing because there are no duplicates in the set
		} else {
			items[numberOfItems] = element;
			numberOfItems++;
		}
	}

	@Override
	public void addAll(T[] elements) {
		for (int i = 0; i < items.length; i++) {
			this.add(elements[i]);
		}
		numberOfItems++;
	}

	@Override
	public boolean contains(T element) {
		boolean contains = false;

		for (int i = 0; i < numberOfItems; i++) {
			if (items[i].equals(element) == true) {
				contains = true;
			}
		}
		return contains;
	}

	@Override
	public int getSize() {
		return numberOfItems;
	}

	@Override
	public void remove(T element) {
		for (int i = 0; i < numberOfItems; i++) {
			if (items[i].equals(element) == true) {
				items[i] = items[numberOfItems - 1];
				numberOfItems--;
			}
		}
	}

	@Override
	public Set union(Set anotherSet) {

		Set S3 = anotherSet.difference(this);
		// adds all of the elements from set1 to set3
		for (int i = 0; i < numberOfItems; i++) {
			if(!S3.contains(items[i]))
			{
			S3.add(items[i]);
			}
		}
		return S3;
	}

	@Override
	public Set intersection(Set anotherSet) {
		Set S3 = new ArraySet();
		for (int i = 0; i < anotherSet.getSize(); i++) {
			if (anotherSet.contains(items[i]) == true) {
				S3.add(items[i]);
			}
		}

		return S3;
	}

	@Override
	public Set difference(Set anotherSet) {
		Set S3 = new ArraySet();
		for (int i = 0; i < this.getSize(); i++) {
			S3.add(items[i]);
		}

		for (int i = 0; i < anotherSet.getSize(); i++) {
			if (anotherSet.contains(items[i]) == true) {
				S3.remove(items[i]);
			}
		}

		return S3;
	}

	private void ensureCapacity() {
		if (numberOfItems == capacity) {
			T[] newArray = (T[]) new Object[(numberOfItems + 1) * CAPACITY_MULTIPLIER];
			System.arraycopy(items, 0, newArray, 0, numberOfItems);
			items = newArray;
		}
	}

	public Iterator<T> iterator() 
	{
		return new ArraySetIterator();
	}
	
	private class ArraySetIterator implements Iterator<T>
	{
		private int index = 0;
		
		/**
		 * Determines if there are more elements
		 * in the iteration.
		 * 
		 * @return true if there are more elements, false otherwise.
		 */
		public boolean hasNext() {
			if (index < numberOfItems)
				return true;
			else
				return false;
		}

		/**
		 * Returns the next element in the iteration.
		 * 
		 * @throws java.util.NoSuchElementException if there are no more elements in the iteration.
		 */
		public T next() {
			if (hasNext()) {
				T nextItem = items[index];
				index++;
				
				return nextItem;
			}
			else
				throw new java.util.NoSuchElementException("No items remaining in the iteration.");
		}

		/**
		 * The remove() operation is not supported.
		 * @throws UnsupportedOperationException if involed.
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}	
	
	
	
	
	
}
