package com.shildon.chowder.transfer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.shildon.chowder.transfer.FileTransfer;

public class FileTransferTest {
	
	private FileTransfer fileTransfer;
	
	@Before
	public void init() {
		fileTransfer = new FileTransfer();
	}
	
	@Test
	public void test() {
		String fullPath = "/home/shildon/Downloads/test.txt";
		File file = new File("/home/shildon/Downloads/newTest.txt");
		try (FileOutputStream fileOutputStream = new FileOutputStream(file);) {

			fileTransfer.download(fullPath, fileOutputStream);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
