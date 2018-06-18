package bg.fmi.uni_sofia.fileuploader.unzipper;

import java.io.File;
import java.io.IOException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Unzipper {

	public static void unzipFile(String zipFileName) {
		if(!doesFileExists(zipFileName)) {
			System.err.println("Invalid file name: file does not exists");
			System.exit(0);
		}
		
		if (!isFilenameValid(zipFileName)) {
			System.err.println("Invalid file name");
			System.exit(0);
		}
		
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

}