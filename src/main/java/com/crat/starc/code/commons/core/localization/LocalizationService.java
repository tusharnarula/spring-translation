package com.crat.starc.code.commons.core.localization;

import com.crat.starc.code.commons.dao.TranslationDao;
import com.crat.starc.code.commons.core.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service("utils:com.oyo.opstech.commons.translation:localizationService")
public class LocalizationService extends TranslationService {

    private final TranslationDao translationDao;

    @Autowired
    public LocalizationService(@Qualifier("utils:com.oyo.opstech.commons.translation:translationDao") TranslationDao translationDao) {
        this.translationDao = translationDao;
    }

    public void localize(Object resource) throws InvocationTargetException, IllegalAccessException {
        if(validateTranslatableResource(resource)) {
            Method identifierMethod = findUniqueIdentifierMethod(resource);
            if(identifierMethod!=null) {
                localizeAllTranslatableFields(resource, identifierMethod);
            }

        }
    }

    @Override
    protected TranslationDao getDaoService() {
        return translationDao;
    }
}
