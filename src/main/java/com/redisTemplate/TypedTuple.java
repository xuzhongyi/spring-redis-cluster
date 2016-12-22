package com.redisTemplate;

public interface TypedTuple<V> extends Comparable<TypedTuple<V>> {
	V getValue();
	
	Double getScore();

}
