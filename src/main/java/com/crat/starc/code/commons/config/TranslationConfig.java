package com.crat.starc.code.commons.config;

import com.crat.starc.code.commons.dao.TranslationDao;
import com.crat.starc.code.commons.manager.TranslationManager;
import com.crat.starc.code.commons.core.localization.LocalizationService;
import com.crat.starc.code.commons.core.update.TranslationUpdateService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;

import javax.annotation.PostConstruct;

public abstract class TranslationConfig {

    protected abstract MongoTemplate translationMongoTemplate();

    @Bean
    public TranslationDao dao(){
        return new TranslationDao(translationMongoTemplate());
    }

    @Bean
    public LocalizationService localizationService(){
        return new LocalizationService(dao());
    }

    @Bean
    public TranslationUpdateService translationUpdateService(){
        return new TranslationUpdateService(dao());
    }

    @Bean
    public TranslationManager translationManager(){
        return new TranslationManager(localizationService(), translationUpdateService());
    }

    @PostConstruct
    public void initIndexes() {
        DBObject indexOptions = new BasicDBObject();
        indexOptions.put("resourceType", 1);
        indexOptions.put("resourceId", 1);
        CompoundIndexDefinition indexDefinition = new CompoundIndexDefinition(indexOptions);
        translationMongoTemplate().indexOps("Translation")
                .ensureIndex(indexDefinition.unique());
    }

}
