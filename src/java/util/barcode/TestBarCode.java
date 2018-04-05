/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.barcode;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author xess
 * 
 */
public class TestBarCode {
    public static void main(String[] args) throws IOException {
        SimpleBarCodeGenerator gen = new SimpleBarCodeGenerator("code39", "image/x-png", 150);
        Path p = Paths.get("c:", "xess","bc.png");
        BarCode b = gen.generateBarCode("000251/2014/1101");
        Files.write(p, b.getData());
        
    }    
}
