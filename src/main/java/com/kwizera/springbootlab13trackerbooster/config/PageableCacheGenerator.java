package com.kwizera.springbootlab13trackerbooster.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("pageableCacheKeyGenerator")
public class PageableCacheGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(method.getName());

        for (Object param : params) {
            if (param instanceof Pageable) {
                Pageable pageable = (Pageable) param;
                keyBuilder.append("_page_").append(pageable.getPageNumber())
                        .append("_size_").append(pageable.getPageSize());

                // Include sort information
                if (pageable.getSort().isSorted()) {
                    for (Sort.Order order : pageable.getSort()) {
                        keyBuilder.append("_sort_").append(order.getProperty())
                                .append("_").append(order.getDirection());
                    }
                }
            } else {
                keyBuilder.append("_").append(param != null ? param.toString() : "null");
            }
        }

        return keyBuilder.toString();
    }
}
