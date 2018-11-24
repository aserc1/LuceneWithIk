package com.zxl.lucene.search;

import com.zxl.lucene.analyzer.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QueryParseTest {
    public static void main(String[] args){
        String field="title";
        String[] fields={"title","content"};
        Path indexPath=Paths.get("indexdir");
        try(Directory dir= FSDirectory.open(indexPath);
            IndexReader reader= DirectoryReader.open(dir)){
            IndexSearcher searcher=new IndexSearcher(reader);

            Query query=QueryBuilder.wildcardQuery();                                 ;

            System.out.println("Query:"+query.toString());

            TopDocs tds=searcher.search(query,10);
            for(ScoreDoc sd:tds.scoreDocs){
                Document doc=searcher.doc(sd.doc);
                System.out.println("DocID:" + sd.doc);
                System.out.println("id:" + doc.get("id"));
                System.out.println("title:" + doc.get("title"));
                System.out.println("文档评分:" + sd.score);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
