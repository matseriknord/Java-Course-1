/*
 * This class handles statistics regarding names given to newborns in USA for a given year.
 * The data is CSV files, with no header. Each year i contained in it's own file.
 * 
 */
package BabyNames;
import java.io.*;
import edu.duke.*;
import org.apache.commons.csv.*;

/**
 *
 * @author matnod
 */
public class BabyNames {
    public String getName(int year, int rank, String gender) {
        //Method takes a year, a rank and a gender gender ("F", "M")
        //and returns the name corresponding to the rank. If name not
        //foind "NO NAME" is returned.
        //rec: (0) = name, (1) = gender, (2) = Total born.
        String name = "NO NAME";
        int current_rank = -1; //Presume name not found
        int final_rank = 0;
        String filename = ""; //File name for the year
        //find and open the file for the expected year.
        filename = "yob" + String.valueOf(year)+".csv";
        System.out.println(filename);
        FileResource fr = new FileResource("data/" + filename);
        for ( CSVRecord rec : fr.getCSVParser(false) ) {
            if ( current_rank == -1 ) {
                current_rank += 2;
            }
            if ( rec.get(1).equals(gender) ) {
                final_rank += current_rank;
            }
            if ( final_rank == rank ) {
                name = rec.get(0);
            }
        }
        return name;
    }
    public int getRank(int year, String name, String gender) {
        //Method takes a year, a name and a gender ("F", "M")
        //and returns the rank in the meaning of total number born that year.
        //rec: (0) = name, (1) = gender, (2) = Total born. 
        //-1 means the name was not found.
        int current_rank = -1; //Presume name not found
        int final_rank = -1;
        String filename = ""; //File name for the year
        //find and open the file for the expected year
        filename = "yob" + String.valueOf(year)+".csv";
        System.out.println(filename);
        FileResource fr = new FileResource("data/" + filename);
        for ( CSVRecord rec : fr.getCSVParser(false) ) {
            if ( current_rank == -1 ) {
                    current_rank += 2;
            }
            if ( rec.get(0).equals(name) && rec.get(1).equals(gender) ) {
                final_rank = current_rank;
            }
            if ( rec.get(1).equals(gender) ) {
                current_rank += 1;
            }
        }
    return final_rank;
    }
        
    public void totalBirths(FileResource fr) {
        //Method provides statistics for one file.
        //Total born, unique girls and boys names.
        //rec: (0) = name, (1) = gender, (2) = Total born. 
        int TotalBabiesBorn = 0;
        int TotalGirlsBorn = 0;
        int TotalBoysBorn = 0;
        int UniqueGirlsNames = 0;
        int UniqueBoysNames = 0;
    
        for (CSVRecord rec : fr.getCSVParser(false)) {
        if ( rec.get(1).equals("F") ) {
            UniqueGirlsNames += 1;
            TotalGirlsBorn += Integer.parseInt(rec.get(2));
        }
        if ( rec.get(1).equals("M") ) {
            UniqueBoysNames += 1;
            TotalBoysBorn += Integer.parseInt(rec.get(2));
        }
        TotalBabiesBorn += Integer.parseInt( rec.get(2) );
    }
    System.out.println("Total babies born: " + TotalBabiesBorn);
    System.out.println("Total girls born: " + TotalGirlsBorn);
    System.out.println("Total boys born: " + TotalBoysBorn);
    System.out.println("Unique girls names: " + UniqueGirlsNames);
    System.out.println("Unique boys names: " + UniqueBoysNames );
    
    }
    public static void testBabyNames() {
        //FileResource fr = new FileResource("data/example-small.csv");
        FileResource fr = new FileResource("data/yob2014.csv");
        BabyNames bm = new BabyNames();
        bm.totalBirths(fr);
    }
    public void testName() {
        int Rank = 5382;
        int year = 2012;
        String name = "";
        String gender = "F";
        name = getName(year, Rank, gender);
        System.out.println("Name: " + name + " Year: " + String.valueOf(year) + " Gender: " + gender + " Rank: " + String.valueOf(Rank) );
    }
    public void testRank() {
        int Rank = -1;
        int year = 2012;
        String name = "oewrteyddft";
        String gender = "F";
        Rank = getRank(year, name, gender);
        System.out.println("Name: " + name + " Year: " + String.valueOf(year) + " Gender: " + gender + " Rank: " + String.valueOf(Rank) );
    }
    public static void main(String[] args ){
        BabyNames bm = new BabyNames();
        //testBabyNames();
        //bm.testRank();
        bm.testName();
    }
}
