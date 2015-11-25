/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package tranfomcsvtoxmlmetadata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author cvizzarri
 */
public class TranfomCsvtoXmlMetadata {
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TranfomCsvtoXmlMetadata obj = new TranfomCsvtoXmlMetadata();
        obj.run();
    }
    
    public void run() {
        
        String csvFile = "/home/cvizzarri/Descargas/XIXEncuentrodeJovenesInvestigadores/Autores_x_Area.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "&";
        int contador=0;
        
        try {
            
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                
                // use comma as separator
                String[] country = line.split(cvsSplitBy);
        
                     // creates a FileWriter Object
                File file = new File("/home/cvizzarri/Descargas/XIXEncuentrodeJovenesInvestigadores/DirectorioItems/item"+contador);
                if (!file.exists()) {
                    if (file.mkdir()) {
                        //System.out.println("Directory is created!");
                    } else {
                        System.out.println("Failed to create directory!");
                    }
                }
      file = new File("/home/cvizzarri/Descargas/XIXEncuentrodeJovenesInvestigadores/DirectorioItems/item"+contador+"/doublin_core.xml");
               
      FileWriter writer = new FileWriter(file); 
      // Writes the content to the file
      writer.write("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\n<dublin_core schema=\"dc\">\n<dcvalue element=\"title\" qualifier=\"none\">"+country[1] +"</dcvalue>\n<dcvalue element=\"date\" qualifier=\"issued\">25-11-2015</dcvalue>\n<dcvalue element=\"creator\" qualifier=\"none\">"+country[4]+","+country[5] +"</dcvalue>\n</dublin_core>");
      
      writer.flush();
      writer.close();
                
  
                
                contador++;
            }
                          System.out.println("FIN" );
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        System.out.println("Done");
    }
    
    
}
