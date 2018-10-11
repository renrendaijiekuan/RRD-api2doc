package com.renrendai.loan.beetle.commons.api2doc.domain;

import com.renrendai.loan.beetle.commons.util.value.KeyedList;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class ApiFolderObject extends ApiObject {

    private boolean restPack = false;

    private Map<String, String> mds;

    private Class<?> sourceClass;

    private final KeyedList<String, ApiDocObject> docs = new KeyedList<>();

    public boolean isRestPack() {
        return restPack;
    }

    public void setRestPack(boolean restPack) {
        this.restPack = restPack;
    }

    public Map<String, String> getMds() {
        return mds;
    }

    public void setMds(Map<String, String> mds) {
        this.mds = mds;
    }

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(Class<?> sourceClass) {
        this.sourceClass = sourceClass;
    }

    public final List<ApiDocObject> getDocs() {
        return docs.getAll();
    }

    public final ApiDocObject getDoc(String id) {
        return docs.get(id);
    }

    public final void addDocs(List<ApiDocObject> docList) {
        if (docList == null) {
            return;
        }
        for (ApiDocObject doc : docList) {
            addDoc(doc);
        }
    }

    public final void addDoc(ApiDocObject doc) {
        if (doc == null) {
            throw new NullPointerException();
        }
        String id = doc.getId();
        if (StringUtils.isEmpty(id)) {
            throw new NullPointerException("doc id is empty.");
        }

        this.docs.add(id, doc);
    }

    public static final String name2Id(String name) {
        int hash = name.hashCode();
        String id;
        if (hash < 0) {
            id = "n" + Math.abs(hash);
        } else {
            id = String.valueOf(hash);
        }
        return id;
    }

}