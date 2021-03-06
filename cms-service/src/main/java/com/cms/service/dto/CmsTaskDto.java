package com.cms.service.dto;

import com.cms.core.foundation.BaseDto;
import com.cms.dao.enums.TaskExecutionCycleUnitEnum;
import com.cms.dao.enums.TaskExecutionTypeEnum;
import com.cms.dao.enums.TaskStaticTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsTaskDto extends BaseDto<Integer> {
    private String name;
    private String code;
    private TaskStaticTypeEnum type;
    private TaskExecutionTypeEnum taskExecutionType;
    private Integer dayOfMonth;
    private Integer dayOfWeek;
    private Integer hour;
    private Integer minute;
    private Integer intervalHour;
    private Integer intervalMinute;
    private String cronExpression;
    private Boolean enable;
    private TaskExecutionCycleUnitEnum intervalUnit;
    private String remark;
}
