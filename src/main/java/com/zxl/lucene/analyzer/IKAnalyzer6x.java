package com.zxl.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

public class IKAnalyzer6x extends Analyzer {

    private boolean useSmart;
    public boolean isUseSmart() {
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }
     public IKAnalyzer6x(){
        this(true);
     }
     public IKAnalyzer6x(boolean useSmart){
        super();
        this.useSmart=useSmart;
     }

    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer ikTokenizer=new IKTokenizer6x(useSmart);
        return new TokenStreamComponents(ikTokenizer);
    }
}
