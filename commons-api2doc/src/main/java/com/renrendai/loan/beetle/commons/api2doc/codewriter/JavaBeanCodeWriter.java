package com.renrendai.loan.beetle.commons.api2doc.codewriter;

import com.renrendai.loan.beetle.commons.api2doc.domain.ApiDataType;
import com.renrendai.loan.beetle.commons.api2doc.domain.ApiResultObject;
import com.renrendai.loan.beetle.commons.api2doc.domain.DateConverter;
import com.renrendai.loan.beetle.commons.api2doc.impl.ClasspathFreeMarker;
import freemarker.template.Template;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.util.Locale.ENGLISH;

@Service
public class JavaBeanCodeWriter {

    private static final String GET_PREFIX = "get";

    private static final String SET_PREFIX = "set";

    private static final String IS_PREFIX = "is";

    @Autowired
    private ClasspathFreeMarker classpathFreeMarker;

    private Template javaBeanTemplate = null;

    @PostConstruct
    public void init() {
        try {
            javaBeanTemplate = classpathFreeMarker.getTemplate(getClass(), //
                    "bean.java.ftl");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void writeCode(ApiResultObject result, String className, //
                          CodeOutput out, CodeConfig config) throws Exception {

        List<ApiResultObject> children = result.getChildren();
        if (children == null || children.size() == 0) {
            return;
        }

        Map<String, Object> model = getModel(result, className, config);

        String code = classpathFreeMarker.build(javaBeanTemplate, model);

        out.writeCodeFile(className + ".java", code);
    }

    public Map<String, Object> getModel(ApiResultObject result, String className, //
                                        CodeConfig config) {

        Map<String, Object> model = new HashedMap<>();

        model.put("class", className);

        if (config == null) {
            config = new CodeConfig();
        }
        model.put("config", config);

        // java 类上的注释。
        String comment = result.getComment().javadoc(0);
        if (StringUtils.hasText(comment)) {
            model.put("comment", comment);
        }

        Set<String> imports = new HashSet<>();
        model.put("imports", imports);

        List<FieldInfo> fields = new ArrayList<FieldInfo>();
        List<ApiResultObject> children = result.getChildren();
        for (ApiResultObject child : children) {
            FieldInfo field = new FieldInfo();

            String name = child.getId();
            field.setName(name);

            String type = toTypeName(child);
            field.setType(type);

            String fieldComment = child.getComment().javadoc(1);
            if (StringUtils.hasText(fieldComment)) {
                field.setComment(fieldComment);
            }

            // Date 自动转成 Long 类型了。
            Class<?> sourceType = getSourceType(child);
            CodeUtils.addImport(sourceType, imports);

            boolean isBooleanClass = (child.getDataType() == ApiDataType.BOOLEAN);
            String getMethod = toGetMethodName(isBooleanClass, name);
            field.setGetMethod(getMethod);

            String setMethod = toSetMethodName(name);
            field.setSetMethod(setMethod);

            fields.add(field);
        }

        model.put("fields", fields);

        return model;
    }

    private String toGetMethodName(boolean isBooleanClass, String fieldName) {
        String baseName = getBaseName(fieldName);
        String methodName = null;
        if (isBooleanClass) {
            methodName = IS_PREFIX + baseName;
        } else {
            methodName = GET_PREFIX + baseName;
        }
        return methodName;
    }

    private String toSetMethodName(String fieldName) {
        String baseName = getBaseName(fieldName);
        String methodName = SET_PREFIX + baseName;
        return methodName;
    }

    private String getBaseName(String name) {
        return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }

    private Class<?> getSourceType(ApiResultObject result) {
        Class<?> sourceType = result.getSourceType();
        return DateConverter.dateAsLongClass(sourceType);
    }

    private String toTypeName(ApiResultObject result) {
        ApiDataType dataType = result.getDataType();
        Class<?> sourceType = getSourceType(result);
        ;
        String typeName = sourceType.getSimpleName();
        if (dataType.isArrayType()) {
            typeName = typeName + "[]";
        }
        return typeName;
    }

    public static final class FieldInfo {

        private String type;

        private String name;

        private String comment;

        private String getMethod;

        private String setMethod;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getGetMethod() {
            return getMethod;
        }

        public void setGetMethod(String getMethod) {
            this.getMethod = getMethod;
        }

        public String getSetMethod() {
            return setMethod;
        }

        public void setSetMethod(String setMethod) {
            this.setMethod = setMethod;
        }

    }
}

