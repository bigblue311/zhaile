package com.zhaile.biz.web.manager;

import java.io.InputStream;

import com.victor.framework.common.shared.Result;

public interface FileUploadManager {
	
	/**
	 * 上传一个文件
	 * @param in
	 * @return
	 */
	Result<String> uploadImg(String fileName, InputStream in);
	
	/**
	 * 上传一个文件,并添加水印
	 * @param in
	 * @return
	 */
	Result<String> uploadImg(String fileName, InputStream in, String waterPrint);
	
	/**
	 * 将零时目录的文件升级成正式目录
	 * @param tempPath
	 * @return
	 */
	Result<String> copyTemp(String tempPath);
	
	/**
	 * 将零时目录的文件升级成正式目录并添加水印
	 * @param tempPath
	 * @param waterPrint
	 * @return
	 */
	Result<String> copyTemp(String tempPath, String waterPrint);
	
	/**
	 * 回收零时目录
	 */
	void recycleTemp();
}
