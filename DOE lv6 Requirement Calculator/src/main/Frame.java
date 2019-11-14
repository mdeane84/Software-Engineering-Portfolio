package main;

import javax.swing.*;
import javax.swing.border.*;

public class Frame extends JFrame {
	
	// creates panes and panels
	JPanel mainPanel = new JPanel();
	static JComboBox voltType = new JComboBox();
	static JTextField outPower = new JTextField();
	static JLabel maxPower = new JLabel();
	static JLabel minEff = new JLabel();

	
	public static void main(String[] args) {
		Frame frame = new Frame();
		frame.setVisible(true);
	}

	public Frame() {
		super("DOE lv6 Efficiency Calculator Rev01");
		
		// create action listener
		Calculate listener = new Calculate();
		
		// define frame
		setSize(425, 225);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// define borders
		Border etched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder inBorder = BorderFactory.createTitledBorder(etched, "Input");
		TitledBorder outBorder = BorderFactory.createTitledBorder(etched, "Output");
		TitledBorder descBorder = BorderFactory.createTitledBorder(etched, "Description");
		inBorder.setTitleJustification(TitledBorder.LEFT);
		outBorder.setTitleJustification(TitledBorder.LEFT);
		descBorder.setTitleJustification(TitledBorder.LEFT);
		
		// voltage type label
		JLabel voltTypeLabel = new JLabel();
		voltTypeLabel.setText("Voltage Type");
		voltTypeLabel.setLocation(15, 65);
		voltTypeLabel.setSize(100, 20);
		
		// voltage type drop down box
		// JComboBox voltType
		voltType.setLocation(15, 85);
		voltType.setSize(250, 20);
		voltType.setEditable(true);
		voltType.setMaximumRowCount(5);
		voltType.addItem("Single-Voltage Ac-Dc, Basic-Voltage");
		voltType.addItem("Single-Voltage Ac-Dc, Low-Voltage");
		voltType.addItem("Single-Voltage Ac-Ac, Basic-Voltage");
		voltType.addItem("Single-Voltage Ac-Ac, Low-Voltage");
		voltType.addItem("Multiple-Voltage Power Supply");
		
		// description first line
		JLabel description1 = new JLabel();
		description1.setText("Calculates minimum efficiency according to DOE lv6 requirements.");
		description1.setBounds(15, 10, 400, 40);
		
		// description second line
		JLabel description2 = new JLabel();
		description2.setText("Calculations are for external power supplies.");
		description2.setBounds(15, 30, 400, 40);
		
		// output power label
		JLabel outPowerLabel = new JLabel();
		outPowerLabel.setText("Output Power (W)");
		outPowerLabel.setLocation(15,20);
		outPowerLabel.setSize(125, 20);
		
		// output power text box
		// JTextFiled outPower
		outPower.setLocation(15, 40);
		outPower.setSize(100, 20);
		
		// minimum efficiency OUTPUT
		JLabel minEffLabel = new JLabel();
		minEffLabel.setText("Minimum Efficiency");
		minEffLabel.setBounds(10, 20, 120, 20);
		// JLabel minEff
		minEff.setText("0.0%");
		minEff.setBounds(10, 35, 120, 20);
		
		// max power in no-load mode OUTPUT
		JLabel maxPowerLabel1 = new JLabel();
		maxPowerLabel1.setText("Maximum Power");
		maxPowerLabel1.setBounds(10, 60, 120, 20);
		JLabel maxPowerLabel2 = new JLabel();
		maxPowerLabel2.setText("(No-Load Mode)");
		maxPowerLabel2.setBounds(10, 75, 120, 20);
		// JLabel maxPower
		maxPower.setText("0.000 W");
		maxPower.setBounds(10, 90, 120, 20);
		
		// calculate button
		JButton calc = new JButton();
		calc.setText("Calculate");
		calc.setBounds(145, 25, 120, 40);
		calc.addActionListener(listener);
		
		// description pane
		JLayeredPane descPane = new JLayeredPane();
		descPane.setBorder(descBorder);
		descPane.setBounds(0, 0, 420, 75);
		descPane.add(description1);
		descPane.add(description2);
		
		// input pane
		JLayeredPane inPane = new JLayeredPane();
		inPane.setBorder(inBorder);
		inPane.setBounds(0, 75, 285, 120);
		inPane.add(voltType);
		inPane.add(voltTypeLabel);
		inPane.add(outPowerLabel);
		inPane.add(outPower);
		inPane.add(calc);
		
		// output pane
		JLayeredPane outPane = new JLayeredPane();
		outPane.setBorder(outBorder);
		outPane.setBounds(285,75, 135, 120);
		outPane.add(minEffLabel);
		outPane.add(minEff);
		outPane.add(maxPowerLabel1);
		outPane.add(maxPowerLabel2);
		outPane.add(maxPower);
		
		
		// fixes layered pane issue
		JLayeredPane fix = new JLayeredPane();
		
		add(descPane);
		add(inPane);
		add(outPane);
		add(fix);

	}
}