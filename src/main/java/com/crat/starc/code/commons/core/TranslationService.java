package com.crat.starc.code.commons.core;

import com.crat.starc.code.commons.annotation.Translatable;
import com.crat.starc.code.commons.annotation.TranslatableResource;
import com.crat.starc.code.commons.annotation.TranslationIdentifier;
import com.crat.starc.code.commons.dao.TranslationDao;
import com.crat.starc.code.commons.model.TranslatedString;
import org.springframework.context.i18n.LocaleContextHolder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

public abstract class TranslationService {

    protected boolean validateTranslatableResource(Object resource){
        return resource.getClass().isAnnotationPresent(TranslatableResource.class);
    }

    protected Method findUniqueIdentifierMethod(Object resource){
        Method identifierMethod = null;
        for(Method method: resource.getClass().getMethods()) {
            if(method.getAnnotation(TranslationIdentifier.class)!=null) {
                identifierMethod = method;
                break;
            }
        }
        return identifierMethod;
    }

    protected void localizeAllTranslatableFields(Object resource, Method identifierMethod) throws InvocationTargetException, IllegalAccessException {
        for(Field field: resource.getClass().getDeclaredFields()) {
            if(field.getAnnotation(Translatable.class)!=null) {
                TranslatedString translatedString = findExistingTranslations(resource, field, identifierMethod);
                if(translatedString!=null) {
                    Map<String, String> translation = translatedString.getTranslation();
                    Locale locale = LocaleContextHolder.getLocale();
                    if(translation.containsKey(locale.getLanguage())){
                        field.set(resource, translation.get(locale.getLanguage()));
                    }
                }
            }
        }
    }

    protected TranslatedString findExistingTranslations(Object resource, Field field, Method identifierMethod) throws InvocationTargetException, IllegalAccessException{
        return getDaoService().findExistingTranslations(
                resource.getClass().getSimpleName() + "." + field.getName(),
                String.valueOf(identifierMethod.invoke(resource))
        );
    }

    protected abstract TranslationDao getDaoService();


}
