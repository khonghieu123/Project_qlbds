package com.javaweb.converter;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.utils.MapUtil;
		
@Component
public class BuildingSearchBuilderConverter {

    public BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params, List<String> typeCode) {
        return new BuildingSearchBuilder.Builder()
            .setName(MapUtil.getObject(params, "name", String.class))
            .setFloorArea(MapUtil.getObject(params, "floorArea", Long.class))
            .setWard(MapUtil.getObject(params, "ward", String.class))
            .setStreet(MapUtil.getObject(params, "street", String.class))
            .setDistrictid(MapUtil.getObject(params, "districtid", String.class))
            .setNumberOfBasement(MapUtil.getObject(params, "numberOfBasement", Integer.class))
            .setTypeCode(typeCode)
            .setManagerName(MapUtil.getObject(params, "managername", String.class))
            .setManagerPhoneNumber(MapUtil.getObject(params, "managerphonenumber", String.class))
            .setRentPriceTo(MapUtil.getObject(params, "rentPriceTo", Long.class))
            .setRentPriceFrom(MapUtil.getObject(params, "rentPriceFrom", Long.class))
            .setAreaFrom(MapUtil.getObject(params, "areaFrom", Long.class))
            .setAreaTo(MapUtil.getObject(params, "areaTo", Long.class))
            .setStaffId(MapUtil.getObject(params, "staffid", Long.class))
            .build();
    }
}
