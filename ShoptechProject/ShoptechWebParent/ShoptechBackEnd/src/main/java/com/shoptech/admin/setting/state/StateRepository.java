package com.shoptech.admin.setting.state;

import com.shoptech.entity.Country;
import com.shoptech.entity.State;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StateRepository extends CrudRepository<State, Integer> {

    public List<State> findByCountryOrderByNameAsc(Country country);
}