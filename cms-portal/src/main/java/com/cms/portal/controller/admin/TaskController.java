package com.cms.portal.controller.admin;

import com.cms.contex.foundation.Result;
import com.cms.contex.utils.UtilsTemplate;
import com.cms.dao.enums.TaskExecutionCycleUnitEnum;
import com.cms.dao.enums.TaskExecutionTypeEnum;
import com.cms.dao.enums.TaskStaticTypeEnum;
import com.cms.service.api.CmsTaskService;
import com.cms.service.dto.CmsTaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("task")
public class TaskController {

    @Autowired
    private CmsTaskService cmsTaskService;

    @GetMapping("index.do")
    public String toIndex(){
        return UtilsTemplate.adminTemplate("task","index");
    }

    @GetMapping("add.do")
    public String toAdd(Model model){
        model.addAttribute("taskType", TaskStaticTypeEnum.values());
        model.addAttribute("taskExecutionType", TaskExecutionTypeEnum.values());
        model.addAttribute("taskExecutionCycle", TaskExecutionCycleUnitEnum.values());
        return UtilsTemplate.adminTemplate("task","add");
    }

    @PostMapping("add.do")
    @ResponseBody
    public Result doAdd(CmsTaskDto cmsTaskDto){
        cmsTaskService.save(cmsTaskDto);
        return Result.success();
    }
}
