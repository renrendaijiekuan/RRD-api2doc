package com.renrendai.loan.beetle.commons.util.value;

public interface ValueSource<K, V> {

	V get(K key);
	
}
