package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import constant.Constant;
import ebizConcept.*;
import ebizTools.EmailSenderCenter;
import ebizTools.GeneralMethod;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/sendEmailtoOneRecipientServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class sendEmailtoOneRecipientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public sendEmailtoOneRecipientServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String message = "";
		EbizCompany company = (EbizCompany) request.getSession().getAttribute("currentCompany");
		EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
		String emailAddress = request.getParameter("emailAddress");
		String emailTitle = request.getParameter("emailTitle");
		String emailContent = request.getParameter("emailContent");
		String userName = request.getParameter("username");
		

		emailContent = "Dear "+userName+"\n\n"+emailContent + "\n\nRegards\n" + currentUser.userName + "(" + company.companyName + ")";

		emailContent=emailContent.replace("\n", "<br>");
		List<File> uploadedFiles = saveUploadedFiles(request, company);
		List<String> chosenFileStrings = new ArrayList<>();
		for (int i = 0; i < uploadedFiles.size(); i++) {
			chosenFileStrings.add(uploadedFiles.get(i).getAbsolutePath());
		}
		
		try {
			if (EmailSenderCenter.getInstance().sendEmailtoOneRecipientFromCompany(company, emailAddress, emailTitle,
					emailContent, chosenFileStrings)) {
				message = message + "Email Send Sucessfully; \n";
			} else {
				message = message + "Send Email Failed, Please Try Again; \n";
			}
		} catch (Exception ex) {
			message = message + "Send Email Failed, Please Try Again; \n";
			ex.printStackTrace();

		} finally {
			deleteUploadFiles(uploadedFiles);
		}
		if (message.length() > 0) {
			response.getWriter().print(message);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Saves files uploaded from the client and return a list of these files
	 * which will be attached to the e-mail message.
	 */
	private List<File> saveUploadedFiles(HttpServletRequest request, EbizCompany company)
			throws IllegalStateException, IOException, ServletException {
		List<File> listFiles = new ArrayList<File>();
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		Collection<Part> multiparts = request.getParts();
		if (multiparts.size() > 0) {
			for (Part part : request.getParts()) {
				// creates a file to be saved
				String fileName = extractFileName(part);
				if (fileName == null || fileName.equals("")) {
					// not attachment part, continue
					continue;
				}
				String temp = GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000).replace(":", "")
						.replace("-", "").replace(" ", "") + "_" + fileName;
				String time=Integer.toString(GeneralMethod.getYear(System.currentTimeMillis()));
				String directoryName = Constant.FilePath + "\\" + company.companyName+"\\"+time;
				File directory = new File(directoryName);
				if (!directory.exists()) {
					directory.mkdirs();
					// If you require it to make the entire directory path
					// including parents,
					// use directory.mkdirs(); here instead.
				}

				File saveFile = new File(directoryName, temp);
				System.out.println("saveFile: " + saveFile.getAbsolutePath());
				FileOutputStream outputStream = new FileOutputStream(saveFile);

				// saves uploaded file
				InputStream inputStream = part.getInputStream();
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				outputStream.close();
				inputStream.close();

				listFiles.add(saveFile);
			}
		}
		return listFiles;
	}

	/**
	 * Retrieves file name of a upload part from its HTTP header
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return null;
	}

	/**
	 * Deletes all uploaded files, should be called after the e-mail was sent.
	 */
	private void deleteUploadFiles(List<File> listFiles) {
		if (listFiles != null && listFiles.size() > 0) {
			for (File aFile : listFiles) {
				 aFile.delete();
			}
		}
	}

}