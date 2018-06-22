package bg.fmi.uni_sofia.fileuploader.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bg.fmi.uni_sofia.fileuploader.unzipper.Unzipper;

/**
 * Servlet implementation class FileUploader
 */
@WebServlet("/FileUploader")
@MultipartConfig
public class FileUploader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploader() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Runtime.
		   getRuntime().
		   exec("cmd /c \"cd D:\\mila\\NS2\\ZipSlipDemo && start \"\" clean_up.bat\"");
		response.getWriter().append("<form action='/ZipSlipDemo/FileUploader' method='post' enctype='multipart/form-data'><input type='file' name='zipFile' /><button type='submit' value='Submit!'/></form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart("zipFile");
		InputStream input = filePart.getInputStream();
		File targetFile = new File("D:\\mila\\NS2\\ZipSlipDemo\\zipped\\" + filePart.getSubmittedFileName());
		targetFile.createNewFile();
		OutputStream out = new FileOutputStream(targetFile);
		
		byte[] buffer = new byte[1024];
        int bytesRead;

        while((bytesRead = input.read(buffer)) !=-1){
            out.write(buffer, 0, bytesRead);
        }
        
        input.close();
        out.flush();
        out.close();
		
        Unzipper.unzipFile(targetFile.getAbsolutePath(), "D:\\mila\\NS2\\ZipSlipDemo\\unzipped\\");
        
		doGet(request, response);
	}

}
