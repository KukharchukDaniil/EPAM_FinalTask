package com.epam.command;

import javax.servlet.http.HttpServletRequest;

public class InjectionProtector {
    public static String getSafeAttribute(HttpServletRequest request, String attributeName){
        String parameter = request.getParameter(attributeName);
        return parameter!=null? parameter.replaceAll("(<\\/?script>)", ""):null;
    }
}
