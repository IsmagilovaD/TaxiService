package ru.itis.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;;
import ru.itis.repositories.*;
import ru.itis.repositories.impl.*;
import ru.itis.services.*;
import ru.itis.services.impl.*;
import ru.itis.services.validation.Validator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class ServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String DB_USERNAME;
        String DB_PASSWORD;
        String DB_URL;
        String DB_DRIVER;
        String IMAGES_STORAGE_PATH;
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Требуется файл properties");
        }
        DB_USERNAME = (String) properties.get("db.user");
        DB_PASSWORD = (String) properties.get("db.password");
        DB_URL = (String) properties.get("db.url");
        DB_DRIVER = (String) properties.get("db.driver");
        IMAGES_STORAGE_PATH = (String) properties.get("storage.path");

        ServletContext servletContext = servletContextEvent.getServletContext();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        CustomersRepository customersRepository = new CustomersRepositoryImpl(dataSource);
        DriversRepository driversRepository = new DriversRepositoryImpl(dataSource);
        FilesRepository filesRepository = new FilesRepositoryImpl(dataSource);
        ShiftsRepository shiftsRepository = new ShiftsRepositoryImpl(dataSource);
        FilesService filesService = new FilesServiceImpl(filesRepository, customersRepository, IMAGES_STORAGE_PATH);
        PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
        Validator validator = new ValidatorImpl(customersRepository);
        ShiftService shiftService = new ShiftServiceImpl(shiftsRepository, driversRepository);
        SignInService signInService = new SignInServiceImpl(customersRepository, passwordEncoder);
        SignUpService signUpService = new SignUpServiceImpl(customersRepository, passwordEncoder, validator);
        ObjectMapper objectMapper = new ObjectMapper();

        servletContext.setAttribute("objectMapper", objectMapper);
        servletContext.setAttribute("fileService", filesService);
        servletContext.setAttribute("shiftService", shiftService);
        servletContext.setAttribute("signInService", signInService);
        servletContext.setAttribute("signUpService", signUpService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
