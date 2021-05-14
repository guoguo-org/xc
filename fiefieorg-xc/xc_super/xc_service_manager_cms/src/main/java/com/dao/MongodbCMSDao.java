package com.dao;

import com.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongodbCMSDao extends MongoRepository<CmsPage,String>{

}
