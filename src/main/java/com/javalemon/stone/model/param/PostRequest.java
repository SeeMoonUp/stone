package com.javalemon.stone.model.param;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lemon
 * @date 2019-11-23
 * @desc
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PostRequest<T> implements Serializable{

    private static final long serialVersionUID = -7852249427272992304L;

    private CommonParams commonParam;

    private T param;
}
