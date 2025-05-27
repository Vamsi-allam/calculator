# Multi-Function Calculator (Python)

A feature-rich calculator application built using Python and Tkinter that provides multiple calculation utilities in a simple, tabbed interface with full keyboard input support.

![image](https://github.com/user-attachments/assets/4afac76a-e97a-427c-867c-31d2a1c3a17c)


## Features

- **Standard Calculator**: Perform basic arithmetic operations
  - Addition, subtraction, multiplication, division
  - Percentage calculations
  - Clear and clear entry functions
  - Full keyboard input support
  
- **Length Conversion**: Convert between different units of length
  - Meters, Centimeters, Kilometers
  - Inches, Feet, Miles
  
- **Logarithm Calculator**: Calculate logarithmic values
  - Natural Log (ln)
  - Base 10 Log (log10)
  - Base 2 Log (log2)

## Requirements

- Python 3.x
- Tkinter (usually comes pre-installed with Python)
- Math module (standard library)

## Installation

1. Clone this repository or download the `calculator.py` file
2. Make sure Python 3.x is installed on your system
3. Run the application:

```bash
python calculator.py
```

4. Create a standalone executable (optional):
```bash
# Using PyInstaller
pip install pyinstaller
pyinstaller --onefile --windowed calculator.py
```

## Usage

### Standard Calculator
- Use either the on-screen buttons or keyboard to input numbers
- Press number keys (0-9) for digits and "." for decimal point
- Press operator keys (+, -, *, /) for operations
- Press "=" or Enter key to evaluate expressions
- Press "%" or % key for percentage calculations
- Press Escape key to clear all input
- Press Backspace to delete the last character

### Length Conversion
1. Enter a numeric value in the input field
2. Select the source unit from the "Convert From" dropdown
3. Select the target unit from the "Convert To" dropdown
4. Click "Convert" to see the result

### Logarithm Calculator
1. Enter a positive numeric value in the input field
2. Select the logarithm type from the dropdown
3. Click "Calculate" to see the result

## Code Structure

- `Calculator` class: Main application class
  - `setup_standard_calculator()`: Sets up the standard calculator tab
  - `setup_conversion_calculator()`: Sets up the length conversion tab
  - `setup_log_calculator()`: Sets up the logarithm calculator tab
  - `handle_keyboard_input()`: Processes keyboard events for calculator operations
  - Helper methods for creating UI elements and handling operations

## License

[MIT License](LICENSE)

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
