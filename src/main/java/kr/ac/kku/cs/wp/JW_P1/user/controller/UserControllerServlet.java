package kr.ac.kku.cs.wp.JW_P1.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import kr.ac.kku.cs.wp.JW_P1.user.entity.User;
import kr.ac.kku.cs.wp.JW_P1.user.service.UserService;
import kr.ac.kku.cs.wp.JW_P1.user.service.UserServiceImpl;
import kr.ac.kku.cs.wp.JW_P1.tools.json.ReflectionUtil;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/userlist")
public class UserControllerServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UserControllerServlet.class);
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Test");

        User paramUser = null;
        String contentType = req.getContentType();

        if (contentType != null && contentType.equals("application/json")) {
            logger.trace("json");

            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            BufferedReader reader = req.getReader();

            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }

            // Convert the string into a JSONObject
            JSONObject jsonParams = new JSONObject(jsonBuffer.toString());

            try {
                // Use ReflectionUtil to create User object from JSON
                paramUser = ReflectionUtil.createObjectFromJson(User.class, jsonParams);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServletException(e.getMessage());
            }

            // Get users based on the parameter User object
            List<User> users = userService.getUsers(paramUser);
            req.setAttribute("users", users);

            // Forward the request to the user cards JSP page
            req.getRequestDispatcher("/WEB-INF/view/user/userCards.jsp").forward(req, resp);
        } else {
            // If not JSON, simply fetch all users
            List<User> users = userService.getUsers(null);
            req.setAttribute("users", users);
            req.getRequestDispatcher("/WEB-INF/view/user/userList.jsp").forward(req, resp);
        }
    }
}

