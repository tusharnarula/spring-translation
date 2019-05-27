# Spring Translation 

Spring abstraction for localising resources

## Getting Started


### Add this to your pom.xml

```
<dependency>
    <groupId>com.crat.starc.code.commons</groupId>
    <artifactId>spring-translation</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### Annotate your resources
  * @TranslatableResource - Annotation that this type can be translated, eg - Employee
  * @Translatable - Annotation that this field can have multiple translations, eg - title
  * @TranslationIdentifier - Annotation that this method is the unique identifier for this type - Employee::getId()

### Create translation configuration
  * Create a configuration extending the abstract class *TranslationConfig*.

```
@Configuration
@DependsOn("translationMongoTemplate")
public class TranslationConfig extends com.oyo.opstech.commons.translation.config.TranslationConfig {

    private final MongoTemplate translationMongoTemplate;

    @Autowired
    public TranslationConfig(
            @Qualifier("translationMongoTemplate") MongoTemplate translationMongoTemplate) {
        this.translationMongoTemplate = translationMongoTemplate;
    }

    @Override
    public MongoTemplate translationMongoTemplate() {
        return translationMongoTemplate;
    }

}
```

This will create a bean - **translationManager**

### Use the following interfaces for translate

* use the following methods from **translationManager**
  * localize(Object) - localize all translatable fields inside the object to the locale from Spring's - LocaleContextHolder
    * example -
    ```
    Employee emp; //logic
    translationManager.localize(emp);
    ```
  * updateTranslationFor(ResourceTranslationUpdateRequest request)
    * example -
    ```
    Employee emp;
    Field titleField;
    Locale locale;
    String translationString;

    ResourceTranslationUpdateRequest resourceTranslationUpdateRequest = new ResourceTranslationUpdateRequest();
    resourceTranslationUpdateRequest.setResource(emp);
    resourceTranslationUpdateRequest.setField(titleField);
    resourceTranslationUpdateRequest.setLanguage(locale);
    resourceTranslationUpdateRequest.setTranslation(translationString);
    try {
        translationManager.updateTranslationFor(resourceTranslationUpdateRequest);
    } catch (Exception exception) {

    }
    ```
