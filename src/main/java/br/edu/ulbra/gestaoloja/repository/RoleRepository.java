package br.edu.ulbra.gestaoloja.repository;

import br.edu.ulbra.gestaoloja.model.Role;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Set<Role> findAllByName(String name);
}
