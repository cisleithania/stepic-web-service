package main;

import accounts.AccountService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
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

        // создание Jetty-сервера и указание, на каком порту он должен работать
        Server server = new Server(8080);
        server.setHandler(context);

        // запуск сервера
        server.start();

        System.out.println("Server started");

        server.join();

    }
}
