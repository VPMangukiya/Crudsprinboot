package com.example.BankAcc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import com.example.BankAcc.entity.Cardtbl;



@Repository
public interface CardRepository extends JpaRepository<Cardtbl, Long>{
	List<Cardtbl> findByName(String name);
	
	//fetch data
	@Query("SELECT c FROM Cardtbl c where  c.status = 1")
	List<Cardtbl> findByStatus();
	// show all undeleted user with pagigneted
	@Query("SELECT c FROM Cardtbl c where  c.status = 1")
	Page<Cardtbl> findByStatus(Pageable  pageable);
	
	//searching query
	//@Query(value="select * from cardtbl1 c where c.name Like '%?1%'", nativeQuery = true)
	@Query("SELECT p FROM Cardtbl p WHERE CONCAT(p.name, p.profession, p.citizenship) LIKE %?1% and status=1")
	List<Cardtbl> searchcname(@Param("keyword") String keyword);
	
	//check card no is already insert or not
	//@Query("SELECT TOP 1 accno FROM cardtbl1  ORDER BY id DESC")
	@Query("SELECT  c.cardno FROM Cardtbl c where  c.cardno = ?1")
	List<Cardtbl> checkalreadyiun( String arno);
	
	
	//check account no is already insert or not
	@Query("SELECT  c.accno FROM Cardtbl c where  c.accno = ?1")
	List<Cardtbl> checkalreadyiacno( String accno);
	
	//soft delete 
	@Transactional
	@Modifying
	@Query("update Cardtbl c set c.status =0 where id =?1")
	void softdelete(long id);
}
