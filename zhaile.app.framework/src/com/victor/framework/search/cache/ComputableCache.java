package com.victor.framework.search.cache;

import com.google.common.collect.Maps;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ComputableCache<A,V> implements Computable<A,V> {
	private final ConcurrentMap<String,Future<V>> cache = Maps.newConcurrentMap();
	
	private final Computable<A,V> c;
	
	public ComputableCache(Computable<A,V> c){
		this.c = c;
	}

	@Override
	public V comput(final A arg) throws Exception {
		while(true){
			Future<V> f = cache.get(arg.toString());
			Callable<V> eval = new Callable<V>(){
				@Override
				public V call() throws Exception {
					return c.comput(arg);
				}
			};
			FutureTask<V> ft = new FutureTask<V>(eval);
			if(f == null) {
				f = cache.putIfAbsent(arg.toString(), ft);
				if(f==null){
					f = ft;
					ft.run();
				}
			}
			try {
				V v = f.get();
				if(forceUpdate(v)){
					cache.put(arg.toString(), f);
					f = ft;
					ft.run();
					v = f.get();
				}
				return v;
			} catch (CancellationException e){
				cache.remove(arg, f);
			} catch (ExecutionException e) {
				throw new Exception(e.getCause());
			}
		}
	}

	@Override
	public boolean forceUpdate(V v) {
		return c.forceUpdate(v);
	}
}
