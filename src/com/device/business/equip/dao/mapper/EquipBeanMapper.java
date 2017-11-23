package com.device.business.equip.dao.mapper;

import com.device.business.equip.bean.EquipBean;

public interface EquipBeanMapper {
    int getMaxId();
    
    int addEquip(EquipBean record);
}