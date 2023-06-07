package com.shoptech.site.category;

import com.shoptech.entity.Category;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
@EntityScan("com.shoptech.entity")
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    public List<Category> findRootCategories();

    @Query("SELECT c FROM Category c WHERE c.enable = true AND c.alias = ?1")
    public Category findByAliasEnabled(String alias);
    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    public List<Category> findRootCategories(Sort sort);

}