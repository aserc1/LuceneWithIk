package com.zxl.lucene.termfrequent;

import com.zxl.lucene.analyzer.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.file.Paths;

public class IndexDocs {

    public static void main(String[] args){
        String path="E:\\DATA\\lucene\\红楼梦.txt";
        System.out.println("begin to analysis file : "+path);
        analysisFile(new File(path));
    }

    public static String textToString(File file){
        StringBuilder result=new StringBuilder();
        try(BufferedReader br=new BufferedReader(
                new InputStreamReader(new FileInputStream(file),"gbk"))){
            String str=null;
            while((str=br.readLine())!=null){
                result.append(System.lineSeparator()+str);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void analysisFile(File file){
        String text=textToString(file);
        Analyzer analyzer=new IKAnalyzer6x(true);
        IndexWriterConfig indexWriterConfig=new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        try(Directory directory= FSDirectory.open(Paths.get("indexdir"));
            IndexWriter indexWriter=new IndexWriter(directory,indexWriterConfig)){
            FieldType type=new FieldType();
            type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
            type.setStored(true);
            type.setStoreTermVectors(true);
            type.setTokenized(true);

            Document doc=new Document();
            Field field=new Field("content",text,type);
            doc.add(field);
            indexWriter.addDocument(doc);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
