package com.zxl.lucene.index;

import org.apache.lucene.document.*;

public class NewsDocBuilder {
    private FieldType idType;
    private FieldType titleType;
    private FieldType contentType;

    public NewsDocBuilder(){

    }

    public void setIdType(FieldType idType) {
        this.idType = idType;
    }

    public void setTitleType(FieldType titleType) {
        this.titleType = titleType;
    }

    public void setContentType(FieldType contentType) {
        this.contentType = contentType;
    }

    public Document createDoc(News news){
        Document doc = new Document();
        doc.add(new Field("id", String.valueOf(news.getId()), idType));
        doc.add(new Field("title", news.getTitle(), titleType));
        doc.add(new Field("content", news.getContent(), contentType));
        doc.add(new IntPoint("reply", news.getReply()));
        doc.add(new StoredField("reply_display", news.getReply()));
        return doc;
    }
}
