/*
 * Finds all the links within a HTML source of a web page.
 * 
 * @author Mats Nord
 */

package FindingWebLinks;

import edu.duke.*;
import java.io.*;

/**
 *
 * @author matnod
 */
public class FindingWebLinks {
    public StorageResource findURLs(String url) {
        URLResource ur = new edu.duke.URLResource(url);
        //Url to search for is set to lower case to get correct matches
        String UrlLow = url.toLowerCase();
        String source = ur.asString();
        //Original string with links is used to ectract the final result
        String sourceOrig = ur.asString();
        //Convert string to lower case to get exact match i either case
        source = source.toLowerCase();
        edu.duke.StorageResource store = new edu.duke.StorageResource();
        int start = 0;
        while (true) {
            //Get the index of the start tag of a link
            int index = source.indexOf("<a href=", start);
            //If nothing found, exit
            if (index == -1) {
                break;
            }
            //Index for the first qoute will be 9 chars forward
            int Quote1 = index + 9;
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
    public void findURLs2() {
        URLResource file = new edu.duke.URLResource("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
        for ( String item : file.words() ) {
            String itemLower = item.toLowerCase();
            int pos = itemLower.indexOf("youtube.com");
            if ( pos != -1 ) {
               int beg = item.lastIndexOf("\"", pos);
               int end = item.indexOf("\"", pos + 1);
               System.out.println(item.substring(beg + 1, end));
            }
        }
    }
    public static void main(String[] args) {
        FindingWebLinks wl = new FindingWebLinks();
        String url = "http://www.cs.duke.edu/~rodger/manylinks.html";
        StorageResource store = wl.findURLs(url);
        for ( String link : store.data() ) {
            System.out.println(link);
        }
    }
}
