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

public class JScreenAPI extends JPanel {
	private JTable table;
	
	//private JTable table;

	/**
	 * Create the panel.
	 */
	
	static Font tableDefaultFont = new Font("Courier New", Font.PLAIN, 11);
	private JButton btnTokenSend;
	private JButton btnSendXchain;
	private JButton btnsendADK;
	private JButton btnsendFccTo;
	private JButton btnsendBTC;
	
	public JScreenAPI(String data[][]) {
		setLayout(new BorderLayout(0, 0));
		
		String headers[] = { "Id", "Address", "ADK", "FCC Token", "BTC Token"};
        
        String data_withTotal[][] = new String[data.length+1][headers.length];
        
        BigInteger btotal = BigInteger.ZERO;
        BigInteger btotal2 = BigInteger.ZERO;
        BigInteger btotal3 = BigInteger.ZERO;
        for (int row = 0; row < data.length; row++) {
        	data_withTotal[row+1][0] = data[row][0];
        	data_withTotal[row+1][1] = data[row][1]; //address
       		data_withTotal[row+1][2] = data[row][2];
       		btotal = btotal.add(new BigInteger(data[row][2]));
       		data_withTotal[row+1][3] = data[row][3];
       		btotal2 = btotal2.add(new BigInteger(data[row][3]));
       		data_withTotal[row+1][4] = data[row][4];
       		btotal3 = btotal3.add(new BigInteger(data[row][4]));
        }
        data_withTotal[0][0] = "";
        data_withTotal[0][1] = "Totals";
        data_withTotal[0][2] = btotal.toString(10);
        data_withTotal[0][3] = btotal2.toString(10);
        data_withTotal[0][4] = btotal3.toString(10);
        
        
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_left = new JPanel();
		panel.add(panel_left, BorderLayout.WEST);
		panel_left.setLayout(new GridLayout(0, 1, 0, 0));
		
		panel_left.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		btnTokenSend = new JButton("<html>AM API<br>Config</html>");
		btnTokenSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						Flow.DoAction("SHOWAPICONFIG", JScreenAPI.this, new String[] {});
			}
		});
		panel_left.add(btnTokenSend);
		
		JButton btnBack = new JButton("<html>Back to<br>Wallet</html>");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Flow.DoAction("REFRESH", JScreenAPI.this, new String[] {});
			}
		});
		
		btnsendFccTo = new JButton("FCC Transfer");
		btnsendFccTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() > 0) {
					String address = table.getValueAt(table.getSelectedRow(),1).toString();
					//String available_amount = table.getValueAt(table.getSelectedRow(),4).toString();
					if (address.startsWith("0x")) {
						//Flow.DoAction("TOKENSEND", JScreenToken.this, new String[] {address, available_amount});
						JOptionPane.showMessageDialog(JScreenAPI.this, "Note: Direct AM Transfers will be enabled (Q3/Q4 2023)\n\n. Please refer to Aidos Kuneen Telegram (Developer)", "",JOptionPane.INFORMATION_MESSAGE);
					}
				}
				//Flow.DoAction("AMTRANSFERFCC", JScreenAPI.this, new String[] {});
			}
		});
		
		JLabel lblNewLabel = new JLabel("<html>Transfer<br>from/to AM:</html>");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_left.add(lblNewLabel);
		
		btnsendADK = new JButton("ADK Transfer");
		btnsendADK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() > 0) {
					String address = table.getValueAt(table.getSelectedRow(),1).toString();
					//String available_amount = table.getValueAt(table.getSelectedRow(),4).toString();
					if (address.startsWith("0x")) {
						//Flow.DoAction("TOKENSEND", JScreenToken.this, new String[] {address, available_amount});
						JOptionPane.showMessageDialog(JScreenAPI.this, "Note: Direct AM Transfers will be enabled (Q3/Q4 2023)\n\n. Please refer to Aidos Kuneen Telegram (Developer)", "",JOptionPane.INFORMATION_MESSAGE);
					}
				}

			}
		});
		panel_left.add(btnsendADK);
		panel_left.add(btnsendFccTo);
		
		btnsendBTC = new JButton("BTC Transfer");
		btnsendBTC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() > 0) {
					String address = table.getValueAt(table.getSelectedRow(),1).toString();
					//String available_amount = table.getValueAt(table.getSelectedRow(),4).toString();
					if (address.startsWith("0x")) {
						//Flow.DoAction("TOKENSEND", JScreenToken.this, new String[] {address, available_amount});
						JOptionPane.showMessageDialog(JScreenAPI.this, "Note: Direct AM Transfers will be enabled (Q3/Q4 2023)\n\n. Please refer to Aidos Kuneen Telegram (Developer)", "",JOptionPane.INFORMATION_MESSAGE);
					}
				}

			}
		});
		panel_left.add(btnsendBTC);
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
		final TableModel tm = new MyTableModelV4(data_withTotal, headers);
		table.setModel(tm);
		CustomRendererV4 cr = new CustomRendererV4 ();
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
		

		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(SystemColor.desktop);
		panel.add(panel_4, BorderLayout.NORTH);
		
		updateButtons();
	 }
	
	void updateButtons() {
	    if (table.getSelectedRow()>0 && JScreenToken.selectedToken != null 
	    		&& JScreenToken.selectedToken.toString().length()==5 ) {
        	btnsendBTC.setEnabled(true);
        	btnsendADK.setEnabled(true);
        	btnsendFccTo.setEnabled(true);
        	
         } else {
        	 btnsendBTC.setEnabled(false);
         	btnsendADK.setEnabled(false);
         	btnsendFccTo.setEnabled(false);
        }
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
	
//	public JButton getMigrateBtn() {
//		return migrateBtn;
//	}
	public JButton getBtnSend() {
		return btnTokenSend;
	}

	public JButton getBtnSendXchain() {
		return btnSendXchain;
	}
	public JButton getBtnsendADK() {
		return btnsendADK;
	}
	public JButton getBtnsendFccTo() {
		return btnsendFccTo;
	}
	public JButton getBtnsendBTC() {
		return btnsendBTC;
	}
}

class MyTableModelV4 extends DefaultTableModel {

	   public MyTableModelV4(Object[][] tableData, Object[] colNames) {
	      super(tableData, colNames);
	   }

	   public boolean isCellEditable(int row, int column) {
	      return false;
	   }
}


class CustomRendererV4 extends DefaultTableCellRenderer 
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
        if (column >= 2) {
        	// show value as ADK
        	newValue = JScreenAPI.ConvertWeiToADK((String)newValue);
        	int indexofDot = newValue.toString().indexOf('.');
        	while (indexofDot != -1 && indexofDot < 8 ) {
        		newValue = " "+newValue.toString();
        		indexofDot = newValue.toString().indexOf('.');
        	}
        }
        newValue = " "+newValue+" ";
        Component c = super.getTableCellRendererComponent(table, newValue, isSelected, hasFocus, row, column);
        if (c instanceof CustomRendererV4) {
        	CustomRendererV4 cr = (CustomRendererV4) c;
        	
        	// default 
        	cr.setForeground(Color.BLACK);
        	cr.setBackground(Color.WHITE);
        	cr.setFont(JScreenAPI.tableDefaultFont);
        	
        	if (column >= 2 && value != null && ((String)value).equals("0")) {
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

