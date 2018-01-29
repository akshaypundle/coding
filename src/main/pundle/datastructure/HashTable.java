package pundle.datastructure;
import java.util.HashMap;

public class HashTable {
	ConstantBucketHashTable h1;
	ConstantBucketHashTable h2;
	boolean migrating;
	int bits;

	public HashTable() {
		bits = 1;
		h1 = new ConstantBucketHashTable(bits);
		h2 = null;
		migrating = false;
	}

	String put(String key, String value) {
		if (h1.get(key) != null) {
			return h1.put(key, value);
		} else if (h2 != null && h2.get(key) != null) {
			return h2.put(key, value);
		}

		if (!migrating && 1.0 * h1.size() / h1.capacity() > 0.7) {
			h1.freeze();
			migrating = true;
		}

		if (migrating) {
			String nextKey = h1.nextKey();
			String nextValue = h1.delete(nextKey);
			
			if (h2 == null) {
				h2 = new ConstantBucketHashTable(bits + 2);
			}
			h2.put(nextKey, nextValue);
			if (h1.size() == 0) {
				h1 = h2;
				bits += 2;
				h2 = null;
				migrating = false;
				return h1.put(key, value);
			} else {
				return h2.put(key, value);
			}
		} else {
			return h1.put(key, value);
		}

	}

	String delete(String key) {
		String val = h1.delete(key);

		if (val == null && h2 != null) {
			val = h2.delete(key);
		}
		return val;
	}

	String get(String key) {
		String val = h1.get(key);

		if (val == null && h2 != null) {
			val = h2.get(key);
		}
		return val;
	}

	long size() {
		long size = h1.size();

		if (h2 != null) {
			size += h2.size();
		}
		return size;
	}

	public int bits() {
		return bits;
	}

	public static void main(String[] args) {
		HashTable hash = new HashTable();
		long time;

		HashMap other = new HashMap<>();
		time = System.currentTimeMillis();
		for (int i = 0; i < 3000000; i++) {
			other.put("akshay" + i % 100000, "pundle" + i % 100000);
			other.get("akshay" + i % 100000);

		}
		System.out.println((System.currentTimeMillis() - time));

		time = System.currentTimeMillis();
		for (int i = 0; i < 3000000; i++) {
			hash.put("akshay" + i % 100000, "pundle" + i % 100000);
			hash.get("akshay" + i % 100000);

		}

		System.out.println((System.currentTimeMillis() - time));
		System.out.println(hash.bits);
		System.out.println(hash.size());
	}

}
