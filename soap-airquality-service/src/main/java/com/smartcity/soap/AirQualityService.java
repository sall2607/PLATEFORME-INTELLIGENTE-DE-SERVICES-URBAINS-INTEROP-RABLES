package com.smartcity.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import com.smartcity.soap.model.AirData;

@WebService
public interface AirQualityService {

    @WebMethod
    int getAQI(@WebParam(name="zone") String zone);

    @WebMethod
    AirData getPollutants(@WebParam(name="zone") String zone);

    @WebMethod
    String compareZones(
        @WebParam(name="zone1") String z1,
        @WebParam(name="zone2") String z2
    );
}
