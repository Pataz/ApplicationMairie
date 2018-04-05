/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package util.barcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.codabar.CodabarBean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

/**
 *
 * @author xess
 */
public class SimpleBarCodeGenerator {
    
    /**     
     * Resolution de l'image rprésentant le code barre. défaut 150.
     */
    int dpi = 150;
    
    /**
     * Type de du code barre à utiliser. par exemple code39, ean13 etc.
     */
    String codeType = "code39";
    /**
     * Type mime de l'image. Par défaut image/x-png
     */
    String mediaType = "image/x-png";
    
    /**
     * obet générique de générateur de code barre. Une instance est assignée par prolymorphisme suivant le type
     */
    AbstractBarcodeBean generator;
    
    BitmapCanvasProvider canvas;
    
    /**
     * Construit une instance avec des valeurs par défaut.
     * <pre>
     * code type = ean13
     * image mime type = image/x-png
     * resolution (dpi) = 150
     * </pre>
     */
    public SimpleBarCodeGenerator() {
        init();
    }   
    
    public SimpleBarCodeGenerator(String codeType, String mediaType, int dpi) {
        this.codeType = codeType;
        this.mediaType = mediaType;
        this.dpi = dpi;   
        init();
    } 
    
    /**
     * Définit le générateur et assigne des valeur par défaut à d'autres paramètres
     * de génération de code barre non modifiable dans le programme.
     */
    public final void init() {
        switch(codeType.toLowerCase())  {
            case "codabar" :
                generator = new CodabarBean();
                break;
            case "ean13" :
                generator = new EAN13Bean();
                break;
            default :
                generator = new Code39Bean();            
        }              
        generator.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar width exactly one pixel
        //generator.setWideFactor(3);
        generator.doQuietZone(false);        
        
    }
    
    /**
     * 
     * @param code  la chaine numérique dont veut générer le code
     * @return une instance de @{link BarCode}.
     * @throws IOException
     */
    public BarCode generateBarCode(String code) throws IOException {
        try(ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            canvas = new BitmapCanvasProvider(out, mediaType, dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            generator.generateBarcode(canvas, code);
            canvas.finish();
            return new BarCode(mediaType, out.toByteArray());
        }
    }    
}
