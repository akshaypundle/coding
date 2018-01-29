package pundle.hash;
public class FnvHash {
	public static final int FNV_PRIME_32 = 16777619;
	public static final long FNV_OFFSET_32 = 2166136261L;
	public static final long FNV_MOD_32 = 1L << 32;

	public static final int fnv1a_32(CharSequence data) {
		long hash = FNV_OFFSET_32;
		for (int i = 0; i < data.length(); i++) {
			char oneChar = data.charAt(i);
			byte b1 = (byte) (oneChar & 0xFF);
			byte b2 = (byte) ((oneChar >> 8) & 0xFF);

			hash = hash ^ b1;
			hash = (hash * FNV_PRIME_32) % FNV_MOD_32;

			hash = hash ^ b2;
			hash = (hash * FNV_PRIME_32) % FNV_MOD_32;

		}

		return (int) hash;
	}
}
