package pundle.datastructure;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pundle.datastructure.BitArray;

public class BitArrayTests {
	@Test
	public void testSimple() {
		BitArray bits = new BitArray(100);
		for (int i = 0; i < 100; i++) {
			assertFalse(bits.isSet(i));
		}
		for (int i = 0; i < 100; i++) {
			bits.set(i);
			assertTrue(bits.isSet(i));
			bits.unset(i);
		}
		for (int i = 0; i < 100; i++) {
			assertFalse(bits.isSet(i));
		}
		for (int i = 0; i < 100; i++) {
			bits.set(i);
		}
		for (int i = 0; i < 100; i++) {
			assertTrue(bits.isSet(i));
		}
	}
}
