package com.smartcity.soap;

import jakarta.jws.WebService;
import jakarta.jws.WebParam;
import com.smartcity.soap.model.AirData;

@WebService(endpointInterface = "com.smartcity.soap.AirQualityService")
public class AirQualityServiceImpl implements AirQualityService {

    @Override
    public int getAQI(@WebParam(name = "zone")String zone) {
        return 30 + (zone.length() * 5) % 150;
    }

    @Override
    public AirData getPollutants(String zone) {
        return new AirData(
            getAQI(zone),
            10 + zone.length(),
            400 + zone.length(),
            15 + zone.length() * 0.5
        );
    }

    @Override
    public String compareZones(String z1, String z2) {
        return getAQI(z1) < getAQI(z2) ? z1 : z2;
    }
}
