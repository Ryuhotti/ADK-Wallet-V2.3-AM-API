package com.aidoskuneen.wallet;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.SystemColor;
import java.awt.FlowLayout;
import java.awt.Dimension;

public class JScreenToken extends JPanel {
	private JTable table;
	
	public static String selectedToken = "akFCC";
	
	//private JTable table;

	/**
	 * Create the panel.
	 */
	
	static Font tableDefaultFont = new Font("Courier New", Font.PLAIN, 15);
	private JButton btnTokenSend;
	private JComboBox comboBoxToken;
	private JButton btnSendXchain;
	
	public JScreenToken(String data[][]) {
		setLayout(new BorderLayout(0, 0));
		
		String headers[] = { "Id", "Address", " "+selectedToken+" ", "Balance"};
        
        String data_withTotal[][] = new String[data.length+1][headers.length];
        
        BigInteger btotal = BigInteger.ZERO;
        BigInteger btotal2 = BigInteger.ZERO;
        for (int row = 0; row < data.length; row++) {
        	data_withTotal[row+1][0] = data[row][0];
        	data_withTotal[row+1][1] = data[row][1]; //address
        	data_withTotal[row+1][2] = selectedToken==null?"Select":selectedToken;
        	if (selectedToken!=null && selectedToken.contains("akFCC")) {
        		data_withTotal[row+1][3] = data[row][2];
        		btotal = btotal.add(new BigInteger(data[row][2]));
        	}
        	if (selectedToken!=null && selectedToken.contains("akBTC")) {
        		data_withTotal[row+1][3] = data[row][3];
        		btotal = btotal.add(new BigInteger(data[row][3]));
        	}
        }
        data_withTotal[0][0] = "";
        data_withTotal[0][1] = "Address";
        data_withTotal[0][2] = selectedToken;
        data_withTotal[0][3] = btotal.toString(10);
        
        
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_left = new JPanel();
		panel.add(panel_left, BorderLayout.WEST);
		panel_left.setLayout(new GridLayout(0, 1, 0, 0));
		
		panel_left.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		btnTokenSend = new JButton("Send");
		btnTokenSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() > 0) {
					String address = table.getValueAt(table.getSelectedRow(),1).toString();
					String available_amount = table.getValueAt(table.getSelectedRow(),3).toString();
					if (address.startsWith("0x")) {
						Flow.DoAction("SENDTOKEN", JScreenToken.this, new String[] {address, available_amount});
					}
				}
			}
		});
		panel_left.add(btnTokenSend);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Flow.DoAction("REFRESHTOKEN", JScreenToken.this, new String[] {comboBoxToken.getSelectedItem().toString()});
				
			}
		});
		
		btnSendXchain = new JButton("<html>Send<br>X-Chain</html>");
		btnSendXchain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() > 0) {
					String address = table.getValueAt(table.getSelectedRow(),1).toString();
					//String available_amount = table.getValueAt(table.getSelectedRow(),4).toString();
					if (address.startsWith("0x")) {
						//Flow.DoAction("TOKENSEND", JScreenToken.this, new String[] {address, available_amount});
						JOptionPane.showMessageDialog(JScreenToken.this, "Note: Token X-Chain Transfer will be enabled soon (Q3/Q4)\n\n. Please refer to Aidos Kuneen Telegram (Developer)", "",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		panel_left.add(btnSendXchain);
		panel_left.add(btnRefresh);
		
		JButton btnBack = new JButton("<html>Back to<br>Wallet</html>");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Flow.DoAction("REFRESH", JScreenToken.this, new String[] {});
			}
		});
		panel_left.add(btnBack);
		
		JPanel panel_0 = new JPanel();
		//tabbedPane.addTab("Wallet", null, panel_0, null);
		panel_0.setLayout(new BorderLayout(0, 0));
		panel.add(panel_0);
		//table = new JTable();
		table = new JTable(){
		    @Override
		       public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		           Component component = super.prepareRenderer(renderer, row, column);
		           int rendererWidth = component.getPreferredSize().width;
		           TableColumn tableColumn = getColumnModel().getColumn(column);
		           tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
		           return component;
		        }
		    };
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		final TableModel tm = new MyTableModelV3(data_withTotal, headers);
		table.setModel(tm);
		CustomRendererV3 cr = new CustomRendererV3 ();
		table.setDefaultRenderer(Object.class, cr);
		table.setRowHeight(table.getRowHeight()*2);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		
		//table.getColumnModel().getColumn(1).setMinWidth(400);
		table.setFont(tableDefaultFont);
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent lse) {
		        updateButtons();
		    }
		});
		
		table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount()==2) {
                	try {
						String val = (String) tm.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
						if (table.getSelectedColumn() == 2) {
							val = ConvertWeiToADK(val);
						}
						StringSelection stringSelection = new StringSelection(val);
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						clipboard.setContents(stringSelection, null);
						JOptionPane.showMessageDialog(Flow.mainFrame, "Copied to clipboard:\n\n"+val);
					} catch (Exception e1) {
						//e1.printStackTrace();
					}
                }
                updateButtons();
            }
        });
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_0.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_0.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_0.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(SystemColor.activeCaption);
		panel_2.add(panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_1 = new JLabel("Select Token: ");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_5.add(lblNewLabel_1);
		
		comboBoxToken = new JComboBox();
		panel_5.add(comboBoxToken);
		comboBoxToken.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBoxToken.setModel(new DefaultComboBoxModel(new String[] {"akFCC", "akBTC"}));
		
		if (selectedToken!=null && selectedToken.contains("akFCC")) {
			comboBoxToken.setSelectedIndex(0);
		} 
		else if (selectedToken!=null && selectedToken.contains("akBTC")) {
			comboBoxToken.setSelectedIndex(1);
		} 
		else {
			comboBoxToken.setSelectedIndex(0);
		}

		comboBoxToken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedToken = comboBoxToken.getSelectedItem().toString();
				Flow.DoAction("REFRESHTOKEN", JScreenToken.this, new String[] {comboBoxToken.getSelectedItem().toString()});
			}
		});

		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(SystemColor.desktop);
		panel.add(panel_4, BorderLayout.NORTH);
		
		JLabel lbltitle = new JLabel(!selectedToken.equals("SELECT TOKEN")?selectedToken + " Token":"Please Select Token");
		lbltitle.setForeground(Color.WHITE);
		lbltitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbltitle.setFont(new Font("Arial", Font.BOLD, 16));
		panel_4.add(lbltitle);
		updateButtons();
	 }

	static boolean NumbersOnly(String check) {
		if (check==null || check.equals("")) return false;
		for (char ch : check.toCharArray()) {
			if (ch < '0' || ch > '9') return false;
		}
		return true;
	}
	
	public static String ConvertWeiToADK ( String wei ) {
		String wei_working = wei;
		if (!NumbersOnly(wei)) {
			wei_working = "0";
		} 
		BigDecimal f = new BigDecimal(wei_working);
		BigDecimal f18 = new BigDecimal("0.000000000000000001");
		BigDecimal adk = f.multiply(f18);
		String adkstr = adk.toPlainString();
		if (!adkstr.contains(".")) adkstr += ".0";
		while (true) {
			char lastchar = adkstr.charAt(adkstr.length()-1);
			char secondlastchar = adkstr.charAt(adkstr.length()-2);
			if (lastchar >= '1' && lastchar <= '9') break;
			if (lastchar == '0' && secondlastchar != '.') {
				adkstr = adkstr.substring(0,adkstr.length()-1);
				continue;
			}
			break;
		}
		return adkstr;
	}
//	
//	public JButton getBtnSend() {
//		return btnSend;
//	}
//
	void updateButtons() {
		    if (table.getSelectedRow()>0 && JScreenToken.selectedToken != null 
		    		&& JScreenToken.selectedToken.toString().length()==5 ) {
            	this.getBtnSend().setEnabled(true);
            	this.getBtnSend().setText("Send Token");
            	this.getBtnSendXchain().setEnabled(true);
             } else {
            	this.getBtnSend().setEnabled(false);
            	this.getBtnSend().setText("<html>Send Token<br><i>no address<br>selected</i>)</html>");
            	this.getBtnSendXchain().setEnabled(false);
            }
    }
//	public JButton getMigrateBtn() {
//		return migrateBtn;
//	}
	public JButton getBtnSend() {
		return btnTokenSend;
	}

	public JComboBox getComboBoxToken() {
		return comboBoxToken;
	}
	public JButton getBtnSendXchain() {
		return btnSendXchain;
	}
}

class MyTableModelV3 extends DefaultTableModel {

	   public MyTableModelV3(Object[][] tableData, Object[] colNames) {
	      super(tableData, colNames);
	   }

	   public boolean isCellEditable(int row, int column) {
	      return false;
	   }
}


class CustomRendererV3 extends DefaultTableCellRenderer 
{

	
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
		String newValue = (String)value;
//        if (column == 2) {
//        	// show value as ADK
//        	newValue = JScreenToken.ConvertWeiToADK((String)newValue);
//        	int indexofDot = newValue.toString().indexOf('.');
//        	while (indexofDot != -1 && indexofDot < 8 ) {
//        		newValue = " "+newValue.toString();
//        		indexofDot = newValue.toString().indexOf('.');
//        	}
//        }
        if (column == 3) {
        	// show value as ADK
        	newValue = JScreenToken.ConvertWeiToADK((String)newValue);
        	int indexofDot = newValue.toString().indexOf('.');
        	while (indexofDot != -1 && indexofDot < 8 ) {
        		newValue = " "+newValue.toString();
        		indexofDot = newValue.toString().indexOf('.');
        	}
        }
        newValue = " "+newValue+" ";
        Component c = super.getTableCellRendererComponent(table, newValue, isSelected, hasFocus, row, column);
        if (c instanceof CustomRendererV3) {
        	CustomRendererV3 cr = (CustomRendererV3) c;
        	
        	// default 
        	cr.setForeground(Color.BLACK);
        	cr.setBackground(Color.WHITE);
        	cr.setFont(JScreenToken.tableDefaultFont);
        	
        	if (column == 3 && value != null && ((String)value).equals("0")) {
        		cr.setForeground(Color.GRAY);
        		cr.setFont(new Font(cr.getFont().getFontName(), Font.ITALIC, cr.getFont().getSize()));
        	}
        	
        	if (column == 0)
        		cr.setHorizontalAlignment(cr.CENTER);
        	
        	if (row == 0) {
        		cr.setFont(new Font(cr.getFont().getFontName(), Font.BOLD, cr.getFont().getSize()+2));
        		cr.setBackground(new Color(169,179,211));
        	}
        	
        	if (row == table.getSelectedRow() && row > 0) {
        		cr.setBackground(new Color(255,255,211));
        	}
        	
        }
        
        
//        if (row == 1)
//          c.setBackground(new java.awt.Color(255, 72, 72));
//        else 
//        	c.setBackground(new java.awt.Color(255, 255, 255));
        
        return c;
    }
}

