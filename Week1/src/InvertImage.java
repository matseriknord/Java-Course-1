
/**
 * InvertImage inverts an image to by taking the each pixels RGB-value and subtract from 255.
 * 
 * @author (Mats Nord) 
 * @version (2015-12-03)
 */
import edu.duke.*;
import java.io.*;

public class InvertImage {
/*Start with an image*/
    public ImageResource invert (ImageResource inImage) {
        /*Make a blank new image same sizw */
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        /*For each pixel in outImage */
        for ( Pixel pixel: outImage.pixels()){
            /*Read the corresponding pixel*/
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            /*Set pixel red to 255 - current value*/
            pixel.setRed(255 - inPixel.getRed());
            /*Set pixel green 255 - current value*/
            pixel.setGreen(255 - inPixel.getGreen());
            /*Set pixel blue 255 - current value */
            pixel.setBlue(255 - inPixel.getBlue());
        }
    /*Return the outImage */
    return outImage;
    }
    
    public void selectInvFiles(){
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            ImageResource inImage = new ImageResource(f);
            ImageResource inv = invert(inImage);
            String fileName = inImage.getFileName();
            String newName = "copy-" + fileName;
            System.out.print(newName);
            inv.setFileName(f.getParentFile() + "/"+ newName);
            inv.save();
            inv.draw();
        }
    }
    
    public void invBatch() {
        selectInvFiles();
    }
    
    public static void main(String[] args) {
        InvertImage iI = new InvertImage();
        iI.selectInvFiles();
    }
}

