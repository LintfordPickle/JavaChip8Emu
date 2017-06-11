package com.ruse.emu.chip;

import java.util.Arrays;

public class Chip8 {

	// ---------------------------------------
	// Variables
	// ---------------------------------------

	/**
	 * 4096 8-bit addresses, unsigned (char)
	 */
	private char[] mMemory;

	/**
	 * 16 8-bit data registers
	 */
	private char[] mV;

	/**
	 * 16-bit address register (only 12-bit is used)
	 */
	private char mI;

	/**
	 * 16-bit program counter
	 */
	private char mPC;

	/**
	 * Used to store return addresses during sub-routines. Original 1802 allocates 48 bytes for upto 12 levels or nesting. EXTENDED increases this to 16 levels of nesting;
	 */
	private char[] mStack;

	/**
	 * Extends the stack to 16 levels of nesting.
	 */
	private boolean mExtendedStack = false;

	private int mStackPointer;

	/**
	 * tick down at 60Hz
	 */
	private char mDelayTimer;

	/**
	 * tick down at 60Hz - sound on !0
	 */
	private char mSoundTimer;

	/**
	 * 16 input keys
	 */
	private char[] mKeys;

	/**
	 * 64*32 byte display array (monochrome)
	 */
	private byte[] mDisplay;

	/**
	 * Flag to track if the display array has been changed since last check
	 */
	private boolean mDisplayDirty = true;

	// ---------------------------------------
	// Properties
	// ---------------------------------------

	/**
	 * 
	 * @return the display array (monochrome)
	 */
	public byte[] display() {
		return mDisplay;
	}

	/**
	 * @return true if the Chip-8 display array has changed since the last dirtyDisplay check. Note this method automatically clears the dirty flag!
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

	// op-codes list: https://en.wikipedia.org/wiki/CHIP-8
	public void run() {
		
		char caddress;
		int ix;
		int inn;
		
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

		case 0x0000:
			break;

		case 0x1000: // 1NNN - jump to address xNNN
			break;

		case 0x2000: // 2NNN - calls subroutine at xNNN
			caddress = (char) (opcode & 0x0FFF);
			mStack[mStackPointer++] = mPC;
			mPC = caddress;
			break;

		case 0x3000: // 3XNN - skips the next instruction if VX = NN
			ix = (opcode & 0x0F00) >> 8;
			inn = (opcode & 0x00FF);
			if(mV[ix] == inn){
				mPC+= 0x4;
			}else 
				mPC+= 0x2;
			
			break;

		case 0x4000: // 4XNN - skips the next instruction if VX != NN
			break;

		case 0x5000: // 5XY0 - skips the next instruction if VX = VY
			break;

		case 0x6000: // 6XNN - sets VX to NN
			ix = (opcode & 0x0F00) >> 8;
			mV[ix] = (char) (opcode & 0x00FF);
			mPC += 0x2;
			break;

		case 0x7000: // 7XNN - Adds NN to VX
			ix = (opcode & 0x0F00) >> 8;
			inn = (opcode & 0x00FF);
			// TODO: Overflow should be flagged in V[15] ??
			mV[ix] = (char) ((mV[ix] + inn) & 0xFF);
			mPC += 0x2;
			break;

		// 0x8NNN
		case 0x8000:
			switch (opcode & 0x000F) {
			case 0x0000: // 8xy0 - Set vx = vY
			default:
				System.err.println("opcode not supported");
				System.exit(0);
			}

			break;

		case 0x9000: // 9XY0 - Skips the next instruction if VX doesn't equal VY
			break;

		case 0xA000: // ANNN - Sets I to the address NNN
			mI = (char) (opcode & 0x0FFF);
			
			
			mPC += 0x2;
			break;

		case 0xB000: // BNNN - Jumps to the address NNN plus V0
			break;

		case 0xC000: // CXNN - Sets VX to the result of a bitwise AND operation on a random number and NN
			break;

		/*
		 * DXYN - Sprites stored in memory at location in index register (I), 8bits wide. Warps around the screen. If when drawn a pixel is cleared, register VF is set to 1, otherwise it is zero. All drawing is XOR drawing (i.e. it toggles the screen pixels). Sprites are drawn starting at position VX, VY. N is the number of 8-bit rows that need to be drawn. If N is greater than 1, the n-line begins at VX,VY+n
		 */
		case 0xD000:
			//  TODO: Drawing by X-ORing to the screen
			// Check for collisions (set to  V[0xF])
			// Read the image from I
			byte bx = (byte) ((opcode & 0x0F00) >> 8);
			byte by = (byte) ((opcode & 0x00F0) >> 4);
			byte bn = (byte) ((opcode & 0x000F));
			
			int x = mV[bx];
			int y = mV[by];
			int h = bn;
			
			// def. to no drawing collisions
			mV[0xF] = 0;
			
			/**
			 * 
			 * 11110000 XXXX
			 * 10010000 X  X
			 * 10010000 X  X
			 * 10010000 X  X
			 * 11110000 XXXX
			 * 
			 * because the bits for the font are stored in the first nibble,
			 * we need to bit-shift the byte right four.
			 * but this can be used for flipping the sprite.
			 */
			
			// render line-by-line of the sprite at memory location mI
			for(int _y = 0; _y < h; _y++){
				int line = mMemory[mI + _y];
				for(int _x = 0; _x < 8; _x++){ // 8 = num bits in byte
					int pixel = line & (0x80 >> _x);
					if(pixel != 0){
						int totalX = x + _x;
						int totalY = y + _y;
						int index = totalY * 64 + totalX;
						
						// TODO: John - Need to implement sprite wrapping
						
						// Check for collision
						if(mDisplay[index] == 1){
							mV[0xF] = 1;
						}
						
						mDisplay[index] ^= 1;
					}
				}
			}
			
			mDisplayDirty = true;
			
			mPC += 2;
			break;

		case 0xE000:
			switch (opcode & 0x00FF) {
			case 0x009E: // EX9E - Skips the next instruction if the key stored in VX is pressed.

				break;
			case 0x00A1: // EXA1 - Skips the next instruction if the key stored in VX isn't pressed.

				break;
			}
			break;

		case 0xF000:
			switch(opcode & 0x00FF){
			case 0x0007: // FX07 - Sets VX to the value of the delay timer
				break;
				
			case 0x000A: // FX0A - A key pressed is awaited, and then stored in VX
				break;
				
			case 0x0015: // FX15 - Sets the delay_timer to VX
				break;
				
			case 0x0018: // FX18 - Sets the sound_timer to VX
				break;
				
			case 0x001E: // FX1E - Adds VX to I
				break;
				
			case 0x0033: // FX33 - Stores the binary-coded decimal representation of VX, with the most significant of three digits at the address in I, the middle digit at I + 1, and the least dignificant digit at i + 2.
				break;
				
			case 0x0055: // FX55 - Stores V0 to VX (including VX) in memory starting at address I
				break;
				
			case 0x0065: // FX65 - Fills V0 to VX (including VX) with values from memory starting at address I
				break;
			
				
			}
			break;

		default:
			// System.err.println( "opcode not supported" );
			// System.exit(0);
		}

		// execute opcode
	}

	public void setMemory(char[] pMem, int pOffset) {
		if (pOffset + pMem.length > 4094) {
			throw new RuntimeException("Not enough memory on Chip-8!");
		}

		if (pMem == null || pMem.length == 0)
			return;

		for (int i = 0; i < pMem.length; i++) {
			mMemory[pOffset + i] = pMem[i];
		}

	}

}
