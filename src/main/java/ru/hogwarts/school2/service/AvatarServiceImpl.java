package ru.hogwarts.school2.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school2.model.Avatar;
import ru.hogwarts.school2.model.Student;
import ru.hogwarts.school2.repository.AvatarRepository;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.List;

@Service
public class AvatarServiceImpl implements AvatarService{

    public static final int IMAGE_SIZE = 1024;

    @Value("${path.to.folder.where.avatars.live")
    private String avatarsDirectory;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }


    @Override
    public Long uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentService.readStudent(studentId);
        if (student == null) {
            throw  new IllegalArgumentException("Can't find existing student by ID number " + studentId + ". Please enter ID of existing student");
        }

        Path filePath = createImageFilePath(avatarFile, student);
        saveImageToFile(avatarFile, filePath);

        Avatar avatar = findOrCreateAvatar(studentId);
        updateAvatar(avatarFile, student, filePath, avatar);
        return avatarRepository.save(avatar).getId();
    }


    @Override
    public Avatar findAvatar(Long id) {
        return avatarRepository.getById(id);
    }

    @Override
    public Collection<Avatar> getAllAvatarsByPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return avatarRepository.findAll(pageRequest).toList();
    }


    private Avatar findOrCreateAvatar(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    private void updateAvatar(MultipartFile avatarFile, Student student, Path filePath, Avatar avatar) throws IOException {
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
    }

    private void saveImageToFile(MultipartFile avatarFile, Path filePath) throws IOException {
        try (
                BufferedInputStream bis = new BufferedInputStream(avatarFile.getInputStream(), IMAGE_SIZE);
                BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW),IMAGE_SIZE)
        ) {
            bis.transferTo(bos);
        }
    }

    private Path createImageFilePath(MultipartFile avatarFile, Student student) throws IOException {
        Path filePath = Path.of(avatarsDirectory, student + "." + getExtentions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        return filePath;
    }

    private String getExtentions(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }
}
