package com.zhaile.biz.web.manager;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.List;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;

public class FileUploadManagerImpl implements FileUploadManager {

	private String apacheRoot;
	private List<String> extWhiteList;
	private String allowedExt;

	@Override
	public Result<String> uploadImg(String fileName, InputStream in) {
		Result<String> result = checkExt(fileName);
		if (!result.isSuccess()) {
			return result;
		}
		String ext = result.getDataObject();
		String returnUri = "/UploadFiles/temp" + "/" + DateTools.getTodayPath()
				+ "/" + DateTools.getRandomId() + ext;
		String tempFile = apacheRoot + returnUri;

		File file = new File(tempFile);
		try {
			if (!file.exists()) {
				String path = file.getParent();
				File dir = new File(path);
				if(!dir.exists()){
					dir.mkdirs();
				}
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			int c;
			byte buffer[] = new byte[1024];
			while ((c = in.read(buffer)) != -1) {
				for (int i = 0; i < c; i++)
					out.write(buffer[i]);
			}
			in.close();
			out.close();
			return Result.newInstance(returnUri, "文件上传成功", true);
		} catch (Exception ex) {
			return Result.newInstance("", "文件上传失败", false);
		}
	}
	
	@Override
	public Result<String> uploadImg(String fileName, InputStream in, String waterPrint) {
		Result<String> result = checkExt(fileName);
		if (!result.isSuccess()) {
			return result;
		}
		String ext = result.getDataObject();
		String returnUri = "/UploadFiles/temp" + "/" + DateTools.getTodayPath()
				+ "/" + DateTools.getRandomId() + ext;
		String tempFile = apacheRoot + returnUri;

		returnUri = "/UploadFiles/temp" + "/" + DateTools.getTodayPath()
				+ "/" + DateTools.getRandomId()+"w" + ext;
		
		String waterPrintFile = apacheRoot + returnUri;
		
		File file = new File(tempFile);
		try {
			if (!file.exists()) {
				String path = file.getParent();
				File dir = new File(path);
				if(!dir.exists()){
					dir.mkdirs();
				}
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			int c;
			byte buffer[] = new byte[1024];
			while ((c = in.read(buffer)) != -1) {
				for (int i = 0; i < c; i++)
					out.write(buffer[i]);
			}
			in.close();
			out.close();
			waterPrint(tempFile,waterPrintFile,waterPrint);
			return Result.newInstance(returnUri, "文件上传成功", true);
		} catch (Exception ex) {
			return Result.newInstance("", "文件上传失败", false);
		}
	}

	private Result<String> checkExt(String fileName) {
		int index = fileName.indexOf(".");
		String ext = fileName.substring(index);
		if (StringTools.isAllEmpty(fileName, ext)) {
			return Result.newInstance("", "文件损坏或者文件后缀为空", false);
		}
		if (extWhiteList.contains(ext.toUpperCase())) {
			return Result.newInstance(ext, "文件可用", true);
		} else {
			return Result.newInstance("", "文件后缀必须为" + allowedExt, false);
		}
	}

	public void setApacheRoot(String apacheRoot) {
		this.apacheRoot = apacheRoot;
	}

	public void setExtWhiteList(List<String> extWhiteList) {
		this.extWhiteList = extWhiteList;
		for (String item : extWhiteList) {
			allowedExt += item + " ";
		}
	}

	@Override
	public Result<String> copyTemp(String tempPath) {
		String relativePath = tempPath.replace("temp/", "");
		String temp = apacheRoot + tempPath;
		String real = apacheRoot + relativePath;
		try {
			FileInputStream in = new FileInputStream(temp);
			File file = new File(real);
			if (!file.exists()) {
				String path = file.getParent();
				File dir = new File(path);
				if(!dir.exists()){
					dir.mkdirs();
				}
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			int c;
			byte buffer[] = new byte[1024];
			while ((c = in.read(buffer)) != -1) {
				for (int i = 0; i < c; i++) {
					out.write(buffer[i]);
				}
			}
			in.close();
			out.close();
			return Result.newInstance(relativePath, "文件可用", true);
		} catch (Exception ex) {
			return Result.newInstance("", "文件损坏或者异常发生", false);
		}
	}
	
	@Override
	public Result<String> copyTemp(String tempPath, String waterPrint) {
		String relativePath = tempPath.replace("temp/", "");
		String temp = apacheRoot + tempPath;
		String real = apacheRoot + relativePath;
		try {
			File file = new File(real);
			if (!file.exists()) {
				String path = file.getParent();
				File dir = new File(path);
				if(!dir.exists()){
					dir.mkdirs();
				}
				file.createNewFile();
			}
			waterPrint(temp,real,waterPrint);
			return Result.newInstance(relativePath, "文件可用", true);
		} catch (Exception ex) {
			return Result.newInstance("", "文件损坏或者异常发生", false);
		}
	}

	@Override
	public void recycleTemp() {
		String todayPath = DateTools.getTodayPath();
		String dir = apacheRoot + "/UploadFiles/temp";
		File tempPath = new File(dir);
		if(tempPath.isDirectory()){
			for(File sub : tempPath.listFiles()){
				if(!sub.getName().contains(todayPath)){
					//System.out.println(sub.getAbsolutePath());
					delFolder(sub.getAbsolutePath());
				}
			}
		}
	}

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	private void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	private boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	
	private void waterPrint(String inputImg, String outputImg,String pressText) throws Exception{
    	String fontName = "方正楷体简体";
    	int fontStyle = Font.PLAIN;
    	int fontSize = 32;
    	Color color = Color.WHITE;
    	int x = 50;
    	int y = 100;
    	float alpha = 0.7f;
    	pressText(inputImg, outputImg, pressText, fontName, fontStyle, fontSize, color, x, y, alpha);
	}
	
	/**
     * 图片添加文字
     * @param inputImg 目标图片路径，如：C://myPictrue//1.jpg
     * @param outputImg 图片输出路径, 如: C://myPictrue//2.jpg
     * @param pressText 水印文字， 如：中国证券网
     * @param fontName 字体名称，    如：宋体
     * @param fontStyle 字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
     * @param fontSize 字体大小，单位为像素
     * @param color 字体颜色
     * @param x 水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y 水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 * @throws Exception 
     */
    private void pressText(String inputImg, 
						 String outputImg,
						 String pressText, 
						 String fontName, 
						 int fontStyle, 
						 int fontSize, 
						 Color color, 
						 int x, int y, float alpha) throws Exception {
        File input = new File(inputImg);
        
        Image image = ImageIO.read(input);
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        
        g.setColor(color);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        
        g.drawImage(image, 0, 0, width, height, null);
        
        int width_1 = fontSize * getLength(pressText);
        int height_1 = fontSize;
        int widthDiff = width - width_1;
        int heightDiff = height - height_1;
        if(x < 0){
            x = widthDiff / 2;
        }else if(x > widthDiff){
            x = widthDiff;
        }
        if(y < 0){
            y = heightDiff / 2;
        }else if(y > heightDiff){
            y = heightDiff;
        }
        
        AttributedString ats = new AttributedString(pressText);
        Font font = new Font(fontName, fontStyle, fontSize);
        g.setFont(font);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        /* 消除java.awt.Font字体的锯齿 */
		//font = g.getFont().deriveFont(30.0f);
		ats.addAttribute(TextAttribute.FONT, font, 0, pressText.length());
		AttributedCharacterIterator iter = ats.getIterator();
        g.drawString(iter, x, y + height_1);
        
        pressText = "该内容仅供宅乐网网页认证使用";
        ats = new AttributedString(pressText);
        font = new Font(fontName, fontStyle, 28);
        g.setFont(font);
        ats.addAttribute(TextAttribute.FONT, font, 0, pressText.length());
		iter = ats.getIterator();
        g.drawString(iter, x-2, y + height_1 + 64);
        
        g.dispose();
        // 输出 文件 到指定的路径
        FileOutputStream out = new FileOutputStream(outputImg);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
        param.setQuality(1f, true);
        encoder.encode(bufferedImage, param);
        out.close();
    }
    
    /**
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
     * @param text
     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
     */
    private int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }
}
