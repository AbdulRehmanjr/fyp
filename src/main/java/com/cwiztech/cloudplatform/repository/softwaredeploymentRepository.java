package com.cwiztech.cloudplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.cloudplatform.model.SoftwareDeployment;




public interface softwaredeploymentRepository extends JpaRepository<SoftwareDeployment,Long> {

	@Query(value="select *from TBLSOFTWAREDEPLOYMENT where ISACTIVE='Y'",nativeQuery =true)
	 public List<SoftwareDeployment> findActive(); 

	@Query(value = "select * TBLSOFTWAREDEPLOYMENT from where  SOFTWARE_ID in (:ids) ", nativeQuery = true)
	public List<SoftwareDeployment> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLSOFTWAREDEPLOYMENT "
			+ "where (SOFTWARE_ID ?1 or VISIBILTY like ?1 ) and ISACTIVE='Y'", nativeQuery = true)
	public List<SoftwareDeployment> findBySearch(String search);

	@Query(value = "select * from TBLSOFTWAREDEPLOYMENT "
			+ "where like SOFTWARE_ID?1 or VISIBILTY like ?1 or DOCUMENTATIN_LINK like ?1 ", nativeQuery = true)
	public List<SoftwareDeployment> findAllBySearch(String search);

	@Query(value = "select * from TBLSOFTWAREDEPLOYMENT " 
			+ "where ISACTIVE='Y'", nativeQuery = true)
	List<SoftwareDeployment> findByAdvancedSearch(Long id);

	@Query(value = "select * from TBLSOFTWAREDEPLOYMENT " 
			+ "where ID LIKE SOFTWARE_ID  WHEN ?1 = 0 THEN VISIBILTY ID   ELSE ?1  END ", nativeQuery = true)
	List<SoftwareDeployment> findAllByAdvancedSearch(Long id);
}
