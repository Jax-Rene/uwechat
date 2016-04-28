package com.youyicun.util;

import com.thoughtworks.xstream.XStream;
import com.youyicun.bean.Item;
import com.youyicun.bean.Message;
import com.youyicun.bean.NewsMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/4/4.
 */
public class MessageUtil {
    public static Map<String,String> xmlToMap(InputStream inputStream){
        Map<String,String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(inputStream);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            inputStream.close();
        }catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String messageToXml(Message message){
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.alias("xml",message.getClass());
        return xStream.toXML(message);
    }

    public static void main(String[] args) {
        NewsMessage s = new NewsMessage("a","b",1);
        s.setMsgType("news");
        List<Item> items = new ArrayList<>();
        Item item = new Item("titile","b","c","d");
        Item item2 = new Item("titile","b","c","d");
        items.add(item);
        items.add(item2);
        s.setArticles(items);
        System.out.println(messageToXml(s));
    }
}
