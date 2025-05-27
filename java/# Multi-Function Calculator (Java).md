# Multi-Function Calculator (Java)

A comprehensive calculator application built with Java and Swing that provides multiple calculation utilities in a clean, tabbed interface with both mouse and keyboard input support.

![image](https://github.com/user-attachments/assets/8c6edd61-4f6a-4aeb-8766-ade3a137e826)


## Features

- **Standard Calculator**: Perform basic arithmetic operations
  - Addition, subtraction, multiplication, division
  - Percentage calculations
  - Clear and clear entry functions
  - Full keyboard input support
  
- **Length Conversion**: Convert between different units of length
  - Meters, Centimeters, Kilometers
  - Inches, Feet, Miles
  - Enter key support for quick conversion
  
- **Logarithm Calculator**: Calculate logarithmic values
  - Natural Log (ln)
  - Base 10 Log (log10)
  - Base 2 Log (log2)
  - Enter key support for quick calculation

## Requirements

- Java Development Kit (JDK) 8 or higher
- Java Swing (included in the JDK)

## Installation

1. Clone this repository or download the `Calculator.java` file
2. Compile the Java file:

```bash
javac Calculator.java
```

3. Run the application:

```bash
java Calculator
```

## Usage

### Standard Calculator
- Use the numeric buttons or keyboard to input numbers
- Use operation buttons or keyboard (+, -, *, /) to perform operations
- Press "=" or Enter key to evaluate expressions
- Press "%" or % key to calculate percentages
- Press "C" or Escape key to clear all input and history
- Press "CE" or Backspace key to delete the last input character

### Length Conversion
1. Enter a numeric value in the input field
2. Select the source unit from the "Convert From" dropdown
3. Select the target unit from the "Convert To" dropdown
4. Click "Convert" or press Enter to see the result

### Logarithm Calculator
1. Enter a positive numeric value in the input field
2. Select the logarithm type from the dropdown
3. Click "Calculate" or press Enter to see the result

## Code Structure

- `Calculator` class: Main application class that extends JFrame
  - `setupStandardCalculator()`: Sets up the standard calculator tab with enhanced styling
  - `setupConversionCalculator()`: Sets up the length conversion tab
  - `setupLogarithmCalculator()`: Sets up the logarithm calculator tab
  - `handleKeyboardInput()`: Manages keyboard events for calculator operations
  - `ExpressionEvaluator` inner class: Custom parser for mathematical expressions

## Implementation Details

The Java implementation features:
- An enhanced user interface with styled buttons and improved visual design
- A custom expression evaluator using recursive descent parsing
- Complete keyboard input support for all calculator functions
- Focus management to ensure keyboard events are properly captured
- Responsive UI with proper event handling

## License

[MIT License](LICENSE)

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
