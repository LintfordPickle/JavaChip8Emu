package com.ruse.emu.chip;

/**
 * 
 * The {@link Chip8Thread} class will take a {@link Chip8} instance and perform
 * an instruction cycle at the desired Hz, on a separate 'CPU' thread.
 */
public class Chip8Thread extends Thread {

	// ---------------------------------------
	// Variables
	// ---------------------------------------

	private Chip8 mChip8;
	private int mTargetHz = 60;
	private long mTimeLastUpdate;

	// ---------------------------------------
	// Constructor
	// ---------------------------------------

	public Chip8Thread(Chip8 pChip8) {
		mChip8 = pChip8;

	}

	// ---------------------------------------
	// Methods
	// ---------------------------------------

	@Override
	public synchronized void start() {
		super.start();

		System.out.println("Chip8Thread started");

	}

	@Override
	public void run() {
		super.run();

		long lTimeNow = System.currentTimeMillis();
		mTimeLastUpdate = lTimeNow;

		while (true) {

			lTimeNow = System.currentTimeMillis();
			float lTargetMilli = 1f / (float) mTargetHz;

			if (lTimeNow - mTimeLastUpdate > lTargetMilli) {
				mChip8.run();
				mTimeLastUpdate = lTimeNow;

			}

		}

	}

}
