import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class test extends JFrame
{
    public static void create_GUI () {
        JFrame form1 = new JFrame("Выбор региона");
        JButton button = new JButton("Проверить регион");
        JLabel label_reg_type = new JLabel("Тип региона:");
        JLabel label_reg = new JLabel("Регион:");
        String[] reg_type_names = {
                "Край",
                "Ресублика",
                "Область",
                "Город федерального значения",
                "Автономная область",
                "Автономный округ"
        };

        final ArrayList<String> listModel = new ArrayList<String>();

        //DefaultListModel listModel = new DefaultListModel();

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                String item = (String)box.getSelectedItem();
                //label.setText(item);
            }
        };

        final JList reg_type_list = new JList(reg_type_names);
        reg_type_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reg_type_list.addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        Object element = reg_type_list.getSelectedValue();
                        //надо написать присовение массива в зависимости от типа региона
                        //label.setText(element.toString());
                        try {
                            BufferedReader reader;
                            String line;
                            switch (element.toString()) {
                                case "Край":
                                    reader = new BufferedReader(new FileReader("C:\\Users\\Max\\IdeaProjects\\AT-Challenge\\src\\reg_types\\krai.txt"));
                                    //listModel.removeAllElements();
                                    listModel.clear();
                                    while ((line = reader.readLine()) != null) {
                                        //listModel.addElement(line);
                                        listModel.add(line);
                                    }
                                    break;
                                case "Ресублика":
                                    reader = new BufferedReader(new FileReader("C:\\Users\\Max\\IdeaProjects\\AT-Challenge\\src\\reg_types\\republic.txt"));
                                    //listModel.removeAllElements();
                                    listModel.clear();
                                    while ((line = reader.readLine()) != null) {
                                        //listModel.addElement(line);
                                        listModel.add(line);
                                    }
                                    break;
                                case "Область":
                                    reader = new BufferedReader(new FileReader("C:\\Users\\Max\\IdeaProjects\\AT-Challenge\\src\\reg_types\\oblast.txt"));
                                    //listModel.removeAllElements();
                                    listModel.clear();
                                    while ((line = reader.readLine()) != null) {
                                        //listModel.addElement(line);
                                        listModel.add(line);
                                    }
                                    break;
                                case "Город федерального значения":
                                    reader = new BufferedReader(new FileReader("C:\\Users\\Max\\IdeaProjects\\AT-Challenge\\src\\reg_types\\fed_city.txt"));
                                    //listModel.removeAllElements();
                                    listModel.clear();
                                    while ((line = reader.readLine()) != null) {
                                        //listModel.addElement(line);
                                        listModel.add(line);
                                    }
                                    break;
                                case "Автономная область":
                                    reader = new BufferedReader(new FileReader("C:\\Users\\Max\\IdeaProjects\\AT-Challenge\\src\\reg_types\\autonom_obl.txt"));
                                    //listModel.removeAllElements();
                                    listModel.clear();
                                    while ((line = reader.readLine()) != null) {
                                        //listModel.addElement(line);
                                        listModel.add(line);
                                    }
                                    break;
                                case "Автономный округ":
                                    reader = new BufferedReader(new FileReader("C:\\Users\\Max\\IdeaProjects\\AT-Challenge\\src\\reg_types\\autonom_okr.txt"));
                                    //listModel.removeAllElements();
                                    listModel.clear();
                                    while ((line = reader.readLine()) != null) {
                                        //listModel.addElement(line);
                                        listModel.add(line);
                                    }
                                    break;
                                default:
                                    //listModel.removeAllElements();
                                    listModel.clear();
                            }
                        } catch (IOException ie) {
                            ie.printStackTrace();
                        }
                    }
                }
        );

        String[] listModel_s = new String [listModel.size()];
        for (int i=0; i<listModel.size()-1; i++) {

        }
        final JComboBox reg_names_list = new JComboBox(listModel_s);
        //JComboBox cb = new JComboBox(listModel);

        form1.setVisible(true);

        //setBounds(x,y,w,h) - (x,y) - top left, wide, height
        form1.setBounds(300, 300, 400, 600);
        form1.setResizable(true);
        form1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagLayout gbl = new GridBagLayout();
        form1.setLayout(gbl);

        GridBagConstraints a = new GridBagConstraints();
        a.anchor = GridBagConstraints.WEST; //EAST
        a.fill   = GridBagConstraints.NONE; //BOTH
        a.gridheight = 1;
        a.gridwidth  = 1;
        a.gridx = GridBagConstraints.RELATIVE;
        a.gridy = 1;
        a.insets = new Insets(0, 20, 0, 20); //margin (t l b r)
        a.ipadx = 0; //growth element x
        a.ipady = 0; //growth element y
        a.weightx = 0.0;
        a.weighty = 0.0;

        gbl.setConstraints(label_reg_type, a);
        form1.add(label_reg_type);

        a.anchor = GridBagConstraints.EAST;
        a.ipadx = 1;
        gbl.setConstraints(reg_type_list, a);
        form1.add(reg_type_list);


        a.anchor = GridBagConstraints.WEST;
        a.gridy = 2;
        gbl.setConstraints(label_reg, a);
        form1.add(label_reg);

        a.anchor = GridBagConstraints.EAST;
        a.ipadx = 1;
        gbl.setConstraints(reg_names_list, a);
        form1.add(reg_names_list);

        a.gridy = 3;
        a.anchor = GridBagConstraints.CENTER;
        a.gridwidth  = 2;
        a.insets = new Insets(20, 10, 0, 10);
        a.ipadx = 25;
        gbl.setConstraints(button, a);
        form1.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame form2 = new JFrame("Статистика региона");
                form2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                form2.setBounds(500, 500, 400, 200);
                form2.setVisible(true);
            }
        });
    }

    public static void main(String[] args)
    {
        test frame = new test();
        frame.create_GUI();
    }
}