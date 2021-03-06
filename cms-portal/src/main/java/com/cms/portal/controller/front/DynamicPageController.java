package com.cms.portal.controller.front;

import com.cms.contex.foundation.Result;
import com.cms.contex.utils.UtilsHttp;
import com.cms.contex.utils.UtilsServletContext;
import com.cms.contex.utils.UtilsTemplate;
import com.cms.core.foundation.Page;
import com.cms.dao.enums.StaticWebSuffixEnum;
import com.cms.service.api.CmsContentListenerService;
import com.cms.service.api.CmsSiteService;
import com.cms.service.dto.CmsContentDto;
import com.cms.service.dto.CmsSiteDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class DynamicPageController {

    @Autowired
    private CmsSiteService cmsSiteService;
    @Autowired
    private UtilsServletContext utilsServletContext;
    @Autowired
    private CmsContentListenerService cmsContentListenerService;

    @GetMapping("index.shtml")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model){
        CmsSiteDto cmsSite = cmsSiteService.get();
        if(BooleanUtils.isTrue(existStaticIndexPage(cmsSite))){
            String contextPath = request.getContextPath();
            try {
                response.sendRedirect(contextPath+"/"+cmsSite.getStaticDir()+"/index"+StaticWebSuffixEnum.HTML.getLabel());
                return null;
            } catch (IOException e) {
                log.error("跳转静态首页报错,错误信息=[{}]",e.getMessage());
            }
        }
        model.addAttribute("site",cmsSite);
        return UtilsTemplate.frontTemplate("index","index");
    }

    /**
     * 判断首页静态文件是否存在
     * @param cmsSite       站点信息
     * @return              布尔值
     */
    private boolean existStaticIndexPage(CmsSiteDto cmsSite){
        Boolean staticIndex = cmsSite.getStaticIndex();
        log.info("判断是否开启首页静态化=[{}]",staticIndex);
        if(BooleanUtils.isTrue(staticIndex)){
            String staticDir = cmsSite.getStaticDir();
            return BooleanUtils.isTrue(FileUtil.canReadFile(new File(utilsServletContext.getRealPath(staticDir+"/index"+ StaticWebSuffixEnum.HTML.getLabel()))));
        }
        return false;
    }

    @GetMapping("search.shtml")
    public String toSearch(Model model){
        model.addAttribute("basePath",utilsServletContext.getContextPath());
        return UtilsTemplate.frontTemplate("search","index");
    }

    @PostMapping("search.jspx")
    @ResponseBody
    public Result<Page<CmsContentDto>> doSearch(String content){
        UtilsHttp.Page pageInfo = UtilsHttp.getPageInfo();
        try {
            return Result.success(cmsContentListenerService.query(content,pageInfo.getPageCurrent()));
        } catch (Exception e) {
            log.error("搜索查询异常=[{}]",e.getMessage());
        }
        return Result.success();
    }
}
