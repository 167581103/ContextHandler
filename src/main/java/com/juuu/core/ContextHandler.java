package com.juuu.core;

import com.juuu.exception.ContextException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于ThreadLocal封装，支持多线程环境下的线程私有上下文存储和获取
 * @author cguo
 */
public class ContextHandler {
    private static final Map<String,ThreadLocal<Object>> CONTEXT_MAP = new ConcurrentHashMap<>();
    private static final Map<String,Object> GLOBAL_MAP = new ConcurrentHashMap<>();
    public static Object get(String key){
        if (GLOBAL_MAP.containsKey(key)){
            return GLOBAL_MAP.get(key);
        }
        if (CONTEXT_MAP.containsKey(key)){
            return CONTEXT_MAP.get(key).get();
        }
        throw new ContextException("线程 "+Thread.currentThread().getName()+" 找不到上下文："+key);
    }

    public static <T> T get(String key,Class<T> clazz){
        return clazz.cast(get(key));
    }

    public static String getString(String key){
        return get(key,String.class);
    }

    public static synchronized void set(String key, Object context){
        if (CONTEXT_MAP.containsKey(key)){
            CONTEXT_MAP.get(key).set(context);
            return;
        }
        ThreadLocal<Object> threadLocal = new ThreadLocal<>();
        threadLocal.set(context);
        CONTEXT_MAP.put(key,threadLocal);
    }

    public static void setGlobal(String key, Object context){
        GLOBAL_MAP.put(key,context);
    }

    public static void remove(String key){
        if(!CONTEXT_MAP.containsKey(key)){
            return;
        }
        CONTEXT_MAP.get(key).remove();
    }

    public static void clearContext(){
        for(ThreadLocal<Object> threadLocal : CONTEXT_MAP.values()){
            threadLocal.remove();
        }
    }
}
