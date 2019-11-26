package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import dataSets.UsersDataSet;
import dbService.DBException;
//import dbService.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    // объявление accountService
    private final AccountService accountService;

    // инициализация accountService
    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    // обработка Post запросов
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

//        response.getWriter().println(request.getParameter("login"));
//        response.getWriter().println(request.getParameter("password"));

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        // создание профиля пользователя
//        UserProfile profile = new UserProfile(login,password);
        UsersDataSet user = new UsersDataSet(login,password);
        // добавляем аккаунт пользователя
        accountService.addNewUser(user);

    }

}
