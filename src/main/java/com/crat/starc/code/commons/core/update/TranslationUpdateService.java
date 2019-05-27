package com.crat.starc.code.commons.core.update;

import com.crat.starc.code.commons.annotation.Translatable;
import com.crat.starc.code.commons.dao.TranslationDao;
import com.crat.starc.code.commons.model.TranslatedString;
import com.crat.starc.code.commons.core.TranslationService;
import com.crat.starc.code.commons.dto.ResourceTranslationUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service("utils:com.oyo.opstech.commons.translation:translationUpdateService")
public class TranslationUpdateService extends TranslationService {

    private final TranslationDao translationDao;

    @Autowired
    public TranslationUpdateService(@Qualifier("utils:com.oyo.opstech.commons.translation:translationDao") TranslationDao translationDao) {
        this.translationDao = translationDao;
    }

    public void updateTranslationFor(ResourceTranslationUpdateRequest updateRequest) throws InvocationTargetException, IllegalAccessException {
        Object resource = updateRequest.getResource();
        if(validateTranslatableResource(resource)) {
            Method identifierMethod = findUniqueIdentifierMethod(updateRequest.getResource());
            if(identifierMethod!=null) {
                Field field = updateRequest.getField();
                if(field.getAnnotation(Translatable.class)!=null) {
                    TranslatedString translatedString = findExistingTranslations(resource, field, identifierMethod);
                    if(translatedString == null) {
                        translatedString = new TranslatedString();
                        translatedString.setResourceId(String.valueOf(identifierMethod.invoke(resource)));
                        translatedString.setResourceType(resource.getClass().getSimpleName() + "." + field.getName());
                    }
                    translatedString.addTranslation(updateRequest.getLanguage(), updateRequest.getTranslation());
                    translationDao.save(translatedString);
                }
            }
        }
    }

    @Override
    protected TranslationDao getDaoService() {
        return translationDao;
    }
}
