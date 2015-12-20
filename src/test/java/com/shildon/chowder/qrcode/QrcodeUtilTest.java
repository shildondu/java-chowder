package com.shildon.chowder.qrcode;

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
		qrcodeUtil.generateQrcode("I Love U", ImageType.PNG, "/home/shildon/Downloads/test.png");
	}

}
