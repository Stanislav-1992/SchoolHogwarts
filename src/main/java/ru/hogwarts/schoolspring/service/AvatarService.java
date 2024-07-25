package ru.hogwarts.schoolspring.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.schoolspring.model.Avatar;

import java.io.IOException;

public interface AvatarService {

    void uploadAvatar(Long studentId, MultipartFile file) throws IOException;

    Avatar findAvatar(Long studentId);
}

