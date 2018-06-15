package unzipper;

import java.io.File;
import java.io.IOException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;



public class Unzippper {

	public static void unzipFile(String zipFileName) {		
		try {
			// Initiate ZipFile object with the path/name of the zip file.
			ZipFile zipFile = new ZipFile(zipFileName);
			
			// Extracts all files to the path specified
			zipFile.extractAll("unzipped");
			
		} catch (ZipException e) {
			e.printStackTrace();
		}
		
	}

	private static boolean isFilenameValid(String fileName) {
		File f = new File(fileName);
		try {
			f.getCanonicalPath();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	private static boolean doesFileExists(String fileName) {
		File f = new File(fileName);
		return f.exists();
	}

	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Usage: java Unzipper zipFile");
			System.exit(0);
		}
		
		String zipFile = args[0];
		if(!doesFileExists(zipFile)) {
			System.err.println("Invalid file name: file does not exists");
			System.exit(0);
		}
		
		if (isFilenameValid(zipFile)) {
			unzipFile(zipFile);
		} else {
			System.err.println("Invalid file name");
		}

	}

}
