# Simple Calculator

A clean, minimal Java Swing calculator featuring a responsive layout and a real-time history panel.  
This version focuses on simplicity, clarity, and efficient functionality with an emphasis on clean design and user experience.

---

## Features

• Basic arithmetic: addition, subtraction, multiplication, division  
• Real-time history display on the right side  
• Continuous calculations without resetting after equals  
• Decimal input support  
• Division-by-zero protection with error handling  
• Clear (C) button resets both display and history  
• Responsive buttons with consistent typography and spacing  

---

## Modern UI

• Built with **Java Swing** using a lightweight **BorderLayout + GridLayout** combination  
• Large, easy-to-read display field (`Segoe UI`, 24pt)  
• Scrollable history panel with monospaced font (`Consolas`, 14pt)  
• Clear separation of display, keypad, and history areas  
• Balanced button sizing for consistent spacing and readability  
• System look-and-feel for a native appearance on all platforms  

---

## Requirements

• Java JDK 8 or newer  
• No external libraries required  

---

## User Interface Overview

<img width="403" height="307" alt="image" src="https://github.com/user-attachments/assets/af052036-e67e-4086-ad63-62bf18241488" />


## File Structure
```bash
 javac CalculatorApp.java
 java CalculatorApp
```

## Controls
- Digits: `0–9`
- Decimal: `.`
- Operators: `+`, `-`, `*`, `/`
- Equals: `=` computes the current expression
- Delete: `DEL` removes the last character
- Clear: `C` resets the expression and display

## Customize Theme
Open `CalculatorApp.java` and tweak these areas:
- Color palette constants: `BG`, `PANEL_BG`, `DISPLAY_BG`, `TEXT`, `BTN_BG`, `BTN_BORDER`, `OP_BG`, `EQ_BG`, `CLEAR_BG`
- Card rounding: `new RoundedPanel(18)` (increase for softer corners)
- Hover/press feel: adjust brightness in `installHoverEffect` and `brighten(...)`
- Spacing: `new GridLayout(4, 4, 10, 10)` controls gaps between buttons

Example palette overrides (set in `main` using `UIManager.put`):
```java
UIManager.put("control", BG);
UIManager.put("text", TEXT);
UIManager.put("nimbusBase", BG);
UIManager.put("nimbusBlueGrey", PANEL_BG);
UIManager.put("background", BG);
UIManager.put("Panel.background", BG);
```

## Project Structure
```
Calculator-GUI-Java/
├── CalculatorApp.java   # Modern dark-themed Swing calculator
└── README.md            # This document
```

## Troubleshooting
• If the UI looks outdated, ensure your system supports Swing’s system look-and-feel (UIManager.getSystemLookAndFeelClassName()).
• On some desktop themes, button colors and borders may differ; this is expected with system-level UI consistency.
• For alignment or spacing issues, verify your layout managers (BorderLayout, GridLayout) are properly applied.

