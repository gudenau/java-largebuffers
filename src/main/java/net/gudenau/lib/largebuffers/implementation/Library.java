package net.gudenau.lib.largebuffers.implementation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class Library{
    @SuppressWarnings("Duplicates")
    static void loadLibrary(){
        try{
            String extension;
            String os = System.getProperty("os.name").toLowerCase();
            if(os.contains("window")){
                extension = "dll";
            }else if(os.contains("linux")){
                extension = "so";
            }else{
                throw new RuntimeException("No support for " + os);
            }
    
            File libraryFile = File.createTempFile("LargeBuffers", extension);
            try(InputStream inputStream = Library.class.getResourceAsStream("LargeBuffers." + extension);
                OutputStream outputStream = new FileOutputStream(libraryFile)){
                inputStream.transferTo(outputStream);
            }
            System.load(libraryFile.getAbsolutePath());
            libraryFile.deleteOnExit();
        }catch(IOException e){
            throw new RuntimeException("Failed to load natives", e);
        }
    }
}
