/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package WhichCountries;

import edu.duke.*;
import org.apache.commons.csv.*;
/**
 *
 * @author matnod
 */
public class WhichCountries {
    public String countryInfo(CSVParser parser, String country ){
        String allExports ="";
        for ( CSVRecord record : parser ) {
            String export = record.get("Country");
            if ( export.equals(country) ) {
                allExports = allExports + record.get("Country") + ':' + record.get("Exports") + ':' + record.get("Value (dollars)");
            }
        }
        return allExports;
    }
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
        for ( CSVRecord record : parser ) {
            String export = record.get("Exports");
            if ( export.contains(exportItem1) && export.contains(exportItem2) ) {
                System.out.println(record.get("Country") );
            }
        }     
    }
    public int numberOfExporters(CSVParser parser, String exportItem) {
        int numberExports = 0;
        for (CSVRecord record : parser ) {
            String export = record.get("Exports");
            if ( export.contains(exportItem) ) {
                numberExports += 1;
            }
        }
        return numberExports;
    }
    public void bigExporters(CSVParser parser, String amount) {
        String number = amount.replaceAll(",", "");
        String number2 = number.replace("$", "");
        long longAmount = Long.parseLong(number2);
        for ( CSVRecord record : parser ) {
            String value = record.get("Value (dollars)");
            String cleanValue = value.replaceAll(",", "");
            String cleanValue2 = cleanValue.replace("$", "");
            long intValue = Long.parseLong(cleanValue2);
            if ( intValue > longAmount ) {
                System.out.println( record.get("Country") + ' ' + record.get("Value (dollars)") );
            }
        }
    }
    public static void main(String[] args ){
     FileResource fr = new FileResource();
     CSVParser parser = fr.getCSVParser();
     WhichCountries nc = new WhichCountries();
     //String Country = "Nauru";
     //System.out.println(nc.countryInfo(parser, Country));
     //nc.listExportersTwoProducts(parser, "gold", "diamonds" );
     //System.out.println(nc.numberOfExporters(parser, "gold"));
     nc.bigExporters(parser, "$999,999,999,999");
    }
}