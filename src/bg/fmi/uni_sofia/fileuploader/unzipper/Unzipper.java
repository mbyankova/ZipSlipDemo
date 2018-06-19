package bg.fmi.uni_sofia.fileuploader.unzipper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Unzipper {

	public static void unzipFile(String zipFileName, String unzipDestination) throws IOException {
		if(!doesFileExists(zipFileName)) {
			throw new FileNotFoundException("The given zip file not found: " + zipFileName);
		}
		
		isFilenameValid(zipFileName);
		
		try {
			// Initiate ZipFile object with the path/name of the zip file.
			ZipFile zipFile = new ZipFile(zipFileName);
			
			// Extracts all files to the path specified
			zipFile.extractAll(unzipDestination);
			
		} catch (ZipException e) {
			e.printStackTrace();
		}
		
	}

	private static void isFilenameValid(String fileName) throws IOException {
		File f = new File(fileName);
		f.getCanonicalPath();
	}
	
	private static boolean doesFileExists(String fileName) {
		File f = new File(fileName);
		return f.exists();
	}

}