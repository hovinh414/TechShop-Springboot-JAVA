package com.shoptech.admin.setting;

import java.util.List;

import com.shoptech.entity.Setting;
import com.shoptech.entity.SettingCategory;
import org.springframework.data.repository.CrudRepository;



public interface SettingRepository extends CrudRepository<Setting, String> {

	public List<Setting> findByCategory(SettingCategory category);
}
