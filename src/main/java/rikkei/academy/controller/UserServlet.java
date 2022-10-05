package rikkei.academy.controller;

import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;
import rikkei.academy.model.User;
import rikkei.academy.service.role.RoleServiceIMPL;
import rikkei.academy.service.user.IUserService;
import rikkei.academy.service.user.UserServiceIMPL;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet({"/", "/userServlet"})
public class UserServlet extends HttpServlet {

    IUserService userService = new UserServiceIMPL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "register":
                formRegister(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            case "change-pass":
                formChangePass(request, response);
                break;
            default:
                if (request.getSession().getAttribute("userLogin") != null) {
                    request.getRequestDispatcher("/home/home.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/login/login.jsp").forward(request, response);
                }
        }

    }

    private void formChangePass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/home/changepass.jsp").forward(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("userLogin") != null) {
            request.getSession().removeAttribute("userLogin");
        }
        request.getRequestDispatcher("/login/login.jsp");
    }

    private void formRegister(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/login/register.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";
        switch (action) {
            case "register":
                actionRegister(request, response);
                break;
            case "login":
                actionLogin(request, response);
                break;
            case "change-pass":
                try {
                    actionChangePass(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("default action");
        }
    }

    private void actionChangePass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        User userLogin = (User) request.getSession().getAttribute("userLogin");

        String oldPass = request.getParameter("old-pass");
        String newPass = request.getParameter("new-pass");
        String repeatPass = request.getParameter("repeat-pass");

        if (!newPass.equals(repeatPass)) {
            request.setAttribute("message", "Repeat password not match");
            request.getRequestDispatcher("home/changepass.jsp").forward(request, response);
            return;
        }

        if (!userLogin.getPassword().equals(oldPass)) {
            request.setAttribute("message", "Old password not match");
            request.getRequestDispatcher("home/changepass.jsp").forward(request, response);
            return;
        }

        userLogin.setPassword(newPass);
        userService.update(userLogin);
        request.getSession().setAttribute("userLogin", userLogin);

        request.setAttribute("success", "Change password success");
        request.getRequestDispatcher("home/home.jsp").forward(request, response);
    }

    private void actionLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            request.getRequestDispatcher("/login/login.jsp").forward(request, response);
            return;
        }

        User user = userService.checkLogin(username, password);

        RoleName roleName = user.getRoleName();

        request.getSession().setAttribute("role", roleName);
        request.getSession().setAttribute("userLogin", user);

        request.getRequestDispatcher("/home/home.jsp").forward(request, response);

    }

    private void actionRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (name.trim().isEmpty() || username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.getRequestDispatcher("/login/register.jsp").forward(request, response);
            return;
        }

        User user = new User(name, username, email, password);

        Role roleUser = new RoleServiceIMPL().findByRoleName(RoleName.USER);
        user.getRoleSet().add(roleUser);

        if (userService.existByEmail(email)) {
            request.setAttribute("message", "Email already exists!");
            request.getRequestDispatcher("/login/register.jsp").forward(request, response);
            return;
        }

        if (userService.existByUsername(username)) {
            request.setAttribute("message", "Username already exists!");
            request.getRequestDispatcher("/login/register.jsp").forward(request, response);
            return;
        }

        userService.save(user);

        request.setAttribute("message", "Create success!");
        request.getRequestDispatcher("/login/login.jsp").forward(request, response);

    }

}
