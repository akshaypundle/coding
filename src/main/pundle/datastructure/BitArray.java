package pundle.datastructure;

public class BitArray {
	private long[] bits;
	private int size;

	BitArray(int size) {
		this.size = size;
		int n = (int) Math.ceil(1.0 * size / 64);
		bits = new long[n];
	}

	void set(int bit) {
		if (bit >= size) {
			throw new IllegalArgumentException(
					"requested 0-inedxed bit " + bit + " but the array has only " + size + " bits");
		}
		int address = bit / 64;
		int offset = bit % 64;
		bits[address] = bits[address] | (1 << offset);
	}

	void unset(int bit) {
		if (bit >= size) {
			throw new IllegalArgumentException(
					"requested 0-inedxed bit " + bit + " but the array has only " + size + " bits");
		}
		int address = bit / 64;
		int offset = bit % 64;
		bits[address] = bits[address] & (~(1 << offset));
	}

	boolean isSet(int bit) {
		if (bit >= size) {
			throw new IllegalArgumentException(
					"requested 0-inedxed bit " + bit + " but the array has only " + size + " bits");
		}
		int address = bit / 64;
		int offset = bit % 64;
		return ((bits[address] & (1 << offset)) != 0);
	}
}
