package com.shoptech.site.setting;

import com.shoptech.entity.Country;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;
=======
>>>>>>> main
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Integer> {
    public List<Country> findAllByOrderByNameAsc();
<<<<<<< HEAD

    @Query("SELECT c FROM Country c WHERE c.code = ?1")
    public Country findByCode(String code);
=======
>>>>>>> main
}
