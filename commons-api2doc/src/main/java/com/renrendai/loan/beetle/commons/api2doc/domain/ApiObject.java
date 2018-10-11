package com.renrendai.loan.beetle.commons.api2doc.domain;

import com.renrendai.loan.beetle.commons.api2doc.annotations.Api2Doc;
import com.renrendai.loan.beetle.commons.api2doc.impl.FlexibleString;
import com.renrendai.loan.beetle.commons.util.Strings;

public class ApiObject implements Comparable<ApiObject>{

    private String id;

    private String name;

    private final FlexibleString comment = new FlexibleString();

    private final FlexibleString sample = new FlexibleString();

    private int order = Api2Doc.DEFAULT_ORDER;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public final int getOrder() {
        return order;
    }

    public final void setOrder(int order) {
        this.order = order;
    }

    public void insertComment(String comment) {
        this.comment.insertLine(comment);
    }

    public FlexibleString getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.setValue(comment);
    }

    public FlexibleString getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample.setValue(sample);
    }

    @Override
    public final String toString() {
        return Strings.toString(this);
    }

    @Override
    public int compareTo(ApiObject other) {
        ApiObject o1 = this;
        ApiObject o2 = other;

        // 优先按用户指定排序。
        if (o1.getOrder() < o2.getOrder()) {
            return -1;
        }
        if (o1.getOrder() > o2.getOrder()) {
            return 1;
        }

        // 其次按 id 字符串排序。
        return o1.getId().compareTo(o2.getId());
    }
}