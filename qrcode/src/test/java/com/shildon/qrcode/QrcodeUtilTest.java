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
		byte[] bytes = "好久没这么畅快的聊天了，今天我又感性了。".getBytes();
		try {
			String content = new String(bytes, "ISO-8859-1");
			qrcodeUtil.generateQrcode(content, ImageType.PNG, "/home/shildon/Downloads/160105.png");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
