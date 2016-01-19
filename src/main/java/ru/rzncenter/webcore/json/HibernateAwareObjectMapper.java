package ru.rzncenter.webcore.json;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

/**
 * Created by anton on 11.01.16.
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {

        Hibernate5Module module = new Hibernate5Module();

        module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
        module.configure(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION, false);

        registerModule(module);

        // When enabled a JSON View, non annotated fields or properties are not serialized

        configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

    }

}
