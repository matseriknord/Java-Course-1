/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matnod
 */
import edu.duke.*;
import java.io.*;

public class TagFinder {
    public String findProtein(String dna) {
        //System.out.println("Before Case: " + dna);
        dna = dna.toLowerCase();
        //System.out.println("After Case: " + dna);
        int start = dna.indexOf("atg");
        //System.out.println("Start: " + start);
        if (start == -1) {
            return "";
        }
        int tag = dna.indexOf("tag", start + 3);
        //System.out.println("tag: " + tag);
        int tga = dna.indexOf("tga", start + 3);
        //System.out.println("tga: " + tga);
        int taa = dna.indexOf("taa", start + 3);
        //System.out.println("taa: " + taa);
        //int stop = dna.indexOf("tag", start + 3);
       /* System.out.println("tag -start % 3: " + (tag - start)%3);
        System.out.println("tga -start % 3: " + (tga - start)%3);
        System.out.println("taa -start % 3: " + (taa - start)%3);
        System.out.println("Sub tag: "+ dna.substring(start, tag + 3));
        System.out.println("Sub tga: "+ dna.substring(start, tga + 3));
        System.out.println("Sub taa: "+ dna.substring(start, taa + 3));*/
        if ( ((tag - start) % 3 == 0) && (tag > -1) ) {
            return dna.substring(start, tag + 3);
        }
        else if ( ((tga - start) % 3 == 0) && (tga > -1) )  {
            return dna.substring(start, tga + 3);
        }
        else if ( ((taa - start) % 3 == 0) && (taa > -1) ) {
            return dna.substring(start, taa + 3); 
         }
        else {
            return "";
        }
    }
public void testing() {
    //String a = "AATGCTAGTTTAAATCTGA";
   //String ap = "atgctagtttaaatctga";
    //String a = "ataaactatgttttaaatgt";
    //String ap = "atgttttaa";
    //String a = "acatgataacctaag";
    //String ap = ""; 
    String a = "ataaactatgtttctctcttaactacac";
    String ap = "atgtttctctcttaa";
    //String a = "ataaactatgtttctctcttagtacac";
    //String ap = "atgtttctctcttag";
    String result = findProtein( a );
    if (ap.equals(result)) {
        System.out.println("success for " + ap + " at length: " + ap.length());
        System.out.println("Stop Codon: " + stopCodon(result));
    }
    else {
        System.out.println("mistake for input: " + a);
        System.out.println("Got: " + result);
        System.out.println("Not: " + ap);
    }
}
public String stopCodon(String dna) {
    return dna.substring(dna.length()-3,dna.length() );
}
public static void main (String[] args) {
    TagFinder tf = new TagFinder();
    tf.testing();
}
}
