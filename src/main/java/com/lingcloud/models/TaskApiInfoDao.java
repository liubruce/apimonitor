package com.lingcloud.models;


import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;


@Transactional
public interface TaskApiInfoDao extends CrudRepository<TaskApiInfo, Long> {
	
	List<TaskApiInfo>  findByTaskName(String taskName);
	
	//public AppDevice findByAppIdAndDeviceId(String appId, String deviceId);
	
}	
