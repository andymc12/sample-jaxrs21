package io.openliberty.sample.scheduling;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

@Provider
public class LocalDateParamConverter implements ParamConverterProvider, ParamConverter<LocalDate> {

    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawtype, Type type, Annotation[] annos) {
        if (LocalDate.class.equals(rawtype)) {
            return (ParamConverter<T>) this;
        }
        return null;
    }

    @Override
    public LocalDate fromString(String text) {
        try {
            return LocalDate.parse(text);
        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
            throw new WebApplicationException(Response.status(400)
                                                      .entity("Date format must be YYYY-MM-DD")
                                                      .build());
        }
    }

    @Override
    public String toString(LocalDate date) {
        return date.toString();
    }

}
