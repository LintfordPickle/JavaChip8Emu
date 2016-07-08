package com.ruse.emu.chip;

import com.ruse.javabase.graphics.Bitmap;

// This class reads the display array of the chip8 (64*32 bytes), and
// displays it into the JFrame
public class Chip8Display {

	// ---------------------------------------
	// Variables
	// ---------------------------------------

	private Bitmap mSurface;
	private byte[] mDisplay;
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

	public Chip8Display() {
		// TODO: Need to allow for properly reseting the display (when the chip8
		// is reset).
		mSurface = new Bitmap(64, 32);
		mScale = 1;
		
	}

	// ---------------------------------------
	// Core-Methods
	// ---------------------------------------

	public void initialise(Chip pChip) {
		if (pChip == null)
			return;

		mDisplay = pChip.display();
	}

	public void draw(Bitmap pBitmap) {
		// First clear the emulator window
		mSurface.clear(mBackgroundColor);

		// Set the chip8 display bits into our bitmap
		for (int i = 0; i < 64 * 32; i++) {
			if (mDisplay[i] != (byte) 0)
				mSurface.pixels[i] = mForegroundColor;
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
