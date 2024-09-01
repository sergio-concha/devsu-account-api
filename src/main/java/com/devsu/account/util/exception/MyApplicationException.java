package com.devsu.account.util.exception;

import com.devsu.account.util.generic.dao.Response;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Setter
@Getter
public class MyApplicationException extends Exception {

	@Serial
    private static final long serialVersionUID = -7713250406255170572L;

    private static final Logger LOGGER = LogManager.getLogger(MyApplicationException.class);
    private static final String ERROR = "Error => {}";

    private String codigo;

    public MyApplicationException() {
        super();
    }

    public MyApplicationException(String message) {
        super(message);
    }

    public MyApplicationException(String codigo, String message) {
        super(message);
        this.codigo = codigo;
    }

    public MyApplicationException(String codigo, String message, Throwable causa) {
        super(message, causa);
        this.codigo = codigo;
    }

    public MyApplicationException(Class<?> clazz, String... searchParamsMap) {
        super(MyApplicationException.generateMessage(clazz.getSimpleName(),
                toMap(String.class, String.class, (Object) searchParamsMap)));
    }


    /*******************************************************************/
    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) + " was not found for parameters " + searchParams;
    }

    private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1) {
            throw new IllegalArgumentException("Invalid entries");
        }
        return IntStream.range(0, entries.length / 2).map(i -> i * 2).collect(HashMap::new,
                (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
    }

    /*******************************************************************/

    public static ResponseEntity<ApiError> validarResultado(MyApplicationException ex, HttpStatus status) {
        LOGGER.error(ERROR, ex.getMessage());

        ApiError error = new ApiError().status(status).codigo(ex.getCodigo()).message(ex.getMessage()).debugMessage(ex.getLocalizedMessage());

        return  new ResponseEntity<>(error, status);
    }

    private static ApiError getApiError(Exception ex, HttpStatus status) {
        LOGGER.error(ERROR, ex.getMessage());
        return new ApiError().status(status).codigo(String.valueOf(status.value())).message(ex.getMessage()).debugMessage(ex.getLocalizedMessage());
    }

    public static ResponseEntity<ApiError> validarResultado(Exception ex, HttpStatus status) {
        return new ResponseEntity<>(getApiError(ex, status), status);
    }

    public static ResponseEntity<Response> validarResultadoResponse(Exception ex, HttpStatus status) {
        return new ResponseEntity<>(getApiError(ex, status), status);
    }
}
