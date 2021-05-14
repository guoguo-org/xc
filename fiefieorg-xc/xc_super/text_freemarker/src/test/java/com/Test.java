package com;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.*;
import java.util.HashMap;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    @org.junit.Test
    public void freemarkerTest() throws IOException, TemplateException {

        //    创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
//    获取模板文件
        String path = this.getClass().getResource("/").getPath();
        configuration.setDirectoryForTemplateLoading(new File(path+"/templates/"));
//    设置字符集
        configuration.setDefaultEncoding("utf-8");
//    加载模板
        Template template = configuration.getTemplate("test1.ftl");
//    设置数据模型
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("name","tom");
//    静态化
        String string = FreeMarkerTemplateUtils.processTemplateIntoString(template, stringStringHashMap);
//    静态化内容(应用apacheIO插件)
        System.out.println(string);
        InputStream toInputStream = IOUtils.toInputStream(string);
//    输出静态化页面
        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\html\\test.html"));
        IOUtils.copy(toInputStream,fileOutputStream);
    }

}
