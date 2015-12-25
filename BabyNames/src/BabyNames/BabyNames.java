/*
 * This class handles statistics regarding names given to newborns in USA for a given year.
 * The data is CSV files, with no header. Each year i contained in it's own file.
 * 
 */
package BabyNames;
import java.io.*;
import java.text.*;
import edu.duke.*;
import org.apache.commons.csv.*;

/**
 *
 * @author matnod
 */
public class BabyNames {
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        //Method takes year, name and gender in the files specified by user and returns
        //the total number of births for a year of the names wich are ranked higher than name.
        //rec: (0) = name, (1) = gender, (2) = Total born.
        int total_births = 0;
        String filename = ""; //File name for the year
        
        //find and open the file for the expected year.
        filename = "yob" + String.valueOf(year)+".csv";
        FileResource fr = new FileResource("data/" + filename);
        for (CSVRecord rec : fr.getCSVParser(false) ) {
            //when name is reached all higher ranked names have been calculated.
            if ( rec.get(0).equals(name) &&  rec.get(1).equals(gender) ) {
                break;
            }
            //if gender and not name then calaculate total births
            if ( rec.get(1).equals(gender) && rec.get(0) != name ) {
                total_births += Integer.parseInt(rec.get(2));
            }
        }
    return total_births;    
    }
    public double getAverageRank(String name, String gender) {
        //Method takes a name and a gender in the files specified by user and returns
        //the avrage rank found for a given name and gender. Returns -1 if name not found.
        String filename = "";
        int year = 0;
        int current_rank = 0;
        int total_rank = 0;
        int counts = 0;
        double average_rank = 0;
        
        DirectoryResource dr = new DirectoryResource();
        for ( File file : dr.selectedFiles() ) {
            //get the year from the filename
            filename = file.getName().substring(3, 7);
            year = Integer.parseInt(filename);
            current_rank = getRank(year, name, gender);
            if ( current_rank != -1 ) {
                total_rank += current_rank;
                counts += 1;
            }
            
        }
        if ( total_rank != 0 ){
            average_rank = total_rank/(double) counts;
        }
        else {
            average_rank = -1;
        }
    return average_rank;
    }
    public int yearOfHighestRank(String name, String gender) {
        //Method check for the highest rank in the files specified by user and returns 
        //the highest rank found for a given name and gender. Returns -1 if name not found.
        String filename = "";
        int max_rank = -1;
        int current_rank = 0;
        int year = 0;
        int year_highest = -1;
        DirectoryResource dr = new DirectoryResource();
        for ( File file : dr.selectedFiles() ) {
            //get the year from the filename
            filename = file.getName().substring(3, 7);
            year = Integer.parseInt(filename);
            current_rank = getRank(year, name, gender);
            System.out.println("Name: " + name + " is ranked " + current_rank + " in " + String.valueOf(year) );
            //first run
            if ( current_rank != -1 && max_rank == -1 ) {
                year_highest = year;
                max_rank = current_rank;
            }
            //if name is ranked in the file and ranking higher than previous ranking
            if ( current_rank != -1 && current_rank < max_rank ) {
                 max_rank = current_rank;
                 year_highest = year;
            }
            
        }
        return year_highest;
    }
    public void WhatIsNameInYear(int year, int newYear, String name, String gender) {
        //Method takes year (1), year (2), name and gender and returns the name for year(2)
        //that has the same rank as the name year(1).
        int rank = getRank(year, name, gender);
        String genderText = "";
        String newName = getName(newYear, rank, gender);
        if ( gender.equals("M") ) {
            genderText = "he";
        }
        else {
            genderText = "she";
        }
        System.out.println( name + " born in " + String.valueOf(year) + " would be " + newName + " if " + genderText + " was born in " + String.valueOf(newYear) );
    }
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
    public void testgetTotalBirthsRankedHigher(){
        int Year = 1990;
        String name = "Drew";
        String gender = "M";
        int total_births = 0;
        total_births = getTotalBirthsRankedHigher(Year, name, gender);
        System.out.println("Total births for names ranked higer than " + name + ": " + String.valueOf(total_births));
    }
    public void testgetAverageRank() {
        String name = "Robert";
        String gender = "M";
        double average_rank = getAverageRank(name, gender);
        DecimalFormat df = new DecimalFormat("#.00000");
        System.out.println("Name: " + name + " Average rank: " + df.format(average_rank) + " Gender: " + gender);
    }
    public void testyearOfHighestRank() {
        String name = "Mich";
        int Year = 0;
        String gender = "M";
        Year = yearOfHighestRank(name, gender);
        System.out.println("Name: " + name + " Highest rank year: " + String.valueOf(Year) + " Gender: " + gender);
    }
    public void testWhatIsNameInYear() {
        String name = "Owen";
        int Year = 1974;
        int newYear = 2014;
        String gender = "M";
        WhatIsNameInYear(Year, newYear, name, gender);
        
    }
    public static void testBabyNames() {
        //FileResource fr = new FileResource("data/example-small.csv");
        FileResource fr = new FileResource("data/yob1905.csv");
        BabyNames bm = new BabyNames();
        bm.totalBirths(fr);
    }
    public void testName() {
        int Rank = 450;
        int year = 1982;
        String name = "";
        String gender = "M";
        name = getName(year, Rank, gender);
        System.out.println("Name: " + name + " Year: " + String.valueOf(year) + " Gender: " + gender + " Rank: " + String.valueOf(Rank) );
    }
    public void testRank() {
        int Rank = -1;
        int year = 1960;
        String name = "Emily";
        String gender = "F";
        Rank = getRank(year, name, gender);
        System.out.println("Name: " + name + " Year: " + String.valueOf(year) + " Gender: " + gender + " Rank: " + String.valueOf(Rank) );
    }
    public static void main(String[] args ){
        BabyNames bm = new BabyNames();
        //testBabyNames();
        //bm.testRank();
        //bm.testName();
        //bm.testWhatIsNameInYear();
        //bm.testyearOfHighestRank();
        //bm.testgetAverageRank();
        bm.testgetTotalBirthsRankedHigher();
    }
}
