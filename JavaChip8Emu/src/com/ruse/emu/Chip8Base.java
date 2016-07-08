/**
 * 
 */
package com.ruse.emu;

import com.ruse.emu.chip.Chip8;
import com.ruse.emu.chip.Chip8Display;
import com.ruse.emu.chip.Chip8Thread;
import com.ruse.javabase.Game;
import com.ruse.javabase.graphics.Art;

public class Chip8Base extends Game implements Runnable {

	private static final long serialVersionUID = 1L;

	// ---------------------------------------
	// Variables
	// ---------------------------------------

	private Chip8 mChip; // The Chip-8 CPU
	private Chip8Thread mChip8Thread;
	private Chip8Display mChipDisplay;

	// ---------------------------------------
	// Properties
	// ---------------------------------------

	public Chip8Base(String pWindowTitle) {
		super(pWindowTitle);

		mChip = new Chip8();
		mChip.initialise();

		mChip8Thread = new Chip8Thread(mChip);
		mChip8Thread.start();

		mChipDisplay = new Chip8Display(mChip);
		mChipDisplay.setPosition(5, 30);
		mChipDisplay.setScale(10);

	}

	// ---------------------------------------
	// Core-Methods
	// ---------------------------------------

	private float time;

	@Override
	protected void update() {
		super.update();

		display().clear(0x00000000);

		display().drawString(Art.font, "Chip-8 Emulator", 5, 5, 128);

		time++;

		display().draw(Art.items, 128 + 8, (int) (5 + Math.sin(time * 0.01f) * 5), 8, 0, 8, 8, 1);
		display().draw(Art.items, 128 + 16, (int) (5 + Math.sin((time + 50) * 0.01f) * 5), 8, 0, 8, 8, 1);
		display().draw(Art.items, 128 + 24, (int) (5 + Math.sin((time + 100) * 0.01f) * 5), 8, 0, 8, 8, 1);

		if(mChip.dirtyDisplay()){
			mChipDisplay.draw(display());
			
		}

	}

	// ---------------------------------------
	// Entry Point
	// ---------------------------------------

	public static void main(String[] args) {
		Chip8Base lBase = new Chip8Base("chip-8 emu");
		lBase.openWindow();

	}

}
