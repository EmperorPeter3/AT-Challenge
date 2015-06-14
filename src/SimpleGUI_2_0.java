import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

public class SimpleGUI_2_0 extends JFrame  {

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

    private JButton button = new JButton("Check web-resource");
    private JTextField input = new JTextField("", 2);
    private JLabel label = new JLabel("Web-resource:");

    public SimpleGUI_2_0() {

        super("AT-Challenge");

        //setBounds(x,y,w,h) - (x,y) - top left, wide, height
        this.setBounds(300, 300, 300, 200);
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
        a.insets = new Insets(0, 10, 0, 10); //margin (t l b r)
        a.ipadx = 0; //growth element x
        a.ipady = 0; //growth element y
        a.weightx = 0.0;
        a.weighty = 0.0;

        gbl.setConstraints(label, a);
        add(label);

        a.anchor = GridBagConstraints.EAST;
        a.ipadx = 125;
        gbl.setConstraints(input, a);
        add(input);

        button.addActionListener(new ButtonEventListener());

        a.gridwidth  = 2;
        a.gridy = 2;
        a.insets = new Insets(20, 10, 0, 10);
        gbl.setConstraints(button, a);
        add(button);
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
                int k=-1;
                String url = input.getText();
                String http1= "http";
                //String https="https://";
                k=url.indexOf(http1);
                String status;
                String code;
                String url2;
                if (k==-1)
                {
                    String url1="http://"+url;
                    status = getStatus(url1);
                    code = getSiteCode(url1);

                }
                else {
                    status = getStatus(url);
                    code = getSiteCode(url);
                }
                //System.out.println(status.matches("301"));
                //int mmm;
                if (status.matches("301"))
                {
                    if (k!=-1)
                    {
                        url2=url.replace("http","https");
                        status = getStatus(url2);
                        code = getSiteCode(url2);
                    }
                    else
                    {
                        url="https://"+ url;
                        status = getStatus(url);
                        code = getSiteCode(url);
                    }
                }
                String description = map.get(status);
                //System.out.println(url + "\t\tCode: " + code + "; Status: " + description);

                String message = "";
                message += "Site " + url + " was checked\n";
                message += "Code: " + code + "; Status: " + description + "\n";

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

        try {
            // select Look and Feel
            UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
            // start application
            SimpleGUI_2_0 app = new SimpleGUI_2_0();

            //show window
            app.setVisible(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}