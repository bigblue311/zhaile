package com.victor.framework.common.tools;

import java.util.List;

import com.google.common.collect.Lists;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinTools {
	
	public static final String[] 声母     = {"B","P","M","F","D","T","N","L","G","K","H","J","Q","X","Z","C","S","R","Y","W","Zh","Ch","Sh"};
	public static final String[] 声母_1   = {"B","P","M","F","D","T","N","L","G","K","H","J","Q","X","Z","C","S","R","Y","W"};
	public static final String[] 声母_2   = {"Zh","Ch","Sh"};
	
	public static final String[] 单韵母   = {"a","o","e","i","u","v"};
	public static final String[] 复合韵母 = {"ai","ei","ui","ao","ou","iu","ie","ue","er","an","en","in","ang","eng","ing","ong"};
	public static final String[] 组合韵母 = {"iao","ian","uan","iang","iong","uang"}; //单+复合韵母
	
	public static final String[] 韵母_1 = {"a","o","e","i","u","v"};
	public static final String[] 韵母_2 = {"ai","ei","ui","ao","ou","iu","ie","ue","er","an","en","in"};
	public static final String[] 韵母_3 = {"ang","eng","ing","ong","iao","ian","uan"};
	public static final String[] 韵母_4 = {"iang","iong","uang"};
	
	public static final String[] 后鼻音 = {"ang","iang","uang","eng","ing","ong","iong"};
	public static final String[] 前鼻音 = {"an","ian","uan","en","in"};
	
	public static final String[] 翘舌音 = {"Zh","Ch","Sh","R"};
	public static final String[] 平舌音 = {"Z","C","S"};
	
	public static final List<List<String>> 易混淆 = Lists.newArrayList();
	
	static {
		//前后鼻音
		易混淆.add(Lists.newArrayList("ang","an"));
		易混淆.add(Lists.newArrayList("eng", "en"));
		易混淆.add(Lists.newArrayList("uang", "uan"));
		易混淆.add(Lists.newArrayList("iang", "ian"));
		易混淆.add(Lists.newArrayList("ing", "in"));
		
		//声母-
		易混淆.add(Lists.newArrayList("B", "P"));
		易混淆.add(Lists.newArrayList("M", "N", "L"));
		易混淆.add(Lists.newArrayList("F", "H"));
		易混淆.add(Lists.newArrayList("D", "T"));
		易混淆.add(Lists.newArrayList("G", "K"));
		
		//翘舌与平舌
		易混淆.add(Lists.newArrayList("Z", "Zh", "R"));
		易混淆.add(Lists.newArrayList("S", "Sh", "R"));
		易混淆.add(Lists.newArrayList("C", "CH", "R"));
		
		//韵母
		易混淆.add(Lists.newArrayList("ai", "ei", "ui"));
		
		易混淆.add(Lists.newArrayList("ao", "ou"));
		
		易混淆.add(Lists.newArrayList("iu", "ie", "ue"));
	}
	
	public static String getPinYin(String inputString) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);

		char[] input = inputString.trim().toCharArray();
		StringBuffer output = new StringBuffer("");

		try {
			for (int i = 0; i < input.length; i++) {
				if (Character.toString(input[i]).matches("[\u4E00-\u9FA5]+")) {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
					String piny = firstLetterUppercase(temp[0]);
					output.append(piny);
				} else
					output.append(Character.toString(input[i]));
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return output.toString();
	}

	public static String firstLetterUppercase(String pinyin){
		if(pinyin == null) {
			return null;
		}
		char[] pinyin_c_a = pinyin.toCharArray();
		StringBuffer output = new StringBuffer("");
		for(int i=0;i<pinyin_c_a.length;i++){
			if(i==0){
				StringBuffer temp = new StringBuffer("");
				output.append(temp.append(pinyin_c_a[i]).toString().toUpperCase());
			} else {
				output.append(pinyin_c_a[i]);
			}
		}
		return output.toString();
	}
	
	//易混淆的
	public static List<String> getConfused(String pinyin){
		if(StringTools.isEmpty(pinyin)){
			return Lists.newArrayList(pinyin);
		}
		String _声母 = "";
		int index = 0;
		if(pinyin.length()>2){
			String first2 = pinyin.substring(0,2);
			for(int i=0;i<声母_2.length;i++){
				if(声母_2[i].equals(first2)){
					_声母 = 声母_2[i];
					index = 2;
				}
			}
		}
		if(index == 0) {
			String first1 = pinyin.substring(0,1);
			for(int i=0;i<声母_1.length;i++){
				if(声母_1[i].equals(first1)){
					_声母 = 声母_1[i];
					index = 1;
				}
			}
		}
		if(index == 0) {
			return Lists.newArrayList(pinyin);
		}
		List<String> _声母_suspect = getConfusedFromDict(_声母);
		String _韵母 = pinyin.substring(index);
		List<String> _韵母_suspect = getConfusedFromDict(_韵母);
		List<String> suspect = Lists.newArrayList();
		for(String s_suspect : _声母_suspect){
			for(String y_suspect : _韵母_suspect) {
				suspect.add(s_suspect+y_suspect);
			}
		}
		return suspect;
	}
	
	private static List<String> getConfusedFromDict(String key){
		for(List<String> seed : 易混淆){
			if(seed.contains(key)){
				return seed;
			}
		}
		return Lists.newArrayList(key);
	}
	
	public static void main(String[] args) {
		String chs = "我是中国人女人! I'm Chinese!";
		System.out.println(chs);
		System.out.println(getPinYin(chs));
	}
}
