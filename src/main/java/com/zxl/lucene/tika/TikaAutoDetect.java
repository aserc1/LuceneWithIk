package com.zxl.lucene.tika;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TikaAutoDetect {
    public static void main(String[] args) {
        File file=new File("E:\\DATA\\tika");
        if(!file.exists()){
            System.out.println("文件夹不存在，请检查:");
            System.exit(0);
        }
        File[] files=file.listFiles();
        BodyContentHandler handler=new BodyContentHandler();
        Metadata metadata=new Metadata();
        Parser parser=new AutoDetectParser();
        ParseContext parseContext=new ParseContext();
        for(File f:files){
            try(InputStream inputStream=new FileInputStream(f)){
                parser.parse(inputStream,handler,metadata,parseContext);
                System.out.println(f.getName());
            }catch (IOException| SAXException|TikaException e){
                e.printStackTrace();
            }
        }
    }
}
