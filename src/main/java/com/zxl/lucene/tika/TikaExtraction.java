package com.zxl.lucene.tika;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.File;
import java.io.IOException;

public class TikaExtraction {
    public static void main(String[] args)throws TikaException, IOException {
        Tika tika=new Tika();
        File fileDir=new File("E:\\DATA\\lucene");
        if(!fileDir.exists()){
            System.out.println("文件夹不存在，请检查");
            System.exit(0);
        }
        File[] files=fileDir.listFiles();
        String fileContent;
        for(File f:files){
            fileContent=tika.parseToString(f);
            System.out.println("Extracted Content:"+fileContent);
        }
    }
}
