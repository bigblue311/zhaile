package com.victor.framework.spymemcached;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.internal.GetFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.common.tools.JsonTools;

public class MemcachedServiceImpl implements CacheService, InitializingBean,DisposableBean{
	 private static Logger logger = LoggerFactory.getLogger(MemcachedServiceImpl.class);
	 private String keyPrefix = "";
	 private String host = "";
	 private String port = "";
	 private String username = "";
	 private String password = "";
     private boolean auth = false;
     private boolean inited = false;
     private boolean enable = true;
     private MemcachedClient  client ;

    @Override
	public String get(String key) {
		if(StringTools.isEmpty(key) || !enable){
			return "";
		}
		GetFuture<Object> result = client.asyncGet(buildKey(key));
		if(result!=null){
			try {
				return (String)result.get(3, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				logger.error("Memcached asyncGet InterruptedException:", e);
			} catch (ExecutionException e) {
				logger.error("Memcached asyncGet ExecutionException:", e);
			} catch (TimeoutException e) {
				logger.error("Memcached asyncGet TimeoutException:", e);
			}
		}
		return "";
	}

	@Override
	public void set(String key, String value) {
		if(StringTools.isEmpty(key) || !enable){
			return ;
		}
		client.set(buildKey(key), 0, value);
	}

	@Override
	public void set(String key, String value, int exprieSecond) {
		if(StringTools.isEmpty(key) || !enable){
			return ;
		}
		if(exprieSecond<0){
			exprieSecond = 0;
		}
		client.set(buildKey(key), exprieSecond, value);
	}

	@Override
	public void del(String key) {
		if(StringTools.isEmpty(key) || !enable){
			return;
		}
		client.delete(buildKey(key));
	}

	@Override
	public <T> T  getObject(String key,Class<T> clazz) {
		if(StringTools.isEmpty(key) || clazz==null || !enable){
			return null;
		}
		String jsonValue = get(key);
		return StringTools.isEmpty(jsonValue)?null:(T)JsonTools.fromJson(jsonValue, clazz);
	}
	
	@Override
	public <T> List<T> getList(String key, Class<T> clazz) {
		@SuppressWarnings("unchecked")
		List<JSONObject> list = (List<JSONObject>)getObject(key, List.class);
		if(list == null){
			return null;
		}
		List<T> result = Lists.newArrayList();
		for(JSONObject jsonObj : list){
			if(jsonObj  != null){
				result.add(JSON.parseObject(jsonObj.toJSONString(), clazz));
			}
		}
		return result;
	}

	@Override
	public void setObject(String key, Object value) {
		if(StringTools.isEmpty(key) || value==null || !enable){
			return ;
		}
		set(key, JsonTools.toJson(value));
	}

	@Override
	public void setObject(String key, Object value, int exprieSecond) {
		if(StringTools.isEmpty(key) || value==null || exprieSecond<0 || !enable){
			return ;
		}
		set(key, JsonTools.toJson(value),exprieSecond);
	}
	
	@Override
    public boolean add(String key, String value, int exprieSecond) {
		if(StringTools.isEmpty(key) || !enable){
			return false;
		}
		if(exprieSecond<0){
			exprieSecond = 0;
		}
		return client.add(buildKey(key), exprieSecond, value).getStatus().isSuccess();
    }
	
	private String buildKey(String key){
		return keyPrefix + ":" +key;
	}
	
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@Override
	public void destroy() throws Exception {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!inited && enable) {
			try {
				ConnectionFactoryBuilder cfBuidler = new ConnectionFactoryBuilder();
				cfBuidler.setProtocol(Protocol.BINARY);
				cfBuidler.setOpTimeout(3000);
				cfBuidler.setTimeoutExceptionThreshold(3000);
				if (auth) {
					cfBuidler.setAuthDescriptor(new AuthDescriptor(
							new String[] { "PLAIN" }, new PlainCallbackHandler(
									username, password)));
				}
				client = new MemcachedClient(cfBuidler.build(),
						AddrUtil.getAddresses(host + ":" + port));
				inited = true;
				logger.info("Memcached Client inited...");
			} catch (IOException e) {
				logger.error("Memcached client initial exception:", e);
				e.printStackTrace();
			}
		}
	}
}
