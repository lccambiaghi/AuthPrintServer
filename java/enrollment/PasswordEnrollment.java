package enrollment;

import hashing.BCrypt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PasswordEnrollment {
    public static void main(String[] args) {

        List<Credential> users = new ArrayList<>();

        users.add(new Credential("Alice", "Frodo", "admin"));
        users.add(new Credential("Bob", "Sam", "technician"));
        users.add(new Credential("Cecilia", "Aragorn", "super"));
        users.add(new Credential("Davide", "Gandalf", "user"));
        users.add(new Credential("Erica", "Legolas", "user"));
        users.add(new Credential("Fred", "Gimli", "user"));
        users.add(new Credential("George", "Boromir", "user"));

        String salt;
        String hashValue;

        try(BufferedWriter out = new BufferedWriter(
                new FileWriter("/Users/lucacambiaghi/IdeaProjects/AuthPrintServer/src/main/resources/credentials.txt"))){

            for(Credential user : users){
                salt = BCrypt.gensalt();
                hashValue = BCrypt.hashpw(user.password, salt);
                out.write(user.username + ";" + salt + ";" + hashValue + ";" + user.role + System.lineSeparator());
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static class Credential {

        String username;
        String password;
        String role;

        Credential(String username, String password, String role){
            this.username = username;
            this.password = password;
            this.role = role;
        }

    }
}
