package com.victor.framework.search.cache;

public interface Computable<A,V> {
	V comput(A arg) throws InterruptedException, Exception;
	
	boolean forceUpdate(V v);
}
