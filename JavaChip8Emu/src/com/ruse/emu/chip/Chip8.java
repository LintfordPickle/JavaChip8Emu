package com.ruse.emu.chip;

import java.util.Arrays;

public class Chip8 {

	// ---------------------------------------
	// Variables
	// ---------------------------------------

	private char[] mMemory; // 4096 8-bit addresses, char for unsigned
	private char[] mV; //
	private char mI; // 16 bits wide (we only use 12)
	private char mPC; // program counter

	private char[] mStack; // 12
	private int mStackPointer;

	private char mDelayTimer; // tick down at 60Hz
	private char mSoundTimer; // tick down at 60Hz - sound on !0

	private char[] mKeys; // 16 keys

	private byte[] mDisplay; // 64*32 byte array
	private boolean mDisplayDirty;

	// ---------------------------------------
	// Properties
	// ---------------------------------------

	public byte[] display() {
		return mDisplay;
	}

	/**
	 * @return true if the Chip-8 display array has changed since the last
	 *         dirtyDisplay check. Note this method automatically clears the
	 *         dirty flag!
	 */
	public boolean dirtyDisplay() {
		boolean lTmp = mDisplayDirty;
		mDisplayDirty = false;
		return lTmp;
	}

	// ---------------------------------------
	// Core-Methods
	// ---------------------------------------

	public void initialise() {
		// memory
		mMemory = new char[4096];

		// registers
		mV = new char[16];
		mI = 0x0;

		mPC = 0x200; // 512

		// the stack
		mStack = new char[16];
		mStackPointer = 0;

		// timers
		mDelayTimer = 0;
		mSoundTimer = 0;

		// input
		mKeys = new char[16];

		// display
		mDisplay = new byte[64 * 32];

	}

	public void run() {
		// fetch opcode
		char opcode = (char) (mMemory[mPC] << 8 | mMemory[mPC + 1]);
		if (opcode != (byte) 0)
			System.out.println(Integer.toHexString(opcode) + ": ");

		synchronized (mDisplay) {
			Arrays.fill(mDisplay, (byte) 0);
			mDisplay[4] = (byte) 1;
		}

		// decode opcode
		switch (opcode & 0xF000) {
		// 1100
		// 0xDxyn // Display n-byte sprite starting at memory location I at (Vx,
		// Vy), set VF = collision.

		// 0x8nnn
		case 0x8000: // contains more data in last nibble
			switch (opcode & 0x000F) {
			case 0x0000: // 8xy0 - Set vx = vY
			default:
				System.err.println("opcode not supported");
				System.exit(0);
			}

			break;

		default:
			// System.err.println( "opcode not supported" );
			// System.exit(0);
		}

		// execute opcode
	}

}
