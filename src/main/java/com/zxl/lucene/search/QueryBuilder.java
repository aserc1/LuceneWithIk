package com.zxl.lucene.search;

import com.zxl.lucene.analyzer.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;

public class QueryBuilder {
    //搜索
    public static Query query(String field) throws ParseException {
        Analyzer analyzer=new IKAnalyzer6x();
        QueryParser parser=new QueryParser(field,analyzer);
        Query query=parser.parse("农村学生");
        return query;
    }
    //多域搜索
    public static Query multiQuery(String... fields)throws ParseException{
        Analyzer analyzer=new IKAnalyzer6x();
        MultiFieldQueryParser parser=new MultiFieldQueryParser(fields,analyzer);
        parser.setDefaultOperator(QueryParser.Operator.AND);
        Query query=parser.parse("日本");
        return query;
    }
    //词项搜索
    public static Query termQuery(){
        Term term=new Term("title","美国");
        Query query=new TermQuery(term);
        return query;
    }
    //布尔搜索
    public static Query boolQuery(){
        Query query1=new TermQuery(new Term("title","美国"));
        Query query2=new TermQuery(new Term("content","日本"));
        BooleanClause bc1=new BooleanClause(query1, BooleanClause.Occur.MUST);
        BooleanClause bc2=new BooleanClause(query2, BooleanClause.Occur.MUST_NOT);
        BooleanQuery query=new BooleanQuery.Builder().add(bc1).add(bc2).build();
        return query;
    }
    //范围搜索
    public static Query rangeQuery(){
        Query query= IntPoint.newRangeQuery("reply",500,1000);
        return query;
    }
    //前缀搜索
    public static Query prefixQuery(){
        Term term=new Term("title","学");
        Query query=new PrefixQuery(term);
        return query;
    }
    //多关键字搜索
    public static Query phraseQuery(){
        PhraseQuery.Builder builder=new PhraseQuery.Builder();
        builder.add(new Term("title","日本"),2);
        builder.add(new Term("title","美国"),3);
        Query query=builder.build();
        return query;
    }
    //模糊搜索
    public  static  Query fuzzyQuery(){
        Term term=new Term("title","Tramp");
        FuzzyQuery query=new FuzzyQuery(term);
        return  query;
    }
    //通配符搜索
    public static Query wildcardQuery(){
        Query query=new WildcardQuery(new Term("title","学?"));
        return query;
    }

}
