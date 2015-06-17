import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.*;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

public class SimpleGUI_2_0 extends JFrame  {

    static Map<String, String>  map = new HashMap<String,String>();

    public void initLookAndFeel() {
        try {
            //get list of install LaFs:
            //System.out.println(UIManager.getInstalledLookAndFeels());
            //install theme: (OceanTheme, DefaultMetalTheme)
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
            // install Look And Feel
            UIManager.setLookAndFeel(new MetalLookAndFeel());

            //sea glass LaF
            //UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
            //JTattoo LaF
            //UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");

        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Can't use the specified look and feel on this platform.");
        } catch (Exception e) {
            System.err.println("Couldn't get specified look and feel, for some reason.");
        }
    }

    private JButton button = new JButton("Проверить регион");
    private JTextField input = new JTextField("", 2);
    private JLabel label_reg_type = new JLabel("Тип региона:");
    private JLabel label_reg = new JLabel("Регион:");
    String[] reg_type_names = {
            "Край",
            "Ресублика",
            "Область",
            "Город федерального значения",
            "Автономная область",
            "Автономный округ"
    };
    String[] reg_names = {
            "Край",
            "Ресублика",
            "Область",
            "Город федерального значения",
            "Автономная область",
            "Автономный округ"
    };
    private JComboBox reg_type_list = new JComboBox(reg_type_names);
    private JComboBox reg_list = new JComboBox(reg_names);

    public SimpleGUI_2_0()
    {

        super("AT-Challenge");

        //setBounds(x,y,w,h) - (x,y) - top left, wide, height
        this.setBounds(300, 300, 400, 200);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

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
        add(label_reg_type);

        a.anchor = GridBagConstraints.EAST;
        a.ipadx = 1;
        gbl.setConstraints(reg_type_list, a);
        add(reg_type_list);


        a.anchor = GridBagConstraints.WEST;
        a.gridy = 2;
        gbl.setConstraints(label_reg, a);
        add(label_reg);

        a.anchor = GridBagConstraints.EAST;
        a.ipadx = 1;
        gbl.setConstraints(reg_list, a);
        add(reg_list);

        button.addActionListener(new ButtonEventListener());

        a.gridwidth  = 2;
        a.gridy = 3;
        a.insets = new Insets(20, 10, 0, 10);
        a.ipadx = 125;
        gbl.setConstraints(button, a);
        add(button);


    }


    public static class CheckUrl
    {
        public String Site;
        public String Code;
        public CheckUrl(String url)
        {
            try
            {
                String status;
                    int http_is_contained = url.indexOf("http");
                    if (http_is_contained == -1) //в имени нет http
                    {
                        url = "http://" + url;
                        status = getStatus(url);
                    } else {//в имени есть http
                        status = getStatus(url);
                    }
                    if (status.equals("301")) {
                        int https_is_contained = url.indexOf("https");
                        if (https_is_contained == -1) {
                            String new_url = url.replace("http", "https");
                            String new_status = getStatus(new_url);
                            if (new_status.equals("200")) {
                                status = new_status;
                                url = new_url;
                            }
                        }
                    }
                    Site=url;
                    Code=status;
            }
            catch(Exception ex)
            {
                System.out.println(ex.getStackTrace());
                System.out.println("Alert!");
            }
        }
    }
    class ButtonEventListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String url = input.getText();
            String message = "";
            if (!url.equals("")) {
                CheckUrl check_this_url = new CheckUrl(url);
                String description = map.get(check_this_url.Code);
                message += "Сайт " + url + " проверен\n";
                message += "Код: " + check_this_url.Code + "\nСтатус: " + description + "\n";

                JOptionPane.showMessageDialog(null,
                        message,
                        "Output",
                        JOptionPane.PLAIN_MESSAGE);
            }
            else {
                message += "Поле не может быть пустым!\n";
                JOptionPane.showMessageDialog(null,
                        message,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public static String getStatus(String url) throws IOException {

        String result = "";
        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            result = Integer.toString(code);
        } catch (Exception e) {
            result = "->Alert code!<-";
        }
        return result;
    }

    public static void main(String[] args) {

        map.put("100","Continue");          map.put("101","Switching Protocols");
        map.put("102","Processing");        map.put("105","Name Not Resolved");

        map.put("200","Good");              map.put("201","Created ");
        map.put("202","Accepted");          map.put("203","Non-Authoritative Information");
        map.put("204","No Content");        map.put("205","Reset Content");
        map.put("206","Partial Content");   map.put("207","Multi-Status");
        map.put("226","IM Used");

        map.put("301","Moved Permanently"); map.put("302","Moved Temporarily/Found");
        map.put("303","See Other");         map.put("304","Not Modified");
        map.put("305","Use Proxy");         map.put("306","Reserved");
        map.put("307","Temporary Redirect");

        map.put("400","Bad Request");       map.put("401","Unauthorized ");
        map.put("402","Payment Required");  map.put("403","Forbidden");
        map.put("404","Not Found");         map.put("405","Method Not Allowed");
        map.put("406","Not Acceptable");    map.put("407","Proxy Authentication Required");
        map.put("409","Conflict");          map.put("410","Gone");
        map.put("411","Length Required");   map.put("412","Precondition Failed");
        map.put("413","Request Entity Too Large");
        map.put("414","Request-URL Too Long");
        map.put("415","Unsupported Media Type");
        map.put("416","Requested Range Not Satisfiable");
        map.put("417","Expectation Failed");map.put("418","I'm a teapot");
        map.put("422","Unprocessable Entity");
        map.put("423","Locked ");           map.put("424","Failed Dependency");
        map.put("425","Unordered Collection");
        map.put("426","Upgrade Required");
        map.put("428","Precondition Required");
        map.put("429","Too Many Requests ");map.put("431","Request Header Fields Too Large");
        map.put("434","Requested host unavailable ");
        map.put("449","Retry With ");       map.put("451","Unavailable For Legal Reasons");
        map.put("456","Locked ");

        map.put("500","Internal Server Error");
        map.put("501","Not Implemented");   map.put("502","Bad Gateway");
        map.put("503","Service Unavailable");
        map.put("504","Gateway Timeout");   map.put("505","HTTP Version Not Supported");
        map.put("506","Variant Also Negotiates");
        map.put("507","Insufficient Storage");
        map.put("509","Bandwidth Limit Exceeded");
        map.put("510","Not Extended");      map.put("511", "Network Authentication Required");

        /*
            for (Map.Entry<String, String> pair : map.entrySet())
            {
                String key = pair.getKey();
                String value = pair.getValue();
                System.out.println(key + ":" + value);
            }
            */

        String subject, subName, url;

        try {
            // select Look and Feel
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
            // start application
            SimpleGUI_2_0 app = new SimpleGUI_2_0();

            //show window
            app.setVisible(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        //addSite(subject,subName,url2);
        subject="Край";
        subName="Приморский";
        url="vk.com";

        addSite(subject, subName, url);
        //siteStatus(subject + "/" + subName + "/" +"site.txt",subject+"/"+subName);
    }
    //Алик

    public static void addSite(String subject_of_RF, String subject_name, String url)
    {
        CheckUrl check_this_url = new CheckUrl(url);
        url = check_this_url.Site;
        if(check_this_url.Code.compareTo("200")>=0 && check_this_url.Code.compareTo("300")<0)
        {
            boolean site_is_contained = false;
            String path_to_file = subject_of_RF;
            File dir;
            dir = new File(path_to_file);
            if (!dir.exists()) {
                dir.mkdir();
            }
            path_to_file += "/" + subject_name;
            dir = new File(path_to_file);
            if (!dir.exists()) {
                dir.mkdir();
            }
            String file_name = path_to_file + "/" + "sites" + ".txt";
            dir = new File(file_name);
            try {
                dir.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String temp_string = "";
            Scanner scanner = null;
            try {
                scanner = new Scanner(new File(file_name));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (scanner.hasNext()) {
                temp_string = scanner.nextLine();
                if (url.equals(temp_string)) {
                    // found
                    site_is_contained = true;
                    break;
                }
            }
            if (site_is_contained == false) {
                try (FileWriter writer = new FileWriter(file_name, true)) {
                    String siteName = url;
                    writer.write(siteName);
                    writer.write('\n');

                } catch (IOException e) {
                    e.printStackTrace();
                }
                String site_name = url.replace("http://", "");
                site_name = site_name.replace("https://", "");
                dir = new File(path_to_file + "/" + site_name + ".txt");
                try {
                    dir.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void siteStatus(String sitePath,String beforeSitePath) {

        String s = "";
        File dir;
        Scanner in = null;
        try {
            in = new Scanner(new File(sitePath));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (in.hasNext()) {
            s = in.nextLine();


        }
        in.close();
    }

}