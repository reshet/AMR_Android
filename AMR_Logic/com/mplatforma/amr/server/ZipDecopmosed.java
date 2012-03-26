package com.mplatforma.amr.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.springframework.core.io.ByteArrayResource;

import db.DataHelper;

public class ZipDecopmosed {
	private File destFile;
	public final int BUFFER = 2048;
	//private DataHelper dh;
	public void unpack(int book_id,DataHelper dh,byte[] ziparr) {
		//this.dh = dh;
		//File sourceZipFile = new File(nameZip);
		int index = 0;
		
		List<Integer> p_ids = dh.getBookPageIDs(dh.getBookID(new BookDTO(book_id,"",null)));
		try {

			//File unzipDestinationDirectory = new File(destinationDirectory);
			//ZipFile jFile = new ZipFile(sourceZipFile);
			ByteArrayInputStream bais = new ByteArrayInputStream(ziparr);
			ZipInputStream jFile = new ZipInputStream(bais);
			ZipEntry entry = null;
			while ((entry = jFile.getNextEntry()) != null && index < p_ids.size()) 
			{
				//ZipEntry ;
				//jFile.
				String entryname = entry.getName();
				if (!entry.isDirectory()) {
					//long size = entry.getSize();
					//if(size > 0)
					ByteArrayOutputStream str = new ByteArrayOutputStream();
					byte [] buff = new byte[8096];
//					for (int c = jFile.read(); c != -1; c = jFile.read()) { 
//			            str.write(c); 
//			          } 
					for (int c = jFile.read(buff); c > -1; c = jFile.read(buff)) { 
			            str.write(buff,0,c); 
			          } 
					
					
					byte [] arr = str.toByteArray();
					//jFile.read(arr);
					dh.update_page(p_ids.get(index), arr);
					index++;
					
				}
				jFile.closeEntry();
				//jFile.getNextEntry();
			}

			jFile.close();

		} catch (IOException ioe) {

			ioe.printStackTrace();

		}

	}

//	private void writeFile(ZipInputStream jFile, ZipEntry entry)
//
//	throws IOException {
//
//		jFile.
//		BufferedInputStream is = new BufferedInputStream(jFile.getInputStream(entry));
//		int currentByte;
//		byte data[] = new byte[BUFFER];
//
//		BufferedOutputStream dest =new BufferedOutputStream(new FileOutputStream(destFile), BUFFER);
//		while ((currentByte = is.read(data, 0, BUFFER)) > 0) {
//
//			dest.write(data, 0, currentByte);
//		}
//		dest.flush();
//		dest.close();
//		is.close();
//
//	}
}
