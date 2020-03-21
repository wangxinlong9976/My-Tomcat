package com.wxl.server;

import java.util.HashSet;
import java.util.Set;

/**
 * ClassName: ServletMapping <br/>
 * Description: <br/>
 * date: 2020/3/21 15:56<br/>
 *
 * @author lenovo<br />
 * @version v1.0
 * @since JDK 1.8
 */
public class ServletMapping {
    private String name;
    private Set<String> pattern;

    public String getName() {
        return name;
    }

    public Set<String> getPattern() {
        return pattern;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPattern(Set<String> pattern) {
        this.pattern = pattern;
    }

    public ServletMapping(){
        pattern = new HashSet<String>();
    }
}
