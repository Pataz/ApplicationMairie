package util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author SI-MJLDH
 */
public class Reporter {

    public static void PDF(JasperPrint jasperPrint) throws IOException, JRException {
        String exportFileName = (new SimpleDateFormat("yyyyddMMHHmmssSSS")).format(Calendar.getInstance().getTime()) + ".pdf";

        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + exportFileName);
        httpServletResponse.addHeader("Content-type", "application/pdf");
        ServletOutputStream servletOutputStream;
        servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

        FacesContext.getCurrentInstance().responseComplete();

    }

    public static void DOCX(JasperPrint jasperPrint) throws JRException, IOException {
        String exportFileName = (new SimpleDateFormat("yyyyddMMHHmmssSSS")).format(Calendar.getInstance().getTime()) + ".docx";
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + exportFileName);
        httpServletResponse.addHeader("Content-type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRDocxExporter docxExporter = new JRDocxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public static void XLSX(JasperPrint jasperPrint) throws JRException, IOException {
        String exportFileName = (new SimpleDateFormat("yyyyddMMHHmmssSSS")).format(Calendar.getInstance().getTime()) + ".xlsx";
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + exportFileName);
        httpServletResponse.addHeader("Content-type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter docxExporter = new JRXlsxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public static void PPT(JasperPrint jasperPrint) throws JRException, IOException {
        String exportFileName = (new SimpleDateFormat("yyyyddMMHHmmssSSS")).format(Calendar.getInstance().getTime()) + ".pptx";
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + exportFileName);
        httpServletResponse.addHeader("Content-type", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRPptxExporter docxExporter = new JRPptxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public static void CSV(JasperPrint jasperPrint) throws JRException, IOException {
        String exportFileName = (new SimpleDateFormat("yyyyddMMHHmmssSSS")).format(Calendar.getInstance().getTime()) + ".csv";
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + exportFileName);
        httpServletResponse.addHeader("Content-type", "text/csv");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRCsvExporter csvExporter = new JRCsvExporter();
        csvExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        csvExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        csvExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public static String XHTML(JasperPrint jasperPrint) throws JRException, IOException {
        JRXhtmlExporter xhtmlExporter = new JRXhtmlExporter();
        StringBuffer sb;
        sb = new StringBuffer();
        xhtmlExporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, sb);
        xhtmlExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xhtmlExporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, JRHtmlExporterParameter.SIZE_UNIT_POINT);
        xhtmlExporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "<br/><br/><br/>");
        xhtmlExporter.setParameter(JRHtmlExporterParameter.HTML_HEADER,
                "<html>\n"
                + "<head>\n"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "<style type=\"text/css\">\n"
                + "a {text-decoration: none } .jrPage\n"
                + "{\n"
                + "    background-color: rgb(255, 255, 255)!important;\n"
                + "    border: 1px solid #cacaca!important;\n"
                + "    box-shadow: 0 0 4px rgba(0,0,0,0.1)!important;\n"
                + "    -moz-box-shadow: 0 0 4px rgba(0,0,0,0.1)!important;\n"
                + "    -webkit-box-shadow: 0 0 4px rgba(0,0,0,0.1)!important;\n"
                + "}\n"
                + "</style>\n"
                + "</head>\n"
                + "<body text=\"#000000\" link=\"#000000\" alink=\"#000000\"\n"
                + "vlink=\"#000000\">\n"
                + "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                + "<tr><td width=\"50%\">&nbsp;</td><td align=\"center\">");
        xhtmlExporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER,
                "</td><td width=\"50%\">&nbsp;</td></tr>\n"
                + "</table>\n"
                + "</body>\n"
                + "</html>");
        xhtmlExporter.exportReport();
        return sb.toString();
    }

    public static StreamedContent PDFStreamedContent(JasperPrint jasperPrint) throws IOException, JRException {
        String exportFileName = (new SimpleDateFormat("yyyyddMMHHmmssSSS")).format(Calendar.getInstance().getTime()) + ".pdf";
        StreamedContent ret;

        ByteArrayOutputStream outputStream;
        //now write the PDF content to the output stream
        byte[] bytes;
        outputStream = new ByteArrayOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        bytes = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ret = new DefaultStreamedContent(inputStream, "application/pdf", exportFileName);
        outputStream.close();
        return ret;
    }
    
    
    public static StreamedContent HtmlStreamedContent(JasperPrint jasperPrint) throws IOException, JRException {
        String exportFileName = (new SimpleDateFormat("yyyyddMMHHmmssSSS")).format(Calendar.getInstance().getTime()) + ".html";
        StreamedContent ret;

        ByteArrayOutputStream outputStream;
        //now write the PDF content to the output stream
        byte[] bytes;
        outputStream = new ByteArrayOutputStream();
        
        JRXhtmlExporter xhtmlExporter = new JRXhtmlExporter();
        //StringBuffer sb;
       // sb = new StringBuffer();
        xhtmlExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xhtmlExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        xhtmlExporter.exportReport();
        bytes = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        bytes = outputStream.toByteArray();
        ret = new DefaultStreamedContent(inputStream, "text/html", exportFileName);
       // ret = new DefaultStreamedContent(inputStream, "application/doc", exportFileName);
        outputStream.close();
        return ret;
    }
    
    public static StreamedContent DocStreamedContent(JasperPrint jasperPrint) throws IOException, JRException {
       String exportFileName = (new SimpleDateFormat("yyyyddMMHHmmssSSS")).format(Calendar.getInstance().getTime()) + ".docx";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRDocxExporter docxExporter = new JRDocxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        docxExporter.exportReport();
        StreamedContent ret;
        //now write the PDF content to the output stream
        byte[] bytes;
        bytes = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ret = new DefaultStreamedContent(inputStream, "application/doc", exportFileName);
       // ret = new DefaultStreamedContent(inputStream, "application/doc", exportFileName);
        outputStream.close();
        return ret;

    }
    
    
    
    public static StreamedContent XlsStreamedContent(JasperPrint jasperPrint) throws IOException, JRException {
       String exportFileName = (new SimpleDateFormat("yyyyddMMHHmmssSSS")).format(Calendar.getInstance().getTime()) + ".xlsx";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsxExporter xlsExporter = new JRXlsxExporter();
        xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        xlsExporter.exportReport();
        StreamedContent ret;
        //now write the PDF content to the output stream
        byte[] bytes;
        bytes = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ret = new DefaultStreamedContent(inputStream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", exportFileName);
       // ret = new DefaultStreamedContent(inputStream, "application/doc", exportFileName);
        outputStream.close();
        return ret;

    }
    public static String exportReportToPdf(JasperPrint jasperPrint) throws IOException, JRException {
         byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
       FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context
                .getExternalContext().getResponse();
        String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".pdf";
        response.addHeader("Content-disposition",
                "attachment;filename=" + outputFile);
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.setContentType("application/pdf");
        context.responseComplete();
        return null;
    }
}
