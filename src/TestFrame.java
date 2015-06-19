import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TestFrame extends JFrame {

    public static void main(String[] args) {
        JFrame form1 = new JFrame("form1");
        JButton button = new JButton("visible");
        form1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form1.setSize(350,150);
        form1.add(button);
        form1.setVisible(true);

        //а теперь главное - обработчик событий кнопки
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //а вот тут как раз код чего делать при нажатии на кнопку
                //в самом простом виде следующие:
                JFrame form2 = new JFrame("form2");
                form2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                form2.setSize(350, 150);
                //не видим первую форму
                form1.setVisible(true);
                //открываем вторую
                form2.setVisible(true);
            }
        });
    }
}