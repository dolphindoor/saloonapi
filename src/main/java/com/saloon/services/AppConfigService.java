/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saloon.services;

import com.dolphindoors.resource.jpa.CrudApi;
import com.saloon.dto.AppConfigDto;
import com.saloon.entity.AppConfig;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pascal
 */
@Stateless
public class AppConfigService {
    @Inject private CrudApi crudApi;
    
    public AppConfigDto save(AppConfigDto dto){
        AppConfigDto configDto = null;
        AppConfig appConfig = toEntity(dto);
        if(appConfig != null && crudApi.save(appConfig) != null){
            configDto = toDto(appConfig);
        }
        return configDto;
    }
    
    public boolean delete(String configId){
        AppConfig appConfig = crudApi.find(AppConfig.class, configId);
        return appConfig != null ? crudApi.delete(appConfig) : false;
    }

    public List<AppConfigDto> fetchAllConfigs() {
        List<AppConfig> appConfigs = AppConfig.query(crudApi.getEm()).list();
        List<AppConfigDto> dtoList = new LinkedList<>();
        appConfigs.forEach(config -> {
            dtoList.add(toDto(config));
        });
        return dtoList;
    }

    public AppConfigDto findById(String configId) {
        AppConfig appConfig = crudApi.find(AppConfig.class, configId);
        return toDto(appConfig);
    }
    
    public AppConfigDto findByConfigName(String configName) {
        AppConfig appConfig = AppConfig.query(crudApi.getEm())
                 .where(AppConfig._configName, configName)
                .execute();
        return toDto(appConfig);
    }

    public AppConfigDto update(String configName, String configValue) {
        AppConfigDto configDto = null;
        AppConfig appConfig = AppConfig.query(crudApi.getEm())
                 .where(AppConfig._configName, configName)
                .execute();
        
        appConfig.setConfigValue(configValue);
        if(crudApi.save(appConfig) != null){
            configDto = toDto(appConfig);
        }
        return configDto;
    }
        
    public AppConfig toEntity(AppConfigDto dto) {
        AppConfig appConfig = new AppConfig();
        if (dto.getId() != null) {
            appConfig.setId(dto.getId());
        }
        appConfig.setConfigName(dto.getConfigName());
        appConfig.setConfigValue(dto.getConfigValue());
        appConfig.setConfigStatus(dto.getConfigStatus());
        return appConfig;
    }
    
    public AppConfigDto toDto(AppConfig appConfig) {
        AppConfigDto dto = new AppConfigDto();
        if (appConfig.getId() != null) {
            dto.setId(appConfig.getId());
        }
        dto.setId(appConfig.getId());
        dto.setConfigName(appConfig.getConfigName());
        dto.setConfigValue(appConfig.getConfigValue());
        dto.setConfigStatus(appConfig.getConfigStatus());
        return dto;
    }
    
}
