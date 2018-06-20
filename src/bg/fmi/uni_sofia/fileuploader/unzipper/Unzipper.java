package bg.fmi.uni_sofia.fileuploader.unzipper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Unzipper {

	public static void unzipFile(String zipFileWithAbsolutePath, String destination) throws IOException {
		if(!doesFileExists(zipFileWithAbsolutePath)) {
			throw new FileNotFoundException("The given zip file not found: " + zipFileWithAbsolutePath);
		}
		
		isFilenameValid(zipFileWithAbsolutePath);
		
		String fileName = getFileFromPath(zipFileWithAbsolutePath);
		String finalDestination = createDirectoryNamedAsZipFile(fileName, destination);
		
		try {
			// Initiate ZipFile object with the path/name of the zip file.
			ZipFile zipFile = new ZipFile(zipFileWithAbsolutePath);
			
			// Extracts all files to the path specified
			zipFile.extractAll(finalDestination);
			
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
	
	private static String getFileFromPath(String path) {
		File fileWithPath = new File(path);
		return fileWithPath.getName();
	}
	
	private static String createDirectoryNamedAsZipFile(String zipFile, String destination) {
		FileSystem fileSystem = FileSystems.getDefault();

		String targetDirectory = zipFile.replaceFirst("[.][^.]+$", "");
		String finalDestination = destination + targetDirectory;
		
		try {
			Files.createDirectory(fileSystem.getPath(finalDestination));
		} catch (FileAlreadyExistsException e) {
			System.out.println("Directory " + targetDirectory + " already exists");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return finalDestination;
	}

}