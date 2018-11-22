package com.zxl.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.IOException;
import java.io.StringReader;

public class StdAnalyzer {
    private static String strCh1="中华人民共和国简称中国，是一个有13亿人口的国家";
    private static String strCh="厉害了我的哥！中国环保部门即将发布治理北京雾霾的方法！";
    private static String strEN="Dogs can not achieve a place,eyes can reach;";
    public static void main(String[] args){
        System.out.println("标准分词：");
        stdAnalyzer(strCh,StandardAnalyzer.class);
        stdAnalyzer(strEN,StandardAnalyzer.class);
        System.out.println("空格分词：");
        stdAnalyzer(strCh,WhitespaceAnalyzer.class);
        stdAnalyzer(strEN, WhitespaceAnalyzer.class);
        System.out.println("简单分词：");
        stdAnalyzer(strCh, SimpleAnalyzer.class);
        stdAnalyzer(strEN,SimpleAnalyzer.class);
        System.out.println("二分法分词：");
        stdAnalyzer(strCh, CJKAnalyzer.class);
        stdAnalyzer(strEN,CJKAnalyzer.class);
        System.out.println("关键字分词:");
        stdAnalyzer(strCh, KeywordAnalyzer.class);
        stdAnalyzer(strEN,KeywordAnalyzer.class);
        System.out.println("停用词分词：");
        stdAnalyzer(strCh, StopAnalyzer.class);
        stdAnalyzer(strEN,StopAnalyzer.class);
        System.out.println("ik分词：");
        stdAnalyzer(strCh,IKAnalyzer6x.class);
        stdAnalyzer(strEN,IKAnalyzer6x.class);
    }
    public static void stdAnalyzer(String str,Class<? extends Analyzer> analyzerClass){
        Analyzer analyzer=null;
        try {
            analyzer = analyzerClass.newInstance();
            StringReader reader = new StringReader(str);
            TokenStream tokenStream = analyzer.tokenStream(str, reader);
            tokenStream.reset();
            CharTermAttribute termAttribute=tokenStream.getAttribute(CharTermAttribute.class);
            System.out.println("分词结果：");
            while(tokenStream.incrementToken()){
                System.out.print(termAttribute.toString()+"|");
            }
            System.out.println("\n");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(analyzer!=null){
                analyzer.close();
            }
        }

    }
}
