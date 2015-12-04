/**
 * GrayScaleConverter converts an image to grayscale by taking the average of each pixels RGB-value.
 * 
 * @author (Mats Nord) 
 * @version (2015-12-02)
 */
import edu.duke.*;
import java.io.*;

public class GrayScaleConverter {
/*Start with an image*/
    public ImageResource makeGray(ImageResource inImage) {
        /*Make a blank new image same sizw */
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        /*For each pixel in outImage */
        for ( Pixel pixel: outImage.pixels()){
            /*Read the corresponding pixel*/
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            /*Compute the pixels average = ( R + G + B )/3 */
            int average = (inPixel.getRed() + inPixel.getGreen() + inPixel.getBlue() )/3;
            /*Set pixel red to average */
            pixel.setRed(average);
            /*Set pixel green to average */
            pixel.setGreen(average);
            /*Set pixel blue to average */
            pixel.setBlue(average);
        }
    /*Return the outImage */
    return outImage;
    }
    
    public void selectFiles(){
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            ImageResource inImage = new ImageResource(f);
            ImageResource gray = makeGray(inImage);
            String fileName = inImage.getFileName();
            String newName = "copy-" + fileName;
            System.out.print(newName);
            gray.setFileName(f.getParentFile() + "/"+ newName);
            gray.save();
            gray.draw();
        }
    }
    
    public void grayBatch() {
        selectFiles();
    }
////    public static void main(String[] args) {
////        GrayScaleConverter gs = new GrayScaleConverter();
////        gs.selectFiles();
//    }
}