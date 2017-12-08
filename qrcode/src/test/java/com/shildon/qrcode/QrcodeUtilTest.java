package com.shildon.qrcode;

import java.io.UnsupportedEncodingException;

import net.glxn.qrgen.image.ImageType;

import org.junit.Before;
import org.junit.Test;

public class QrcodeUtilTest {
	
	private QrcodeUtil qrcodeUtil;
	
	@Before
	public void init() {
		qrcodeUtil = new QrcodeUtil();
	}
	
	@Test
	public void test() {
		byte[] bytes = "http://www.baidu.com".getBytes();
		try {
			String content = new String(bytes, "ISO-8859-1");
			qrcodeUtil.generateQrcode(content, ImageType.PNG, "C:\\Users\\Administrator\\Downloads\\test.png");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
