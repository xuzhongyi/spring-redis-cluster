package com.redisTemplate;

import java.util.Arrays;

public class DefaultTypedTuple<V> implements TypedTuple<V>{

	private final Double score;
	private final V value;

	/**
	 * Constructs a new <code>DefaultTypedTuple</code> instance.
	 * 
	 * @param value
	 * @param score
	 */
	public DefaultTypedTuple(V value, Double score) {
		this.score = score;
		this.value = value;
	}

	public Double getScore() {
		return score;
	}

	public V getValue() {
		return value;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DefaultTypedTuple))
			return false;
		DefaultTypedTuple<?> other = (DefaultTypedTuple<?>) obj;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (value instanceof byte[]) {
			if (!(other.value instanceof byte[])) {
				return false;
			}
			return Arrays.equals((byte[]) value, (byte[]) other.value);
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public int compareTo(Double o) {

		double thisScore = (score == null ? 0.0 : score);
		double otherScore = (o == null ? 0.0 : o);

		return Double.compare(thisScore, otherScore);
	}

	public int compareTo(TypedTuple<V> o) {

		if (o == null) {
			return compareTo(Double.valueOf(0));
		}

		return compareTo(o.getScore());
	}
	
}
