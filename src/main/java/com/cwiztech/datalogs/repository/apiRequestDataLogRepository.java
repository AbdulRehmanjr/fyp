package com.cwiztech.datalogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cwiztech.datalogs.model.APIRequestDataLog;

@Repository
public interface apiRequestDataLogRepository extends JpaRepository<APIRequestDataLog,Long>{

}
