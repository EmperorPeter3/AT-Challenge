import javax.mail.*;
import javax.mail.internet.*;
import com.xeiam.xchart.*;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

class TwoComboboxFrame extends JFrame {
    //Map for two ComboBoxes (Lists) 'Region Types' - 'Region Names'
    private Map<String, List<String>> regionNameRegionType = new LinkedHashMap<String, List<String>>();
    //String array of 'Region names'
    private JComboBox<String> regionNameComboBox;
    private String selectedValue;

    public TwoComboboxFrame() {
        GridBagLayout gbl = new GridBagLayout();    //type of element's composition in form

        init(gbl);                                  //initialize main frame with gbl type
        buildDataModel();                           //get a data into the map
        buildRegionTypeComboBox(gbl);               //add a 1st list 'Region Types'
        buildRegionNameComboBox(gbl);               //add a 2nd list 'Region Names'
        createButtonWithInnerForm(gbl);             //add a button with inner form
    }

    private void createButtonWithInnerForm(GridBagLayout gbl) {
        JButton button = new JButton("Статистика региона"); //init button

        //customise button (element position)
        GridBagConstraints a = new GridBagConstraints();
        a.anchor = GridBagConstraints.CENTER;
        a.fill   = GridBagConstraints.HORIZONTAL;
        a.gridheight = 1;
        a.gridwidth  = 1;
        a.gridx = GridBagConstraints.RELATIVE;
        a.gridy = 2;
        a.insets = new Insets(10, 0, 25, 0); //margin (t l b r)
        a.ipadx = 0; //growth element x
        a.ipady = 0; //growth element y
        a.weightx = 0.0;
        a.weighty = 0.0;

        //assign settings to button (element) then add it to form
        gbl.setConstraints(button, a);
        add(button);

        //add action to button : open new frame with inner form
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(selectedValue); // print choosing region type
                String selectedRegion = (String) regionNameComboBox.getSelectedItem();
                System.out.println(selectedRegion); // print choosing region name

                if ((selectedValue == null)||(selectedValue == "Выберите тип субъекта РФ")){
                    JOptionPane.showMessageDialog(null, "Необходимо указать тип региона", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                else if (selectedRegion == "Выберите наименование региона") {
                    JOptionPane.showMessageDialog(null, "Необходимо выбрать регион", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } else {
                    JFrame innerForm = new JFrame("Статистика региона: " + selectedRegion);
                    //change to GBL!!!
                    JPanel panel = createAndAddPanel(1, 3);
                    innerForm.add(panel);

                    innerForm.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    innerForm.setBounds(300, 300, 600, 150);
                    innerForm.setVisible(true);

                    createButtonWIthXlExport(panel);
                    createButtonWithChart(panel);
                    createButtonWithEmail(panel);
                }
            }
        });
    }

    private void createButtonWIthXlExport(JPanel panel){
        JButton button = new JButton("Эскпорт в Excel");
        panel.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 //create excel-file
                File myExcelFile = new File("myExcelFile1.xls");
                try {
                    WritableWorkbook workbook = Workbook.createWorkbook(myExcelFile);
                    WritableSheet sheet = workbook.createSheet("export", 0);
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
                //frame with info-message
                JOptionPane.showMessageDialog(null, "Файл " + myExcelFile.getName() + " создан", "Экспорт", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void createButtonWithEmail (JPanel panel){
        JButton button = new JButton("Отослать на почту");
        panel.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String smtpHost = "smtp.gmail.com"; //host of smtp mail server
                int smtpPort = 25;               //port of smtp mail server
                String username = "shoveltouch@gmail.com"; //your username e-mail account
                String password = "Shoveltouch123"; //your password e-mail account
                SmtpMessageSender messageSender = new SmtpMessageSender();

                try {
                    //creates the new JavaMail session
                    Session session = messageSender.createSession(smtpHost,
                            smtpPort,
                            username,
                            password);
                    //creates the message with empty content
                    String [] recipients = new String [] {"anis-aleksandra@yandex.ru"}; //"Kocapb2012@gmail.com", "smok1@inbox.ru", "albert.podusenko@gmail.com", "tokarevroma93@gmail.com"};

                    for (int i=0; i<recipients.length; i++ ) {
                        MimeMessage message = messageSender.createMimeMessage(session,
                                "За работу! Go to work!",   //subject
                                "shoveltouch@gmail.com",    //from
                                recipients[i],              //to
                                Message.RecipientType.TO);

                        //adds the plain text
                        messageSender.addText(message,
                            "Мотивация к работе!" +
                                    "\n\nОтправлено с приложения AT-Consulting",
                            "utf-8", "plain");

                        //adds the binary file
                        messageSender.addAttachment(message, new File("./src/images/sis1.jpg"));

                        //send the message
                        messageSender.sendMimeMessage(message);
                    }
                    /*
                    //adds the html text
                    messageSender.addText(message,
                            "<a href='#'>HTML link</a>",
                            "utf-8",
                            "html");
                    //adds the text file
                    messageSender.addAttachment(message,
                            new File("./src/main/resources/file.txt"));


                            */

                } catch (Exception ie) {
                    ie.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Письмо отправлено", "Почта", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void createButtonWithChart(JPanel panel){
        JButton button = new JButton("График");
        panel.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create Chart
                Chart chart = new Chart(800, 600);

                // generates linear data
                Collection<Date> xData_time = new ArrayList<Date>();
                Collection<Double> yData_status = new ArrayList<Double>();

                DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date date = null;
                for (int i = 1; i <= 10; i++) {

                    try {
                        date = sdf.parse(i + ".10.2008");
                    } catch (ParseException pe) {
                        pe.printStackTrace();
                    }
                    xData_time.add(date);
                    yData_status.add(Math.random() * i);
                }

                // Customize Chart
                chart.setChartTitle("График доступности ресурса");
                chart.setXAxisTitle("Дата");
                chart.setYAxisTitle("Статус ресурса");
                chart.getStyleManager().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.GREY));
                chart.getStyleManager().setPlotGridLinesColor(new Color(255, 255, 255));
                chart.getStyleManager().setChartBackgroundColor(Color.WHITE);
                chart.getStyleManager().setLegendBackgroundColor(Color.PINK);
                chart.getStyleManager().setChartFontColor(Color.MAGENTA);
                chart.getStyleManager().setChartTitleBoxBackgroundColor(new Color(0, 222, 0));
                chart.getStyleManager().setChartTitleBoxVisible(true);
                chart.getStyleManager().setChartTitleBoxBorderColor(Color.BLACK);
                chart.getStyleManager().setPlotGridLinesVisible(false);
                chart.getStyleManager().setAxisTickPadding(20);
                chart.getStyleManager().setAxisTickMarkLength(15);
                chart.getStyleManager().setPlotPadding(20);
                chart.getStyleManager().setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
                chart.getStyleManager().setLegendFont(new Font(Font.SERIF, Font.PLAIN, 18));
                chart.getStyleManager().setLegendPosition(StyleManager.LegendPosition.InsideSE);
                chart.getStyleManager().setLegendSeriesLineLength(12);
                chart.getStyleManager().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
                chart.getStyleManager().setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 11));
                chart.getStyleManager().setDatePattern("dd-MMM");
                chart.getStyleManager().setLocale(Locale.ENGLISH);

                Series series = chart.addSeries("Fake Data", xData_time, yData_status);
                series.setLineColor(SeriesColor.BLUE);
                series.setMarkerColor(Color.ORANGE);
                series.setMarker(SeriesMarker.CIRCLE);
                series.setLineStyle(SeriesLineStyle.SOLID);


                // Show it setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                new SwingWrapper(chart).displayChart("График доступности");

                /* save chart
                try {
                    // Save it
                    //BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);

                    // or save it in high-res
                    //BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
                } catch (IOException ie) {
                    ie.printStackTrace();
                }*/
            }
        });
    }

    private void buildRegionNameComboBox(GridBagLayout gbl) {
        regionNameComboBox = new JComboBox<String>();
        regionNameComboBox.addItem("Выберите наименование региона"); //1st string in 'Region Names' list

        //customise list (element position)
        GridBagConstraints a = new GridBagConstraints();
        a.anchor = GridBagConstraints.CENTER;
        a.fill   = GridBagConstraints.HORIZONTAL;
        a.gridheight = 1;
        a.gridwidth  = 1;
        a.gridx = GridBagConstraints.RELATIVE;
        a.gridy = 1;
        a.insets = new Insets(10, 0, 10, 0); //margin (t l b r)
        a.ipadx = 0; //growth element x
        a.ipady = 0; //growth element y
        a.weightx = 0.0;
        a.weighty = 0.0;

        //assign settings to list (element) then add it to form
        gbl.setConstraints(regionNameComboBox, a);
        add(regionNameComboBox);
    }

    private void buildRegionTypeComboBox(GridBagLayout gbl) {
        JComboBox<String> regionType = new JComboBox<String>();
        regionType.addItem("Выберите тип субъекта РФ");     //1st string in 'Region Types' list
        for (String value : regionNameRegionType.keySet()) {
            regionType.addItem(value);                      //'value' means us names of 'Region Types' (наименования типов регионов)
        }

        //customise list (element position)
        GridBagConstraints a = new GridBagConstraints();
        a.anchor = GridBagConstraints.CENTER;
        a.fill   = GridBagConstraints.HORIZONTAL;
        a.gridheight = 1;
        a.gridwidth  = 1;
        a.gridx = GridBagConstraints.RELATIVE;
        a.gridy = 0;
        a.insets = new Insets(25, 0, 10, 0); //margin (t l b r)
        a.ipadx = 0; //growth element x
        a.ipady = 0; //growth element y
        a.weightx = 0.0;
        a.weighty = 0.0;

        //assign settings to list (element) then add it to form
        gbl.setConstraints(regionType, a);
        add(regionType);

        //add action to list : connect two lists (ComboBoxes)
        regionType.addActionListener(new ActionListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> source = (JComboBox<String>) e.getSource();
                selectedValue = source.getSelectedItem().toString();
                List<String> regionNames = regionNameRegionType.get(selectedValue);
                regionNameComboBox.removeAllItems();
                if (regionNames == null) {
                    regionNameComboBox.addItem("Выберите наименование региона");
                } else {
                    regionNameComboBox.addItem("Выберите наименование региона");
                    for (String name : regionNames) {
                        regionNameComboBox.addItem(name);
                    }
                }
            }
        });
    }

    private void buildDataModel() {
        regionNameRegionType.put("Республика",
                Arrays.asList("Адыгея","Алтай","Башкортостан","Бурятия","Дагестан","Ингушетия","Кабардино-Балкария","Калмыкия",
                        "Карачаево-Черкесия","Карелия","Коми","Крым","Марий Эл","Мордовия","Саха (Якутия)","Северная Осетия — Алания",
                        "Татарстан","Тыва","Удмуртия","Хакасия","Чечня","Чувашия"));
        regionNameRegionType.put("Область",
                Arrays.asList("Амурская область","Архангельская область","Астраханская область","Белгородская область","Брянская область",
                        "Владимирская область","Волгоградская область","Вологодская область","Воронежская область","Ивановская область",
                        "Иркутская область","Калининградская область","Калужская область","Кемеровская область","Кировская область",
                        "Костромская область","Курганская область","Курская область","Ленинградская область","Липецкая область",
                        "Магаданская область","Московская область","Мурманская область","Нижегородская область","Новгородская область",
                        "Новосибирская область","Омская область","Оренбургская область","Орловская область","Пензенская область",
                        "Псковская область","Ростовская область","Рязанская область","Самарская область","Саратовская область",
                        "Сахалинская область","Свердловская область","Смоленская область","Тамбовская область","Тверская область",
                        "Томская область","Тульская область","Тюменская область","Ульяновская область","Челябинская область","Ярославская область"));
        regionNameRegionType.put("Край",
                Arrays.asList("Алтайский край","Забайкальский край","Камчатский край","Краснодарский край","Красноярский край","Пермский край",
                        "Приморский край","Ставропольский край","Хабаровский край"));
        regionNameRegionType.put("Город федерального значения",
                Arrays.asList("Москва","Санкт-Петербург","Севастополь"));
        regionNameRegionType.put("Автономный округ",
                Arrays.asList("Ненецкий АО","Ханты-Мансийский АО — Югра","Чукотский АО","Ямало-Ненецкий АО"));
        regionNameRegionType.put("Автономная область",
                Arrays.asList("Еврейская АО"));
    }

    private void init(GridBagLayout gbl) {
        setTitle("AT-Consulting Challenge");
        setBounds(300, 300, 400, 200); //x,y, width,height
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(gbl);
    }

    private JPanel createAndAddPanel(int i, int j) {
        JPanel panel = new JPanel(true);
        panel.setLayout(new GridLayout(i, j));
        add(panel);
        return panel;
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // select Look and Feel
                    UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
                } catch (Exception e){
                    e.printStackTrace();
                }
                TwoComboboxFrame app = new TwoComboboxFrame();
                 app.setVisible(true);
            }
        });
    }

}