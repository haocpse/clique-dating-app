package com.haocp.clique.mapper;

import com.haocp.clique.dto.request.match.AddDateScheduleRequest;
import com.haocp.clique.dto.request.match.UpdateDateScheduleRequest;
import com.haocp.clique.dto.response.match.DateScheduleResponse;
import com.haocp.clique.entity.DateSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {PartnerMapper.class})
public interface DateScheduleMapper {

    DateSchedule toDateSchedule(AddDateScheduleRequest request);
    DateSchedule updateToDateSchedule(UpdateDateScheduleRequest request, @MappingTarget DateSchedule dateSchedule);
    DateScheduleResponse toDateScheduleResponse(DateSchedule dateSchedule);
}
