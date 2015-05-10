import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class SimpleGUI extends JFrame  {
    private JButton button = new JButton("Check web-resource");
    private JTextField input = new JTextField("", 2);
    private JLabel label = new JLabel("Web-resource:");
    private JRadioButton radio1 = new JRadioButton("Select this");
    private JRadioButton radio2 = new JRadioButton("Select that");
    private JCheckBox check = new JCheckBox("Alik loh?", true);

    public SimpleGUI() {
        super("AT-Challenge");
        //setBounds(x,y,w,h) - (x,y) - top left, wide, height
        this.setBounds(100,100,500,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //возвращает контейнер верхнего уровня
        Container container = this.getContentPane();
        //поменять GridLayout на GridBagLayout (!) отвечает за расположение элементов
        container.setLayout(new GridLayout(3,2,2,2));
        //добавление по порядку элементов
        //label + text field
        container.add(label);
        container.add(input);
        //группа переключателей
        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);
        container.add(radio1);
        radio1.setSelected(true);
        container.add(radio2);
        //checkbox
        container.add(check);
        //button с действием по функции
        button.addActionListener(new ButtonEventListener());
        container.add(button);
    }

    class ButtonEventListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Map<String, String> map = new HashMap<String,String>();
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

            for (Map.Entry<String, String> pair : map.entrySet())
            {
                String key = pair.getKey();
                String value = pair.getValue();
                //System.out.println(key + ":" + value);
            }


            try {
                String url = input.getText();
                String status = getStatus(url);
                String code = getSiteCode(url);
                String description = map.get(status);
                //System.out.println(url + "\t\tCode: " + code + "; Status: " + description);

                String message = "";
                message +="Site " + url + " was checked\n";
                message += "Code: " + code + "; Status: " + description + "\n";
                message += (radio1.isSelected()?"Radio #1":"Radio #2") + " is selected\n";
                message += "CheckBox is " + ((check.isSelected())?"checked":"unchecked");
                JOptionPane.showMessageDialog(null,
                        message,
                        "Output",
                        JOptionPane.PLAIN_MESSAGE);
            } catch (Exception ex) {
                System.out.println(ex.getStackTrace());
                System.out.println("Alert!");
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
    public static String getSiteCode(String url) throws IOException {

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
            result = "->Alert<-";
        }
        return result;
    }

    public static void main(String[] args) {
        SimpleGUI app = new SimpleGUI();

        //отображение окна
        app.setVisible(true);
    }
}