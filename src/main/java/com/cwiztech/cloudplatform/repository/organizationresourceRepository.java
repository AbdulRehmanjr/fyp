package com.cwiztech.cloudplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.cloudplatform.model.OrganizationResource;


public interface organizationresourceRepository extends JpaRepository<OrganizationResource,Long> {

	@Query(value="select *from TBLORGANIZATIORESOURCE where ISACTIVE='Y'",nativeQuery =true)
	 public List<OrganizationResource> findActive(); 

	@Query(value = "select * TBLORGANIZATIORESOURCE from where  ORGANIZATIONRESOURCE_ID in (:ids) ", nativeQuery = true)
	public List<OrganizationResource> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLORGANIZATIORESOURCE "
			+ "where (MAXIMUM_NODES ?1 or ORGANIZATIONRESOURCE_DESCRIPTION like ?1 or ACCESSINTERNET like ?1) and ISACTIVE='Y'", nativeQuery = true)
	public List<OrganizationResource> findBySearch(String search);

	@Query(value = "select * from TBLORGANIZATIORESOURCE "
			+ "where like MAXIMUM_NODES?1 or ORGANIZATIONRESOURCE_DESCRIPTION like ?1 or PROXY_USERNAME like ?1 ", nativeQuery = true)
	public List<OrganizationResource> findAllBySearch(String search);

	@Query(value = "select * from TBLORGANIZATIORESOURCE " 
			+ "where ISACTIVE='Y'", nativeQuery = true)
	List<OrganizationResource> findByAdvancedSearch(Long id);

	@Query(value = "select * from TBLORGANIZATIORESOURCE " 
			+ "where ID LIKE PROXY_HOSTNAME  WHEN ?1 = 0 THEN ORGANIZATIONRESOURCE_ ID   ELSE ?1  END ", nativeQuery = true)
	List<OrganizationResource> findAllByAdvancedSearch(Long id);
}
