package pundle.datastructure;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import pundle.hash.Hashes;

public class ConstantBucketHashTable {
	List<TableEntry>[] objects;
	private int numBits;
	private int size;
	private int collisions;
	private int longestChain;
	private int frozenOffset;
	private int capacity;

	@SuppressWarnings("unchecked")
	ConstantBucketHashTable(int numBits) {
		if (numBits <= 0 || numBits > 31) {
			throw new IllegalArgumentException();
		}
		this.numBits = numBits;
		capacity = 1 << numBits;
		objects = new List[capacity];
		size = 0;
		collisions = 0;
		longestChain = 0;
		frozenOffset = -1;
	}

	String put(String key, String value) {
		if (key == null || value == null) {
			throw new IllegalArgumentException();
		}
		TableEntry entry = new TableEntry(key, value);

		int hash = Hashes.hash(key, 1, numBits)[0];

		if (objects[hash] == null) {

			objects[hash] = new LinkedList<>();
			objects[hash].add(entry);
			size++;

			return null;
		} else {
			for (TableEntry curEntry : objects[hash]) {
				if (key.equals(curEntry.key)) {
					String origVal = curEntry.value;
					curEntry.value = value;

					return origVal;
				}
			}
			objects[hash].add(entry);
			size++;
			collisions++;
			if (objects[hash].size() > longestChain) {
				longestChain = objects[hash].size();
			}

		}
		return null;
	}

	String delete(String key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		int hash = Hashes.hash(key, 1, numBits)[0];

		if (objects[hash] == null) {
			return null;
		} else {
			for (int i = 0; i < objects[hash].size(); i++) {
				TableEntry curEntry = objects[hash].get(i);
				if (key.equals(curEntry.key)) {
					String val = curEntry.value;
					objects[hash].remove(i);
					size--;
					if (objects[hash].isEmpty()) {
						objects[hash] = null;
					}
					return val;
				}
			}
		}
		return null;
	}

	String get(String key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		int hash = Hashes.hash(key, 1, numBits)[0];

		if (objects[hash] == null) {
			return null;
		} else {
			for (TableEntry curEntry : objects[hash]) {
				if (key.equals(curEntry.key)) {
					return curEntry.value;
				}
			}
		}
		return null;
	}

	int size() {
		return size;
	}

	int longestChain() {
		return longestChain;
	}

	int collisions() {
		return collisions;
	}

	void freeze() {
		frozenOffset = 0;
	}

	boolean isFrozen() {
		return frozenOffset >= 0;
	}

	int capacity() {
		return capacity;
	}

	String nextKey() {
		if (!isFrozen()) {
			throw new IllegalStateException();
		}
		List<TableEntry> bucket = null;
		while (bucket == null || (bucket.isEmpty() && frozenOffset < capacity)) {
			bucket = objects[frozenOffset];
			if(bucket == null || bucket.isEmpty()) {
				frozenOffset++;
			}
		}
		return bucket.get(0).key;
	}

	private static final class TableEntry {
		String key;
		String value;

		TableEntry(String key, String value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TableEntry other = (TableEntry) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

	}

	public static void main(String[] args) {
		ConstantBucketHashTable hash = new ConstantBucketHashTable(10);
		long time;

		HashMap other = new HashMap<>();
		time = System.currentTimeMillis();
		for (int i = 0; i < 300000; i++) {
			other.put("akshay" + i % 10000, "pundle" + i % 10000);
			other.get("akshay" + i % 10000);
			other.remove("akshay" + i % 10000);


		}
		System.out.println((System.currentTimeMillis() - time));

		time = System.currentTimeMillis();
		for (int i = 0; i < 300000; i++) {
			hash.put("akshay" + i % 100000, "pundle" + i % 100000);
			hash.get("akshay" + i % 10000);
			hash.delete("akshay" + i % 100000);

		}

		System.out.println((System.currentTimeMillis() - time));
		System.out.println(hash.size);
	}
}
