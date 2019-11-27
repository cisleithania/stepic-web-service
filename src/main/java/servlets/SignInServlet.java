package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import dataSets.UsersDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    // объявление accountService
    private final AccountService accountService;

    // инициализация accountService
    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    // обработка Post запросов
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        // получение пользователя по принятому логину
        UsersDataSet dataSet = accountService.getUserByLogin(login);

            // пароль заданный пользователем при регистрации, должен совпадать с введенным при авторизации
            if ((dataSet != null) && dataSet.getPassword().equals(password)) {

//                // получение идентификатора пользователя, который ему дается при занесении в БД
//                final long userId = dataSet.getId();
//                // получение id сессии
//                final String session = request.getSession().getId();
//                // добавление сессии
//                accountService.addSession(userId, session);

                response.setStatus(HttpServletResponse.SC_OK); // Status code (200)
                response.getWriter().println("Authorized: " + dataSet.getLogin());

            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Status code (401)
                response.getWriter().println("Unauthorized");
            }

        }
}
