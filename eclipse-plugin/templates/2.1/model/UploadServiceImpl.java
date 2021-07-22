/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %serverServicePackage%;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

public class UploadServiceImpl extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(final HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		boolean isMultiPart = ServletFileUpload
				.isMultipartContent(new ServletRequestContext(request));

		if(isMultiPart) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			try {
				@SuppressWarnings("unchecked")
				List<Object> items = upload.parseRequest(request);
				Iterator<Object> iter = items.iterator();
				String dirFile = request.getSession().getServletContext().getRealPath("/files");
				File diretorioFile = new File(dirFile);
				String name = "0";
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next(); 
					if (!item.isFormField()) {
						if (item.getName().length() > 0) {
							name = item.getName();				            
							String arq[] = name.split("\\\\");
							for (int i = 0; i < arq.length; i++) {
								name = arq[i];
							}		 
							File file = new File(diretorioFile, name);
							FileOutputStream output = new FileOutputStream(file);
							InputStream is = item.getInputStream();
							byte[] buffer = new byte[2048];
							int nlidos;
							while ((nlidos = is.read(buffer)) >= 0) {
								output.write(buffer, 0, nlidos);
							}
							output.flush();
							output.close();
						}
					}
				}
				response.setStatus(HttpServletResponse.SC_CREATED);
				response.getWriter().print(name);
				response.flushBuffer();				
			} catch(Exception e) {
				e.printStackTrace();
				response.getWriter().print("0");
				response.flushBuffer();				
			}
		}
		else {
			super.service(request, response);
			return;
		}
	}

}
