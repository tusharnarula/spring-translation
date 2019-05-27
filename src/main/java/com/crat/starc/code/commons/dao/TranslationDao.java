package com.crat.starc.code.commons.dao;

import com.crat.starc.code.commons.model.TranslatedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service("utils:com.oyo.opstech.commons.translation:translationDao")
public class TranslationDao {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public TranslationDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public TranslatedString findExistingTranslations(String resourceType, String resourceId){
        Query query = new Query().addCriteria(
                Criteria.where("resourceType").is(resourceType)
                        .andOperator(
                                Criteria.where("resourceId").is(resourceId)
                        )
        );
        return mongoTemplate.findOne(query, TranslatedString.class);
    }


    public void save(TranslatedString translatedString) {
        mongoTemplate.save(translatedString);
    }
}
