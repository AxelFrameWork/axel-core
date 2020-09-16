package org.xmlactions.common.arrays;

import java.util.Arrays;

public class MultiArrayIndexer {

	int index = 0;
	private final Integer [] indexingArrays;
	private final Integer [] arrayLimits;
	
	public MultiArrayIndexer(final Integer[] indexes, final Integer[] indexesCount) {
		this.indexingArrays = indexes;
		this.arrayLimits = indexesCount;
		if (this.indexingArrays.length != this.arrayLimits.length) {
			throw new IllegalArgumentException("Arrays must be the same size");
		}
	}
	
	/**
	 * Will advance to next - and return false if past end.
	 * @return true if has next else false if all processed.
	 */
	public boolean hasNext() {
		if (indexingArrays.length == 0) {
			return false;
		}
		indexingArrays[indexingArrays.length-1] ++;
		if (indexingArrays[indexingArrays.length-1] >= arrayLimits[indexingArrays.length-1]) {
			return jump(indexingArrays.length-2);
		}
		return true;
	}
	
	public boolean jump(int arrayIndex) {
		if (arrayIndex < 0) {
			return false;
		}
		indexingArrays[arrayIndex+1] = 0;
		indexingArrays[arrayIndex] ++;
		if (indexingArrays[arrayIndex] >= arrayLimits[arrayIndex]) {
			return jump(arrayIndex-1);
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("indexingArrays:" + Arrays.toString(this.indexingArrays));
		sb.append(" arrayLimits:" + Arrays.toString(this.arrayLimits));
		return sb.toString();
	}
}
