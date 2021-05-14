package com;

import com.dao.MongodbCMSDao;
import com.domain.cms.CmsPage;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private MongodbCMSDao mongodbCMSDao;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;


    /*测试下载模型数据*/
    @org.junit.Test
    public void testGridFSBucket() throws IOException {
        /*id*/
        String fileId ="609910ab146ebf2dc056764f";
        /*根据id查询对象*/
        GridFSFile fsFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        /*打开下载对象*/
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(fsFile.getObjectId());
        /*创建gridFsResource用于获取流对象*/
        GridFsResource gridFsResource = new GridFsResource(fsFile, gridFSDownloadStream);
        /*获取流中数据*/
        InputStream inputStream = gridFsResource.getInputStream();

        String s = IOUtils.toString(inputStream, "utf-8");
        System.out.println(s);

    }
    /*删除模板*/
    @org.junit.Test
    public void  delete(){
        String fileId ="609910ab146ebf2dc056764f";
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(fileId)));
    }

    @org.junit.Test
    public void testGridFsTemplate() throws FileNotFoundException {
        File file = new File("D:\\html\\test.html");
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectId testFile = gridFsTemplate.store(fileInputStream, "testFile");
        String s = testFile.toString();
        System.out.println(s);
    }


    @org.junit.Test
    public void mongodbTest() {
        PageRequest pageRequest = PageRequest.of(1, 2);

        Page<CmsPage> all = mongodbCMSDao.findAll(pageRequest);
        for (CmsPage cmsPage : all) {
            String s = cmsPage.toString();
            System.out.println(s);
        }
    }

    @org.junit.Test
    public void restTemplateTest() {
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getModel/5a791725dd573c3574ee333f", Map.class);
        System.out.println(forEntity);
    }
}
