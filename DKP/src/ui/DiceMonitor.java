package ui;

import java.io.PrintWriter;
import bot.Auctions;
import bot.DiceRolls;
import database.Cache;
import deprecated.MainMenu;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import util.Data;
import util.EverQuest;
import static util.EverQuest.*;
import util.SQL;

/**
 *
 * @author Aaron
 */
public class DiceMonitor extends javax.swing.JFrame {
    public static final String VERSION = "1.2";
    
    public static DiceMonitor diceMonitor;
    public static File parseLog = new File("C:\\Program Files (x86)\\Sony\\Everquest\\Logs\\eqlog_Raidbot_vox.txt");
    public static String CHANNEL = "/gu ";
    public static boolean botMode = false;
    public static boolean isGambling = false;
    public static boolean debugMode = false;
    
    public final static ArrayList<String> GAMBLER_BOTS = new ArrayList<>();
    static {
        GAMBLER_BOTS.add("Raidbot");
    }

    public static SQL sql;
    
    public static void reset() {
        try {
            EverQuest.closeItem();
            EverQuest.closeItem();
            CHANNEL = "/gu";
            
            botMode = diceMonitor.chkEnableBotMode.isSelected();
            isGambling = diceMonitor.chkEnableGambling.isSelected();
            debugMode = false;

            sql.closeConnection();
            sql = new SQL();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates new form DMUI
     */
    public DiceMonitor() {
        sql = new SQL();
        
        diceMonitor = this;

        initComponents();
        
        // Clean up before you go
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Shutdown Hook: Cleanup");
                for (SQL sql : SQL.openConnections) {
                    sql.closeConnection();
                }
                try {
                    if (bufReader != null) {
                        bufReader.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        long free = Runtime.getRuntime().freeMemory();
                        long total = Runtime.getRuntime().maxMemory();
                        
                        int per = (int)(free * 100 / total);
                        
                        diceMonitor.memoryBar.setValue(per);
                        
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static BufferedReader bufReader;
    public void init(final File log) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    bufReader = new BufferedReader(new FileReader(log));

                    chkReading.setSelected(true);

                    while (true) {
                        if (bufReader.ready()) {
                            String line = bufReader.readLine();
                            try {
                                parse(line);
                            } catch (Exception e) {
                                System.err.println(e);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        chkReading = new javax.swing.JCheckBox();
        chkEnableBotMode = new javax.swing.JCheckBox();
        memoryBar = new javax.swing.JProgressBar();
        chkEnableGambling = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuNewBot = new javax.swing.JMenuItem();
        menuLoadBot = new javax.swing.JMenuItem();
        menuDeleteBot = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuAddItem = new javax.swing.JMenuItem();
        menuAddRaid = new javax.swing.JMenuItem();
        menuBotMode = new javax.swing.JMenu();
        menuReset = new javax.swing.JMenuItem();

        jMenuItem3.setText("jMenuItem3");

        jMenuItem4.setText("jMenuItem4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        chkReading.setText("Reading Log ...");
        chkReading.setEnabled(false);

        chkEnableBotMode.setText("Enable Bot Mode");
        chkEnableBotMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEnableBotModeActionPerformed(evt);
            }
        });

        memoryBar.setOrientation(1);

        chkEnableGambling.setText("Enable Gambling");
        chkEnableGambling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEnableGamblingActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        menuNewBot.setText("New Bot Profile ...");
        menuNewBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNewBotActionPerformed(evt);
            }
        });
        jMenu1.add(menuNewBot);

        menuLoadBot.setText("Load Bot Profile ...");
        menuLoadBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLoadBotActionPerformed(evt);
            }
        });
        jMenu1.add(menuLoadBot);

        menuDeleteBot.setText("Delete Bot Profile ...");
        menuDeleteBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDeleteBotActionPerformed(evt);
            }
        });
        jMenu1.add(menuDeleteBot);
        jMenu1.add(jSeparator1);

        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Website");

        menuAddItem.setText("Add Item");
        menuAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddItemActionPerformed(evt);
            }
        });
        jMenu2.add(menuAddItem);

        menuAddRaid.setText("Add Raid");
        menuAddRaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddRaidActionPerformed(evt);
            }
        });
        jMenu2.add(menuAddRaid);

        jMenuBar1.add(jMenu2);

        menuBotMode.setText("Bot");

        menuReset.setText("Reset/Clean");
        menuReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuResetActionPerformed(evt);
            }
        });
        menuBotMode.add(menuReset);

        jMenuBar1.add(menuBotMode);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkEnableBotMode)
                    .addComponent(chkEnableGambling)
                    .addComponent(chkReading))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(memoryBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(memoryBar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chkReading)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(chkEnableBotMode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkEnableGambling)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkEnableBotModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEnableBotModeActionPerformed
        botMode = chkEnableBotMode.isSelected();
    }//GEN-LAST:event_chkEnableBotModeActionPerformed

    private void chkEnableGamblingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEnableGamblingActionPerformed
        isGambling = chkEnableGambling.isSelected();
    }//GEN-LAST:event_chkEnableGamblingActionPerformed

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuNewBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNewBotActionPerformed
        // Create a new dialog to create a bot, and force them to deal with that screen first
        NewBot newBot = new NewBot(this, true);
        newBot.show();
        
        String botName = newBot.getBotName();
        String logFile = newBot.getLogFile();
        
        // Cancel if they hit cancel
        if (botName.isEmpty() || logFile.isEmpty()) return;
        
        // Add bot to the profiles
        Data.profiles.addEntry(botName, logFile);
        
        init(new File(logFile));
    }//GEN-LAST:event_menuNewBotActionPerformed

    private void menuLoadBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLoadBotActionPerformed
        LoadBot loadBot = new LoadBot(this, true);
        loadBot.show();
        
        // If person clicked load instead of cancel
        if (loadBot.retValue == JFileChooser.APPROVE_OPTION) {
            String botName = loadBot.getSelectedBotName();
            String logFile = Data.profiles.where("Name", botName).select("Log");
            
      
            File file = new File(logFile);
            file.delete();
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(logFile, "UTF-8");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DiceMonitor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(DiceMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
            writer.println(" ");
            
            writer.close();
            
            init(new File(logFile));
        }
    }//GEN-LAST:event_menuLoadBotActionPerformed

    private void menuDeleteBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDeleteBotActionPerformed
        LoadBot loadBot = new LoadBot(this, true);
        loadBot.show();
        
        if (loadBot.retValue == JFileChooser.APPROVE_OPTION) {
            String botName = loadBot.getSelectedBotName();
            
            Data.profiles.removeEntry(botName);
        }
    }//GEN-LAST:event_menuDeleteBotActionPerformed

    private void menuResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuResetActionPerformed
        resetRaidbot("root");
    }//GEN-LAST:event_menuResetActionPerformed

    private void menuAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddItemActionPerformed
        AddItem addItem = new AddItem(this, true);
        addItem.show();
    }//GEN-LAST:event_menuAddItemActionPerformed

    private void menuAddRaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddRaidActionPerformed
        AddRaid addRaid = new AddRaid(this, true);
        addRaid.show();
    }//GEN-LAST:event_menuAddRaidActionPerformed
    
    public static void resetRaidbot(String user) {
        sendTell(user, "Resetting...");
        EverQuest.resetInputBar();
        sendTell(user, "Progress 10%");
        sendTell(user, "Progress 20%");
        DiceMonitor.reset();
        sendTell(user, "Progress 30%");
        MainMenu.reset();
        sendTell(user, "Progress 40%");
        EverQuest.reset();
        sendTell(user, "Progress 50%");
        util.ScreenSearch.reset();
        sendTell(user, "Progress 60%");
        bot.Auctions.reset();
        sendTell(user, "Progress 70%");
        Cache.refresh();
        sendTell(user, "Progress 80%");
        util.SQL.reset();
        sendTell(user, "Progress 90%");
        sendTell(user, "Progress 100%, Completed Succesfully!");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DiceMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DiceMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DiceMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DiceMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DiceMonitor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkEnableBotMode;
    private javax.swing.JCheckBox chkEnableGambling;
    private javax.swing.JCheckBox chkReading;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JProgressBar memoryBar;
    private javax.swing.JMenuItem menuAddItem;
    private javax.swing.JMenuItem menuAddRaid;
    private javax.swing.JMenu menuBotMode;
    private javax.swing.JMenuItem menuDeleteBot;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenuItem menuLoadBot;
    private javax.swing.JMenuItem menuNewBot;
    private javax.swing.JMenuItem menuReset;
    // End of variables declaration//GEN-END:variables

    public static volatile boolean isBidding = false;
    public static volatile boolean auctionMode = false;

    public void parse(String s) throws Exception {
        // Look for MQ2 Timestamps and strip them
        String[] subs = s.split("\\[");
        for (String sub : subs) {
            if (sub.length() > 8 && sub.charAt(8) == ']') {
                int index = s.indexOf(sub);
                s = s.substring(0, index-1) + s.substring(index+10, s.length());
            }
        }

        if (s.toLowerCase().contains("#botmodeoff")) {
            String name = s.split(" ")[5];
            botMode = false;
            sendTell(name, "Bot mode off");
            return;
        }
        if (s.toLowerCase().contains("#botmodeon")) {
            String name = s.split(" ")[5];
            botMode = true;
            sendTell(name, "Bot mode on");
            return;
        }
        
        if (isGambling) {
            for (String bot : GAMBLER_BOTS) {
                if (s.toLowerCase().contains("hail, " + (bot.toLowerCase()))) {
                    String name = s.split(" ")[5];
                    
                    String[] tradeInfo = EverQuest.getTradeInfo();
                    if (tradeInfo[0].isEmpty() || tradeInfo[1].isEmpty()) {
                        EverQuest.sendToEQ("/say Hail, " + name);
                        System.out.println(tradeInfo[0] + " / " + tradeInfo[1]);
                    } else {
                        EverQuest.sendToEQ("/say This action will deposit " + tradeInfo[1] + "pp into account: [" + tradeInfo[0] + "]");
                    }
                }
            }
        }

        String sLower = s.toLowerCase();

        if (sLower.contains("sorry, i am a.f.k"))
            return;
        
        if (sLower.contains("afk message:"))
            return;

        if (botMode && DiceRolls.isRolling && (sLower.contains(" -> ") || sLower.contains(" tells you"))) {
            String name = s.split(" ")[5];
            
            DiceRolls.placeBid(name);
            
            return;
        }

        if (isBidding && (sLower.contains(" -> ") || sLower.contains(" tells you"))) {
            String[] str = s.split(" ");

            if (str[6].equals("tells") && str[7].equals("you,")) {
                for (int i = 8; i < str.length; i++) {
                    String st = str[i].replaceAll("\'", "");
                    if (isNumeric(st)) {
                        int num = Integer.parseInt(st);

                        Auctions.placeBid(str[5], num);
                        break;
                    } else {
                        String number = "";
                        for (int j = 0; j < str[i].length(); j++) {
                            if (isNumeric(""+str[i].charAt(j))) {
                                number += str[i].charAt(j);
                            } else if (str[i].charAt(j) == '.') {
                                break;
                            }
                        }
                        if (isNumeric(number)) {
                            Auctions.placeBid(str[5], Integer.parseInt(number));
                            break;
                        }
                    }
                }
            }
        }

        if (botMode && sLower.contains("#") && (sLower.contains(" -> ") || sLower.contains(" tells you"))) {
            String name = s.split(" ")[5];

            String ret = "/tell " + name + " ";

            if (name.equals("You")) {
                ret = CHANNEL;
            }
            if (sLower.contains("#dkp")) {
                ret += "Current DKP: " + sql.getDKP(name) + ". ";
            }
            if (sLower.contains("#ra") || sLower.contains("#att") || sLower.contains("#raidatt")) {
                ret += "RA: " + sql.getRA(name, SQL.DAYS_30) + "%. ";
            }
            if (sLower.contains("#reset")) {
                Auctions.reset();
                reset();
                resetRaidbot(name);
            }
           
            if (sLower.contains("#echo")) {
                sendToEQ(s.substring(s.indexOf("#echo") + 5).replaceAll("\'", ""));
            }
            if (!ret.equals("/tell " + name + " ")) {
                sendToEQ(ret);

                System.out.println(ret);

                return;
            }
        }

        if (botMode && !isBidding && (sLower.contains(" -> ") || sLower.contains(" tells you"))) {
            String[] str = s.split(" ");
            if (str[6].equals("tells") && str[7].equals("you,"))
                MainMenu.getSession(str[5]).parse(s.substring(s.indexOf("tells you")+12, s.length()-1));
        }
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
