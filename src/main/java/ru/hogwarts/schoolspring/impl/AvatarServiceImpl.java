package ru.hogwarts.schoolspring.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.schoolspring.model.Avatar;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.repositories.AvatarRepository;
import ru.hogwarts.schoolspring.service.AvatarService;
import ru.hogwarts.schoolspring.service.StudentService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    @Value("avatar")
    private String avatarDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    private final Logger log = LoggerFactory.getLogger(AvatarService.class);

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        log.info("Был вызван метод, чтобы загрузить аватар");
        Student student = studentService.getStudent(studentId);

        Path filePath = Path.of(avatarDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateImagePreview(filePath));

        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(Long studentId) {
        log.info("Был вызван метод, чтобы найти аватар");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());

    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        log.info("Был вызван метод, чтобы создать превью аватарок");
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtension(String fileName) {
        log.info("Был вызван метод для получения необходимого расширения имени файла");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> getListsOfAvatarsPageByPage(Integer pageNumber, Integer pageSize) {
        log.info("Был вызван метод, чтобы получить список аватарок постранично");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}