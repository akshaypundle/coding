package pundle.datastructure;
import pundle.hash.Hashes;

public class BloomFilter {
	private BitArray bitArray; // filter bits
	private int n; // number of hashes
	private int bits; // number of bits
	private static final double LN_2 = 0.69314718056;

	/**
	 * Creates a bloom filter with 2^bits storage and optimizes the number of
	 * hash functions for storing at most expectedNumElements.
	 */
	public BloomFilter(int bits, int expectedNumElements) {
		this.bitArray = new BitArray(1 << bits);
		this.n = calculateNumHashes(bits, expectedNumElements);
		this.bits = bits;
	}

	public void put(String s) {
		for (int hash : Hashes.hash(s, n, bits)) {
			bitArray.set(hash);
		}
	}

	public boolean mightContain(String s) {
		for (int hash : Hashes.hash(s, n, bits)) {
			if (!bitArray.isSet(hash)) {
				return false;
			}
		}
		return true;
	}

	private static int calculateNumHashes(int bits, int expectedNumElements) {
		return (int) Math.ceil(LN_2 * bits / expectedNumElements);
	}
}
