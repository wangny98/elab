package com.device.filter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.device.component.cache.UrlFilterCache;
import com.device.component.cache.UrlFilterCacheDto;
import com.device.util.PathHelper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class URLFilterHelper {

    @Autowired
    PathHelper pathHelper;

    @Autowired
    private UrlFilterCache cache;

    private Element root = null;

    private String configurationXMLFolder = "spring/";

    private String configurationXMLFile = "urlFilter.xml";

    /**
     * 将url压入缓存
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    public void pushAllUrl() {
        Element root = readConfiguration();
        Iterator<Element> typeIt = root.elementIterator();
        while (typeIt.hasNext()) {
            Element e = (Element) typeIt.next();
            String urlType = e.attributeValue("type");
            UrlFilterCacheDto dto = new UrlFilterCacheDto();
            ArrayList<String> urls = new ArrayList<String>();
            Iterator<Element> urlIt = e.elementIterator();
            while (urlIt.hasNext()) {
                Element patternElement = (Element) urlIt.next();
                String url = patternElement.getTextTrim();
                if ("null".equals(url) || url == null) {
                    continue;
                }
                urls.add(url);
                System.out.println("urls : " + urls);
            }
            dto.setUrls(urls);
            cache.addUrl(urlType, dto);
        }
    }

    /**
     * 取得警告页面地址
     * @return
     */
    public String getWarningPage() {
        /*  Element root = readConfiguration();
            Element warn = root.element("warn");*/
        String warningPage = cache.getUrl("warning-page").getUrls().get(0);
        return warningPage;
    }

    /**
     * 取得登陆之后才能访问 的URL
     * @return
     */
    public ArrayList<String> getLoginURLList() {
        ArrayList<String> loginURLList = new ArrayList<String>();
        loginURLList = cache.getUrl("login").getUrls();
        return loginURLList;
    }

    /**
     * 未登陆也能访问的URL
     * @return
     */
    public ArrayList<String> getAnyURLList() {
        ArrayList<String> anyURLList = new ArrayList<String>();
        anyURLList = cache.getUrl("any").getUrls();
        return anyURLList;
    }

    /**
     * 取得模糊匹配的URL
     */
    public ArrayList<String> getRoughURLList() {
        ArrayList<String> roughURLList = new ArrayList<String>();
        roughURLList = cache.getUrl("rough").getUrls();
        return roughURLList;
    }

    /**
     * 读取配置文件
     * @return
     */
    private Element readConfiguration() {
        try {
            if (null == root) {
                String path = pathHelper.getParentPath("classes") + "/classes/" + configurationXMLFolder;
                path = path + configurationXMLFile;
                File xmlFile = new File(path);
                SAXReader reader = new SAXReader();
                Document doc = reader.read(xmlFile);
                root = doc.getRootElement();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return root;
    }

    /**
     * 字符串转XML对象
     * <功能详细描述>
     * @param str
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public Document strChangeXML(String str) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(new ByteArrayInputStream(str.getBytes()));
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return document;
    }

    public InputStream loadResource(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

}
