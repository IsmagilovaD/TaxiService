package ru.itis.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import ru.itis.dto.CustomerDto;
import ru.itis.dto.CustomerForm;
import ru.itis.exceptions.ValidationException;
import ru.itis.services.SignInService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/sign-in")
public class SignInServlet extends HttpServlet {

    private SignInService signInService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.signInService = (SignInService) config.getServletContext().getAttribute("signInService");
        this.objectMapper = (ObjectMapper) config.getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("sign_in.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerForm form = CustomerForm.builder()
                .phoneNumber(request.getParameter("phoneNumber"))
                .password(request.getParameter("password"))
                .build();
        CustomerDto customerDto;
        response.setContentType("application/json");
        try {
             customerDto = signInService.signIn(form);
        } catch (ValidationException e) {
            response.setStatus(e.getEntity().getStatus());
            objectMapper.writeValue(response.getOutputStream(), e.getEntity());
            return;
        }
            HttpSession session = request.getSession(true);
            session.setAttribute("user", customerDto);
            response.getWriter().println("{}");
    }

}
