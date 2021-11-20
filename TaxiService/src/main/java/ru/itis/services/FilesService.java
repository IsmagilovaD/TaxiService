package ru.itis.services;

import ru.itis.dto.CustomerDto;
import ru.itis.models.FileInfo;

import java.io.InputStream;
import java.io.OutputStream;

public interface FilesService {
    FileInfo saveFileToStorage(CustomerDto customerDto, InputStream inputStream, String originalFileName, String contentType, Long size);
    void writeFileFromStorage(Long fileId, OutputStream outputStream);
    FileInfo getFileInfo(Long fileId);
}
