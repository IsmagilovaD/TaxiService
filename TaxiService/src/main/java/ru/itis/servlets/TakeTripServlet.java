package ru.itis.servlets;

import org.springframework.context.ApplicationContext;
import ru.itis.dto.CustomerDto;
import ru.itis.dto.CustomerForm;
import ru.itis.dto.ShiftDto;
import ru.itis.dto.ShiftForm;
import ru.itis.exceptions.ValidationException;
import ru.itis.models.Shift;
import ru.itis.services.ShiftService;
import ru.itis.services.SignInService;
import ru.itis.services.SignUpService;
import ru.itis.services.validation.ErrorEntity;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/take-trip")
public class TakeTripServlet extends HttpServlet {

    private ShiftService shiftService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.shiftService = (ShiftService) config.getServletContext().getAttribute("shiftService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("take_trip.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShiftForm shiftForm;
        HttpSession session = request.getSession(true);
        CustomerDto userDto = (CustomerDto) session.getAttribute("user");
        try {
            shiftForm = ShiftForm.builder()
                    .departurePlace(request.getParameter("departurePlace"))
                    .arrivalPlace(request.getParameter("arrivalPlace"))
                    .userId(userDto.getId())
                    .build();
        }catch (NumberFormatException e) {
            Set<ErrorEntity> errors = new HashSet<>();
            errors.add(ErrorEntity.INVALID_REQUEST);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("take_trip.ftl").forward(request, response);
            return;
        }
        try {
            ShiftDto shiftDto = shiftService.takeTrip(shiftForm);
            request.getSession(true).setAttribute("shift",shiftDto);
        } catch (ValidationException e) {
            request.setAttribute("error", e.getEntity());
            request.getRequestDispatcher("take_trip.ftl").forward(request, response);
            return;
        }
        request.getRequestDispatcher("successful_trip.ftl").forward(request, response);
    }
}
