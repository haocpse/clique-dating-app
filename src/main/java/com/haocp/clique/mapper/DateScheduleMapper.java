package com.haocp.clique.mapper;

import com.haocp.clique.dto.request.match.AddDateScheduleRequest;
import com.haocp.clique.dto.response.match.DateScheduleResponse;
import com.haocp.clique.entity.DateSchedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DateScheduleMapper {

    DateSchedule toDateSchedule(AddDateScheduleRequest request);
    DateScheduleResponse toDateScheduleResponse(DateSchedule dateSchedule);
}
