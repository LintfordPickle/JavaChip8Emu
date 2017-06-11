package com.ruse.emu;

import org.junit.Assert;
import org.junit.Test;

import com.ruse.emu.chip.ChipData;

public class ChipDataTest {
	
	@Test
	public void chipData_fontset_correctMemorySize() {
		// Arrange
		// Act
		// Assert
		Assert.assertEquals(ChipData.fontset.length, 80);
	}

}
