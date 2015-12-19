package edu.tridenttech.cpt237.williams;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
/**
 * 
 * @author Alexis Williams 
 *
 */

public class MultiCabUI implements ActionListener, ItemListener
{
	private ExtendedCab cabPlus;
    private CabLoader loading;
    private ArrayList<String> listing = new ArrayList<String>();
    private JLabel error;
    	
	private JTextField gasAvailOutput = new JTextField(12);
	private JTextField milesAvailOutput = new JTextField(12);
	private JTextField milesSinceServiceOutput = new JTextField(12);
	private JTextField milesSinceResetOutput = new JTextField(12);
	private JTextField grossSinceResetOutput = new JTextField(12);
	private JTextField netSinceResetOutput = new JTextField(12);
	private JTextField amountInput = new JTextField(12);
	private JTextField gasPriceInput = new JTextField(12);
		
	private String mileage = "Record Trip";
	private String haveGas = "Add Gas";
	private String haveService = "Service Cab";
	private String haveReset = "Reset";
	
	private JButton okBtn = new JButton("OK");
    private JButton refreshBtn = new JButton("Refresh");
	
    private JComboBox<String> cabCombo;
    private JComboBox<String> actionCombo;
    
	public MultiCabUI(ArrayList<String> cabName, CabLoader cl)
	{
		listing = cabName;
		loading = cl;
		createUI();
	}
	
	public void setCab(ExtendedCab c)
	{
		cabPlus = c;
	}
	
	private void createUI()
    {
        JLabel gasAvail = new JLabel("Gas Available");
        JLabel milesAvail = new JLabel("Miles Available");
        JLabel milesSinceService = new JLabel("Miles since service");
        JLabel milesSinceReset = new JLabel("Miles since reset");
        JLabel grossSinceReset = new JLabel("Gross earnings since reset");
        JLabel netSinceReset = new JLabel("Net earnings since reset");
        
        JFrame frame = new JFrame("Acme Taxi Co.");
        JPanel pane1 = new JPanel();
        JPanel cabControlPanel = new JPanel();
        Container contentPane = frame.getContentPane();
        
        String[] cabNames = listing.toArray(new String[listing.size()]);        
        String[] actionNames = {mileage, haveGas, haveService, haveReset};

        cabCombo = new JComboBox<String>(cabNames);
        cabCombo.addItemListener(this);
        actionCombo = new JComboBox<String>(actionNames);
        actionCombo.addItemListener(this);

        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createTitledBorder("Cab Info"));
        statusPanel.setLayout(new GridLayout(0,2,5,5));
        statusPanel.add(cabCombo);
        refreshBtn.addActionListener(this);
        statusPanel.add(refreshBtn);

        // disable all of the output fields
        // western panel
        gasAvailOutput.setEnabled(false);
        milesAvailOutput.setEnabled(false);
        milesSinceServiceOutput.setEnabled(false);
        milesSinceResetOutput.setEnabled(false);
        grossSinceResetOutput.setEnabled(false);
        netSinceResetOutput.setEnabled(false);

       // eastern panel
        gasPriceInput.setEnabled(false);
        error = new JLabel("Please Try Again");
        error.setVisible(false);
        
        // add the labels and output fields in pairs
        statusPanel.add(gasAvail);
        statusPanel.add(gasAvailOutput);

        statusPanel.add(milesAvail);
        statusPanel.add(milesAvailOutput);
        
        statusPanel.add(milesSinceService);
        statusPanel.add(milesSinceServiceOutput);
        
        statusPanel.add(milesSinceReset);
        statusPanel.add(milesSinceResetOutput);
        
        statusPanel.add(grossSinceReset);
        statusPanel.add(grossSinceResetOutput);
        
        statusPanel.add(netSinceReset);
        statusPanel.add(netSinceResetOutput);
        
        // construct the second panel, place it in a secondary panel to
        // allow better sized fields
        contentPane.add(statusPanel, BorderLayout.WEST);
        JPanel p1 = new JPanel(); // dummy panel
        contentPane.add(p1, BorderLayout.EAST);
        p1.setBorder(BorderFactory.createTitledBorder("Cab Control"));
        p1.add(cabControlPanel);

        cabControlPanel.setLayout(new GridLayout(0,1,5,5));
        cabControlPanel.add(actionCombo);
        cabControlPanel.add(amountInput);
        cabControlPanel.add(gasPriceInput);
        cabControlPanel.add(error);
        JSeparator js = new JSeparator();
        js.setVisible(false);
        cabControlPanel.add(js);
        okBtn.addActionListener(this);
        cabControlPanel.add(okBtn);
              
        frame.pack();
        frame.setVisible(true);
        
	}
	
	public void show()
	{
        String name = (String) cabCombo.getSelectedItem();
        loading.loadCab(name);
	}

	public void itemStateChanged(ItemEvent e) 
	{
		String item = (String) e.getItem();
			if (e.getStateChange() == ItemEvent.SELECTED && item.equals(haveGas))
			{
				gasPriceInput.setEnabled(true);	
			}
			else
			{
				gasPriceInput.setEnabled(false);
			}
		
		String name = (String) cabCombo.getSelectedItem();
			if(e.getStateChange() == ItemEvent.SELECTED && item.equals(name))
			{
				loading.loadCab(name);		
			}
	}

	public void actionPerformed(ActionEvent e) 
	{
		JButton button = (JButton)e.getSource();
		String choice = (String) actionCombo.getSelectedItem();		
		String name = (String) cabCombo.getSelectedItem();
		
			if(button == okBtn)
			{
				itemChosen(choice);
			}
			else if(button == refreshBtn)
			{
				itemChosen(name);
			}
	}
	
	public void itemChosen(String choice)
	{
		if(choice.equals(mileage))
		{
			if(amountInput.getText().isEmpty())
			{
				error.setVisible(true);
			}	
			else if(amountInput.getText() != null)
			{
				int miles = 0;								
				miles = Integer.parseInt(amountInput.getText());

				double fare = cabPlus.recordTrip(miles);					
				error.setVisible(false);
				
					try
					{
						ActionRecorded record = ActionRecorded.getRecorder();
						String name = cabPlus.getName();
						
						record.writeRecord(name, RecordType.FARE, miles, fare);						
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				
			}//end if
		}//end if		
		else if(choice.equals(haveGas))
		{
			if(amountInput.getText().isEmpty() || gasPriceInput.getText().isEmpty())
			{
				error.setVisible(true);
			}
			else if(amountInput.getText() != null && gasPriceInput.getText() != null)
			{
				double gas = 0;
				double cost = 0;
				String gasString = amountInput.getText();
				String costString = gasPriceInput.getText();
				
				gas = Double.parseDouble(gasString);
				cost = Double.parseDouble(costString);
								
				cabPlus.addGas(gas, cost);				
				error.setVisible(false);
				
				try
				{
					ActionRecorded record = ActionRecorded.getRecorder();
					String name = cabPlus.getName();
					
					record.writeRecord(name, RecordType.GAS, gas, cost);		
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				
			}//end if
		}//end else if
		else if(choice.equals(haveService))
		{
			cabPlus.serviceCab();	
			
			double serviceMiles = cabPlus.getMilesSinceService();
			double cost = cabPlus.getService();			
			
			try
			{
				ActionRecorded record = ActionRecorded.getRecorder();
				String name = cabPlus.getName();

				record.writeRecord(name, RecordType.SERVICE, serviceMiles, cost);		
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		else if(choice.equals(haveReset))
		{
			cabPlus.reset();
		}
		else if(choice.contains("Cab") || choice.contains("cab"))
		{
			gasAvailOutput.setText(String.valueOf(cabPlus.getGasAvailable()));
	        milesAvailOutput.setText(String.valueOf(cabPlus.milesAvailable()));
	        milesSinceServiceOutput.setText(String.valueOf(cabPlus.getMilesSinceService()));
	        milesSinceResetOutput.setText(String.valueOf(cabPlus.getMilesSinceReset()));
	        grossSinceResetOutput.setText(String.format("$%.2f", cabPlus.getGrossEarnings()));
	        netSinceResetOutput.setText(String.format("$%.2f", cabPlus.getNetEarnings()));
		}		
	}
	

}//end Class
