package com.damiskot.classes;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Component
public class Barcode {

    public String saveBarcode(String codeType,String text,Boolean textVisible) throws IOException {
        String path = "src/main/resources/barcode/tempBarcode.png";
        URL url;
        if(textVisible){
            url = new URL("http://bwipjs-api.metafloor.com/?bcid=" + codeType + "&text=" + text + "&includetext");
        }else{
            url = new URL("http://bwipjs-api.metafloor.com/?bcid=" + codeType + "&text=" + text);
        }
        RenderedImage renderedImage  = ImageIO.read(url);
        File output = new File(path);
        ImageIO.write(renderedImage,"png",output);
        return path;
    }

    public boolean deleteTempBarcode(){
        File temp = new File("src/main/resources/barcode/tempBarcode.png");
        return temp.delete();
    }
}