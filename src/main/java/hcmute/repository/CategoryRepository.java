package hcmute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByName(String string);
    
	
}