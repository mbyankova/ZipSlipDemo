package unzipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzippper {

	public static void unzipFile(String zipFile) {
		String targetDirectory = createDirectoryNamedAsZipFile(zipFile);

		byte[] buffer = new byte[1024];
		ZipInputStream zis = null;
		try {
			zis = new ZipInputStream(new FileInputStream(zipFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ZipEntry zipEntry = null;
		try {
			zipEntry = zis.getNextEntry();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (zipEntry != null) {
			String fileName = zipEntry.getName();
			File newFile = new File(targetDirectory + "/" + fileName);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(newFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int len;
			try {
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				zipEntry = zis.getNextEntry();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			zis.closeEntry();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			zis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String createDirectoryNamedAsZipFile(String zipFile) {
		FileSystem fileSystem = FileSystems.getDefault();

		String targetDirectory = zipFile.replaceFirst("[.][^.]+$", "");
		
		try {
			Files.createDirectory(fileSystem.getPath(targetDirectory));
		} catch (FileAlreadyExistsException e) {
			System.out.println("Directory " + targetDirectory + " already exists");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return targetDirectory;
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
