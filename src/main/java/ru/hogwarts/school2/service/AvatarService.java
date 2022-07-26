package ru.hogwarts.school2.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school2.model.Avatar;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface AvatarService {
    Long uploadAvatar(Long studentId, MultipartFile avatar) throws IOException;

    Avatar findAvatar(Long id);

    Collection<Avatar> getAllAvatarsByPage(int page, int size);
}
