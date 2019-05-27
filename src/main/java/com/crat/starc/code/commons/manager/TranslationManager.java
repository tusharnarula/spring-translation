package com.crat.starc.code.commons.manager;

import com.crat.starc.code.commons.core.localization.LocalizationService;
import com.crat.starc.code.commons.core.update.TranslationUpdateService;
import com.crat.starc.code.commons.dto.ResourceTranslationUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service("com.oyo.opstech.commons.translation:translationManager")
public class TranslationManager {

    private final LocalizationService localizationService;

    private final TranslationUpdateService translationUpdateService;

    @Autowired
    public TranslationManager(@Qualifier("utils:com.oyo.opstech.commons.translation:localizationService") LocalizationService localizationService, @Qualifier("utils:com.oyo.opstech.commons.translation:translationUpdateService") TranslationUpdateService translationUpdateService) {
        this.localizationService = localizationService;
        this.translationUpdateService = translationUpdateService;
    }

    public void localize(Object resource) {
        try{
            localizationService.localize(resource);
        } catch (Exception ignored){}
    }

    public void updateTranslationFor(ResourceTranslationUpdateRequest updateRequest) throws InvocationTargetException, IllegalAccessException {
        translationUpdateService.updateTranslationFor(updateRequest);
    }

}
