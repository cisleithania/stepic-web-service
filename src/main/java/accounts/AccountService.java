package accounts;

import dao.UsersDAO;
import dataSets.UsersDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";
    private static final String hibernate_username = "test";
    private static final String hibernate_password = "test";

    // sessionFactory
    private final SessionFactory sessionFactory;
//    // сохранение сессий
//    private final Map<String, Long> sessionIdToProfile;

    public AccountService() {
        Configuration configuration = getH2Configuration();
        sessionFactory = createSessionFactory(configuration);
//        sessionIdToProfile = new HashMap<>();
    }

    // добавление пользователя в БД
    public long addNewUser(UsersDataSet user) {
        // открывается сессия
        Session session = sessionFactory.openSession();
        // открытие транзакции
        Transaction transaction = session.beginTransaction();
        // создается dao (работа с БД)
        UsersDAO dao = new UsersDAO(session);
        // сохранение пользователя в БД и получение id (идентификатора пользователя)
        long id = dao.insertUser(user);
        // проведение транзакции
        transaction.commit();
        // закрытие сессии
        session.close();
        return id;
    }

    // получение пользователя из БД по логину
    public UsersDataSet getUserByLogin(String login) {
        // открывается сессия
        Session session = sessionFactory.openSession();
        // создается dao (работа с БД)
        UsersDAO dao = new UsersDAO(session);
        // получение пользователя по логину
        UsersDataSet ds = dao.getUserId(login);
        // закрытие сессии
        session.close();
        return ds;
    }

//    // добавление сессии
//    public void addSession(long userId, String sessionId) {
//        sessionIdToProfile.put(sessionId, userId);
//    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", hibernate_username);
        configuration.setProperty("hibernate.connection.password", hibernate_password);
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

}
