package com.zhaile.admin.webpage.screen.upload;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.victor.framework.common.shared.Result;
import com.zhaile.biz.web.manager.FileUploadManager;

public class FileUpload {
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	private FileUploadManager fileUploadManager;
	
	public Result<String> execute() throws IOException {
		TurbineRunDataInternal rundata = (TurbineRunDataInternal) getTurbineRunData(request);
		FileItem uploadFile = rundata.getParameters().getFileItem("Filedata");
		if(uploadFile == null) {
			return Result.newInstance("", "文件未找到", false);
		}
		InputStream in = uploadFile.getInputStream();
		Result<String> result = fileUploadManager.uploadImg(uploadFile.getName(), in);
		return result;
	}
}
