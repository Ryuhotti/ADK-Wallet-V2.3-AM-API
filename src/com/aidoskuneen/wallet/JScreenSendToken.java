package com.aidoskuneen.wallet;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class JScreenSendToken extends JPanel {
	private JTextField tAddressFrom;
	private JTextField tAvailableTOKEN;
	private JTextField txtSendTo;
	private JTextField tAmount;

	/**
	 * Create the panel.
	 */
	
	boolean useGas = false;
	synchronized void UpdateAvailable() {
		
		tAvailableTOKEN.setText(JScreenWallet.ConvertWeiToADK(availableWei+"")+" "+JScreenToken.selectedToken);
		lblfeeInfo.setText(JScreenToken.selectedToken + " at address: "+JScreenWallet.ConvertWeiToADK(availableWei+"")+" "+ JScreenToken.selectedToken );
		//tAmount.setText("");
		invalidate();
		this.repaint();
	}
	
	BigInteger availableWei = BigInteger.ZERO;
	//gInteger availableWei_minusGas = BigInteger.ZERO;
	BigInteger gas = new BigInteger("21000000000000000");
	BigInteger gasCostWEI  = BigInteger.ZERO;
	
	private JRadioButton rdbtnUseGas;
	private JLabel lblfeeInfo;
	
	public JScreenSendToken(String sendingFrom, String availableTOKENinWEI) {
		setLayout(new BorderLayout(0, 0));
		availableWei = new BigInteger(availableTOKENinWEI);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(34, 118, 72));
		panel.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblSending = new JLabel("Sending ADK");
		lblSending.setHorizontalAlignment(SwingConstants.CENTER);
		lblSending.setForeground(Color.WHITE);
		lblSending.setFont(new Font("Arial", Font.BOLD, 16));
		panel_4.add(lblSending);
		
		JPanel panel_center = new JPanel();
		panel.add(panel_center, BorderLayout.CENTER);
		panel_center.setLayout(new GridLayout(0, 1, 0, 0));
		panel_center.setBorder(BorderFactory.createEmptyBorder(10,30, 10, 30));
		
		JLabel lblNewLabel = new JLabel("Sending FROM:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_center.add(lblNewLabel);
		
		tAddressFrom = new JTextField();
		tAddressFrom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tAddressFrom.setEditable(false);
		tAddressFrom.setBackground(new Color(255,255,200));
		panel_center.add(tAddressFrom);
		tAddressFrom.setColumns(10);
		tAddressFrom.setText(sendingFrom);
		
		JLabel lblNewLabel_1 = new JLabel("Available "+JScreenToken.selectedToken+":");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel_center.add(lblNewLabel_1);
		
		tAvailableTOKEN = new JTextField();
		tAvailableTOKEN.setFont(new Font("Tahoma", Font.BOLD, 15));
		tAvailableTOKEN.setEditable(false);
		tAvailableTOKEN.setBackground(new Color(255,255,200));
		panel_center.add(tAvailableTOKEN);
		tAvailableTOKEN.setColumns(10);
		tAvailableTOKEN.setText(JScreenWallet.ConvertWeiToADK(availableTOKENinWEI)+" "+JScreenToken.selectedToken);
		
		lblfeeInfo = new JLabel("fee info");
		lblfeeInfo.setForeground(Color.GRAY);
		panel_center.add(lblfeeInfo);
		
		JLabel lblNewLabel_2 = new JLabel("Send To (0x address / Receiver):");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		panel_center.add(lblNewLabel_2);
		
		txtSendTo = new JTextField();
		txtSendTo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_center.add(txtSendTo);
		txtSendTo.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel(JScreenToken.selectedToken+" Amount to Send:");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		panel_center.add(lblNewLabel_3);
		
		JPanel panel_send = new JPanel();
		panel_center.add(panel_send);
		panel_send.setLayout(new BorderLayout(0, 0));
		
		tAmount = new JTextField();
		panel_send.add(tAmount, BorderLayout.CENTER);
		tAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tAmount.setColumns(10);
		
		JButton btnMaxButton = new JButton("(max)");
		btnMaxButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tAmount.setText(JScreenWallet.ConvertWeiToADK(availableWei+""));
			}
		});
		panel_send.add(btnMaxButton, BorderLayout.EAST);
		
		rdbtnUseGas = new JRadioButton("Pay GAS (instant, ~0.09 ADK fee)");
//		rdbtnUseGas.addChangeListener(new ChangeListener() {
//			public void stateChanged(ChangeEvent e) {
//				UpdateAvailable();
//			}
//		});
		panel_center.add(rdbtnUseGas);
		rdbtnUseGas.setSelected(false);
		rdbtnUseGas.setSelected(true);
		rdbtnUseGas.setEnabled(false);
		
		ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnUseGas);
		
		JPanel panel_3 = new JPanel();
		panel_center.add(panel_3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createEmptyBorder(0,30, 0, 30));
		panel_center.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 3, 0, 0));
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Flow.DoAction("CANCELSENDTOKEN", JScreenSendToken.this, null);
			}
		});
		panel_1.add(btnCancel);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
	    
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// checks
				BigDecimal sendADK = BigDecimal.ZERO;
				String destinationAddress = "";
				try {
					sendADK = new BigDecimal(tAmount.getText().toUpperCase().replace(JScreenToken.selectedToken, "").trim());
					tAmount.setText(sendADK.toPlainString());
					BigDecimal d18 = new BigDecimal("1000000000000000000");
					BigInteger sendADKinWEI = sendADK.multiply(d18).toBigInteger();
					if (sendADKinWEI.compareTo(BigInteger.ZERO) < 0  ||
							sendADKinWEI.compareTo(availableWei) > 0 
							) {
						Flow.ShowErrorMessage("The amount entered is not between "+
								JScreenWallet.ConvertWeiToADK(BigInteger.ZERO+"")+" "+JScreenToken.selectedToken+" and "+
								JScreenWallet.ConvertWeiToADK(availableWei+"")+ " "+JScreenToken.selectedToken);
						return;
					}
				} catch (Exception e1) {
					Flow.ShowErrorMessage("Invalid amount entered.");
					return;
				}
				
				try {
					destinationAddress = txtSendTo.getText().trim();
					if (!destinationAddress.matches("^0x[a-fA-F0-9]{40}$")){
						Flow.ShowErrorMessage("The destination address "+destinationAddress+" is not a valid address (0x format)");
						return;
					}
					
				} catch (Exception e1) {
					Flow.ShowErrorMessage("Invalid destination address (0x format) entered.");
					return;
				}
				//
				// one last check
				if (useGas && sendADK.compareTo(BigDecimal.ZERO)==0) {
					Flow.ShowErrorMessage("You cannot send ZERO value transactions.");
					return;
				}
				// if we get here all is ok
				
				Flow.DoAction("DOSENDTOKEN", JScreenSendToken.this, new String[] {
						"gas", // gas
						sendingFrom.trim(),
						destinationAddress,
						sendADK.toPlainString()
				});
				
			}
			
		});
		panel_1.add(btnSend);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5, BorderLayout.EAST);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JTextArea txtrSendingAdkSending = new JTextArea();
		txtrSendingAdkSending.setFont(new Font("Arial", Font.PLAIN, 13));
		txtrSendingAdkSending.setEnabled(false);
		txtrSendingAdkSending.setEditable(false);
		txtrSendingAdkSending.setLineWrap(true);
		txtrSendingAdkSending.setWrapStyleWord(true);
		txtrSendingAdkSending.setText("Sending akToken:\r\n\r\nPlease note, Token can only be sent using ADK as gas. You need to have enough ADK on the sending address in order to cover the Token transaction fee.");
		txtrSendingAdkSending.setColumns(20);
		txtrSendingAdkSending.setBorder(BorderFactory.createEmptyBorder(10,10, 10, 10));
		JScrollPane scrollPane = new JScrollPane(txtrSendingAdkSending);
		panel_5.add(scrollPane);
		
		UpdateAvailable();
	}

	public JRadioButton getRdbtnUseGas() {
		return rdbtnUseGas;
	}
	public JLabel getLblfeeInfo() {
		return lblfeeInfo;
	}
	public JTextField getTxtSendTo() {
		return txtSendTo;
	}
}
