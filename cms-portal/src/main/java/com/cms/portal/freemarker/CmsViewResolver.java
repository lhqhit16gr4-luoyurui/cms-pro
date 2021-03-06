package com.cms.portal.freemarker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 视图解析器
 */
@Slf4j
public class CmsViewResolver extends FreeMarkerView {
    //后台路径
    private static final String ADMIN_PATH = "/admin/cms/";


    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        List<String> includeGoBackList = Arrays.asList("add.do", "edit.do","error.do");
        //就认为是后台请求路径
        if (requestURI.contains(ADMIN_PATH)) {
            model.put("adminPath", contextPath + servletPath);
            //判断回退按钮
            includeGoBackList.forEach(x->{
                if(requestURI.contains(x)){
                    model.put("goBack",true);
                    model.put("operationUrl",x);
                }
            });
        }
        model.put("basePath", contextPath);
    }
}
