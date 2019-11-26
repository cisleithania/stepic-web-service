package main;

import accounts.AccountService;
import accounts.UserProfile;
import dataSets.UsersDataSet;
import dbService.DBException;
import dbService.DBService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.h2.jdbcx.JdbcDataSource;
import servlets.AllRequestsServlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;

public class Main {
    public static void main(String[] args) throws Exception{

        AccountService accountService = new AccountService();

        // создание сервлетов и передача им accountService
        SignUpServlet signUpServlet = new SignUpServlet(accountService);
        SignInServlet signInServlet = new SignInServlet(accountService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signUpServlet), "/signup");
        context.addServlet(new ServletHolder(signInServlet), "/signin");

//        // создание сервлета
//        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();
//
//        // создание сервлет-контейнера
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        // в контекст закладывается созданный сервлет, который должен обрабатывать запросы, которые придут на /mirror
//        context.addServlet(new ServletHolder(allRequestsServlet), "/mirror");

        // создание Jetty-сервера и указание, на каком порту он должен работать
        Server server = new Server(8080);
        server.setHandler(context);

        // запуск сервера
        server.start();

        System.out.println("Server started");

        server.join();

    }
}
