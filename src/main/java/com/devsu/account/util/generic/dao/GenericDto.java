package com.devsu.account.util.generic.dao;

import java.io.Serial;
import java.io.Serializable;

public abstract class GenericDto<T extends GenericDto<T>> implements IGenericDto<Long>, Serializable {
    @Serial
    private static final long serialVersionUID = -374580369079967296L;
}
