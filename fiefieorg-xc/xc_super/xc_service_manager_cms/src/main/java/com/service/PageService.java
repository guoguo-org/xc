package com.service;

import com.api.cms.CmsPageControllerApi;
import com.dao.CmsTemplateRepository;
import com.dao.MongodbCMSDao;
import com.domain.cms.CmsPage;
import com.domain.cms.CmsTemplate;
import com.domain.cms.request.QueryPageRequest;
import com.domain.cms.response.CmsCode;
import com.model.response.CommonCode;
import com.model.response.QueryResponseResult;
import com.model.response.QueryResult;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.utility.StringUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PageService implements CmsPageControllerApi {

    @Autowired
    private MongodbCMSDao mongodbCMSDao;

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Override
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;
        if (size <= 0) {
            size = 20;
        }
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        //????????????
        Pageable pageRequest = PageRequest.of(page, size);
        Page<CmsPage> pages = mongodbCMSDao.findAll(pageRequest);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<>();
        cmsPageQueryResult.setList(pages.getContent());//????????????
        cmsPageQueryResult.setTotal(pages.getTotalElements());//???????????????


        return new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
    }


    /*???????????????*/
    public String getPageHtml(String id) {
        //????????????????????????
        Map modelByPageId = getModelByPageId(id);
        //????????????
        String templateByPageId = getTemplateByPageId(id);
        //???????????????
        String html = generateHtml(templateByPageId, modelByPageId);
        return html;
    }


    //???????????????
    public String generateHtml(String template, Map model) {


        try {
            //???????????????
            Configuration configuration = new Configuration(Configuration.getVersion());
            //?????????????????????
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", template);
            //?????????????????????
            configuration.setTemplateLoader(stringTemplateLoader);
            //????????????
            Template template1 = configuration.getTemplate("template");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
            return html;
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

    //????????????
    public String getTemplateByPageId(String pageId) {
        //????????????????????????
        Optional<CmsPage> byId = mongodbCMSDao.findById(pageId);
        if (byId.isPresent()) {
            CmsPage cmsPage = byId.get();
            String templateId = cmsPage.getTemplateId();
            //??????????????????
            Optional<CmsTemplate> cmsTemplateRepositoryById = cmsTemplateRepository.findById(templateId);
            if (cmsTemplateRepositoryById.isPresent()) {
                CmsTemplate cmsTemplate = cmsTemplateRepositoryById.get();
                //??????????????????id
                String templateFileId = cmsTemplate.getTemplateFileId();
                //????????????
                GridFSFile gridFsTemplateOne = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
                GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(gridFsTemplateOne.getObjectId());
                //??????gridFsResource
                GridFsResource gridFsResource = new GridFsResource(gridFsTemplateOne, downloadStream);


                String content = null;
                try {
                    content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return content;
            }
        }
        return null;
    }

    //????????????????????????
    public Map getModelByPageId(String pageId) {
        Optional<CmsPage> byId = mongodbCMSDao.findById(pageId);
        if (byId.isPresent()) {
            CmsPage cmsPage = byId.get();
            String dataUrl = cmsPage.getDataUrl();
            ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
            Map body = forEntity.getBody();
            return body;
        }
        return null;
    }
}
