package com.service;

import com.api.cms.CmsConfigControllerApi;
import com.dao.CmsConfigRepository;
import com.domain.cms.CmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CmsConfigService implements CmsConfigControllerApi {

    @Autowired
    CmsConfigRepository cmsConfigRepository;

    @Override
    public CmsConfig getModel(String id) {
        Optional<CmsConfig> byId = cmsConfigRepository.findById(id);
        if (byId.isPresent()){
            CmsConfig cmsConfig = byId.get();
            return cmsConfig;
        }
        return null;
    }
}
