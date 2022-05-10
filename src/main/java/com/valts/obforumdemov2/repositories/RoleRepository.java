package com.valts.obforumdemov2.repositories;

import com.valts.obforumdemov2.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
