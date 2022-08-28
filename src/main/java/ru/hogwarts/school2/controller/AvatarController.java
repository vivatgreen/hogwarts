package ru.hogwarts.school2.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school2.model.Avatar;
import ru.hogwarts.school2.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) {
        try {
            Long id = avatarService.uploadAvatar(studentId, avatar);
            return ResponseEntity.ok(id + " was uploaded successfully");
        } catch (IOException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Avatar upload failed", exception);
        }
    }

    @GetMapping(value = "/{avaId}/avadb")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long avaId) {
        Avatar avatar = avatarService.findAvatar(avaId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getFileSize());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{avaId}/avafile")
    public void downloadAvatar(@PathVariable Long avaId, HttpServletResponse httpServletResponse) throws IOException {
        Avatar avatar = avatarService.findAvatar(avaId);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = httpServletResponse.getOutputStream()) {
            httpServletResponse.setStatus(200);
            httpServletResponse.setContentType(avatar.getMediaType());
            httpServletResponse.setContentLength(avatar.getFileSizeInt());
            is.transferTo(os);
        }
    }

    @GetMapping(params = {"page","size"})
    public Collection<Avatar> getAllAvatarsByPage(@RequestParam int page, @RequestParam int size) {
        return avatarService.getAllAvatarsByPage(page, size);
    }

}
