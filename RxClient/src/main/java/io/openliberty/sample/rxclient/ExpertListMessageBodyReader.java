package io.openliberty.sample.rxclient;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

public class ExpertListMessageBodyReader implements MessageBodyReader<List<Expert>> {

    @Override
    public boolean isReadable(Class<?> clazz, Type type, Annotation[] anns, MediaType mediaType) {
        return MediaType.APPLICATION_JSON_TYPE.equals(mediaType) &&
               type instanceof ParameterizedType && 
               List.class.equals(((ParameterizedType)type).getRawType()) &&
               ((ParameterizedType)type).getActualTypeArguments().length == 1 &&
               Expert.class.equals(((ParameterizedType)type).getActualTypeArguments()[0]);
    }

    @Override
    public List<Expert> readFrom(Class<List<Expert>> clazz, Type type, Annotation[] anns, MediaType mt,
            MultivaluedMap<String, String> map, InputStream is) throws IOException, WebApplicationException {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(is, new ArrayList<Expert>() {}.getClass().getGenericSuperclass());
    }

}
