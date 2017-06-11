package com.ruse.emu.util;

import com.ruse.emu.chip.Chip8;
import com.ruse.javabase.graphics.Bitmap;

/**
 *
 * This {@link Chip8Display} reads the display array of the {@link Chip8}
 * instance passed in, and displays it into the JFrame (display array is
 * typically 64*32 bytes).
 * 
 * This class acquires a lock on the {@link Chip8.display()} during
 * {@link Chip8Display.draw()}
 *
 */
public class Chip8Display {

	// ---------------------------------------
	// Variables
	// ---------------------------------------

	private Chip8 mChip8;
	private Bitmap mSurface;
	private byte[] mChip8DisplayArray;
	private int mPositionX;
	private int mPositionY;
	private int mScale = 1;

	private int mBackgroundColor = 0x000000C0;
	private int mForegroundColor = 0x00F0F000;

	// ---------------------------------------
	// Properties
	// ---------------------------------------

	// ---------------------------------------
	// Constructor
	// ---------------------------------------

	public Chip8Display(Chip8 pChip) {
		// TODO: Need to allow for properly reseting the display (when the chip8
		// is reset).
		mSurface = new Bitmap(64, 32);
		mSurface.clear(mBackgroundColor);
		mScale = 1;

		mChip8 = pChip;
		mChip8DisplayArray = pChip.display();

	}

	// ---------------------------------------
	// Core-Methods
	// ---------------------------------------

	public void draw(Bitmap pBitmap) {
		if(!mChip8.dirtyDisplay()) return;
		
		// First clear the emulator window
		mSurface.clear(mBackgroundColor);

		synchronized (mChip8DisplayArray) { // lock display array before access
			// Set the chip8 display bits into our bitmap
			for (int i = 0; i < mChip8DisplayArray.length; i++) {
				if (mChip8DisplayArray[i] != (byte) 0)
					mSurface.pixels[i] = mForegroundColor;
			}

		}

		// Then copy our bitmap into the JavaFrame display
		pBitmap.draw(mSurface, mPositionX, mPositionY, 0, 0, mSurface.width, mSurface.height, mScale);
	}

	// ---------------------------------------
	// Methods
	// ---------------------------------------

	public void setScale(int pScale) {
		if (pScale < 0)
			pScale = 1;
		if (pScale > 10) // 640x320
			pScale = 10;

		mScale = pScale;

	}

	public void setPosition(int pX, int pY) {
		mPositionX = pX;
		mPositionY = pY;

	}

}
