package com.shoptech.admin.user;

import com.shoptech.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateFirstRole(){
        Role roleAdmin = new Role("Admin", "Quản lý mọi thứ");
        Role savedRole = repo.save(roleAdmin);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRoles(){
        Role roleSalesperson = new Role("Salesperson", "Quản lý giá sản phẩm, khách hàng, " +
                "vận chuyển, đơn hàng " + "và báo cáo doanh thu");
        Role roleEditor = new Role("Editor", "Quản lý danh mục, thương hiệu," +
                " sản phẩm, bài viết và menu");
        Role roleShipper = new Role("Shipper", "Xem sản phẩm, xem đơn hàng " +
                "và cập nhật trạng thái đơn hàng");
        Role roleAssistant = new Role("Assistant", "Quản lý bình luận và đánh giá");

        repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
    }
}
