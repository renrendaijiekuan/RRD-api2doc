package com.renrendai.loan.beetle.commons.api2doc.impl;

import com.renrendai.loan.beetle.commons.api2doc.domain.ApiDocObject;
import com.renrendai.loan.beetle.commons.api2doc.domain.ApiFolderObject;
import com.renrendai.loan.beetle.commons.api2doc.domain.ApiDocObject;
import com.renrendai.loan.beetle.commons.api2doc.domain.ApiFolderObject;
import com.renrendai.loan.beetle.commons.restpack.HttpResult;
import com.renrendai.loan.beetle.commons.restpack.RestPackConfiguration;
import com.renrendai.loan.beetle.commons.util.Strings;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.spec.example.SpecExampleExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.MutableDataSet;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class DocPageBuilder {

    private static final Logger log = LoggerFactory.getLogger(DocPageBuilder.class);

    private static final String FILE_DOC_MD = "doc.md.ftl";

    private static final String FILE_DOC_HTML = "doc.html";

    @Autowired
    private Api2DocProperties api2DocProperties;

    @Autowired
    private Api2DocService apiDocService;

    @Autowired
    private ClasspathFreeMarker freeMarker;

    private Template mdTemplate = null;

    private Template docTemplate = null;

    private Parser parser = null;

    private HtmlRenderer renderer = null;

    @PostConstruct
    public void init() {
        try {
            mdTemplate = freeMarker.getTemplate(DocPageBuilder.class, FILE_DOC_MD);
            docTemplate = freeMarker.getTemplate(DocPageBuilder.class, FILE_DOC_HTML);

            MutableDataSet options = new MutableDataSet();
            options.setFrom(ParserEmulationProfile.GITHUB_DOC);
            options.set(Parser.EXTENSIONS, Arrays.asList(
                    TablesExtension.create(), // 表格渲染插件。
                    SpecExampleExtension.create() // 代码渲染插件。
            ));

            // References compatibility
            options.set(Parser.REFERENCES_KEEP, KeepType.LAST);

            // Set GFM table parsing options
            options.set(TablesExtension.COLUMN_SPANS, false) //
                    .set(TablesExtension.MIN_HEADER_ROWS, 1) //
                    .set(TablesExtension.MAX_HEADER_ROWS, 1) //
                    .set(TablesExtension.APPEND_MISSING_COLUMNS, true) //
                    .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true) //
                    .set(TablesExtension.WITH_CAPTION, false) //
                    .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true);

            // Setup List Options for GitHub profile which is kramdown for documents
            options.setFrom(ParserEmulationProfile.GITHUB_DOC);

            // You can re-use parser and renderer instances
            parser = Parser.builder(options).build();
            renderer = HtmlRenderer.builder(options).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String md2Html(String content) throws Exception {

        Node document = parser.parse(content);
        String html = renderer.render(document);
        return html;
    }

    public String md2HtmlPage(String md, String title) throws Exception {
        String content = md2Html(md);

        Map<String, Object> model = new HashMap<String, Object>();
        if (title != null) {
            model.put("title", title);
        }
        model.put("content", content);
        model.put("v", apiDocService.getComponentVersion());

        String html = freeMarker.build(docTemplate, model);
        return html;
    }

    public String doc2HtmlPage(String folderId, String docId) throws Exception {
        ApiFolderObject folder = apiDocService.getFolder(folderId);
        if (folder == null) {
            if (log.isWarnEnabled()) {
                log.warn("ApiFolder NOT Found: {}", folderId);
            }
            return null;
        }

        ApiDocObject doc = folder.getDoc(docId);
        if (doc == null) {
            if (log.isWarnEnabled()) {
                log.warn("ApiDoc NOT Found: {}", folderId);
            }
            return null;
        }

        String md = doc2Md(folder, doc);
        String title = doc.getName();
        return md2HtmlPage(md, title);
    }

    public String doc2Md(ApiFolderObject folder, ApiDocObject doc) {
        if (folder == null || doc == null) {
            return null;
        }

        try {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("folder", folder);
            model.put("doc", doc);

            if (api2DocProperties.isShowCurl()) {
                String serverURL = api2DocProperties.getServerURL();
                String curl = CurlBuilder.toCurl(doc, serverURL);
                if (StringUtils.hasText(curl)) {
                    model.put("curl", curl);
                }
            }

            Object mockResult = doc.toMockResult();
            if (folder.isRestPack()) {
                mockResult = HttpResult.successFully(mockResult);
            }
            if (mockResult != null) {
                String resultJson = RestPackConfiguration.getObjectMapper()
                        .writeValueAsString(mockResult);
                if (StringUtils.hasText(resultJson)) {
                    model.put("resultJson", resultJson);
                }
            }

            String folderId = folder.getId();
            String upFirst = folderId.substring(0, 1).toUpperCase() + folderId.substring(1);
            String folderClasses = upFirst + "Service / " + upFirst + "Retrofit";
            model.put("folderClasses", folderClasses);

            String content = freeMarker.build(mdTemplate, model);
            if (log.isInfoEnabled()) {
//                log.debug("\n{}", content);
            }
            return content;
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    public String md2HtmlPageByPath(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            throw new NullPointerException("path is null.");
        }
        path = path.trim();
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        path = "api2doc" + path;

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String md = Strings.getResourceByPath(path, loader);
        return md2HtmlPage(md, null);
    }

    public String mdFile2HtmlPage(String folderId, String docId) throws Exception {
        ApiFolderObject folder = apiDocService.getFolder(folderId);
        if (folder == null) {
            log.warn("ApiFolder NOT Found: {}", folderId);
            return null;
        }

        Map<String, String> mds = folder.getMds();
        if (mds == null || !mds.containsKey(docId)) {
            log.warn("Markdown doc {} NOT Found in Folder: {}", docId, folderId);
            return null;
        }

        String fileName = mds.get(docId);
        String path = folderId + "/" + fileName;
        String html = md2HtmlPageByPath(path);
        return html;
    }

}
