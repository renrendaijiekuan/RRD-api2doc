package com.renrendai.loan.beetle.commons.api2doc.domain;

import com.google.gson.JsonObject;
import com.renrendai.loan.beetle.commons.api2doc.impl.Api2DocObjectFactory;
import com.renrendai.loan.beetle.commons.restpack.RestPackUtils;
import com.renrendai.loan.beetle.commons.util.value.KeyedList;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

public class ApiDocObject extends ApiObject {

    private ApiFolderObject folder;

    private String[] paths;

    private Method sourceMethod;

    private RequestMethod[] methods;

    private String returnTypeDesc;

    private List<ApiResultObject> results;

    private ApiResultObject resultType;

    private final KeyedList<String, ApiParamObject> params = new KeyedList<>();

    private final KeyedList<String, ApiErrorObject> errors = new KeyedList<>();

    public ApiResultObject getResultType() {
        return resultType;
    }

    public void setResultType(ApiResultObject resultType) {
        this.resultType = resultType;
    }

    public Method getSourceMethod() {
        return sourceMethod;
    }

    public void setSourceMethod(Method sourceMethod) {
        this.sourceMethod = sourceMethod;
    }

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    public RequestMethod[] getMethods() {
        return methods;
    }

    public void setMethods(RequestMethod[] methods) {
        this.methods = methods;
    }

    public final List<ApiParamObject> getParams() {
        return params.getAll();
    }

    public final ApiParamObject getParam(String id) {
        return params.get(id);
    }

    public final void addParam(ApiParamObject param) {
        this.params.add(param.getId(), param);
    }

    public List<ApiResultObject> getResults() {
        return results;
    }

    public void setResults(List<ApiResultObject> results) {
        this.results = results;
    }

    public List<ApiErrorObject> getErrors() {
        return errors.getAll();
    }

    public void addError(ApiErrorObject error) {
        this.errors.add(error.getId(), error);
    }

    public ApiFolderObject getFolder() {
        return folder;
    }

    public void setFolder(ApiFolderObject folder) {
        this.folder = folder;
    }

    public String getReturnTypeDesc() {
        return returnTypeDesc;
    }

    public void setReturnTypeDesc(String returnTypeDesc) {
        this.returnTypeDesc = returnTypeDesc;
    }

    public final Object toMockResult() {
        List<ApiResultObject> results = getResults();
        if (results != null && results.size() > 0) {
            ApiResultObject result = results.get(0);
            Object object = Api2DocObjectFactory.createObject(result.getDataType(),
                    result.getSourceType(), result.getSample().getValue());
            //1.遍历children
            List<ApiResultObject> children = result.getChildren();
            for (ApiResultObject child : children) {
                String gid = child.getGroupId();
                if (gid != null && !"".equals(gid)) {
                    try {
                        Class childClazz = Class.forName(gid);
                        if (childClazz == JsonObject.class) {
                            continue;
                        }
                        //2.创建参数对象,并且set进去
                        Object childObj = childClazz.newInstance();
                        //3.初始化对象内参数
                        Field[] declaredFields = childClazz.getDeclaredFields();
                        for (Field field : declaredFields) {
                            field.setAccessible(true);
                            Class<?> fieldType = field.getType();
                            if (RestPackUtils.isBasicType(fieldType) || RestPackUtils.isJavaType(fieldType)) {
                                Object initObj = initByType(fieldType);
                                if (initObj != null){
                                    ReflectionUtils.setField(field, childObj, initObj);
                                }
                            }
                        }

                        Field childField = ReflectionUtils.findField(result.getSourceType(), child.getName());
                        childField.setAccessible(true);
                        ReflectionUtils.setField(childField, object, childObj);
                        System.out.println("1");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return object;
        }
        if (resultType != null) {
            return Api2DocObjectFactory.createObject(resultType.getDataType(),
                    resultType.getSourceType(), resultType.getSample().getValue());
        }
        return null;
    }

    private Object initByType(Class<?> fieldType) {
//        basicClasses.add(boolean.class);
//        basicClasses.add(byte.class);
//        basicClasses.add(char.class);
//        basicClasses.add(short.class);
//        basicClasses.add(int.class);
//        basicClasses.add(long.class);
//        basicClasses.add(float.class);
//        basicClasses.add(double.class);

//        javaClasses.add(Boolean.class);
//        javaClasses.add(Byte.class);
//        javaClasses.add(Character.class);
//        javaClasses.add(Short.class);
//        javaClasses.add(Integer.class);
//        javaClasses.add(Long.class);
//        javaClasses.add(Float.class);
//        javaClasses.add(Double.class);
//        javaClasses.add(String.class);
//        javaClasses.add(Date.class);
//        javaClasses.add(Class.class);
        Random random = new Random();
        if (RestPackUtils.isBasicType(fieldType)) {
            if (long.class == fieldType) {
                return random.nextLong();
            } else if (boolean.class == fieldType) {
                return random.nextBoolean();
            } else if (byte.class == fieldType) {
                return ("" + random.nextInt(100)).getBytes()[0];
            } else if (char.class == fieldType) {
                return new char[random.nextInt()];
            } else if (short.class == fieldType) {
                return new short[random.nextInt()];
            } else if (int.class == fieldType) {
                return random.nextInt(10000);
            } else if (float.class == fieldType) {
                return random.nextFloat();
            } else if (double.class == fieldType) {
                return random.nextDouble();
            }
        }

        if (RestPackUtils.isJavaType(fieldType)) {
            if (Long.class == fieldType) {
                return new Long(random.nextLong());
            } else if (Boolean.class == fieldType) {
                return new Boolean(random.nextBoolean());
            } else if (Byte.class == fieldType) {
                return new Byte(("" + random.nextInt(9)).getBytes()[0]);
            } else if (Character.class == fieldType) {
                return new Character('a');
            } else if (Short.class == fieldType) {
                return new Short("1");
            } else if (Integer.class == fieldType) {
                return new Integer(random.nextInt(10000));
            } else if (Float.class == fieldType) {
                return new Float(random.nextFloat());
            } else if (Double.class == fieldType) {
                return new Double(random.nextDouble());
            } else if (String.class == fieldType) {
                return "str" + random.nextLong();
            }
        }
        return null;
    }
}