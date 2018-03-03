package com.pepe.apps.proxyjar;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author pepe
 */
public class ReadDB extends JFrame {   
    
    private final int[] LINE = { // Start Lines (9 by 9)
        70, // IP Address Line
        71, // Port Line
        72, // Country Line
        74, // Type Line
        69, // Last Update Line
        75 // Response Time Line
    };
    
    private final String[][] PARAM_S = {
        {"\'", "\'"}, // IP Addresses params
        {"\'", "\'"}, // Port params
        {"c=", "\">"}, // Country params
        {">", "<"}, // Type params
        {">", "<"}, // Last Update params
        {">", "<"} // Response Times params
    };

    private final int[][] PARAM_I = {
        {+1, 0, 0}, // IP Addresses params
        {+1, 0, 0}, // Port params
        {+2, 0, 0}, // Country params
        {+5, 0, 0}, // Type params
        {+1, 0, 0}, // Last Update params
        {+1, 0, 0} // Response Times params
    };

    public static String[][] DATA;
    private URL url;
    private URLConnection connection;
    private BufferedReader reader;
    private JLabel lbl1;
    private int animCooldown = 100;
    private String txtAnim;
    private ArrayList<String> harvested;
    
    public ReadDB() {
        initUI();
        ReadDB.DATA = new String[25][6];
        data();
        setVisible(false);
    }

    private void initUI() {
        lbl1 = new JLabel();

        setTitle("Updating...");
        setSize(300, 50);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        lbl1.setOpaque(true);
        lbl1.setFont(new Font("Arial", 0, 20));
        lbl1.setBackground(Color.BLACK);
        lbl1.setForeground(Color.GREEN);
        add(lbl1);
        
        setVisible(true);
        anim.start();
    }

    Thread anim = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    txtAnim = " [*] Reading database... ";
                    setAnim(txtAnim, "\\");
                    anim.sleep(animCooldown);

                    setAnim(" [*] rEading database... ", "|");
                    anim.sleep(animCooldown);

                    setAnim(" [*] reAding database... ", "/");
                    anim.sleep(animCooldown);

                    setAnim(" [*] reaDing database... ", "-");
                    anim.sleep(animCooldown);

                    setAnim(" [*] readIng database... ", "\\");
                    anim.sleep(animCooldown);

                    setAnim(" [*] readiNg database... ", "|");
                    anim.sleep(animCooldown);

                    setAnim(" [*] readinG database... ", "/");
                    anim.sleep(animCooldown);

                    setAnim(" [*] reading Database... ", "-");
                    anim.sleep(animCooldown);

                    setAnim(" [*] reading dAtabase... ", "\\");
                    anim.sleep(animCooldown);

                    setAnim(" [*] reading daTabase... ", "|");
                    anim.sleep(animCooldown);

                    setAnim(" [*] reading datAbase... ", "/");
                    anim.sleep(animCooldown);

                    setAnim(" [*] reading dataBase... ", "-");
                    anim.sleep(animCooldown);

                    setAnim(" [*] reading databAse... ", "\\");
                    anim.sleep(animCooldown);

                    setAnim(" [*] reading databaSe... ", "|");
                    anim.sleep(animCooldown);

                    setAnim(" [*] reading databasE... ", "/");
                    anim.sleep(animCooldown);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    private void setAnim(String txt, String charge) {
        lbl1.setText(txt + charge);
    }

    private void data() {
        try {     
            harvestData();
            int cont = 0;
            for (int i = 0; i < 25; i++) {
                ReadDB.DATA[i][0] = getIP(LINE[0] + cont);
                ReadDB.DATA[i][1] = getPort(LINE[1] + cont);
                ReadDB.DATA[i][2] = getCountry(LINE[2] + cont);
                ReadDB.DATA[i][3] = getType(LINE[3] + cont);
                ReadDB.DATA[i][4] = getLastUpdate(LINE[4] + cont);
                ReadDB.DATA[i][5] = getResponseTimes(LINE[5] + cont);
                cont = cont + 9;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void harvestData() {
        try {
            this.url = new URL("http://www.gatherproxy.com/sockslist");
            this.connection = url.openConnection();
            this.reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            this.harvested = new ArrayList<>();
            String line = reader.readLine();
            while(line != null) {
                this.harvested.add(line);
                line = reader.readLine();
            }            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private String getIP(int line) {
        String st = null;
        String[] str = new String[9];
        int l = 0;
        st = harvested.get(line - 1);                
        l = st.length() - 99; // MAX: 107 MIN: 99
        str[l] = st;
        str[l] = str[l].substring(str[l].indexOf(PARAM_S[0][0]) + (PARAM_I[0][0]));
        str[l] = str[l].substring(PARAM_I[0][1], str[l].indexOf(PARAM_S[0][1]) + (PARAM_I[0][2]));
        return str[l];
    }

    private String getPort(int line) {
        String str = null;
        str = harvested.get(line - 1);
        str = str.substring(str.indexOf(PARAM_S[1][0]) + (PARAM_I[1][0]));
        str = str.substring(PARAM_I[1][1], str.indexOf(PARAM_S[1][1]) + (PARAM_I[1][2]));
        return str;
    }

    private String getCountry(int line) {
        String st = null;
        String[] str = new String[30];
        int l = 0;
        st = harvested.get(line - 1);
        // FILTRAR VAR
        l = st.length() - 76; // MAX: 105 MIN: 76
        str[l] = st;
        str[l] = str[l].substring(str[l].indexOf(PARAM_S[2][0]) + (PARAM_I[2][0]));
        str[l] = str[l].substring(PARAM_I[2][1], str[l].indexOf(PARAM_S[2][1]) + (PARAM_I[2][2]));        
        return str[l];
    }

    private String getType(int line) {
        String str = null;
        str = harvested.get(line - 1);
        str = str.substring(str.indexOf(PARAM_S[3][0]) + (PARAM_I[3][0]));
        str = str.substring(PARAM_I[3][1], str.indexOf(PARAM_S[3][1]) + (PARAM_I[3][2]));
        return str;
    }

    private String getLastUpdate(int line) {
        String str = null;
        str = harvested.get(line - 1);
        str = str.substring(str.indexOf(PARAM_S[4][0]) + (PARAM_I[4][0]));
        str = str.substring(PARAM_I[4][1], str.indexOf(PARAM_S[4][1]) + (PARAM_I[4][2]));
        return str;
    }

    private String getResponseTimes(int line) {
        String str = null;
        str = harvested.get(line - 1);
        str = str.substring(str.indexOf(PARAM_S[5][0]) + (PARAM_I[5][0]));
        str = str.substring(PARAM_I[5][1], str.indexOf(PARAM_S[5][1]) + (PARAM_I[5][2]));
        return str;
    }
}