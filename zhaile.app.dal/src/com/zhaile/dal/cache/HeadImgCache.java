package com.zhaile.dal.cache;

import java.util.Collection;

import com.zhaile.dal.model.HeadImageDO;

public interface HeadImgCache {
	public void reload();
	
	public HeadImageDO getCahce(Long id);
	
	public Collection<HeadImageDO> values();
}
