package com.controller;


import com.api.cms.CmsPageControllerApi;
import com.domain.cms.request.QueryPageRequest;
import com.model.response.QueryResponseResult;
import com.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CmsManagerController implements CmsPageControllerApi {

    @Autowired
    private PageService pageService;
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,@PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        QueryResponseResult queryResponseResult = pageService.findList(page, size, queryPageRequest);
        return queryResponseResult;
    }
}
