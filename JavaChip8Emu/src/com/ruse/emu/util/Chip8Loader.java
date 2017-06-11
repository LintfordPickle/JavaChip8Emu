package com.ruse.emu.util;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.ruse.emu.chip.Chip8;

public class Chip8Loader {

	// ---------------------------------------
	// Variables
	// ---------------------------------------

	private Chip8 mChip8;

	// ---------------------------------------
	// Properties
	// ---------------------------------------

	// ---------------------------------------
	// Constructor
	// ---------------------------------------

	public Chip8Loader(Chip8 pChip) {
		mChip8 = pChip;

	}

	// ---------------------------------------
	// Methods
	// ---------------------------------------

	public void loadProgram(String pFile) {
		DataInputStream lInput = null;

		try {
			lInput = new DataInputStream(Chip8Loader.class.getResourceAsStream(pFile));

			char[] memoryBlock = new char[lInput.available()];

			// We need to transfer all the bytes into the chip8 memory
			int lOffset = 0;
			while (lInput.available() > 0) {
				// program data in chip-8 memory starts at 512 (0x200)
				memoryBlock[lOffset] = (char) (lInput.readByte() & 0xFF);

				lOffset++;

			}

			// once load, copy to Chip-8
			mChip8.setMemory(memoryBlock, 0x200);

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}

		catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (lInput != null) {
				try {
					lInput.close();
				} catch (IOException e) {

				}
			}
		}

	}

}
