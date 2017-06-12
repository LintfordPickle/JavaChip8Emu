# JavaChip8Emu

This project is a Chip8 emulator in Java using AWT. It uses my [JavaFrame](https://github.com/LintfordPickle/JavaFrame) library project for updating and software rendering. The project it still underdevelopment, and it currently missing many opcode implementations, tape/ROM loading etc.

CHIP-8 is a language interpreter used on a few computers in the 1980s. It is small, uses simple instructions and is typically used as an 'Hello World' introduction to emulator programming. The decription of Chip-8 from wikipedia:

> CHIP-8 is an interpreted programming language, developed by Joseph Weisbecker. It was initially used on the COSMAC VIP and Telmac 1800 8-bit microcomputers in the mid-1970s. CHIP-8 programs are run on a CHIP-8 virtual machine. It was made to allow video games to be more easily programmed for said computers.

# Documentation

The following documentation pertains to the implementation details of the CHIP-8:

A chip-8 technical reference document: http://devernay.free.fr/hacks/chip8/C8TECH10.HTM

A chip-8 instruction set reference: http://www.multigesture.net/wp-content/uploads/mirror/goldroad/chip8_instruction_set.shtml


# Project

The respoitory includes a .project file, created in Eclipse Neon (4.6.0).
The source was compiled with Java 8 (1.8.0) - although it uses no Java 8 specific features.
