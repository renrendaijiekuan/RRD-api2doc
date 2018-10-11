**API标识**
- ${folder.id}-${doc.id}

<#if doc.comment?? && doc.comment!="">
**API简介** 
- ${doc.comment}
</#if>

<br/>

<#--**客户端API类**-->
<#--- ${folderClasses}-->

<#--**客户端API方法**-->
<#--- ${doc.id}-->

<#--<br/>-->

**请求URI**
<#list doc.paths as path>
- `${path}`
</#list>

**请求方法**
<#list doc.methods as method>
- `${method}`
</#list>

<#if doc.params?? && (doc.params?size > 0) >
**请求参数**

| 参数名 | 是否必须 | 参数形式 | 数据类型 | 说明    | 示例值 |
|:-----    |-----         |-----        |-----         |------   |-----     |
<#list doc.params as param>
| ${param.id} | ${param.requiredName} | ${param.location} | ${param.typeName} | ${param.comment.html()!} | ${param.sample.html()!} |
</#list>
</#if>

<#if curl??>
**请求示例（curl 命令格式）**

```
${curl}
```
</#if>

<br/>

<#if resultJson??>

**返回数据示例**

```json
${resultJson}
```
</#if>

<#if doc.results?? && (doc.results?size > 0) >
<!-- 复杂数据类型的情况 -->

**返回数据说明**
- [${doc.returnTypeDesc}](#${doc.results[0].groupId}) （参见下面的类型说明）

<!-- 所有的子类型的列表说明 -->
<#list doc.results as result>

<!-- 给子类型定义一个锚，好点到这里。 -->
<#if result.groupId??>
<span id="${result.groupId}"><br /></span>
</#if>

**${result.groupName!}类型说明：**

| 字段名 | 类型   | 说明 | 示例值 |
|:---- |----- |-----  |----- |
<#list result.children as item>
| ${item.id} | <#if item.refGroupId??>[${item.typeName}](#${item.refGroupId})<#else>${item.typeName}</#if> | ${item.comment.html()!} | ${item.sample.html()!} |
</#list>
</#list>

<#elseif doc.resultType?? >
<!-- 简单数据类型的情况。 -->

**返回数据说明**

| 类型  | 说明 | 示例值 |
|:----   |-----  |-----     |
|  ${doc.resultType.typeName} |  ${doc.resultType.comment.html()!} | ${doc.resultType.sample.html()!} |
</#if>

<br/>

<#if doc.errors?? && (doc.errors?size > 0) >
**错误码**

| 错误码  | 说明   |
|:----      |-----   |
<#list doc.errors as error>
| ${error.id} | ${error.comment.html()!} |
</#list>
</#if>
