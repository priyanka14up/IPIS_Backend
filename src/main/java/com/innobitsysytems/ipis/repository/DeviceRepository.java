/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Device Repository
 */
package com.innobitsysytems.ipis.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
	
	@Query("SELECT i FROM Device i WHERE i.deviceType=:n and i.platformNo=:p")
	public Device getIdByDeviceType(@Param("n") DeviceType devicetype, @Param("p") String[] platformNo);

	Device findAllById(Long convertedLong);

	Device findByDeviceType(DeviceType cds);
	
	Device findByPortNumber(String portNumber);

	@Query("SELECT i FROM Device i WHERE i.deviceType=:n and i.platformNo=:p")
	public List <Device> getAllBydeviceType(@Param("n") DeviceType devicetype, @Param("p") String[] platformNo);
	
	List <Device> findAllBydeviceType(DeviceType deviceType);
	
	public Device findByIpAddress(String ipAddress);
	
	 Device findByDeviceTypeAndPlatformNo(DeviceType devicetype,String platformNo);
	
	@Query("SELECT d FROM Device d WHERE d.id=:n ")
	public List<Device> getDeviceById(@Param("n") Long id);
	
	


}
