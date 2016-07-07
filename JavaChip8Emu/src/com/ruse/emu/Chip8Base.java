/**
 * 
 */
package com.ruse.emu;

import com.ruse.emu.chip.Chip;
import com.ruse.emu.chip.Chip8Display;
import com.ruse.javabase.Game;
import com.ruse.javabase.JavaBase;
import com.ruse.javabase.graphics.Art;
import com.ruse.javabase.graphics.Display;

public class Chip8Base extends Game {

	private static final long serialVersionUID = 1L;

	// ---------------------------------------
	// Variables
	// ---------------------------------------

	private Chip mChip; // The Chip-8 CPU
	private Chip8Display mChipDisplay;

	// ---------------------------------------
	// Properties
	// ---------------------------------------

	public Chip8Base(String pWindowTitle) {
		super(pWindowTitle);

		mChip = new Chip();
		mChip.initialise();

		mChipDisplay = new Chip8Display();
		mChipDisplay.initialise(mChip);
		mChipDisplay.setPosition(5, 30);
		mChipDisplay.setScale(10);

		// Start the chip running
		mChip.run();

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

		display().draw(Art.items, 128 + 8, (int) (5 + Math.sin(time * 0.01f) * 5), 8, 0, 8, 8, 1, 128);
		display().draw(Art.items, 128 + 16, (int) (5 + Math.sin((time + 50) * 0.01f) * 5), 8, 0, 8, 8, 1, 255);
		display().draw(Art.items, 128 + 24, (int) (5 + Math.sin((time + 100) * 0.01f) * 5), 8, 0, 8, 8, 1, 255);

		mChipDisplay.draw(display());

	}

	@Override
	public void render(Display display) {
		super.render(display);

	}

	// ---------------------------------------
	// Entry Point
	// ---------------------------------------

	public static void main(String[] args) {
		JavaBase lBase = new Chip8Base("chip-8 emu");
		lBase.openWindow();

	}

}
