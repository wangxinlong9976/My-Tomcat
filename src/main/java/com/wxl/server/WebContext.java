package com.wxl.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ClassName: WebContext <br/>
 * Description: <br/>
 * date: 2020/3/21 16:32<br/>
 *
 * @author lenovo<br />
 * @version v1.0
 * @since JDK 1.8
 */
public class WebContext {
    private List<ServletTag> servletTags;
    private List<ServletMapping> servletMappings;

    private Map<String,String> servletMap = new HashMap<String, String>();
    private Map<String,String> mappingMap = new HashMap<String, String>();

    public WebContext(List<ServletTag> servletTags,List<ServletMapping> servletMappings){
        this.servletTags = servletTags;
        this.servletMappings = servletMappings;
        handlerMapping();
    }

    private void handlerMapping(){
        for (ServletTag servletTag : servletTags){
            servletMap.put(servletTag.getName(),servletTag.getClazz());
        }

        for (ServletMapping servletMapping : servletMappings){
            for (String pattern :servletMapping.getPattern()){
                mappingMap.put(pattern,servletMapping.getName());
            }
        }
        System.out.println("--> pattern:name" + mappingMap);
    }


    public String getClass(String pattern){
        System.out.println("--> 获取class:"+pattern);
        return servletMap.get(mappingMap.get(pattern));
    }
}
