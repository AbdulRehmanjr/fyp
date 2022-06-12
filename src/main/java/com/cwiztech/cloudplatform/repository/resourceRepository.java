package com.cwiztech.cloudplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.cloudplatform.model.Resource;

	

public interface resourceRepository extends JpaRepository<Resource,Long> {

	@Query(value="select *from TBLRESOURCE where ISACTIVE='Y'",nativeQuery =true)
	 public List<Resource> findActive(); 

	@Query(value = "select * TBLRESOURCE from where  RESOURCE_ID in (:ids) ", nativeQuery = true)
	public List<Resource> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLRESOURCE "
			+ "where (RESOURCE_ID ?1 or AWS_ACCOUNT_ID like ?1) and ISACTIVE='Y'", nativeQuery = true)
	public List<Resource> findBySearch(String search);

	@Query(value = "select * from TBLRESOURCE "
			+ "where like RESOURCE_ID?1 or AWS_ACCOUNT_ID like ?1 or ACCESS_KEY_ID like ?1 ", nativeQuery = true)
	public List<Resource> findAllBySearch(String search);

	@Query(value = "select * from TBLRESOURCE " 
			+ "where ISACTIVE='Y'", nativeQuery = true)
	List<Resource> findByAdvancedSearch(Long id);

	@Query(value = "select * from TBLRESOURCE " 
			+ "where ID LIKE AWS_ACCOUNT_ID  WHEN ?1 = 0 THEN SECRET_ACCESS_KEY   ELSE ?1  END ", nativeQuery = true)
	List<Resource> findAllByAdvancedSearch(Long id);
}
