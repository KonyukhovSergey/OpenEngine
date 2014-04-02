package ru.serjik.utils;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;



public class Program
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		File file = new File("tiles");
		
		for (File fileName : file.listFiles())
		{
		System.out.println(fileName.getAbsolutePath());	
		}
		
		System.out.println("all correct!");
		
	
	}


}
