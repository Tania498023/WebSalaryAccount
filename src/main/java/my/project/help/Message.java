package my.project.help;
import javax.swing.*;

public class Message {
    public static void infoBox(String infoMessage, String titleBar) {
        String mes = "Логин или пароль введен неверно!";
        JOptionPane.showMessageDialog(null,
                mes,
                "Message",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
