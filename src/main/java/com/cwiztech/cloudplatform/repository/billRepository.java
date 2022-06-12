package com.cwiztech.cloudplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.cloudplatform.model.Bill;
public interface billRepository extends JpaRepository<Bill,Long> {

	@Query(value="select *from TBLBILL where ISACTIVE='Y'",nativeQuery =true)
	 public List<Bill> findActive(); 

	@Query(value = "select * TBLBILL from where  BILL_ID in (:ids) ", nativeQuery = true)
	public List<Bill> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLBILL "
			+ "where (BILL_ID ?1 or USER_ID like ?1 or RESOURCE_ID like ?1) and ISACTIVE='Y'", nativeQuery = true)
	public List<Bill> findBySearch(String search);

	@Query(value = "select * from TBLBILL "
			+ "where like BILL_ID?1 or RESOURCE_ID like ?1 or TOTAL_AMOUNT like ?1 ", nativeQuery = true)
	public List<Bill> findAllBySearch(String search);

	@Query(value = "select * from TBLBILL " 
			+ "where ISACTIVE='Y'", nativeQuery = true)
	List<Bill> findByAdvancedSearch(Long id);

	@Query(value = "select * from TBLBILL " 
			+ "where ID LIKE STATUS  WHEN ?1 = 0 THEN QUANTITY ID   ELSE ?1  END ", nativeQuery = true)
	List<Bill> findAllByAdvancedSearch(Long id);
}
