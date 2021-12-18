package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.dto.CustomerDto;
import ru.itis.exceptions.NotFoundException;
import ru.itis.models.FileInfo;
import ru.itis.repositories.CustomersRepository;
import ru.itis.repositories.FilesRepository;
import ru.itis.services.FilesService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;


public class FilesServiceImpl implements FilesService {

    private String storagePath;

    private final FilesRepository filesRepository;
    private final CustomersRepository customersRepository;

    public FilesServiceImpl(FilesRepository filesRepository, CustomersRepository customersRepository, String storagePath) {
        this.filesRepository = filesRepository;
        this.customersRepository = customersRepository;
        this.storagePath = storagePath;
    }

    @Override
    public FileInfo saveFileToStorage(CustomerDto customerDto, InputStream inputStream, String originalFileName, String contentType, Long size) {
        FileInfo fileInfo = new FileInfo(
                null,
                originalFileName,
                UUID.randomUUID().toString(),
                size,
                contentType
        );
        try {
            Files.copy(inputStream, Paths.get(storagePath + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]));
            fileInfo = filesRepository.save(fileInfo);
            customersRepository.updateAvatar(customerDto.getId(),fileInfo.getId());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return fileInfo;
    }

    @Override
    public void writeFileFromStorage(Long fileId, OutputStream outputStream) {
        Optional<FileInfo> optionalFileInfo = filesRepository.findById(fileId);
        FileInfo fileInfo = optionalFileInfo.orElseThrow(() -> new NotFoundException("File not found"));

        File file = new File(storagePath + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]);
        try {
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public FileInfo getFileInfo(Long fileId) {
        return filesRepository.findById(fileId).orElseThrow(() -> new NotFoundException("File not found"));
    }
}
