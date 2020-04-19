package com.jt.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Test {
	
	public static void main(String[] args) {
		String phone="15852744001";
		
		isMobile(phone);
	}
	
	
	
	public static boolean isMobile(String phone) {
		
		boolean isMatch =false;
		 
		String regex= "^1[3|4|5|7|8][0-9]\\d{4,8}$";
		if(phone.length()!=11) {
			System.out.println("手机号位数不对");
		}else {
			Pattern p =Pattern.compile(regex);
			Matcher m = p.matcher(phone);
			isMatch = m.matches();
			if(isMatch) {
				System.out.println("您的手机号" + phone + "是正确格式");
			}else {
				System.out.println("您的手机号" + phone + "是错误格式！！！");
			}
			
		}
	
		
		return isMatch;
		
	}
	

}
