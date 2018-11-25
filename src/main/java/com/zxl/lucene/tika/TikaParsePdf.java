package com.zxl.lucene.tika;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.FileInputStream;

public class TikaParsePdf {
    public static void main(String[] args){
        String filePath="E:\\DATA\\lucene\\中国人工智能大会CCAI 2016圆满落幕.pdf";
        File pdfFile=new File(filePath);
        BodyContentHandler handler=new BodyContentHandler();
        Metadata metadata=new Metadata();
        try(FileInputStream inputStream=new FileInputStream(pdfFile)){
            ParseContext parseContext=new ParseContext();
            PDFParser parser=new PDFParser();
            parser.parse(inputStream,handler,metadata,parseContext);
            System.out.println("文件属性信息：");
            for(String name:metadata.names()){
                System.out.println(name+" : "+metadata.get(name));
            }
            System.out.println("pdf中的内容：");
            System.out.println(handler.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
