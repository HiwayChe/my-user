package com.my.user.ws;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.Map;

import com.my.common.utils.JsonUtils;
import com.my.common.utils.MapUtils;

public class FileUtils {

	private static final long MAX_SIZE = 4096L;
	private static String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * 
	 * @param name
	 * @param pos  if 0 means get the lastest MAX_SIZE bytes
	 * @return
	 */
	public static Map<String, Object> read(String name, long pos) {
		RandomAccessFile file = null;
		String content = "";
		long size = 0L;
		try {
			file = new RandomAccessFile(name, "r");
			FileChannel channel = file.getChannel();
			size = channel.size();
			if(pos >= size) {
				return Collections.emptyMap();
			}
			if(pos == 0) {
				pos = Math.max(0, size - MAX_SIZE);
			}
			channel.position(pos);
			ByteBuffer buffer = ByteBuffer.allocate((int) (size - pos));
			channel.read(buffer);
			byte[] bytes = buffer.array();
			content = new String(bytes, "utf-8");
			content = content.replaceAll(LINE_SEPARATOR, "<br>");
			buffer.clear();
			buffer = null;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (Exception e) {

				}
			}
		}
		return MapUtils.build("pos", size, "content", content);
	}
}
