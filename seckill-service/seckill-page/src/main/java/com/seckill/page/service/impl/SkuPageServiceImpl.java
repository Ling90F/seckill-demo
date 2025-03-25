package com.seckill.page.service.impl;

import com.seckill.page.service.SkuPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.util.Map;

@Service
public class SkuPageServiceImpl implements SkuPageService {

    @Autowired
    private Configuration configuration;

    /**
     * 删除静态页
     * @param id
     * @param htmlPath
     */
    @Override
    public void delItemPage(String id, String htmlPath) {
        File file = new File(htmlPath,id+".html");
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 生成静态页
     *
     * @param dataMap dataMap.templateName: 模板名字,例如item.ftl,模板放到resources/templates目录下
     *                dataMap.path: 生成文件存储路径，例如C:/page/html
     *                dataMap.name: 生成的文件名字，例如：12345.html
     */
    @Override
    public void writePage(Map<String, Object> dataMap) throws Exception {

        //获取模板名字
        String templateName = dataMap.get("templateName").toString();

        //文件生存的路径
        String path = dataMap.get("path").toString();

        //文件路径如果不存在，则创建
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        //获取文件名字
        String fileName = dataMap.get("name").toString();

        //获取模板对象
        Template template = configuration.getTemplate(templateName);

        //模板处理，获取生成的文件字符串
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);

        //生成文件
        FileUtils.writeStringToFile(new File(path, fileName), content);
    }
}