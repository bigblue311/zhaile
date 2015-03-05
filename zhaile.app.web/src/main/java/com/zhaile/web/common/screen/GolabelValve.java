package com.zhaile.web.common.screen;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.Valve;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.zhaile.biz.common.Cache;

public class GolabelValve implements Valve {

	@Autowired
	private Cache cache;
	
	@Autowired
    private HttpServletRequest request;
	
	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		TurbineRunDataInternal rundata = (TurbineRunDataInternal) getTurbineRunData(request);
		rundata.getContext().put("cacheTools", cache);
		pipelineContext.invokeNext();
	}

}
