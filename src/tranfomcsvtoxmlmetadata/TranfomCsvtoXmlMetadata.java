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
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
        String nameFile="diseno.csv";
        String csvFile = "/home/cvizzarri/Descargas/XIXEncuentrodeJovenesInvestigadores/"+nameFile;
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
      file = new File("/home/cvizzarri/Descargas/XIXEncuentrodeJovenesInvestigadores/DirectorioItems/item"+contador+"/dublin_core.xml");               
      FileWriter writer = new FileWriter(file); 
      // Writes the content to the file
      
      String xml="<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\n<dublin_core schema=\"dc\">\n<dcvalue element=\"title\" qualifier=\"none\">"+country[1] +"</dcvalue>\n<dcvalue element=\"date\" qualifier=\"issued\">25-11-2015</dcvalue>\n";
            xml=xml+"<dcvalue element=\"creator\" qualifier=\"none\">"+country[4]+","+country[5] +"</dcvalue>";
               if (!"".equals(country[6]))
                 xml=xml+"<dcvalue element=\"creator\" qualifier=\"none\">"+country[6]+","+country[7] +"</dcvalue>";
              if (!"".equals(country[8]))
                 xml=xml+"<dcvalue element=\"creator\" qualifier=\"none\">"+country[8]+","+country[8] +"</dcvalue>";                 
               
               xml=xml+"\n</dublin_core>";
      writer.write(xml);      
      writer.flush();
      writer.close();
      
      
      file = new File("/home/cvizzarri/Descargas/XIXEncuentrodeJovenesInvestigadores/DirectorioItems/item"+contador+"/contents");               
      writer = new FileWriter(file); 
      // Writes the content to the file
      writer.write(country[0]+".pdf\ttbundle:BUNDLE");
      writer.flush();
      writer.close();
      
      copyFile_Java7("/home/cvizzarri/Descargas/XIXEncuentrodeJovenesInvestigadores/allPDF/"+country[0]+".pdf","/home/cvizzarri/Descargas/XIXEncuentrodeJovenesInvestigadores/DirectorioItems/item"+contador+"/"+country[0]+".pdf");
      
      
                
  
                
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
    
  
        public static void copyFile_Java7(String origen, String destino) throws IOException {
        Path FROM = Paths.get(origen);
        Path TO = Paths.get(destino);
        //sobreescribir el fichero de destino, si existe, y copiar
        // los atributos, incluyendo los permisos rwx
        CopyOption[] options = new CopyOption[]{
          StandardCopyOption.REPLACE_EXISTING,
          StandardCopyOption.COPY_ATTRIBUTES
        }; 
        Files.copy(FROM, TO, options);
    }
    
}
