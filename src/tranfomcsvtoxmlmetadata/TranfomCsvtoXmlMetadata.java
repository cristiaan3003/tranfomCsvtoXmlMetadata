/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package tranfomcsvtoxmlmetadata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 *
 * @author cvizzarri
 */
public class TranfomCsvtoXmlMetadata {
    //ubicacion de la carpeta()
    List<String> fileList;
    private static final String NAME="encuentroji";
    private static final String PATCH="/home/pcourault/Descargas/EncuentroJI/";
    private static final String OUTPUT_ZIP_FILE = PATCH+"zip/"+NAME+".zip";

    // En el directorio /DirectorioItems van los csv con campos separados por "&" y las carpetas con los pdf
    private static final String SOURCE_FOLDER = PATCH+"DirectorioItems";

    //fileList= guarda en un listado el nombre de los archivos para armar el zip
    TranfomCsvtoXmlMetadata(){
	fileList = new ArrayList<String>();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //PATCH para armar la carpeta a comprimir
        String nameFile=NAME+".csv";        
        String line ="";
        String cvsSplitBy = "&";
        // TODO code application logic here
        TranfomCsvtoXmlMetadata obj = new TranfomCsvtoXmlMetadata();
        obj.run(nameFile, line, cvsSplitBy);
        
        
        //genero archivo .zip y lo guardo
        TranfomCsvtoXmlMetadata appZip = new TranfomCsvtoXmlMetadata();
    	appZip.generateFileList(new File(SOURCE_FOLDER));
    	appZip.zipIt(OUTPUT_ZIP_FILE);
        
        delete(SOURCE_FOLDER);
        
         File file = new File(SOURCE_FOLDER);
         
         file.mkdir();
        
        
    }
    
    public void run(String nameFile, String line, String cvsSplitBy) {
        
        String csvFile = PATCH+nameFile;
        BufferedReader br = null;

        int contador=0;
        
        try {
            
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                
                // use comma as separator
                String[] country = line.split(cvsSplitBy);
        
                     // creates a FileWriter Object
                File file = new File(PATCH+"DirectorioItems/"+NAME+contador);
                if (!file.exists()) {
                    if (file.mkdir()) {
                        //System.out.println("Directory is created!");
                    } else {
                        System.out.println("Failed to create directory!");
                    }
                }
      file = new File(PATCH+"DirectorioItems/"+NAME+contador+"/dublin_core.xml");               
      FileWriter writer = new FileWriter(file); 
      // Writes the content to the file
      
      String xml="<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\n<dublin_core schema=\"dc\">\n<dcvalue element=\"title\" qualifier=\"none\">"+country[1] +"</dcvalue>\n<dcvalue element=\"date\" qualifier=\"issued\">23-11-2017</dcvalue>\n";
            xml=xml+"<dcvalue element=\"creator\" qualifier=\"none\">"+country[4]+","+country[5] +"</dcvalue>";
               
            if (country.length>6 && !"".equals(country[6]) )
                 xml=xml+"<dcvalue element=\"creator\" qualifier=\"none\">"+country[6]+","+country[7] +"</dcvalue>";
              if ( country.length>8 && !"".equals(country[8]))
                 xml=xml+"<dcvalue element=\"creator\" qualifier=\"none\">"+country[8]+","+country[9] +"</dcvalue>";                 
               
               xml=xml+"\n</dublin_core>";
      writer.write(xml);      
      writer.flush();
      writer.close();
      
      
      file = new File(PATCH+"DirectorioItems/"+NAME+contador+"/contents");               
      writer = new FileWriter(file); 
      // Writes the content to the file
      writer.write(country[0]+".pdf\ttbundle:BUNDLE");
      writer.flush();
      writer.close();
      
      copyFile_Java7(PATCH+"allFile3/"+country[0]+".pdf",PATCH+"DirectorioItems/"+NAME+contador+"/"+country[0]+".pdf");
      
      
                
  
                
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
        
          /**
     * Zip it
     * @param zipFile output ZIP file location
     */
    public void zipIt(String zipFile){

     byte[] buffer = new byte[1024];

     try{

    	FileOutputStream fos = new FileOutputStream(zipFile);
    	ZipOutputStream zos = new ZipOutputStream(fos);

    	System.out.println("Output to Zip : " + zipFile);

    	for(String file : this.fileList){

    		System.out.println("File Added : " + file);
    		ZipEntry ze= new ZipEntry(file);
        	zos.putNextEntry(ze);

        	FileInputStream in =
                       new FileInputStream(SOURCE_FOLDER + File.separator + file);

        	int len;
        	while ((len = in.read(buffer)) > 0) {
        		zos.write(buffer, 0, len);
        	}

        	in.close();
    	}

    	zos.closeEntry();
    	//remember close it
    	zos.close();

    	System.out.println("Done");
    }catch(IOException ex){
       ex.printStackTrace();
    }
   }

    /**
     * Traverse a directory and get all files,
     * and add the file into fileList
     * @param node file or directory
     */
    public void generateFileList(File node){

    	//add file only
	if(node.isFile()){
		fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
	}

	if(node.isDirectory()){
		String[] subNote = node.list();
		for(String filename : subNote){
			generateFileList(new File(node, filename));
		}
	}

    }

    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file){
    	return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }
    
    private static void delete(String SOURCE_FOLDER)
    {

    	File directory = new File(SOURCE_FOLDER);

    	//make sure directory exists
    	if(!directory.exists()){

           System.out.println("Directory does not exist.");
           System.exit(0);

        }else{

           try{

               delete(directory);

           }catch(IOException e){
               e.printStackTrace();
               System.exit(0);
           }
        }

    	System.out.println("Done");
    }
    
    
    private static void delete(File file)
    	throws IOException{

    	if(file.isDirectory()){

    		//directory is empty, then delete it
    		if(file.list().length==0){

    		   file.delete();
    		   System.out.println("Directory is deleted : "
                                                 + file.getAbsolutePath());

    		}else{

    		   //list all the directory contents
        	   String files[] = file.list();

        	   for (String temp : files) {
        	      //construct the file structure
        	      File fileDelete = new File(file, temp);

        	      //recursive delete
        	     delete(fileDelete);
        	   }

        	   //check the directory again, if empty then delete it
        	   if(file.list().length==0){
           	     file.delete();
        	     System.out.println("Directory is deleted : "
                                                  + file.getAbsolutePath());
        	   }
    		}

    	}else{
    		//if file, then delete it
    		file.delete();
    		System.out.println("File is deleted : " + file.getAbsolutePath());
    	}
    }
    
}
