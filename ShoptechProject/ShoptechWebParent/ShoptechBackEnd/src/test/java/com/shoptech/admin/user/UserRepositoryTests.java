package com.shoptech.admin.user;

import com.shoptech.entity.Role;
import com.shoptech.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;
    @Test
    public void testCreateUserWithOneRole(){
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userQuynhPhan = new User("yoliephan2255@gmail.com", "quynh2002", "Quynh", "Phan");
        userQuynhPhan.addRole(roleAdmin);

        User savedUser = repo.save(userQuynhPhan);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRole(){
        User userTrung = new User("nguyennguyentrung@gmail.com", "trung2002", "Trung", "Nguyen");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);
        userTrung.addRole(roleEditor);
        userTrung.addRole(roleAssistant);

        User savedUser = repo.save(userTrung);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers(){
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(System.out::println);

    }

    @Test
    public void testGetUserById(){
        User userQuynh = repo.findById(1).get();
        System.out.println(userQuynh);
        assertThat(userQuynh).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User userQuynh = repo.findById(1).get();
        userQuynh.setEnabled(true);
        userQuynh.setEmail("luisywang1411@gmail.com");
        repo.save(userQuynh);
    }

    @Test
    public void testUpdateUserRoles(){
        User userTrung = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);
        userTrung.getRoles().remove(roleEditor);
        userTrung.addRole(roleSalesperson);
        repo.save(userTrung);
    }

    @Test
    public void testDeleteUser(){
        int userId = 2;
        repo.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail()
    {
        String email = "luisywang1411@gmail.com";
        User user = repo.getUserByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {

        Integer userId = 14;
        Long countById = repo.countById(userId);

        assertThat(countById).isNotNull().isGreaterThan(0).isEqualTo(1);
    }

    @Test
    public void testDisableUser() {
        Integer userId = 2;

        repo.updateEnabledStatus(userId, false);
    }

    @Test
    public void testEnableUser() {
        Integer userId = 5;

        repo.updateEnabledStatus(userId, true);
    }



}
