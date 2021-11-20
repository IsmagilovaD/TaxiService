package ru.itis.servlets;

import org.springframework.context.ApplicationContext;
import ru.itis.dto.CustomerDto;
import ru.itis.models.Customer;
import ru.itis.models.Shift;
import ru.itis.services.ShiftService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/shifts")
public class ShiftsServlet extends HttpServlet {
    private ShiftService shiftService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext) servletContext.getAttribute("springContext");
        this.shiftService = applicationContext.getBean(ShiftService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDto customerDto = (CustomerDto) request.getSession(true).getAttribute("user");
        List<Shift> shifts = shiftService.showShifts(customerDto);
        request.getSession().setAttribute("shifts", shifts);
        request.getRequestDispatcher("shifts.ftl").forward(request, response);
    }
}
