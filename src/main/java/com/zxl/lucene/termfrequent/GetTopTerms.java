package com.zxl.lucene.termfrequent;

import javafx.collections.transformation.SortedList;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class GetTopTerms {
    public static void main(String[] args){
        try(Directory directory= FSDirectory.open(Paths.get("indexdir"));
            IndexReader indexReader= DirectoryReader.open(directory)){
            //只有一个文档，所以docID为0
            Terms terms=indexReader.getTermVector(0,"content");
            TermsEnum termsEnum=terms.iterator();
            Map<String,Integer> map=new HashMap<>();
            BytesRef thisTerm=null;
            while((thisTerm=termsEnum.next())!=null){
                String termText=thisTerm.utf8ToString();
                map.put(termText,(int)termsEnum.totalTermFreq());
            }
            List<Map.Entry<String,Integer>> list = new ArrayList<>(map.entrySet());
            Collections.sort(list,((o1, o2) -> o2.getValue()-o1.getValue()));
            for(int i=0;i<10;i++){
                System.out.println(list.get(i).getKey()+" : "+list.get(i).getValue());
            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
