package com.wanli.collect.exception;

/**
 * @author Hu
 * @date 2018/12/23 16:21
 */

public interface ErrorCode {
    int getCode();
    String getMessage();
    int getHttpCode();
}
