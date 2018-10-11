package com.renrendai.loan.beetle.commons.util.value;

import java.util.Stack;

public class ValueSources<K, V> implements ValueSource<K, V> {
	
	private Stack<ValueSource<K, V>> stack = new Stack<>();

	@Override
	public V get(K key) {
		if (key == null) {
			return null;
		}
		
		if (stack.size() == 0) {
			return null;
		}

		synchronized (stack) {
            for (int i = stack.size() - 1; i >= 0; i--) {
                ValueSource<K, V> values = stack.get(i);
                V value = values.get(key);
                if (value != null) {
                    return value;
                }
            }
        }

		return null;
	}

	public ValueSource<K, V> push(ValueSource<K, V> item) {
		if (item == null) {
			throw new NullPointerException("push ValueGetter is null."); 
		}
		stack.push(item);
		return this;
	}
	
	public ValueSource<K, V> pop() {
		return stack.pop();
	}

}
