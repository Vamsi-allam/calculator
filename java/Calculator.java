import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;

public class Calculator extends JFrame {
    
    private final JTabbedPane tabbedPane;
    
    // Standard calculator components
    private JPanel standardPanel;
    private JTextField displayField;
    private JLabel expressionLabel;
    private String currentExpression = "";
    private String totalExpression = "";
    private final HashMap<String, String> operations;
    
    // Length conversion components
    private JPanel conversionPanel;
    private JTextField conversionValueField;
    private JComboBox<String> fromUnitCombo;
    private JComboBox<String> toUnitCombo;
    private JTextField conversionResultField;
    
    // Logarithm components
    private JPanel logPanel;
    private JTextField logValueField;
    private JComboBox<String> logTypeCombo;
    private JTextField logResultField;
    
    public Calculator() {
        // Set up the JFrame
        super("Multi-Function Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        // Initialize operations map
        operations = new HashMap<>();
        operations.put("/", "÷");
        operations.put("*", "×");
        operations.put("-", "-");
        operations.put("+", "+");
        
        // Create a tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.setBackground(new Color(240, 240, 240));
        
        // Set up tabs
        setupStandardCalculator();
        setupConversionCalculator();
        setupLogarithmCalculator();
        
        // Add tabs to the tabbed pane
        tabbedPane.addTab("Standard", standardPanel);
        tabbedPane.addTab("Length Conversion", conversionPanel);
        tabbedPane.addTab("Logarithm", logPanel);
        
        // Add the tabbed pane to the JFrame
        add(tabbedPane);
        
        // Add keyboard listener for the standard calculator
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyboardInput(e);
            }
        });
        
        // Make the frame focusable to receive keyboard events
        setFocusable(true);
        requestFocus();
        
        // Add window focus listener to ensure keyboard focus
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                requestFocus();
            }
        });
    }
    
    private void setupStandardCalculator() {
        standardPanel = new JPanel();
        standardPanel.setLayout(new BorderLayout());
        standardPanel.setBackground(new Color(240, 240, 240));
        
        // Create display panel with a nicer look
        JPanel displayPanel = new JPanel(new BorderLayout(0, 5));
        displayPanel.setBackground(new Color(240, 240, 240));
        
        expressionLabel = new JLabel("");
        expressionLabel.setHorizontalAlignment(JLabel.RIGHT);
        expressionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        expressionLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
        expressionLabel.setForeground(new Color(100, 100, 100));
        
        displayField = new JTextField();
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setFont(new Font("Arial", Font.BOLD, 28));
        displayField.setEditable(false);
        displayField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        displayField.setBackground(Color.WHITE);
        
        displayPanel.add(expressionLabel, BorderLayout.NORTH);
        displayPanel.add(displayField, BorderLayout.CENTER);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        
        standardPanel.add(displayPanel, BorderLayout.NORTH);
        
        // Create buttons panel with improved styling
        JPanel buttonsPanel = new JPanel(new GridLayout(5, 4, 8, 8));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        buttonsPanel.setBackground(new Color(240, 240, 240));
        
        // Create styled buttons
        JButton clearButton = createStyledButton("C", new Color(255, 150, 150));
        clearButton.addActionListener(e -> clearAll());
        
        JButton clearEntryButton = createStyledButton("CE", new Color(255, 200, 150));
        clearEntryButton.addActionListener(e -> clearEntry());
        
        JButton percentButton = createStyledButton("%", new Color(220, 220, 255));
        percentButton.addActionListener(e -> calculatePercent());
        
        // Add buttons for row 1
        buttonsPanel.add(clearButton);
        buttonsPanel.add(clearEntryButton);
        buttonsPanel.add(percentButton);
        buttonsPanel.add(createOperatorButton("/"));
        
        // Add digit buttons
        addDigitButton(buttonsPanel, "7");
        addDigitButton(buttonsPanel, "8");
        addDigitButton(buttonsPanel, "9");
        buttonsPanel.add(createOperatorButton("*"));
        
        addDigitButton(buttonsPanel, "4");
        addDigitButton(buttonsPanel, "5");
        addDigitButton(buttonsPanel, "6");
        buttonsPanel.add(createOperatorButton("-"));
        
        addDigitButton(buttonsPanel, "1");
        addDigitButton(buttonsPanel, "2");
        addDigitButton(buttonsPanel, "3");
        buttonsPanel.add(createOperatorButton("+"));
        
        addDigitButton(buttonsPanel, ".");
        addDigitButton(buttonsPanel, "0");
        
        JButton equalsButton = createStyledButton("=", new Color(120, 200, 255));
        equalsButton.addActionListener(e -> evaluate());
        buttonsPanel.add(equalsButton);
        buttonsPanel.add(new JLabel()); // Empty cell
        
        standardPanel.add(buttonsPanel, BorderLayout.CENTER);
    }
    
    // Create a styled button with custom background
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1, true),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        return button;
    }
    
    private void addDigitButton(JPanel panel, String digit) {
        JButton button = createStyledButton(digit, new Color(250, 250, 250));
        button.addActionListener(e -> addToExpression(digit));
        panel.add(button);
    }
    
    private JButton createOperatorButton(String operator) {
        JButton button = createStyledButton(operations.get(operator), new Color(230, 230, 255));
        button.addActionListener(e -> appendOperator(operator));
        return button;
    }
    
    // Keyboard input handler
    private void handleKeyboardInput(KeyEvent e) {
        // Only handle keyboard input when the standard calculator tab is selected
        if (tabbedPane.getSelectedIndex() == 0) {
            char keyChar = e.getKeyChar();
            int keyCode = e.getKeyCode();
            
            // Handle digits and decimal
            if (Character.isDigit(keyChar) || keyChar == '.') {
                addToExpression(String.valueOf(keyChar));
            }
            // Handle operators
            else if (keyChar == '+' || keyChar == '-' || keyChar == '*' || keyChar == '/') {
                appendOperator(String.valueOf(keyChar));
            }
            // Handle enter or equals key
            else if (keyChar == '=' || keyCode == KeyEvent.VK_ENTER) {
                evaluate();
            }
            // Handle escape key for clear
            else if (keyCode == KeyEvent.VK_ESCAPE) {
                clearAll();
            }
            // Handle backspace for removing last character
            else if (keyCode == KeyEvent.VK_BACK_SPACE) {
                if (!currentExpression.isEmpty()) {
                    currentExpression = currentExpression.substring(0, currentExpression.length() - 1);
                    displayField.setText(currentExpression);
                }
            }
            // Handle percent key
            else if (keyChar == '%') {
                calculatePercent();
            }
        }
    }
    
    // Add key listeners to the conversion and logarithm input fields
    private void setupConversionCalculator() {
        conversionPanel = new JPanel();
        conversionPanel.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Value input
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(new JLabel("Enter Value:"), gbc);
        
        gbc.gridx = 1;
        conversionValueField = new JTextField(15);
        contentPanel.add(conversionValueField, gbc);
        
        // From unit selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(new JLabel("Convert From:"), gbc);
        
        gbc.gridx = 1;
        String[] lengthUnits = {"Meters", "Centimeters", "Kilometers", "Inches", "Feet", "Miles"};
        fromUnitCombo = new JComboBox<>(lengthUnits);
        contentPanel.add(fromUnitCombo, gbc);
        
        // To unit selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(new JLabel("Convert To:"), gbc);
        
        gbc.gridx = 1;
        toUnitCombo = new JComboBox<>(lengthUnits);
        toUnitCombo.setSelectedIndex(1); // Default to centimeters
        contentPanel.add(toUnitCombo, gbc);
        
        // Convert button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(e -> performConversion());
        contentPanel.add(convertButton, gbc);
        
        // Result display
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(new JLabel("Result:"), gbc);
        
        gbc.gridx = 1;
        conversionResultField = new JTextField(15);
        conversionResultField.setEditable(false);
        contentPanel.add(conversionResultField, gbc);
        
        conversionPanel.add(contentPanel, BorderLayout.NORTH);
        
        // Add keyboard event for conversion input
        conversionValueField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performConversion();
                }
            }
        });
    }
    
    private void setupLogarithmCalculator() {
        logPanel = new JPanel();
        logPanel.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Value input
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(new JLabel("Enter Value:"), gbc);
        
        gbc.gridx = 1;
        logValueField = new JTextField(15);
        contentPanel.add(logValueField, gbc);
        
        // Log type selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(new JLabel("Log Type:"), gbc);
        
        gbc.gridx = 1;
        String[] logTypes = {"Natural Log (ln)", "Log base 10 (log10)", "Log base 2 (log2)"};
        logTypeCombo = new JComboBox<>(logTypes);
        contentPanel.add(logTypeCombo, gbc);
        
        // Calculate button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> performLogCalculation());
        contentPanel.add(calculateButton, gbc);
        
        // Result display
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(new JLabel("Result:"), gbc);
        
        gbc.gridx = 1;
        logResultField = new JTextField(15);
        logResultField.setEditable(false);
        contentPanel.add(logResultField, gbc);
        
        logPanel.add(contentPanel, BorderLayout.NORTH);
        
        // Add keyboard event for log input
        logValueField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogCalculation();
                }
            }
        });
    }
    
    private void addToExpression(String value) {
        currentExpression += value;
        displayField.setText(currentExpression);
    }
    
    private void appendOperator(String operator) {
        if (!currentExpression.isEmpty()) {
            totalExpression += currentExpression + operator;
            currentExpression = "";
            expressionLabel.setText(formatExpression(totalExpression));
            displayField.setText("");
        } else if (!totalExpression.isEmpty()) {
            // Replace the last operator if there's no current expression
            totalExpression = totalExpression.substring(0, totalExpression.length() - 1) + operator;
            expressionLabel.setText(formatExpression(totalExpression));
        }
    }
    
    private String formatExpression(String expression) {
        for (String op : operations.keySet()) {
            expression = expression.replace(op, " " + operations.get(op) + " ");
        }
        return expression;
    }
    
    private void clearAll() {
        currentExpression = "";
        totalExpression = "";
        expressionLabel.setText("");
        displayField.setText("");
    }
    
    private void clearEntry() {
        currentExpression = "";
        displayField.setText("");
    }
    
    private void evaluate() {
        if (!currentExpression.isEmpty()) {
            try {
                totalExpression += currentExpression;
                expressionLabel.setText(formatExpression(totalExpression));
                
                // Use our custom expression evaluator instead of JavaScript engine
                double result = evaluateExpression(totalExpression);
                
                currentExpression = String.valueOf(result);
                totalExpression = "";
                displayField.setText(currentExpression);
            } catch (RuntimeException e) {
                displayField.setText("Error");
                System.out.println("Calculation error: " + e.getMessage());
                currentExpression = "";
                totalExpression = "";
            }
        }
    }
    
    // Add this custom expression evaluator method
    private double evaluateExpression(String expression) {
        return new ExpressionEvaluator().evaluate(expression);
    }
    
    private void calculatePercent() {
        if (!currentExpression.isEmpty()) {
            try {
                if (!totalExpression.isEmpty() && 
                    (totalExpression.endsWith("+") || totalExpression.endsWith("-") || 
                     totalExpression.endsWith("*") || totalExpression.endsWith("/"))) {
                    
                    // Use our custom evaluator instead of JavaScript engine
                    String baseExpr = totalExpression.substring(0, totalExpression.length() - 1);
                    double baseValue = evaluateExpression(baseExpr);
                    
                    double percentage = Double.parseDouble(currentExpression) / 100;
                    double result = baseValue * percentage;
                    
                    currentExpression = String.valueOf(result);
                } else {
                    // Just convert to percentage
                    double value = Double.parseDouble(currentExpression);
                    currentExpression = String.valueOf(value / 100);
                }
                displayField.setText(currentExpression);
            } catch (Exception e) {
                displayField.setText("Error");
                currentExpression = "";
            }
        }
    }
    
    private void performConversion() {
        try {
            double value = Double.parseDouble(conversionValueField.getText());
            String fromUnit = (String) fromUnitCombo.getSelectedItem();
            String toUnit = (String) toUnitCombo.getSelectedItem();
            
            // Convert to meters first
            double meters = convertToMeters(value, fromUnit);
            
            // Convert from meters to target unit
            double result = convertFromMeters(meters, toUnit);
            
            // Format the result with appropriate precision
            conversionResultField.setText(String.format("%.6g", result));
        } catch (NumberFormatException e) {
            conversionResultField.setText("Invalid input");
        }
    }
    
    private double convertToMeters(double value, String unit) {
        switch (unit) {
            case "Meters":
                return value;
            case "Centimeters":
                return value / 100;
            case "Kilometers":
                return value * 1000;
            case "Inches":
                return value * 0.0254;
            case "Feet":
                return value * 0.3048;
            case "Miles":
                return value * 1609.34;
            default:
                return value;
        }
    }
    
    private double convertFromMeters(double meters, String unit) {
        switch (unit) {
            case "Meters":
                return meters;
            case "Centimeters":
                return meters * 100;
            case "Kilometers":
                return meters / 1000;
            case "Inches":
                return meters / 0.0254;
            case "Feet":
                return meters / 0.3048;
            case "Miles":
                return meters / 1609.34;
            default:
                return meters;
        }
    }
    
    private void performLogCalculation() {
        try {
            double value = Double.parseDouble(logValueField.getText());
            String logType = (String) logTypeCombo.getSelectedItem();
            
            if (value <= 0) {
                logResultField.setText("Invalid input (must be > 0)");
                return;
            }
            
            double result;
            switch (logType) {
                case "Natural Log (ln)":
                    result = Math.log(value);
                    break;
                case "Log base 10 (log10)":
                    result = Math.log10(value);
                    break;
                case "Log base 2 (log2)":
                    result = Math.log(value) / Math.log(2);
                    break;
                default:
                    result = Math.log(value);
            }
            
            logResultField.setText(String.format("%.8g", result));
        } catch (NumberFormatException e) {
            logResultField.setText("Invalid input");
        }
    }
    
    public static void main(String[] args) {
        try {
            // Use a better look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Adjust UI properties for a more modern look
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("ProgressBar.arc", 10);
            UIManager.put("TextComponent.arc", 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
            calculator.requestFocus(); // Request focus for keyboard input
        });
    }
    
    // Add this inner class at the end of the Calculator class
    private class ExpressionEvaluator {
        private int pos = -1;
        private int ch;
        private String expr;
        
        public double evaluate(String expression) {
            this.expr = expression;
            pos = -1;
            nextChar();
            double x = parseExpression();
            if (pos < expr.length()) {
                throw new RuntimeException("Unexpected character: " + (char)ch);
            }
            return x;
        }
        
        private void nextChar() {
            ch = (++pos < expr.length()) ? expr.charAt(pos) : -1;
        }
        
        private boolean eat(int charToEat) {
            while (Character.isWhitespace(ch)) {
                nextChar();
            }
            if (ch == charToEat) {
                nextChar();
                return true;
            }
            return false;
        }
        
        private double parseExpression() {
            double x = parseTerm();
            while (true) {
                if (eat('+')) {
                    x += parseTerm();
                } else if (eat('-')) {
                    x -= parseTerm();
                } else {
                    return x;
                }
            }
        }
        
        private double parseTerm() {
            double x = parseFactor();
            while (true) {
                if (eat('*')) {
                    x *= parseFactor();
                } else if (eat('/')) {
                    x /= parseFactor();
                } else {
                    return x;
                }
            }
        }
        
        private double parseFactor() {
            if (eat('+')) {
                return parseFactor();
            }
            if (eat('-')) {
                return -parseFactor();
            }
            
            double x;
            int startPos = this.pos;
            
            if (eat('(')) {
                x = parseExpression();
                eat(')');
            } else if (Character.isDigit(ch) || ch == '.') {
                // Parse numbers
                StringBuilder sb = new StringBuilder();
                while (Character.isDigit(ch) || ch == '.') {
                    sb.append((char)ch);
                    nextChar();
                }
                x = Double.parseDouble(sb.toString());
            } else {
                throw new RuntimeException("Unexpected character: " + (char)ch);
            }
            
            return x;
        }
    }
}
