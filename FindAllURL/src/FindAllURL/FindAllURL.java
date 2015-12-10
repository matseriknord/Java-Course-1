/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FindAllURL;

import edu.duke.*;
import java.io.*;
/**
 *
 * @author matnod
 */
public class FindAllURL {
    public StorageResource findURLs(String url) {
        FileResource ur = new edu.duke.FileResource(url);
        String source = ur.asString();
        //Original string with links is used to ectract the final result
        String sourceOrig = ur.asString();
        //Convert string to lower case to get exact match i either case
        source = source.toLowerCase();
        StorageResource store = new StorageResource();
        int start = 0;
        while (true) {
            //Get the index of the start tag of a link
            int index = source.indexOf("href=", start);
            //If nothing found, exit
            if (index == -1) {
                break;
            }
            //Index for the first qoute will be 9 chars forward
            int Quote1 = index + 6;
            //Look for the last qoute in the string. \ needs escaping
            int Quote2 = source.indexOf("\"", Quote1);
            //Extract the substring between first and last qoute
            String link = source.substring(Quote1, Quote2);
            //If we have a valid http-tag and the link contains the search url
            //then store the link in original case
            if ( link.startsWith("http") ) {
                //Use the original strings to preserve the links
                store.add(sourceOrig.substring(Quote1, Quote2));
            }
            //Jump to next character and search for next link
            start = Quote2 + 1;
            
        }
        return store;
    }
    public void testURLWithStorage() {
        FindAllURL fall = new FindAllURL();
        String file = "newyorktimes.html";
        String url = "http://www.dukelearntoprogram.com/course2/data/manylinks.html";
        StorageResource store = fall.findURLs(file);
        int numLinks = 0;
        int numSecure = 0;
        int dotCom = 0;
        int endsCom = 0;
        int noDots = 0;
        for ( String link : store.data() ) {
            System.out.println(link);
            if ( link.startsWith("http") ) {
                numLinks += 1;
            }
            if ( link.startsWith("https") ) {
                numSecure += 1;
            }
            if ( link.contains(".com") ) {
                dotCom += 1;
            }
            if ( link.endsWith(".com") || link.endsWith(".com/") ) {
                endsCom += 1;
            }
            int start = 0;
            while (true) {
                int index = link.indexOf(".", start);
                if (index == -1) {
                    break;
                }
                start = index + 1;
                noDots += 1;
            }
        }
        System.out.println("================================================" );        
        System.out.println("Total number of URLs via size: " + store.size() );
        System.out.println("Total number of URLs via expr: " + numLinks );
        System.out.println("Total number of https: " + numSecure );
        System.out.println("Links that contain \".com\": " + dotCom );
        System.out.println("Links that ends with \".com\" or \".com/\": " + endsCom );
        System.out.println("Total number of \".\": " + noDots );
    }
    
    public static void main(String[] args) {
        FindAllURL fall = new FindAllURL();
        fall.testURLWithStorage();
        
    }
   
}
