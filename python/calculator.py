import tkinter as tk
from tkinter import ttk
import math

class Calculator:
    def __init__(self, root):
        self.root = root
        self.root.title("Multi-Function Calculator")
        self.root.geometry("400x500")
        self.root.resizable(False, False)
        
        # Create tabs for different calculator functions
        self.tab_control = ttk.Notebook(root)
        
        # Standard calculator tab
        self.standard_tab = ttk.Frame(self.tab_control)
        self.tab_control.add(self.standard_tab, text="Standard")
        
        # Conversion calculator tab
        self.conversion_tab = ttk.Frame(self.tab_control)
        self.tab_control.add(self.conversion_tab, text="Length Conversion")
        
        # Log calculator tab
        self.log_tab = ttk.Frame(self.tab_control)
        self.tab_control.add(self.log_tab, text="Logarithm")
        
        self.tab_control.pack(expand=1, fill="both")
        
        # Setup each calculator interface
        self.setup_standard_calculator()
        self.setup_conversion_calculator()
        self.setup_log_calculator()
        
        # Bind keyboard events for the calculator
        self.root.bind("<Key>", self.handle_keyboard_input)
        
        # Set focus to the main window to capture keyboard inputs
        self.root.focus_set()
    
    def setup_standard_calculator(self):
        # Variables for standard calculator
        self.current_expression = ""
        self.total_expression = ""
        
        # Create display frames
        self.display_frame = self.create_display_frame(self.standard_tab)
        
        # Create display labels
        self.total_expression_label = self.create_display_label(self.display_frame, self.total_expression, position="top")
        self.current_expression_label = self.create_display_label(self.display_frame, self.current_expression, position="bottom")
        
        # Create buttons frame
        self.buttons_frame = self.create_buttons_frame(self.standard_tab)
        
        # Create digit buttons
        self.digits = {
            7: (1, 1), 8: (1, 2), 9: (1, 3),
            4: (2, 1), 5: (2, 2), 6: (2, 3),
            1: (3, 1), 2: (3, 2), 3: (3, 3),
            0: (4, 2), '.': (4, 1)
        }
        self.create_digit_buttons()
        
        # Create operation buttons
        self.operations = {"/": "÷", "*": "×", "-": "-", "+": "+"}
        self.create_operator_buttons()
        
        # Create special buttons
        self.create_special_buttons()
        
        # Configure the grid layout
        self.buttons_frame.rowconfigure(0, weight=1)
        for x in range(1, 5):
            self.buttons_frame.rowconfigure(x, weight=1)
            self.buttons_frame.columnconfigure(x, weight=1)
    
    def setup_conversion_calculator(self):
        # Create frame for conversion calculator
        conversion_frame = ttk.Frame(self.conversion_tab, padding="10")
        conversion_frame.pack(fill="both", expand=True)
        
        # Create input field for value
        ttk.Label(conversion_frame, text="Enter Value:").grid(column=0, row=0, sticky=tk.W, pady=5)
        self.conversion_value = tk.StringVar()
        ttk.Entry(conversion_frame, width=15, textvariable=self.conversion_value).grid(column=1, row=0, pady=5)
        
        # Create conversion type selection
        ttk.Label(conversion_frame, text="Convert From:").grid(column=0, row=1, sticky=tk.W, pady=5)
        self.from_unit = tk.StringVar()
        
        # Create conversion type selection
        ttk.Label(conversion_frame, text="Convert To:").grid(column=0, row=2, sticky=tk.W, pady=5)
        self.to_unit = tk.StringVar()
        
        # Create dropdown lists for unit selection
        length_units = ["Meters", "Centimeters", "Kilometers", "Inches", "Feet", "Miles"]
        
        from_dropdown = ttk.Combobox(conversion_frame, textvariable=self.from_unit, values=length_units, width=12)
        from_dropdown.grid(column=1, row=1, pady=5)
        from_dropdown.current(0)
        
        to_dropdown = ttk.Combobox(conversion_frame, textvariable=self.to_unit, values=length_units, width=12)
        to_dropdown.grid(column=1, row=2, pady=5)
        to_dropdown.current(1)
        
        # Create convert button
        convert_button = ttk.Button(conversion_frame, text="Convert", command=self.perform_conversion)
        convert_button.grid(column=0, row=3, columnspan=2, pady=10)
        
        # Create result display
        ttk.Label(conversion_frame, text="Result:").grid(column=0, row=4, sticky=tk.W, pady=5)
        self.conversion_result = tk.StringVar()
        result_display = ttk.Entry(conversion_frame, textvariable=self.conversion_result, state="readonly", width=20)
        result_display.grid(column=1, row=4, pady=5)

    def setup_log_calculator(self):
        # Create frame for log calculator
        log_frame = ttk.Frame(self.log_tab, padding="10")
        log_frame.pack(fill="both", expand=True)
        
        # Create input field for value
        ttk.Label(log_frame, text="Enter Value:").grid(column=0, row=0, sticky=tk.W, pady=5)
        self.log_value = tk.StringVar()
        ttk.Entry(log_frame, width=15, textvariable=self.log_value).grid(column=1, row=0, pady=5)
        
        # Create log type selection
        ttk.Label(log_frame, text="Log Type:").grid(column=0, row=1, sticky=tk.W, pady=5)
        self.log_type = tk.StringVar()
        
        log_types = ["Natural Log (ln)", "Log base 10 (log10)", "Log base 2 (log2)"]
        log_dropdown = ttk.Combobox(log_frame, textvariable=self.log_type, values=log_types, width=15)
        log_dropdown.grid(column=1, row=1, pady=5)
        log_dropdown.current(0)
        
        # Create calculate button
        calc_button = ttk.Button(log_frame, text="Calculate", command=self.perform_log_calculation)
        calc_button.grid(column=0, row=2, columnspan=2, pady=10)
        
        # Create result display
        ttk.Label(log_frame, text="Result:").grid(column=0, row=3, sticky=tk.W, pady=5)
        self.log_result = tk.StringVar()
        result_display = ttk.Entry(log_frame, textvariable=self.log_result, state="readonly", width=20)
        result_display.grid(column=1, row=3, pady=5)
    
    def create_display_frame(self, parent):
        frame = ttk.Frame(parent)
        frame.pack(expand=True, fill="both", padx=10, pady=10)
        return frame
    
    def create_display_label(self, parent, text, position):
        if position == "top":
            label = ttk.Label(parent, text=text, anchor=tk.E, background="#F8F8F8", font=("Arial", 12))
            label.pack(expand=True, fill="both")
        else:
            label = ttk.Label(parent, text=text, anchor=tk.E, background="#FFFFFF", font=("Arial", 24, "bold"))
            label.pack(expand=True, fill="both")
        return label
    
    def create_buttons_frame(self, parent):
        frame = ttk.Frame(parent)
        frame.pack(expand=True, fill="both", padx=10, pady=10)
        return frame
    
    def create_digit_buttons(self):
        for digit, grid_value in self.digits.items():
            button = ttk.Button(self.buttons_frame, text=str(digit),
                                command=lambda x=digit: self.add_to_expression(x))
            button.grid(row=grid_value[0], column=grid_value[1], sticky=tk.NSEW, padx=2, pady=2)
    
    def create_operator_buttons(self):
        i = 0
        for operator, symbol in self.operations.items():
            button = ttk.Button(self.buttons_frame, text=symbol,
                                command=lambda x=operator: self.append_operator(x))
            button.grid(row=i, column=4, sticky=tk.NSEW, padx=2, pady=2)
            i += 1
    
    def create_special_buttons(self):
        self.create_clear_button()
        self.create_equals_button()
        self.create_percent_button()
        self.create_clear_entry_button()
    
    def create_clear_button(self):
        button = ttk.Button(self.buttons_frame, text="C",
                           command=self.clear)
        button.grid(row=0, column=1, sticky=tk.NSEW, padx=2, pady=2)
    
    def create_clear_entry_button(self):
        button = ttk.Button(self.buttons_frame, text="CE",
                           command=self.clear_entry)
        button.grid(row=0, column=2, sticky=tk.NSEW, padx=2, pady=2)
    
    def create_equals_button(self):
        button = ttk.Button(self.buttons_frame, text="=",
                           command=self.evaluate)
        button.grid(row=4, column=3, columnspan=2, sticky=tk.NSEW, padx=2, pady=2)
    
    def create_percent_button(self):
        button = ttk.Button(self.buttons_frame, text="%",
                           command=self.calculate_percent)
        button.grid(row=0, column=3, sticky=tk.NSEW, padx=2, pady=2)
    
    def add_to_expression(self, value):
        self.current_expression += str(value)
        self.update_current_label()
    
    def append_operator(self, operator):
        self.current_expression += operator
        self.total_expression += self.current_expression
        self.current_expression = ""
        self.update_total_label()
        self.update_current_label()
    
    def clear(self):
        self.current_expression = ""
        self.total_expression = ""
        self.update_total_label()
        self.update_current_label()
    
    def clear_entry(self):
        self.current_expression = ""
        self.update_current_label()
    
    def calculate_percent(self):
        if self.current_expression:
            try:
                # If there's a base value to calculate percentage from
                if self.total_expression and self.total_expression[-1] in "+-*/":
                    base_value = eval(self.total_expression[:-1])
                    percentage = float(self.current_expression) / 100
                    self.current_expression = str(base_value * percentage)
                else:
                    # Just convert to percentage
                    self.current_expression = str(float(self.current_expression) / 100)
                self.update_current_label()
            except Exception as e:
                self.current_expression = "Error"
                self.update_current_label()
    
    def evaluate(self):
        self.total_expression += self.current_expression
        self.update_total_label()
        try:
            self.current_expression = str(eval(self.total_expression))
            self.total_expression = ""
        except Exception as e:
            self.current_expression = "Error"
        finally:
            self.update_current_label()
    
    def update_total_label(self):
        expression = self.total_expression
        for operator, symbol in self.operations.items():
            expression = expression.replace(operator, f' {symbol} ')
        self.total_expression_label.config(text=expression)
    
    def update_current_label(self):
        self.current_expression_label.config(text=self.current_expression[:11])
    
    def perform_conversion(self):
        try:
            value = float(self.conversion_value.get())
            from_unit = self.from_unit.get()
            to_unit = self.to_unit.get()
            
            # Convert to meters first (base unit)
            meters = self.convert_to_meters(value, from_unit)
            
            # Convert from meters to target unit
            result = self.convert_from_meters(meters, to_unit)
            
            self.conversion_result.set(f"{result:.6g}")
        except ValueError:
            self.conversion_result.set("Invalid input")
    
    def convert_to_meters(self, value, unit):
        if unit == "Meters":
            return value
        elif unit == "Centimeters":
            return value / 100
        elif unit == "Kilometers":
            return value * 1000
        elif unit == "Inches":
            return value * 0.0254
        elif unit == "Feet":
            return value * 0.3048
        elif unit == "Miles":
            return value * 1609.34
    
    def convert_from_meters(self, meters, unit):
        if unit == "Meters":
            return meters
        elif unit == "Centimeters":
            return meters * 100
        elif unit == "Kilometers":
            return meters / 1000
        elif unit == "Inches":
            return meters / 0.0254
        elif unit == "Feet":
            return meters / 0.3048
        elif unit == "Miles":
            return meters / 1609.34
    
    def perform_log_calculation(self):
        try:
            value = float(self.log_value.get())
            log_type = self.log_type.get()
            
            if value <= 0:
                self.log_result.set("Invalid input (must be > 0)")
                return
            
            if log_type == "Natural Log (ln)":
                result = math.log(value)
            elif log_type == "Log base 10 (log10)":
                result = math.log10(value)
            else:  # Log base 2
                result = math.log2(value)
            
            self.log_result.set(f"{result:.8g}")
        except ValueError:
            self.log_result.set("Invalid input")
    
    # Add keyboard handler method
    def handle_keyboard_input(self, event):
        # Handle numeric keys and decimal point
        if event.char.isdigit() or event.char == '.':
            self.add_to_expression(event.char)
        # Handle operators
        elif event.char in ['+', '-', '*', '/']:
            self.append_operator(event.char)
        # Handle equals sign and Enter key
        elif event.char == '=' or event.keysym == 'Return':
            self.evaluate()
        # Handle Escape key for clear
        elif event.keysym == 'Escape':
            self.clear()
        # Handle Backspace for clear entry
        elif event.keysym == 'BackSpace':
            if self.current_expression:
                self.current_expression = self.current_expression[:-1]
                self.update_current_label()
        # Handle percent key
        elif event.char == '%':
            self.calculate_percent()

# Run the application
if __name__ == "__main__":
    root = tk.Tk()
    calculator = Calculator(root)
    root.mainloop()
