/**
 * 
 */
package com.ruse;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ruse.javabase.Game;
import com.ruse.javabase.JavaBase;

/**
 * @author Lintford Pickle
 *
 */
public class Chip8Base {

	// ---------------------------------------
	// Entry Point
	// ---------------------------------------

	public static void main(String[] args) {
		JavaBase lBase = new Game();

		JFrame lFrame = new JFrame("Test");

		JPanel lPanel = new JPanel(new BorderLayout());
		lPanel.add(lBase, BorderLayout.CENTER);

		lFrame.setContentPane(lPanel);
		lFrame.pack();
		lFrame.setLocationRelativeTo(null);
		lFrame.setResizable(true);
		lFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lFrame.setVisible(true);

		lBase.start();

	}

}
