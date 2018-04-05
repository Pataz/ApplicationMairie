package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import jpa.Agent;
import util.pojo.Month;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.ByteArrayImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.template.formatter.NullImageBehaviour;
import java.util.HashMap;

public class Docx {

    private String pathIn;
    private String pathOut;

    public Docx() {

        pathIn = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/modeles") + File.separator;
        pathOut = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/output") + File.separator;
        System.out.println("chemin in==>" + pathIn);
        System.out.println("chemin in==>" + pathOut);
    }

    public Docx(String pathIn, String pathOut) {
        this.pathIn = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathIn) + File.separator;
        this.pathOut = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathOut) + File.separator;
    }

    public String generate(String inputFile, Map<String, Object> maps) throws IOException, XDocReportException {
        InputStream in = new FileInputStream(new File(pathIn + inputFile));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        // 3) Create context Java model
        IContext context = report.createContext();
        context.putMap(maps);
        String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".docx";
        // 4) Generate report by merging Java model with the Docx
        //OutputStream out = new FileOutputStream(new File(pathOut + outputFile));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        report.process(context, out);
        try {
            sendFile(out, outputFile);
        } catch (Exception ex) {
            Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputFile;
    }

    public String generate(String inputFile, Map<String, Object> maps, Agent agent, byte[] image) throws IOException, XDocReportException {
        InputStream in = new FileInputStream(new File(pathIn + inputFile));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        // 3) Create context Java model
        IContext context = report.createContext();
        context.putMap(maps);
//        context.put("DEMANDES", dossier.getDemandes());
        IImageProvider barCodeImage = new ByteArrayImageProvider(image);
        FieldsMetadata metadata = new FieldsMetadata();
        metadata.addFieldAsImage("barcode", NullImageBehaviour.KeepImageTemplate);
        metadata.addFieldAsImage("barcode2", NullImageBehaviour.KeepImageTemplate);
        report.setFieldsMetadata(metadata);
        context.put("barcode", barCodeImage);
        context.put("barcode2", barCodeImage);
        String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".docx";
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        report.process(context, out);
        try {
            sendFile(out, outputFile);
        } catch (Exception ex) {
            Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return outputFile;
    }

    public ByteArrayOutputStream generateConvocCabinetArmel(String inputFile) throws IOException, XDocReportException {
        InputStream in = new FileInputStream(new File(pathIn + inputFile));
        System.out.println("chemin===>" + new File(pathIn + inputFile).getAbsolutePath());
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        System.out.println("report===>" + report.toString());
        // 3) Create context Java model
        IContext context = report.createContext();
//        context.putMap(maps);
//        context.put("PARTIES", listPartieOutput);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        report.process(context, out);
        //report.process(context, new FileOutputStream("c:/test/montest.docx"));

        try {
            return out;
        } catch (Exception ex) {
        }
        return null;
    }

    public String generate1(String inputFile, Map<String, Object> maps) throws IOException, XDocReportException {
        System.out.println("doc name ===> " + inputFile);
        InputStream in = new FileInputStream(new File(pathIn + inputFile));
        System.out.println("après InputStream  ===> ");
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        System.out.println("après report  ===>" + report.getId());
        // 3) Create context Java model
        IContext context = report.createContext();
        System.out.println("après context  ===>");
        context.putMap(maps);
        context.put("DOSSIERS", "Esperance");
        String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".docx";
        System.out.println("après outputFile  ===>" + outputFile);
        // 4) Generate report by merging Java model with the Docx
        //OutputStream out = new FileOutputStream(new File(pathOut + outputFile));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        report.process(context, out);
        try {
            sendFile(out, outputFile);
        } catch (Exception ex) {
//            Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputFile;
    }

    public ByteArrayOutputStream generateConv(String inputFile, Map<String, Object> maps) throws IOException, XDocReportException {
        System.out.println("debut docx z ");
        InputStream in = new FileInputStream(new File(pathIn + inputFile));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        // 3) Create context Java model
        //listPartieOutput = new ArrayList<>();
        IContext context = report.createContext();
        System.out.println("taille " + maps.size());
        context.putMap(maps);
        //String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".docx";
        // 4) Generate report by merging Java model with the Docx
        //OutputStream out = new FileOutputStream(new File(pathOut + outputFile));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        //report.process(context, new FileOutputStream("c:/test/montest.docx"));
        report.process(context, out);

        try {
            return out;
        } catch (Exception ex) {
            Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ByteArrayOutputStream generateDec(String inputFile, Map<String, Object> maps, byte[] image) throws IOException, XDocReportException {
        InputStream in = new FileInputStream(new File(pathIn + inputFile));
        System.out.println("chemin===>" + new File(pathIn + inputFile).getAbsolutePath());
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        System.out.println("report===>" + report);
        // 3) Create context Java model
        IContext context = report.createContext();
        context.putMap(maps);

        IImageProvider barCodeImage = new ByteArrayImageProvider(image);
        FieldsMetadata metadata = new FieldsMetadata();
        metadata.addFieldAsImage("barcode", NullImageBehaviour.KeepImageTemplate);
        report.setFieldsMetadata(metadata);
        context.put("barcode", barCodeImage);

        //String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".docx";
        // 4) Generate report by merging Java model with the Docx
        //OutputStream out = new FileOutputStream(new File(pathOut + outputFile));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        report.process(context, out);
        try {
            return out;
        } catch (Exception ex) {
            Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ByteArrayOutputStream generateNoteFactum(String inputFile, Map<String, Object> maps) throws IOException, XDocReportException {
        InputStream in = new FileInputStream(new File(pathIn + inputFile));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        // 3) Create context Java model
        IContext context = report.createContext();
        context.putMap(maps);

        //String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".docx";
        // 4) Generate report by merging Java model with the Docx
        //OutputStream out = new FileOutputStream(new File(pathOut + outputFile));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        report.process(context, out);
        try {
            return out;
        } catch (Exception ex) {
            Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /* public String generateJournal(String inputFile, Map<String, Object> maps, TreeMap<String, Log> logs) throws IOException, XDocReportException {
     InputStream in = new FileInputStream(new File(pathIn + inputFile));
     IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
     // 3) Create context Java model
     IContext context = report.createContext();
     context.putMap(maps);
     context.put("LOGS", logs);
     String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".docx";
     // 4) Generate report by merging Java model with the Docx
     //OutputStream out = new FileOutputStream(new File(pathOut + outputFile));
     ByteArrayOutputStream out = new ByteArrayOutputStream();

     report.process(context, out);
     try {
     sendFile(out, outputFile);
     } catch (Exception ex) {
     Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
     }
     return outputFile;
     }*/
    public String generateCalendrier(String inputFile, Map<String, Object> maps, List<Month> months) throws IOException, XDocReportException {
        InputStream in = new FileInputStream(new File(pathIn + inputFile));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        // 3) Create context Java model
        IContext context = report.createContext();
        context.putMap(maps);
        context.put("MONTHS", months);
        String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".docx";
        // 4) Generate report by merging Java model with the Docx
        //OutputStream out = new FileOutputStream(new File(pathOut + outputFile));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        report.process(context, out);
        try {
            sendFile(out, outputFile);
        } catch (Exception ex) {
            Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputFile;
    }

    public ByteArrayOutputStream generate(String inputFile, Map<String, Object> maps, boolean toStream) throws IOException, XDocReportException {
        InputStream in = new FileInputStream(new File(pathIn + inputFile));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        // 3) Create context Java model
        IContext context = report.createContext();
        context.putMap(maps);

        String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".docx";
        // 4) Generate report by merging Java model with the Docx
        //OutputStream out = new FileOutputStream(new File(pathOut + outputFile));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        report.process(context, out);
        return out;
    }

//    public String generate(String inputFile, Map<String, Object> maps, List<DossierOutput> listDossier) throws IOException, XDocReportException {
//        System.out.println("la liste est docx ===> " + listDossier);
//        InputStream in = new FileInputStream(new File(pathIn + inputFile));
//        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
//        // 3) Create context Java model
//        IContext context = report.createContext();
//        context.putMap(maps);
//        context.put("DOSSIERS", listDossier);
//        String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".docx";
//        // 4) Generate report by merging Java model with the Docx
//        //OutputStream out = new FileOutputStream(new File(pathOut + outputFile));
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        report.process(context, out);
//        try {
//            sendFile(out, outputFile);
//        } catch (Exception ex) {
//            Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return outputFile;
//    }
    public String generate(String inputFile, Map<String, Object> maps, String tableName, List<String> fields, List<Map<String, Object>> rows) throws IOException, XDocReportException {
        InputStream in = new FileInputStream(new File(pathIn + inputFile));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        // 3) Create context Java model
        IContext context = report.createContext();
        context.putMap(maps);
        //Put the table
        FieldsMetadata metadata = new FieldsMetadata();
        for (String field : fields) {
            metadata.addFieldAsList(tableName + "." + field);
        }
        report.setFieldsMetadata(metadata);
        context.put(tableName, rows);
        String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".docx";
        // 4) Generate report by merging Java model with the Docx
        //OutputStream out = new FileOutputStream(new File(pathOut + outputFile));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        report.process(context, out);
        try {
            sendFile(out, outputFile);
        } catch (Exception ex) {
            Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pathOut + outputFile;
    }

    public String toPDF(String inputFile, Map<String, Object> maps) throws FileNotFoundException, XDocReportException, IOException {
        String output1 = generate(inputFile, maps);
        InputStream in = new FileInputStream(new File(pathOut + output1));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        // 2) Create context Java model
        IContext context = report.createContext();
        // 3) Generate report by merging Java model with the Docx
        String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".pdf";
        OutputStream out = new FileOutputStream(pathOut + outputFile);
        // report.process(context, out);
        Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
        report.convert(context, options, out);
        return outputFile;
    }

    public String toPDF(String inputFile, Map<String, Object> maps, String tableName, List<String> fields, List<Map<String, Object>> rows) throws FileNotFoundException, XDocReportException, IOException {
        String output1 = generate(inputFile, maps, tableName, fields, rows);
        InputStream in = new FileInputStream(new File(pathOut + output1));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        // 2) Create context Java model
        IContext context = report.createContext();
        // 3) Generate report by merging Java model with the Docx
        String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".pdf";
        OutputStream out = new FileOutputStream(pathOut + outputFile);
        // report.process(context, out);
        Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
        report.convert(context, options, out);
        return outputFile;
    }

    public String toHTML(String inputFile, Map<String, Object> maps) throws FileNotFoundException, XDocReportException, IOException {
        String output1 = generate(inputFile, maps);
        InputStream in = new FileInputStream(new File(pathOut + output1));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        // 2) Create context Java model
        IContext context = report.createContext();
        // 3) Generate report by merging Java model with the Docx
        String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".html";
        OutputStream out = new FileOutputStream(pathOut + outputFile);
        // report.process(context, out);
        Options options = Options.getTo(ConverterTypeTo.XHTML).via(ConverterTypeVia.XWPF);
        report.convert(context, options, out);
        return outputFile;
    }

    public String toHTML(String inputFile, Map<String, Object> maps, String tableName, List<String> fields, List<Map<String, Object>> rows) throws FileNotFoundException, XDocReportException, IOException {
        String output1 = generate(inputFile, maps, tableName, fields, rows);
        InputStream in = new FileInputStream(new File(pathOut + output1));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        // 2) Create context Java model
        IContext context = report.createContext();
        // 3) Generate report by merging Java model with the Docx
        String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".html";
        OutputStream out = new FileOutputStream(pathOut + outputFile);
        // report.process(context, out);
        Options options = Options.getTo(ConverterTypeTo.XHTML).via(ConverterTypeVia.XWPF);
        report.convert(context, options, out);
        return outputFile;
    }

    public void flushToBrowser(File file) {

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext context = (ServletContext) facesContext.getExternalContext().getContext();
            HttpServletResponse response = (HttpServletResponse) facesContext
                    .getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
            response.setContentLength((int) file.length());
            FileInputStream input = new FileInputStream(file);
            BufferedInputStream buf = new BufferedInputStream(input);

            int readBytes = 0;
            while ((readBytes = buf.read()) != -1) {
                response.getOutputStream().write(readBytes);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
            facesContext.responseComplete();
            facesContext.renderResponse();
            buf.close();

        } catch (IOException ex) {
            Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    public void flushZipToBrowser(File file) {

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext
                    .getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
            response.setContentLength((int) file.length());
            FileInputStream input = new FileInputStream(file);
            BufferedInputStream buf = new BufferedInputStream(input);

            int readBytes = 0;
            while ((readBytes = buf.read()) != -1) {
                response.getOutputStream().write(readBytes);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
            facesContext.responseComplete();
            facesContext.renderResponse();
            buf.close();

        } catch (IOException ex) {
            Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    public String getPathIn() {
        return pathIn;
    }

    public void setPathIn(String pathIn) {
        this.pathIn = pathIn;
    }

    public String getPathOut() {
        return pathOut;
    }

    public void setPathOut(String pathOut) {
        this.pathOut = pathOut;
    }

    public void sendFile(ByteArrayOutputStream out, String fileName) throws Exception {
        System.out.println("fileName ==>  " + fileName);
//        System.out.println("out ==>  "+out);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
//        response.setContentLength((int) file.length());
//        FileInputStream fis = new FileInputStream(file);
//        BufferedInputStream bis = new BufferedInputStream(fis);
//        int readBytes;
        try {

//            while ((readBytes = bis.read()) != -1) {
//                response.getOutputStream().write(readBytes);
//            }
            response.getOutputStream().write(out.toByteArray());
//            while ((ch = bis.read(buf)) >= 0) {
//                response.getOutputStream().write(buf, 0, ch);
//            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
//            bis.close();
//            fis.close();
            facesContext.responseComplete();
            facesContext.renderResponse();

        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        } finally {
        }

    }

    public ByteArrayOutputStream generateRequisitoire(String inputFile, Map<String, Object> maps) throws IOException, XDocReportException {
        InputStream in = new FileInputStream(new File(pathIn + inputFile));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        // 3) Create context Java model
        IContext context = report.createContext();
        context.putMap(maps);

        //String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".docx";
        // 4) Generate report by merging Java model with the Docx
        //OutputStream out = new FileOutputStream(new File(pathOut + outputFile));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        report.process(context, out);
        try {
            return out;
        } catch (Exception ex) {
            Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String generateRequisitoireDownload(String inputFile, Map<String, Object> maps) throws IOException, XDocReportException {
//        System.out.println("la liste est docx ===> "+listDossier);
        InputStream in = new FileInputStream(new File(pathIn + inputFile));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        // 3) Create context Java model
        IContext context = report.createContext();
        context.putMap(maps);
//        context.put("DOSSIERS", listDossier);
        String outputFile = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date()) + ".docx";
        // 4) Generate report by merging Java model with the Docx
        //OutputStream out = new FileOutputStream(new File(pathOut + outputFile));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        report.process(context, out);
        try {
            sendFile(out, outputFile);
        } catch (Exception ex) {
            Logger.getLogger(Docx.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputFile;
    }  
}
