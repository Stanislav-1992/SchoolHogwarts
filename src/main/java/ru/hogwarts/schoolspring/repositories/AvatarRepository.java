package ru.hogwarts.schoolspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.schoolspring.model.Avatar;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByStudentId(Long studentId);
}
