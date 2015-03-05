package com.zhaile.web.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhaile.biz.web.manager.FlashGoManager;
import com.zhaile.biz.web.model.TodayEventVO;
import com.zhaile.dal.model.FlashGoDO;

public class CheckFlashGoAvaliable {
	@Autowired
	private FlashGoManager flashGoManager;
	
	public TodayEventVO execute() throws IOException {
		FlashGoDO flashGoDO = flashGoManager.getToday();
		TodayEventVO todayEventVO = new TodayEventVO();
		todayEventVO.setFlashGo(flashGoDO);
		return todayEventVO;
    }
}
