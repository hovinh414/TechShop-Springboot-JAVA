package com.shoptech.site.category;

import com.shoptech.entity.Category;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
@EntityScan("com.shoptech.entity")
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.enable = true ORDER BY c.name ASC")
    public List<Category> findAllEnabled();

    @Query("SELECT c FROM Category c WHERE c.enable = true AND c.alias = ?1")
    public Category findByAliasEnabled(String alias);
}