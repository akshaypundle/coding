package pundle.hash;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import pundle.hash.Hashes;

public class HashesTests {

	@Test
	public void testSimple() {
		testHashes("akshay pundle", 10, 31);
		testHashes("vidya iyer", 10, 23);
		testHashes("amrita pundle", 10, 14);
		testHashes("akshay pundle skdjhksdafjksjdfh", 10, 15);
		testHashes("akshay pundle", 10, 7);
	}

	private void testHashes(String s, int numHashes, int maxBits) {
		int[] hashes = Hashes.hash(s, numHashes, maxBits);
		assertEquals(hashes.length, numHashes);
		testWithinMaxbits(hashes, maxBits);
		testNoDuplicates(hashes);
	}

	private void testWithinMaxbits(int[] hashes, int maxBits) {
		for (int i = 0; i < hashes.length; i++) {
			long hash = hashes[i] & 0x00000000ffffffffL;
			assertTrue(hash < (1L << maxBits));
		}
	}

	private void testNoDuplicates(int[] hashes) {
		Set<Integer> hashSet = new HashSet<>();
		for (int i = 0; i < hashes.length; i++) {
			assertFalse(hashSet.contains(hashes[i]));
			hashSet.add(hashes[i]);
		}
	}
}
