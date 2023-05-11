package com.shoptech.admin.user;

import com.shoptech.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer>
{

}
