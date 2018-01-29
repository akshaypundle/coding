package pundle.hash;

public class Hashes {
	/**
	 * Produces n hashes for the input, each at most maxBits long.
	 */
	public static int[] hash(String s, int n, int maxBits) {
		int[] ret = new int[n];
		int murmur = MurmurHash3.murmurhash3_x86_32(s, 0, s.length(), 0);
		int fnv = FnvHash.fnv1a_32(s);
		for (int i = 0; i < n; i++) {
			ret[i] = foldHash(fnv + (i + 1) * murmur, maxBits);
		}
		return ret;
	}

	static int foldHash(int data, int maxBits) {
		int mask = (1 << maxBits) - 1;
		return ((data >>> maxBits) ^ (data & mask)) & mask;
	}

	private Hashes() {
	}
}
