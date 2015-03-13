

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.util.Arrays;

public class MyBitSet {
    private final static int ADDRESS_BITS_PER_WORD = 6;
    private final static int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;
    private final static int BIT_INDEX_MASK = BITS_PER_WORD - 1;

    /* Used to shift left or right for a partial word mask */
    private static final long WORD_MASK = 0xffffffffffffffffL;
    /**
     * The internal field corresponding to the serialField "bits".
     */
    private long[] words;
    /**
     * The number of words in the logical size of this BitSet.
     */
    private  int wordsInUse = 0;

    private static int wordIndex(int bitIndex) {
        return bitIndex >> ADDRESS_BITS_PER_WORD;
    }

    public MyBitSet(int nbits) {
        initWords(nbits);
    }
    private void initWords(int nbits) {
        wordsInUse = wordIndex(nbits-1) + 1;
        words = new long[wordIndex(nbits-1) + 1];
    }

    public void set(int bitIndex) {
        int wordIndex = wordIndex(bitIndex);
        words[wordIndex] |= (1L << bitIndex); // Restores invariants
    }
    public void set(int bitIndex, boolean value) {
        if (value)
            set(bitIndex);
        else
            clear(bitIndex);
    }
    public void clear(int bitIndex) {
        int wordIndex = wordIndex(bitIndex);
        if (wordIndex >= wordsInUse)
            return;
        words[wordIndex] &= ~(1L << bitIndex);
    }
    public void clear() {
        int len = wordsInUse;
        while (len > 0)
            words[--len] = 0;
    }
    public boolean get(int bitIndex) {
        int wordIndex = wordIndex(bitIndex);
        return (wordIndex < wordsInUse)
                && ((words[wordIndex] & (1L << bitIndex)) != 0);
    }
    public void xor(MyBitSet set) {
        int wordsInCommon = Math.min(wordsInUse, set.wordsInUse);
        for (int i = 0; i < wordsInCommon; i++)
            words[i] ^= set.words[i];
    }
}

