/**
 * 
 */
package com.ruse;

import com.ruse.javabase.Game;
import com.ruse.javabase.JavaBase;
import com.ruse.javabase.graphics.Art;
import com.ruse.javabase.graphics.Display;

public class Chip8Base extends Game {

	private static final long serialVersionUID = 1L;

	public Chip8Base(String pWindowTitle) {
		super(pWindowTitle);

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
		
		display().draw(Art.items, 128 + 8, (int)(5 + Math.sin(time * 0.01f) * 5), 8, 0, 8, 8, 1, 128);
		display().draw(Art.items, 128 + 16, (int)(5 + Math.sin((time + 50) * 0.01f) * 5), 8, 0, 8, 8, 1, 255);
		display().draw(Art.items, 128 + 24, (int)(5 + Math.sin((time + 100) * 0.01f) * 5), 8, 0, 8, 8, 1, 255);
		
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
