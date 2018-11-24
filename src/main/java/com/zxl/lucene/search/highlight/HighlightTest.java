package com.zxl.lucene.search.highlight;

import com.zxl.lucene.analyzer.IKAnalyzer6x;
import com.zxl.lucene.search.QueryBuilder;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class HighlightTest {
    public static void main(String[] args){
        String field="title";
        String[] fields={"title","content"};
        Path indexPath= Paths.get("indexdir");
        try(Directory dir= FSDirectory.open(indexPath);
            IndexReader reader= DirectoryReader.open(dir)){
            IndexSearcher searcher=new IndexSearcher(reader);

            Analyzer analyzer=new IKAnalyzer6x();
            QueryParser parser=new QueryParser(field,analyzer);
            Query query=parser.parse("农村学生");
            System.out.println("Query:"+query.toString());

            QueryScorer score=new QueryScorer(query,field);
            SimpleHTMLFormatter shf=new SimpleHTMLFormatter("<span style=\"color:red;\">","</span>");
            Highlighter highlighter=new Highlighter(shf,score);

            TopDocs tds=searcher.search(query,10);
            for(ScoreDoc sd:tds.scoreDocs){
                Document doc=searcher.doc(sd.doc);
                System.out.println("DocID:" + sd.doc);
                System.out.println("id:" + doc.get("id"));
                System.out.println("title:" + doc.get("title"));
                System.out.println("文档评分:" + sd.score);
                TokenStream tokenStream=TokenSources.getAnyTokenStream(searcher.getIndexReader(),
                        sd.doc,field,analyzer);
                Fragmenter fragmenter=new SimpleSpanFragmenter(score);
                highlighter.setTextFragmenter(fragmenter);
                String str = highlighter.getBestFragment(tokenStream,doc.get(field));
                System.out.println("高亮的片段："+str);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
