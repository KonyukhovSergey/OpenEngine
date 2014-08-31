package ru.serjik.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtils
{
	public static void Write(OutputStream stream, String value) throws IOException
	{
		byte[] data = value.getBytes();
		stream.write(data.length);
		stream.write(data.length >> 8);
	}

	public static List<String> readAllLines(InputStream stream, boolean closeStream) throws IOException
	{
		List<String> lines = new ArrayList<String>();

		BufferedReader input = new BufferedReader(new InputStreamReader(stream));

		String line = null;

		while ((line = input.readLine()) != null)
		{
			lines.add(line);
		}

		if (closeStream)
		{
			stream.close();
		}

		return lines;
	}
}
