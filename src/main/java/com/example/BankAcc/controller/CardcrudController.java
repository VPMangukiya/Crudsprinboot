package com.example.BankAcc.controller;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import org.apache.lucene.search.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.BankAcc.entity.Cardtbl;

import com.example.BankAcc.repository.CardRepository;





@Controller
@RequestMapping()
public class CardcrudController {

	@Autowired
	private CardRepository cardtblRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
		
	
	//private cardtblServic StudentServic;
	
	//index page
	@RequestMapping(value="/", method=RequestMethod.GET)    
    public String index() {    
            
        return "index";    
    }    
	
	//login page
    @RequestMapping(value="/login", method=RequestMethod.GET)    
    public String login() {    
            
        return "login";    
    }    
      
	
	
	
	//auto genrate number
	public String autoGenrate()
	{
		long min1=1000000000000000L;
		long max1=9999999999999999L;
		
		long auto = (long)(Math.random()*(max1-min1+1)+min1); 
		return String.valueOf(auto);
	}
	
	// add cars form
	@GetMapping("showForm")
	public String showCardForm(Cardtbl cardtbl, Model model) {
		
		String acno = autoGenrate();
		List<Cardtbl> list = cardtblRepository.checkalreadyiacno(acno);
		
		//check alredy insrt or not
		if(list.isEmpty())
		{
			cardtbl.setAccno( acno);
			model.addAttribute("cardtbls", acno);
			return "insert";
		}
		else
		{
			
			
			return autoGenrate();
		}
		
		//return "insert";
	}
	
	
	
	//search using lucene 
	@GetMapping("/lu")
	@Transactional(readOnly = true)
	public String getSearch(Model model,Cardtbl  cardtbl, String keyword)
	{
		if(keyword.equals(""))
		{
			return "redirect:list";
		}
		//index
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		
		
		try {
			 	 
			 fullTextEntityManager.createIndexer() .threadsToLoadObjects(2) .startAndWait(); 
		} 
		catch (InterruptedException e) { 
			// TODO Auto-generated
			 e.printStackTrace(); 
		} 
			 
		//get a JPA FullTextEntityManager and from that a QueryBuilder:
			 QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Cardtbl.class) .get();
    		 
			 // create a Lucene query via the Hibernate query DSL:
			Query query = queryBuilder.keyword().onFields("name","profession","citizenship").matching(keyword) .createQuery();
			 
			 //Query query = queryBuilder.keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(0).onFields("name","profession","citizenship").matching(keyword).createQuery();
			 
			 
			// create a Lucene query via the Hibernate query DSL:
			 javax.persistence.Query persistenceQuery = fullTextEntityManager.createFullTextQuery(query, Cardtbl.class);
			 

			// excute the query and pass a value in model
			 List<Cardtbl> result =persistenceQuery.getResultList();
			
			 System.out.println("33333333333334########         "+result+"    status   "+ cardtbl.getStatus());
			 model.addAttribute("keyword",keyword );
			 model.addAttribute("cardtbls", result);
		
		//step = 5
		
		return "show";
	}
	
	
	//show grid view 
	@GetMapping("list")
	public String cards(Model model) {
		return findPaginated(1, "id", "asc", model);
	}
	
	//add data into table
	@PostMapping("add")
	public String addCard(@Valid Cardtbl cardtbl, BindingResult result, Model model) {

			if(result.hasErrors()) {
				return "insert";
			}
			
			String arno = cardtbl.getCardno();
			List<Cardtbl> list = cardtblRepository.checkalreadyiun(arno);
			
			//check alredy insrt or not
			if(list.isEmpty())
			{
				cardtbl.setStatus(1);
				this.cardtblRepository.save(cardtbl);
				return "redirect:list";
			}
			else
			{
				
				model.addAttribute("check", "already insert");
				return "insert";
			}
		
	}

	//show edit form
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable ("id") long id, Model model) {
		//get the id in table
		Cardtbl cardtbl = this.cardtblRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid cardtbl id : " + id));
		
		model.addAttribute("cardtbl", cardtbl);
		return "update";
	}
	
	//update data into table
	@PostMapping("update/{id}")
	public String updatecardtbl(@PathVariable("id") long id, @Valid Cardtbl cardtbl, BindingResult result, Model model) {
		if(result.hasErrors()) {
			cardtbl.setId(id);
			return "update";
		}
		
		// update cardtbl
		cardtbl.setStatus(1);
		cardtblRepository.save(cardtbl);
		
		// get all cardtbls ( with update)
		model.addAttribute("cardtbls", this.cardtblRepository.findAll());
		return "redirect:../list";
	}
	
	//delete data into table
	@GetMapping("delete/{id}")
	public String deletecardtbl(@PathVariable ("id") long id, Model model) {
		//get the id in table
		Cardtbl cardtbl = this.cardtblRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid cardtbl id : " + id));
		
				
		this.cardtblRepository.softdelete(id);
		model.addAttribute("cardtbls", this.cardtblRepository.findByStatus());
		return "redirect:../list";
		
	}
	
	//pagignation
	@GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo, @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, Model model) {
        int pageSize = 5;
 
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		  Page<Cardtbl> page =  this.cardtblRepository.findByStatus(pageable);

        List<Cardtbl> listCourses = page.getContent();
 
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
 
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
 
        model.addAttribute("cardtbls", listCourses);
        return "show";
    }
	
}
