package ru.itis.servlets;

import org.springframework.context.ApplicationContext;
import ru.itis.dto.CustomerDto;
import ru.itis.models.FileInfo;
import ru.itis.services.FilesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/avatar-upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    private FilesService filesService;

    @Override
    public void init(ServletConfig config) {
        filesService = (FilesService) config.getServletContext().getAttribute("fileService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("fileUpload.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("file");
        HttpSession session = request.getSession(true);
        CustomerDto userDto = (CustomerDto) session.getAttribute("user");
        FileInfo fileInfo = filesService.saveFileToStorage(userDto,
                part.getInputStream(),
                part.getSubmittedFileName(),
                part.getContentType(),
                part.getSize());
        userDto.setAvatarId(fileInfo.getId());
        session.setAttribute("user",userDto);
        response.sendRedirect("/profile");
    }
}
