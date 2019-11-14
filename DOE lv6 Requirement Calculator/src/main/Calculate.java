package main;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculate extends JButton implements ActionListener{
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		
		/* voltType index key
		 * 0: Single-Voltage Ac-Dc, Basic-Voltage
		 * 1: Single-Voltage Ac-Dc, Low-Voltage
		 * 2: Single-Voltage Ac-Ac, Basic-Voltage
		 * 3: Single-Voltage Ac-Ac, Low-Voltage
		 * 4: Multiple-Voltage
		 */
		
		int voltType = Frame.voltType.getSelectedIndex(), range;
		float Pout = Float.parseFloat(Frame.outPower.getText());
		
		// creates array of equations
		double minEff[][] = {
				{(0.5*Pout+0.16), (0.071*Math.log(Pout)-0.0014*Pout+0.67), (0.880), (0.875)},
				{(0.517*Pout+0.087), (0.0834*Math.log(Pout)-0.0014*Pout+0.609), (0.870), (0.875)},
				{(0.517*Pout+0.087), (0.0834*Math.log(Pout)-0.0014*Pout+0.609), (0.870), (0.875)},
				{(0.5*Pout+0.16), (0.071*Math.log(Pout)-0.0014*Pout+0.67), (0.880), (0.875)},
				{(0.497*Pout+0.067), (0.075*Math.log(Pout)+0.561), (0.860), (0.860)},
		};
		
		
		// creates array of max power in no-load mode
		double maxPow[][] = {
				{0.100, 0.100, 0.210, 0.500},
				{0.100, 0.100, 0.210, 0.500},
				{0.210, 0.210, 0.210, 0.500},
				{0.210, 0.210, 0.210, 0.500},
				{0.300, 0.300, 0.300, 0.300},
		};
		
		/* find range index of the output power
		 * 0: Pout <= 1
		 * 1: 1W < Pout <= 49W
		 * 2: 49W < Pout <= 250W
		 * 3: Pout > 250W
		 */
		if (Pout <= 1) {
			range = 0;
		}
		else if (Pout <= 49) {
			range = 1;
		}
		else if (Pout <= 250) {
			range = 2;
		}
		else {
			range = 3;
		}
		
		Frame.minEff.setText(String.format("%.1f%%", 100 * minEff[voltType][range]));
		Frame.maxPower.setText(String.format("%.3f W", maxPow[voltType][range]));
	}

}
