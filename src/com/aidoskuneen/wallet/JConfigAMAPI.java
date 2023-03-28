package com.aidoskuneen.wallet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.Properties;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import java.awt.SystemColor;
import java.awt.Color;

public class JConfigAMAPI extends JDialog{

	public static String AMserverAPIURL = "https://api1.mainnet.aidoskuneen.com:31112";
	public static String AMserverUser = "not@yet.set";
	public static String AMserverAPIKEY = "1234567890";
	public static String AMserverBTCtype = "notset";
	
	static String AMserverAPIURLDefault = AMserverAPIURL;
	private JTextField txtserverField;
	private static String config_filename = "api.config";
	private static File configFileAMAPI = new File(config_filename);
	private JPasswordField apiKeyField;
	private JRadioButton rdbtnUSEnativeBTC;
	private JRadioButton rdbtnUSEakBTC;
	private JTextField txtEmail;
	
	static void CheckConfigFile() {
		if (Flow.CLI!= null) {
			File parent = Flow.CLI.getParentFile();
			if (parent!=null) {
				configFileAMAPI = new File(parent,config_filename);
			}
		}
		if (configFileAMAPI==null) {
			configFileAMAPI = new File(config_filename);
		}
	}
	
	static synchronized void LoadConfig() {
		
		Properties prop = new Properties();
		
		CheckConfigFile();
		
		try (FileInputStream fis = new FileInputStream(configFileAMAPI)) {
		    prop.load(fis);
		    AMserverAPIURL = prop.getProperty("AMserverAPIURL", AMserverAPIURL);
		    AMserverUser = prop.getProperty("AMserverUser", AMserverUser);
		    AMserverAPIKEY = prop.getProperty("AMserverAPIKEY", AMserverAPIKEY);
		    AMserverBTCtype = prop.getProperty("AMserverBTCtype", AMserverBTCtype);
		    
		} catch (FileNotFoundException ex) {
			SaveConfig(); //create
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
	}
	
	static synchronized void SaveConfig() {
		Properties prop = new Properties();
		
		CheckConfigFile();

		 prop.setProperty("AMserverAPIURL", AMserverAPIURL);
	     prop.setProperty("AMserverUser", AMserverUser);
	     prop.setProperty("AMserverAPIKEY", AMserverAPIKEY);
	     prop.setProperty("AMserverBTCtype", AMserverBTCtype);
		
		try (FileOutputStream fos = new FileOutputStream(configFileAMAPI, false)) {
		    prop.store(fos,"AM API Config Properties");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public JConfigAMAPI() {
		setTitle("Aidos Market API Configuration");
		LoadConfig();
		
		setBounds(100, 100, 450, 579);
		
		setModal(true);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		BufferedImage logo;
		try {
			logo = ImageIO.read(new File("images/64x64.png"));
		} catch (IOException e) {
			try {
				logo = ImageIO.read(getClass().getResourceAsStream("/images/64x64.png"));
			} catch (IOException e1) {
				logo = null;
			}
				
		}
		
		this.setIconImage(new ImageIcon(logo).getImage());
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10,10, 10, 10));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("API URL");
		lblNewLabel.setBounds(10, 10, 414, 20);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("(default is: "+AMserverAPIURLDefault+")");
		lblNewLabel_1.setBounds(10, 30, 414, 20);
		panel.add(lblNewLabel_1);
		
		txtserverField = new JTextField();
		txtserverField.setBounds(10, 50, 414, 20);
		panel.add(txtserverField);
		txtserverField.setColumns(10);
		txtserverField.setText(AMserverAPIURL);
		
		JLabel lblApiKey = new JLabel("Aidos Market Account eMail (lowercase)");
		lblApiKey.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblApiKey.setBounds(10, 81, 414, 20);
		panel.add(lblApiKey);
		
		apiKeyField = new JPasswordField();
		apiKeyField.setBounds(10, 201, 414, 20);
		apiKeyField.setText(AMserverAPIKEY);
		panel.add(apiKeyField);
		if (AMserverBTCtype.equalsIgnoreCase("native")) {
			rdbtnUSEnativeBTC.setSelected(true);
			rdbtnUSEakBTC.setSelected(false);
		}
		
		if (AMserverBTCtype.equalsIgnoreCase("wrapped")) {
			rdbtnUSEnativeBTC.setSelected(false);
			rdbtnUSEakBTC.setSelected(true);
		}
		
		JLabel lblApiKey_1 = new JLabel("<html>(create / find your API Key in your Aidos Market Wallet/Account, <br/>\r\nThe value labeled \"API Keys:\" in the API section of your Aidos Market Wallet)\r\n</html>");
		lblApiKey_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblApiKey_1.setBounds(10, 154, 414, 44);
		panel.add(lblApiKey_1);
		
		txtEmail = new JTextField();
		txtEmail.setText(AMserverUser);
		txtEmail.setColumns(10);
		txtEmail.setBounds(10, 103, 414, 20);
		panel.add(txtEmail);
		
		JPanel JbtcPanel = new JPanel();
		JbtcPanel.setBackground(SystemColor.info);
		JbtcPanel.setBounds(0, 293, 434, 236);
		panel.add(JbtcPanel);
		JbtcPanel.setLayout(null);
		
		JLabel lblBtcNative = new JLabel("BTC Native / BTC Token Setting");
		lblBtcNative.setBounds(10, 11, 414, 20);
		JbtcPanel.add(lblBtcNative);
		lblBtcNative.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		rdbtnUSEnativeBTC = new JRadioButton("Use native BTC for this AM account");
		rdbtnUSEnativeBTC.setForeground(new Color(0, 0, 128));
		rdbtnUSEnativeBTC.setFont(new Font("Tahoma", Font.BOLD, 12));
		rdbtnUSEnativeBTC.setBackground(SystemColor.info);
		rdbtnUSEnativeBTC.setBounds(10, 37, 414, 23);
		JbtcPanel.add(rdbtnUSEnativeBTC);
		
		rdbtnUSEakBTC = new JRadioButton("Use wrapped BTC for this AM account");
		rdbtnUSEakBTC.setForeground(new Color(0, 0, 128));
		rdbtnUSEakBTC.setFont(new Font("Tahoma", Font.BOLD, 12));
		rdbtnUSEakBTC.setBackground(SystemColor.info);
		rdbtnUSEakBTC.setBounds(10, 63, 414, 23);
		JbtcPanel.add(rdbtnUSEakBTC);
		
		JButton btnSetBTCPref = new JButton("Submit BTC Type Preference");
		btnSetBTCPref.setBounds(84, 104, 218, 23);
		JbtcPanel.add(btnSetBTCPref);
		
		JLabel lblNewLabel_2 = new JLabel("<html>IMPORTANT: \r\n<br>Selecting native vs wrapped BTC (akBTC) can \r\n<br>only be done ONCE per Aidos Market account.\r\n<br>This cannot be undone, choose carefully!");
		lblNewLabel_2.setBounds(10, 127, 414, 90);
		JbtcPanel.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 249, 434, 33);
		panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JConfigAMAPI.this.setVisible(false);
			}
		});
		panel_1.add(btnCancel);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newServerURL = txtserverField.getText().trim();
				if (!newServerURL.toLowerCase().startsWith("http")) {
					JOptionPane.showMessageDialog(JConfigAMAPI.this, "Invalid server address (has to start with http/https).","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					new URL(newServerURL);
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(JConfigAMAPI.this, "Invalid URL.","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
//				serverAPI = newServerURL;
				
				if (txtEmail.getText() != null && !txtEmail.getText().contains("@")) {
					JOptionPane.showMessageDialog(JConfigAMAPI.this, "Invalid USERNAME (should be your AM email).","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if ((apiKeyField.getPassword()).length < 8) {
					JOptionPane.showMessageDialog(JConfigAMAPI.this, "Invalid API KEY (min 8 char). This can be your AM API KEY, \n or the PHONE NO stored in your Aidos Market Profile.","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				AMserverAPIURL = newServerURL;
				AMserverUser = txtEmail.getText();
				AMserverAPIKEY = new String(apiKeyField.getPassword()).trim().replaceAll(" ", "");
				
				SaveConfig();
				JOptionPane.showMessageDialog(JConfigAMAPI.this, "AM API Config saved.","Info", JOptionPane.INFORMATION_MESSAGE);
				//JConfigAMAPI.this.setVisible(false);
			}
		});
		panel_1.add(btnSave);
		
		JLabel lblApiKey_1_1 = new JLabel("Aidos Market API KEY");
		lblApiKey_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblApiKey_1_1.setBounds(10, 134, 414, 20);
		panel.add(lblApiKey_1_1);
		btnSetBTCPref.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newServerURL = txtserverField.getText().trim();
				if (!newServerURL.toLowerCase().startsWith("http")) {
					JOptionPane.showMessageDialog(JConfigAMAPI.this, "Invalid server address (has to start with http/https).","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					new URL(newServerURL);
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(JConfigAMAPI.this, "Invalid URL.","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
//				serverAPI = newServerURL;
				
				if (txtEmail.getText() != null && !txtEmail.getText().contains("@")) {
					JOptionPane.showMessageDialog(JConfigAMAPI.this, "Invalid USERNAME (should be your AM email).","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if ((apiKeyField.getPassword()).length < 8) {
					JOptionPane.showMessageDialog(JConfigAMAPI.this, "Invalid API KEY (min 8 char). This can be your AM API KEY, \n or the PHONE NO stored in your Aidos Market Profile.","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				AMserverAPIURL = newServerURL;
				AMserverUser = txtEmail.getText();
				AMserverAPIKEY = new String(apiKeyField.getPassword()).trim().replaceAll(" ", "");
				
				SaveConfig();
				
				if (!rdbtnUSEnativeBTC.isSelected() && !rdbtnUSEakBTC.isSelected()) {
					JOptionPane.showMessageDialog(JConfigAMAPI.this, "please select the BTC native or token option.");
					return;
				}
				ProcResult result_rfr = Flow.CallCLIWallet(
							"amapi",
							AMserverAPIURL,
							AMserverUser,
							AMserverAPIKEY,
							"set_account_btc_format",
							rdbtnUSEnativeBTC.isSelected()?"native":"token"
							)
						;
				JSONObject jrfr = Flow.getJsonFromResult(result_rfr);
				if (jrfr==null || !Flow.isOKJson(jrfr) ) {
					//System.out.println(jrfr.toJSONString());
					JOptionPane.showMessageDialog(JConfigAMAPI.this, "Error in API call. Check your settings, and the log window");
					Flow.LogLn(jrfr==null?"null":jrfr.toJSONString());
					
				} else {
					Flow.LogLn(jrfr.toJSONString());
					JSONObject data = (JSONObject) jrfr.get("data");
					JOptionPane.showMessageDialog(JConfigAMAPI.this, "API call result: \n" 
					+ "Status: "+ ((String)data.getOrDefault("apistatus","fail")) +" \n" 
					+ "Request ID: "+ ((String)data.getOrDefault("apiresult","n/a")) +" \n" 
					+ "Message from API: "+ ((String)data.getOrDefault("message"," ")));
					Flow.LogLn("Request ID: "+((String)data.getOrDefault("requestid","n/a")));
				}
			}
		});
		
		rdbtnUSEakBTC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnUSEakBTC.isSelected()) {
					rdbtnUSEnativeBTC.setSelected(false);
				}
			}
		});
		
			rdbtnUSEnativeBTC.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnUSEnativeBTC.isSelected()) {
						rdbtnUSEakBTC.setSelected(false);
					}
				}
			});
		this.setVisible(true);
	}

	public JTextField getTxtserverField() {
		return txtserverField;
	}
	public JRadioButton getRdbtnUSEnativeBTC() {
		return rdbtnUSEnativeBTC;
	}
	public JRadioButton getRdbtnUSEakBTC() {
		return rdbtnUSEakBTC;
	}
	public JTextField getTxtEmail() {
		return txtEmail;
	}
}
