/*
 * Copyright (c) 2002-2012 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhaile.web.webpage.screen;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.tools.QRcodeTools;

public class CookBookQRCode {
	
    public void execute(@Param("cookId") int id,HttpServletResponse response,HttpServletRequest request) throws Exception {
    	response.setContentType("image/jpeg");  
        //禁止图像缓存。  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
    	String content = "http://www.fyzhaile.com/cook_book_item?id="+id;
    	ImageIO.write(QRcodeTools.generateQRCode(content), "JPEG", response.getOutputStream());
    }
}
