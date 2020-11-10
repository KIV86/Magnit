package javacode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Scanner;

public class UpdateDB {
    private static Logger logger = LoggerFactory.getLogger(UpdateDB.class.getSimpleName());
    int n = 0;

    private int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    int scanNum() {
        // метод принимает от пользователя введенное значение
        logger.debug("Инициализакия сканера и ввод значения от пользователя");
        System.out.println("пожалуйста введите число 'N': ");
        var scanner = new Scanner(System.in);
        setN(scanner.nextInt());
        scanner.close();
        logger.debug("Значение введено, сканер закрыт ");
        return n;
    }

    void updateDB() {

        logger.debug("Метод updateDB: Добавление значений в БД");

        String queryAdd = "INSERT INTO TEST (FIELD) VALUES(?)";
        final String queryDel = "DELETE FROM TEST";

//повторное подключение к БД
        try (var preparedStatement = ConnectionDB.conn.prepareStatement(queryAdd)) {
            logger.debug("Подключение к базе данных прошло успешно ");

            logger.debug("Очистка таблицы перед вставкой значений");
            preparedStatement.execute(queryDel);

            logger.debug("отклчение автокомита");
            ConnectionDB.conn.setAutoCommit(false);
            int i = 1;

            logger.debug("добавление значений в БД");
            while (i <= getN()) {
                preparedStatement.setInt(1, i);
                //добавление значений из цикла в буфер
                preparedStatement.addBatch();
                if (i == getN()) {
                    // добавление значений в БД
                    preparedStatement.executeBatch();
                    break;
                }
                i++;
            }
            logger.debug("Добавление значений в БД прошло успешно");
        } catch (SQLException throwables) {
            logger.error("Ошибка при создании обьекта PreparedStatement", throwables);
        }
    }
}

