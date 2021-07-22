/*******************************************************************************
 * Generator by Pug Plugin %version%
 *******************************************************************************/
package %serverServicePackage%;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.query.JRHibernateQueryExecuterFactory;

import %servicePackageDAO%.DaoFactory;

public class ReportServiceImpl extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(final HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DaoFactory daoFactory = null;
		try {
			daoFactory = new DaoFactory();
			
			String path = request.getSession().getServletContext().getRealPath("WEB-INF/reports/") + "/";
			String fileJasper = request.getParameter("file");
			String typeExport = request.getParameter("type");
			String titulo = "";

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION, daoFactory.session);
			parameters.put("REPORT_LOCALE", new Locale("pt", "BR")); 
			parameters.put("titulo", titulo);
			
			JRDataSource jrds = null;
			// jrds = new JRBeanCollectionDataSource(listDTO);
			
			JasperPrint print = JasperFillManager.fillReport(path
					+ fileJasper + ".jasper", parameters, jrds);

			if (typeExport.equals("pdf")) {
				response.setContentType("application/pdf");   
				response.setHeader("Content-Disposition","attachment;filename="+fileJasper+".pdf");   
				JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
				response.flushBuffer();							
			} else if (typeExport.equals("xls")) {
				response.setContentType("application/octet-stream");   
				response.setHeader("Content-Disposition","attachment;filename="+fileJasper+".xls");   
				JRXlsExporter exporterXLS = new JRXlsExporter(); 
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print); 
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
				exporterXLS.exportReport();								
				response.flushBuffer();							
			} else if (typeExport.equals("rtf")) {
				response.setContentType("application/octet-stream");   
				response.setHeader("Content-Disposition","attachment;filename="+fileJasper+".rtf");   
				JRRtfExporter exporterRTF = new JRRtfExporter(); 
				exporterRTF.setParameter(JRExporterParameter.JASPER_PRINT, print); 
				exporterRTF.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
				exporterRTF.exportReport(); 				
				response.flushBuffer();							
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
		if (daoFactory != null) {
			daoFactory.close();	
		}					
	}
	
}