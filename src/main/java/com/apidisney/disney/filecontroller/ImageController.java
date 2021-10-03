package com.apidisney.disney.filecontroller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class ImageController {
	
	public static String imageUpload(String savePath, MultipartFile image) {
			Path pathImages = Paths.get(savePath);
			String pathAbsolut = pathImages.toFile().getAbsolutePath();
			
			try {
				byte[] bytesImg = image.getBytes();
				Path pathComplete = Paths.get(pathAbsolut + "//" + image.getOriginalFilename());
				Files.write(pathComplete, bytesImg);
				
				return image.getOriginalFilename();
			}
			
			catch(IOException e) {
				e.printStackTrace();
				return null;
			}
	}
	
	public static void imageDelete(String completePath) {
		
		try {
			Files.delete(Paths.get(completePath));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
