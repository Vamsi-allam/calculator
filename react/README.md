# 🧮 Multi-Function Calculator (Advanced)

A comprehensive calculator application built with React and Material UI, featuring standard and scientific calculation modes, memory functions, history tracking, and responsive design.

## 🚀 Features

### Core Features
- **Standard Calculator** - Basic arithmetic operations (+, -, ×, ÷)
- **Scientific Calculator** - Advanced mathematical functions and operations
- **Memory Functions** - Store, recall, add, and subtract values from memory
- **History Tracking** - View and access previous calculations
- **Function-First Workflow** - Select trigonometric functions first, then enter values

### Advanced Features
- **Local Storage Persistence** - History persists across page refreshes
- **Keyboard Support** - Use keyboard for rapid calculations
- **Tabbed Interface** - Switch between Standard and Scientific modes
- **Responsive Design** - Works on all devices with adaptive layout
- **Visual Feedback** - Highlighted buttons for active functions

## 🔧 Tech Stack

- **React** for UI components and logic
- **Material UI** for styling and components
- **React Router** for navigation
- **localStorage API** for data persistence
- **CSS Grid** for calculator layout

## 📁 Project Structure

```
src/
├── components/         # Reusable UI components
│   ├── Calculator.jsx  # Main calculator component with logic
│   └── Home.jsx        # Home page component
├── App.jsx            # Main app component with routing
├── main.jsx           # Application entry point
└── App.css            # Global styles
```

## 🚀 Quick Start

1. **Install Dependencies**
   ```bash
   npm install
   ```

   You can also install dependencies individually:

   ```bash
   # Core React packages
   npm install react react-dom
   
   # Routing
   npm install react-router-dom
   
   # Material UI
   npm install @mui/material @mui/icons-material @emotion/react @emotion/styled
   ```

2. **Start Development Server**
   ```bash
   npm start
   ```

3. **Build for Production**
   ```bash
   npm run build
   ```

4. **Open the application**
   Navigate to [http://localhost:3000](http://localhost:3000)

## 📊 Key Components

### Standard Calculator
- **Number Pad** - Digits 0-9 with decimal point
- **Basic Operations** - Addition, subtraction, multiplication, division
- **Memory Functions** - MC, MR, M+, M- buttons
- **History View** - Access previous calculations

### Scientific Calculator
- **Trigonometric Functions** - sin, cos, tan with degree input
- **Logarithmic Functions** - log (base 10) and ln (natural log)
- **Power Functions** - Square, square root, exponents
- **Constants** - Mathematical constants and conversions
- **Advanced Operations** - Factorial, parentheses, percentage

### Memory & History
- **Memory Storage** - Store values for repeated use
- **Calculation History** - Chronological list of operations
- **Local Storage** - Persists between sessions
- **History Management** - Clear history or reuse calculations

## 🎨 Design Features

- **Material Design** - Clean, modern UI with Material components
- **Grid Layout** - Organized button placement with CSS Grid
- **Visual Feedback** - Button highlighting for active functions
- **Responsive Layout** - Works on mobile, tablet and desktop
- **Accessible Design** - Keyboard navigation and screen reader support

## 🔧 Development Features

- **Custom Hooks** - State management for calculator logic
- **Event Handling** - Keyboard and button input processing
- **LocalStorage Integration** - Persistent history storage
- **Scientific Function Handling** - Special handling for complex operations

## 🌟 Calculator Functions

### Standard Operations
- **Basic Math** - Addition (+), Subtraction (-), Multiplication (×), Division (÷)
- **Memory** - Memory Clear (MC), Memory Recall (MR), Memory Add (M+), Memory Subtract (M-)
- **Utilities** - Clear (C), Backspace, Decimal point (.), Equals (=), Percentage (%)

### Scientific Operations
- **Trigonometry** - Sine (sin), Cosine (cos), Tangent (tan)
- **Logarithms** - Base-10 (log), Natural (ln)
- **Powers** - Square (x²), Square root (√x), Exponents
- **Advanced** - Factorial (x!), Reciprocal (1/x)

## 📱 Keyboard Support

- **Numbers** - 0-9 keys
- **Operations** - +, -, *, / keys
- **Equals** - Enter key or = key
- **Clear** - Escape key or C key
- **Decimal** - . key
- **Backspace** - Backspace key
- **Parentheses** - ( and ) keys
- **Percent** - % key

## 🚀 Future Enhancements

- **Unit Conversion** - Temperature, length, weight, and currency
- **Graphing Capabilities** - Plot functions and equations
- **History Search** - Search through calculation history
- **Custom Themes** - Multiple color schemes and layouts
- **Expression Preview** - Real-time evaluation of expressions
- **User Accounts** - Sync history across devices
- **Advanced Functions** - Statistics, matrices, and complex numbers

---

Built with React and Material UI for modern, responsive calculator functionality.
