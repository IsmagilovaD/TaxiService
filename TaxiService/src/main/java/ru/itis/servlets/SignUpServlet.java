package ru.itis.servlets;

import org.springframework.context.ApplicationContext;
import ru.itis.dto.CustomerForm;
import ru.itis.exceptions.ValidationException;
import ru.itis.services.SignUpService;
import ru.itis.services.validation.ErrorEntity;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {

    private SignUpService signUpService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext) servletContext.getAttribute("springContext");
        this.signUpService = applicationContext.getBean(SignUpService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("sign_up.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerForm form;
        try {
            form = CustomerForm.builder()
                    .firstName(request.getParameter("firstName"))
                    .lastName(request.getParameter("lastName"))
                    .phoneNumber(request.getParameter("phoneNumber"))
                    .password(request.getParameter("password"))
                    .age(Integer.valueOf(request.getParameter("age")))
                    .build();
        } catch (NumberFormatException e) {
            Set<ErrorEntity> errors = new HashSet<>();
            errors.add(ErrorEntity.INVALID_REQUEST);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("sign_up.ftl").forward(request, response);
            return;
        }

        try {
            signUpService.signUp(form);
        } catch (ValidationException e) {
            request.setAttribute("error", e.getEntity());
            request.getRequestDispatcher("sign_up.ftl").forward(request, response);
            return;
        }
        response.sendRedirect("/sign-in");
    }
}
