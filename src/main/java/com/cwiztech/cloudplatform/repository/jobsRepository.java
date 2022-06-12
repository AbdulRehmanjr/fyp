package com.cwiztech.cloudplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.cloudplatform.model.Jobs;
import com.cwiztech.cloudplatform.model.OrganizationResource;

public interface jobsRepository extends JpaRepository<Jobs,Long> {

	@Query(value="select *fromTBLJOBS where ISACTIVE='Y'",nativeQuery =true)
	 public List<Jobs> findActive(); 

	@Query(value = "select *TBLJOBS from where  JOB_ID in (:ids) ", nativeQuery = true)
	public List<Jobs> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLJOBS "
			+ "where (JOB_ID ?1 or SOFTWARE_ID like ?1 or NO_OF_NODES like ?1) and ISACTIVE='Y'", nativeQuery = true)
	public List<Jobs> findBySearch(String search);

	@Query(value = "select * fromTBLJOBS "
			+ "where like JOB_ID?1 or EXECUTABLES like ?1 or NO_OF_NODES like ?1 ", nativeQuery = true)
	public List<Jobs> findAllBySearch(String search);

	@Query(value = "select * fromTBLJOBS " 
			+ "where ISACTIVE='Y'", nativeQuery = true)
	List<Jobs> findByAdvancedSearch(Long id);

	@Query(value = "select * fromTBLJOBS " 
			+ "where ID LIKE JOB_ID  WHEN ?1 = 0 THEN EXECUTABLES ID   ELSE ?1  END ", nativeQuery = true)
	List<Jobs> findAllByAdvancedSearch(Long id);
}
