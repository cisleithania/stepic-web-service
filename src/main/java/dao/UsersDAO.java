package dao;

import dataSets.UsersDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UsersDAO {

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    // метод, для получения пользователя из БД по логину
    public UsersDataSet getUserId (String login) throws HibernateException {
        // метод createCriteria, который можно использовать для создания объекта Criteria,
        // который возвращает объект UsersDataSet с заданным логином
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        return ((UsersDataSet) criteria.add(Restrictions.eq("login", login)).uniqueResult());
    }

    // метод сохраняет пользователя в БД и возвращает уникальный идентификатор
    public long insertUser(UsersDataSet user) throws HibernateException {
        return (Long) session.save(user);
    }
}
