package com.github.tky.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 * 
 * @author Kenny
 *
 */
public class FileUtils {

    public static final int BSIZE = 1024 ;
    
    /**
     * 
     * @param src   source file
     * @param dest  target file
     * @throws IOException
     */
    public static void copyFile(File src, File dest) throws IOException {
        if(!src.exists()) return ;
        
        try(FileInputStream ins =  new FileInputStream (src) ;
            FileOutputStream ous = new FileOutputStream (dest);
            FileChannel in = ins.getChannel();
            FileChannel out = ous.getChannel ();){
            ByteBuffer buff = ByteBuffer.allocate(BSIZE);
            while(in.read(buff)!= -1){
                buff.flip();    // Prepare for writing 
                out.write(buff);
                buff.clear();   // Prepare for reading
            }
        }catch(IOException e){
            throw e ;
        }
    }

}
