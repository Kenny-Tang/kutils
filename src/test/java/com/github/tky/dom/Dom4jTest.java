package com.github.tky.dom;

import java.io.FileOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import com.test.BaseTest;

public class Dom4jTest extends BaseTest {

    @Test
    public void createDom() {
        try {
            //创建文档
            Document document = DocumentHelper.createDocument();
            // 添加根节点
            Element root = document.addElement("books");
            // 添加书籍
            Element ldj = root.addElement("book");
            Element ldjName = ldj.addElement("name") ;
            ldjName.setText("鹿鼎记");
            Element ldjAuth = ldj.addElement("author") ;
            ldjAuth.setText("金庸");
            Element ldjC = ldj.addElement("character") ;
            ldjC.setText("韦小宝");
            
            Element tlbb = root.addElement("book");
            Element tlbbName = tlbb.addElement("name") ;
            tlbbName.setText("天龙八部");
            Element tlbbAuth = tlbb.addElement("author") ;
            tlbbAuth.setText("金庸");
            Element tlbbC = tlbb.addElement("character") ;
            tlbbC.setText("乔峰");
            
            Element xajh = root.addElement("book");
            Element xajhName = xajh.addElement("name") ;
            xajhName.setText("笑傲江湖");
            Element xajhAuth = xajh.addElement("author") ;
            xajhAuth.setText("金庸");
            Element xajhC = xajh.addElement("character") ;
            xajhC.setText("令狐冲");
            
            //设置输出格式 使用格式化输出
            OutputFormat format = OutputFormat.createPrettyPrint();
            //设置文件编码
            format.setEncoding("UTF-8");
            // 添加文本时不进行去空格处理
            format.setTrimText(false);
            // 使用4空格缩进，默认使用2空格缩进
            format.setIndentSize(4);
            //将写好的文档document输出到指定XML文件中并关闭XMLWriter对象
            XMLWriter xml = new XMLWriter(new FileOutputStream("books.xml"),format);
            //有时候我们的内容text中会有诸如/、>之类的，我们要告诉XML,不要转义这些字符
            xml.setEscapeText(false);
            xml.write(document);
            xml.close();
        } catch (Exception e) {    
            e.printStackTrace();
        }
    }
}
