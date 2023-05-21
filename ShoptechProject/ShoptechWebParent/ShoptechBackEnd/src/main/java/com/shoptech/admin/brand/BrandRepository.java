
package com.shoptech.admin.brand;

import com.shoptech.entity.Brand;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepository extends CrudRepository<Brand, Integer> {
    public Long countById(Integer id);
    public Brand findByName(String name);
}

