package com.zxl.lucene.analyzer;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;

public class IKTokenizer6x extends Tokenizer {
    //ik分词器
    private IKSegmenter ikSegmenter;
    //词元文本属性
    private final CharTermAttribute termAttribute;
    //词元位移属性
    private final OffsetAttribute offsetAttribute;
    private final TypeAttribute typeAttribute;
    private int endPosition;

    public IKTokenizer6x(boolean useSmart){
        super();
        offsetAttribute=addAttribute(OffsetAttribute.class);
        termAttribute=addAttribute(CharTermAttribute.class);
        typeAttribute=addAttribute(TypeAttribute.class);
        ikSegmenter=new IKSegmenter(input,useSmart);
    }

    @Override
    public boolean incrementToken() throws IOException {
        clearAttributes();
        Lexeme nextLexeme=ikSegmenter.next();
        if(nextLexeme!=null) {
            termAttribute.append(nextLexeme.getLexemeText());
            termAttribute.setLength(nextLexeme.getLength());
            offsetAttribute.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());
            endPosition = nextLexeme.getEndPosition();
            typeAttribute.setType(nextLexeme.getLexemeText());
            return true;
        }
        return false;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        ikSegmenter.reset(input);
    }

    @Override
    public void end() throws IOException {
        int finalOffset=correctOffset(this.endPosition);
        offsetAttribute.setOffset(finalOffset,finalOffset);
        super.end();
    }
}
