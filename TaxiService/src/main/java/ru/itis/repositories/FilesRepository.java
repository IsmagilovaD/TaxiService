package ru.itis.repositories;

import ru.itis.models.FileInfo;
import ru.itis.repositories.base.CrudRepository;

public interface FilesRepository extends CrudRepository<FileInfo, Long> {}
