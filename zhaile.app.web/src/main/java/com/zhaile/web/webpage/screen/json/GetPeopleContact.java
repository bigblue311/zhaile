package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.List;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.dal.model.PeopleContactDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class GetPeopleContact extends DefaultLayout{
	
	public List<PeopleContactDO> execute(@Param("userId") Long userId) throws IOException{
		return super.getPeopleContactList(userId);
	}
}
