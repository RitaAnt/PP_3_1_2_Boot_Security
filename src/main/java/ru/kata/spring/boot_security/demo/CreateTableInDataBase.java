package ru.kata.spring.boot_security.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableInDataBase {

    static final String Driver = "com.mysql.cj.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost:3306/pp_3_1_3";
    static final String user = "root";
    static final String pass = "root";

    public static void dataBase() {
        Connection conn = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Подключение к выбраной базе данных...");
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Подключение успешно...");
            System.out.println("Создание таблицы в выбранной базе данных");
            st = conn.createStatement();

            String operatorsql = "CREATE TABLE user (\n" +
                    "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                    "  `username` varchar(255) DEFAULT NULL,\n" +
                    "  `password` varchar(255) DEFAULT NULL,\n" +
                    "  `email` varchar(255) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (id))";

            String telefonsql = "CREATE TABLE role (\n" +
                    "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(255) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (id))";

            String usersandrolessql = "CREATE TABLE users_roles (\n" +
                    "  user_id int NOT NULL,\n" +
                    "  role_id int NOT NULL,\n" +
                    "  PRIMARY KEY (user_id,role_id),\n" +
                    "  KEY id_idx (user_id),\n" +
                    "  KEY id_idx1 (role_id),\n" +
                    "  CONSTRAINT NAME1 FOREIGN KEY (role_id) REFERENCES role (id),\n" +
                    "  CONSTRAINT NAME2 FOREIGN KEY (user_id) REFERENCES user (id))";

            //Создание таблицы операторы_связи

            if (st.executeUpdate(operatorsql) == 0)

                System.out.println("Таблица операторы_связи создана...");
            //Создание таблицы номера_телефонов

            if (st.executeUpdate(telefonsql) == 0)

                System.out.println("Таблица номера_телефонов создана...");

            if (st.executeUpdate(usersandrolessql) == 0)

                System.out.println("Таблица номера_телефонов создана...");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertData(){
        Connection conn = null;
        Statement st = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Подключение к выбраной базе данных...");
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Подключение успешно...");
            System.out.println("Создание запросов в выбранной базе данных");
            st = conn.createStatement();

        //Загрузка данных в таблицу номера_телефонов

        st.executeUpdate("INSERT INTO user VALUES (1,'admin','$2a$12$jJUkgWh64Ao.5N8jZWkHB.POTSatZz3N7tyEp98ou1E5Qoa5jsGx2','admin@mail.ru'), (2,'user','$2a$12$VMzqSZjcukatMhjQLCHuC.gNEKEXzBTe.bRveVQ5jImeJALbjzI0e','sdfsdf@mail.ru')");

        st.executeUpdate("INSERT INTO role VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER')");

        st.executeUpdate("INSERT INTO users_roles VALUES (1,'1'),(1,'2'),(2,'2')");

        System.out.println("Загрузка данных закончена...");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

//    public static void dataBase() {
//        Connection conn = null;
//        Statement st = null;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("Подключение к выбраной базе данных...");
//            conn = DriverManager.getConnection(url, user, pass);
//            System.out.println("Подключение успешно...");
//            System.out.println("Создание таблицы в выбранной базе данных");
//            st = conn.createStatement();
//
//            String sql = "CREATE TABLE `user` (\n" +
//                    "  `id` int NOT NULL AUTO_INCREMENT,\n" +
//                    "  `username` varchar(255) DEFAULT NULL,\n" +
//                    "  `password` varchar(255) DEFAULT NULL,\n" +
//                    "  `email` varchar(255) DEFAULT NULL,\n" +
//                    "  PRIMARY KEY (`id`)\n" +
//                    "CREATE TABLE `role` (\n" +
//                    "  `id` int NOT NULL AUTO_INCREMENT,\n" +
//                    "  `name` varchar(255) DEFAULT NULL,\n" +
//                    "  PRIMARY KEY (`id`)\n" +
//                    "CREATE TABLE `users_roles` (\n" +
//                    "  `user_id` int NOT NULL,\n" +
//                    "  `role_id` int NOT NULL,\n" +
//                    "  PRIMARY KEY (`user_id`,`role_id`),\n" +
//                    "  KEY `id_idx` (`user_id`),\n" +
//                    "  KEY `id_idx1` (`role_id`),\n" +
//                    "  CONSTRAINT `123` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),\n" +
//                    "  CONSTRAINT `id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)\n";
//
//
//            String sql2 = "INSERT INTO `user` VALUES (1,'admin','$2a$12$jJUkgWh64Ao.5N8jZWkHB.POTSatZz3N7tyEp98ou1E5Qoa5jsGx2','admin@mail.ru')," +
//                    "(2,'user','$2a$12$VMzqSZjcukatMhjQLCHuC.gNEKEXzBTe.bRveVQ5jImeJALbjzI0e','user@mail.ru');\n" +
//                    "INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');\n" +
//                    "INSERT INTO `users_roles` VALUES (1,1),(1,2),(2,2);\n"
//                    ;
//
//            st.executeUpdate(sql);
//            st.executeUpdate(sql2);
//            System.out.println("Таблица создана...");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (st != null)
//                    conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            if (conn != null)
//                conn.close();
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }