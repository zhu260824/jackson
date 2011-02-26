package org.codehaus.jackson.map.module;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

/**
 * Simple implementation {@link KeyDeserializers} which allows registration of
 * deserializers based on raw (type erased class).
 * It can work well for basic bean and scalar type deserializers, but is not
 * a good fit for handling generic types (like {@link Map}s and {@link Collection}s
 * or array types).
 *<p>
 * Unlike {@link SimpleSerializers}, this class does not currently support generic mappings;
 * all mappings must be to exact declared deserialization type.
 * 
 * @since 1.7
 */
public class SimpleKeyDeserializers implements KeyDeserializers
{
    protected HashMap<JavaType,KeyDeserializer> _classMappings = null;

    /*
    /**********************************************************
    /* Life-cycle, construction and configuring
    /**********************************************************
     */
    
    public SimpleKeyDeserializers() { }

    public SimpleKeyDeserializers addDeserializer(JavaType forType, KeyDeserializer deser)
    {
        if (_classMappings == null) {
            _classMappings = new HashMap<JavaType,KeyDeserializer>();
        }
        _classMappings.put(forType, deser);
        return this;
    }

    public SimpleKeyDeserializers addDeserializer(Class<?> forClass, KeyDeserializer deser)
    {
        return addDeserializer(TypeFactory.type(forClass), deser);
    }

    /*
    /**********************************************************
    /* Serializers implementation
    /**********************************************************
     */

    @Override
    public KeyDeserializer findKeyDeserializer(JavaType type,
            DeserializationConfig config, DeserializerProvider provider,
            BeanDescription beanDesc, BeanProperty property)
    {
        return (_classMappings == null) ? null : _classMappings.get(type);
    }
}