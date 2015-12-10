/*
 * To find start and stop codeon of genes. Extract valid genes that has an 
 * multiple of 3 between start and stop codon.
 */

package FindingAllGenes;

import edu.duke.*;
import java.io.*;
import java.math.*;
/**
 *
 * @author matnod
 */
public class FindAllGenes {
    public void printAllStarts(String dna) {
        int start = 0;
        dna = dna.toLowerCase();
        //loop until end of string
        while (true) {
            //find index of star codon ATG
            int loc = dna.indexOf("atg", start);
            if (loc == -1) {
                break;
            }
            System.out.println(dna + " Starts at: " + loc );
            //if we find a start then move 3 steps forward
            start = loc + 3;
        }
    }
    public void printAll(String dna) {
        int start = 0;
        String dna_orig = dna;
        dna = dna.toLowerCase();
        System.out.println("DNA String is:");
        System.out.println(dna_orig);
        System.out.println("Gene found is: ");
        while (true) {
            int tag = dna.indexOf("atg", start);
            if ( tag == -1 ){
                break;
            }
            //System.out.println("Start: " + tag );
            int end = findStopIndex( dna, tag + 3);
            //System.out.println("DNA: " + dna.substring(tag) + " End: " + end);
            if (end < dna.length() ) {
                System.out.println(dna_orig.substring(tag, end+3));
                //if a valid gene is found jump one idex forward and start checking next start codon
                start = end + 3;
            }
            else {
            //Jump 3 steps forward and look for the next start codon
            start = start + 3;
            }
        }
    }
    public StorageResource storeAll(String dna) {
        
        StorageResource store = new StorageResource();
        int start = 0;
        String dna_orig = dna;
        dna = dna.toLowerCase();
        while (true) {
            int tag = dna.indexOf("atg", start);
            if ( tag == -1 ){
                break;
            }
            int end = findStopIndex( dna, tag + 3);
            if (end < dna.length() ) {
                //System.out.println(dna_orig.substring(tag, end+3));
                store.add( dna_orig.substring(tag, end+3) );
                //if a valid gene is found jump three index forward and start checking next start codon
                start = end + 3;
            }
            else {
            //Jump 3 steps forward and look for the next start codon
            start = start + 3;
            }
        }
    return store;
    }
    
    public int findStopIndex( String dna, int index) {
     //from a gene and an index for the start codon, the function finds
     //all the stop codons, checks if it is a multiple of 3 and 
     //returns the stop codon closest to the start codon
        //dna = dna.toLowerCase();
//        System.out.println("DNA: " + dna );
//        System.out.println("index: " + index );
        int stop1 = dna.indexOf("tga", index);
//        System.out.println("Stop1: " + stop1 );
        if ( stop1 == -1 || (stop1 - index) % 3 != 0 ) {
            stop1 = dna.length();
        }
        int stop2 = dna.indexOf("taa", index);
//        System.out.println("Stop2: " + stop2 );
        if ( stop2 == -1 || (stop2 - index) % 3 != 0 ) {
            stop2 = dna.length();
        }
        int stop3 = dna.indexOf("tag", index);
        if ( stop3 == -1 || (stop3 - index) % 3 != 0 ) {
            stop3 = dna.length();
            
        }
        int min = Math.min(stop1, Math.min(stop2, stop3)); 
        return min;
    }
    public double cgRatio(String dna) {
        int numCG = 0;
        
        for ( int i = 0, n = dna.length(); i < n; i++ ) {
            char c = dna.charAt(i);
            if ( c == 'c' || c == 'g' ) {
                numCG += 1;
            }
            
        }
        //float fnumCG = numCG;
        return ( (float) numCG) / dna.length();
    }
    
    public void printGenes( StorageResource sr) {
        
        int numStrings = 0;
        int numcgRatio = 0;
        int numCTG = 0;
        int numLongest = 0;
        
        System.out.println("Genes with length > 60 or C-G-ratio > 0.35 found:");
        for ( String dna : sr.data() ) {
            if ( (dna.length() > 60) ) {
                System.out.println(dna);
                numStrings += 1;
            }
            if ( (cgRatio(dna) > 0.35) ) {
                System.out.println(dna);
                numcgRatio += 1;
            }
            if (dna.contains("ctg")) {
                numCTG += 1;
            }
            if ( dna.length() > numLongest ) {
                numLongest = dna.length();
            }
        }
        System.out.println("Number of Genes in total: " + sr.size() );
        System.out.println("Number of Genes with length > 60: " + numStrings );
        System.out.println("Number of Genes with C-G-ratio > 0.35: " + numcgRatio);
        System.out.println("Number of times the CTG codon appears: " + numCTG);
        System.out.println("Longest Gene has a length of: " + numLongest);
    }
    public void testAllStrings() {
        String g1 = "ATGAAATGAAAA";
        String g2 = "ccatgccctaataaatgtctgtaatgtaga";
        String g3 = "CATGTAATAGATGAATGACTGATAGATATGCTTGTATGCTATGAAAATGTGAAATGACCCA";
        printAll( g1 );
        printAll( g2 );
        printAll( g3 );
    }
    public void testStorageFinder() {
        FileResource file = new FileResource("brca1line.fa");
        StorageResource sr = new StorageResource();
        String g1 = "ATGAAATGAAAA";
        String g2 = "ccatgccctaataaatgtctgtaatgtaga";
        String g3 = "CATGTAATAGATGAATGACTGATAGATATGCTTGTATGCTATGAAAATGTGAAATGACCCA";
        //sr = storeAll( g1 );
        //sr = storeAll( g2 );
        //sr = storeAll( g3 );
        //System.out.println("File: " + file.toString());
        sr = storeAll(file.asString());
        //printAll(g3);
        //for (String dna : sr.data() ) {
        //    System.out.println("Storage: " + dna);
        //}
        printGenes(sr);
    }
    public static void main(String[] args) {
        FindAllGenes fg = new FindAllGenes();
        //fg.testAllStrings();
        fg.testStorageFinder();
    }
}
