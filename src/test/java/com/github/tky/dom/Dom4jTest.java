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
            document.addDocType("mapper", "PUBLIC", "-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd") ;
            // 添加根节点
            Element mapper = document.addElement("mapper");
            mapper.addAttribute("namespace" , "com.github.tky.Mapper");
            Element sql = mapper.addElement("sql");
            sql.addAttribute("id", "base_columns");
            sql.setText("\n       insert id , optimistic, pos_cati, pos_sn, type, soft_version, param_version, customer_no , shop_id, shop_no, pos_sn_activity, phone_no, "
                    + "create_time, update_time, manage_password,  mkey, mkey_last_modify_time\n    ");
            Element insert = mapper.addElement("insert");
            insert.addAttribute("id", "insert");
            insert.setText(" insert into phone_pos \r\n" + 
                    "            (<include refid=\"base_columns\"></include>)\r\n" + 
                    "        values(seq_phone_pos_id.nextval ,0,#{posCati,jdbcType=VARCHAR},#{posSn ,jdbcType=VARCHAR},#{type ,jdbcType=VARCHAR}, #{ softVersion ,jdbcType=VARCHAR},#{ paramVersion ,jdbcType=VARCHAR},\r\n" + 
                    "            #{ customerNo },#{ shopId},#{ shopNo},#{ posSnActivity  ,jdbcType=VARCHAR }, #{phoneNo ,jdbcType=VARCHAR }, sysdate, #{updateTime }, #{managePassword }, #{mKey },#{mKeyLastModifyTime}) "
                    + "\r\n    ");
            //设置输出格式            
            OutputFormat format = OutputFormat.createPrettyPrint();
            //设置文件编码
            format.setEncoding("UTF-8");        
            format.setTrimText(false);
            format.setIndentSize(4);
            format.setNewlines(true);
            format.setNewLineAfterNTags(1);
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
