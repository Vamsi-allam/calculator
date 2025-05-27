import { useState, useEffect } from 'react';
import { 
  Container, Paper, Button, Typography,
  Box, IconButton, Tab, Tabs
} from '@mui/material';
import { Backspace, History } from '@mui/icons-material';

function Calculator() {
  // Add state for pending scientific function and first digit flag
  const [display, setDisplay] = useState('0');
  const [expression, setExpression] = useState('');
  const [memory, setMemory] = useState(0);
  const [pendingFunction, setPendingFunction] = useState(null);
  const [isFirstDigitAfterFunction, setIsFirstDigitAfterFunction] = useState(false);
  const [history, setHistory] = useState(() => {
    const savedHistory = localStorage.getItem('calculatorHistory');
    return savedHistory ? JSON.parse(savedHistory) : [];
  });
  const [calculatorType, setCalculatorType] = useState(0); // 0: standard, 1: scientific

  // Save history to localStorage whenever it changes
  useEffect(() => {
    localStorage.setItem('calculatorHistory', JSON.stringify(history));
  }, [history]);

  // Fixed number input handler to correctly handle multi-digit input after functions
  const handleNumberClick = (num) => {
    if (display === '0' || display === 'Error' || isFirstDigitAfterFunction) {
      // Replace display for first digit
      setDisplay(num.toString());
      setIsFirstDigitAfterFunction(false);
    } else {
      // Append digit in all other cases
      setDisplay(display + num);
    }
  };

  // Handle operator input
  const handleOperatorClick = (op) => {
    try {
      setExpression(expression + display + op);
      setDisplay('0');
    } catch (error) {
      setDisplay('Error');
    }
  };

  // Modified scientific functions handler to include log and ln in function-first workflow
  const handleScientificFunction = (func) => {
    // For trigonometric and logarithmic functions, set as pending
    if (['sin', 'cos', 'tan', 'log', 'ln'].includes(func)) {
      setPendingFunction(func);
      setExpression(func + " ");
      setDisplay('0');
      setIsFirstDigitAfterFunction(true); // Set flag for first digit input
    } else {
      // For other functions, continue with immediate calculation
      try {
        let result;
        const num = parseFloat(display);
        
        switch(func) {
          case 'sqrt':
            result = Math.sqrt(num);
            break;
          case 'square':
            result = Math.pow(num, 2);
            break;
          case 'pow':
            setExpression(display + '^');
            setDisplay('0');
            return;
          case 'reciprocal':
            result = 1 / num;
            break;
          case 'factorial':
            result = factorial(num);
            break;
          case 'exp':
            result = Math.exp(num);
            break;
          default:
            return;
        }
        setDisplay(result.toString());
      } catch (error) {
        setDisplay('Error');
      }
    }
  };

  // Calculate result - updated to add new history items at the beginning of array
  const handleEqualsClick = () => {
    try {
      let result;
      let finalExpression = expression + display;
      
      // Process pending function if exists
      if (pendingFunction) {
        const num = parseFloat(display);
        switch(pendingFunction) {
          case 'sin':
            result = Math.sin(num * Math.PI / 180); // Degrees
            break;
          case 'cos':
            result = Math.cos(num * Math.PI / 180);
            break;
          case 'tan':
            result = Math.tan(num * Math.PI / 180);
            break;
          case 'log':
            result = Math.log10(num);
            break;
          case 'ln':
            result = Math.log(num);
            break;
          default:
            // Should not happen
            break;
        }
        finalExpression = pendingFunction + '(' + display + ')';
        
        // Add to history at the beginning of array
        const newHistory = [{ expression: finalExpression, result }, ...history];
        setHistory(newHistory);
        
        setDisplay(result.toString());
        setExpression('');
        setPendingFunction(null);
        setIsFirstDigitAfterFunction(false);
      } else {
        // Normal calculation
        result = new Function('return ' + finalExpression)();
        
        // Add to history at the beginning of array
        const newHistory = [{ expression: finalExpression, result }, ...history];
        setHistory(newHistory);
        
        setDisplay(result.toString());
        setExpression('');
      }
    } catch (error) {
      setDisplay('Error');
      setPendingFunction(null);
      setIsFirstDigitAfterFunction(false);
    }
  };

  // Clear display and expression - also clear pending function and first digit flag
  const handleClearClick = () => {
    setDisplay('0');
    setExpression('');
    setPendingFunction(null);
    setIsFirstDigitAfterFunction(false);
  };

  // Remove last character
  const handleBackspaceClick = () => {
    if (display.length === 1 || display === 'Error') {
      setDisplay('0');
    } else {
      setDisplay(display.slice(0, -1));
    }
  };

  // Add decimal point
  const handleDecimalClick = () => {
    if (!display.includes('.')) {
      setDisplay(display + '.');
    }
  };

  // Memory functions
  const handleMemoryAdd = () => {
    setMemory(memory + parseFloat(display));
  };

  const handleMemorySubtract = () => {
    setMemory(memory - parseFloat(display));
  };

  const handleMemoryRecall = () => {
    setDisplay(memory.toString());
  };

  const handleMemoryClear = () => {
    setMemory(0);
  };

  // Helper function for factorial
  const factorial = (n) => {
    if (n < 0) return NaN;
    if (n === 0) return 1;
    let result = 1;
    for (let i = 1; i <= n; i++) {
      result *= i;
    }
    return result;
  };

  // Toggle positive/negative
  const toggleSign = () => {
    setDisplay(parseFloat(display) * -1 + '');
  };

  // Handle percentage
  const handlePercentage = () => {
    setDisplay((parseFloat(display) / 100).toString());
  };

  // Switch between standard and scientific calculator
  const handleTabChange = (event, newValue) => {
    setCalculatorType(newValue);
  };

  // Add keyboard event handling
  useEffect(() => {
    const handleKeyDown = (event) => {
      // Numbers
      if (/^[0-9]$/.test(event.key)) {
        handleNumberClick(parseInt(event.key));
      } 
      // Operators
      else if (['+', '-', '*', '/'].includes(event.key)) {
        handleOperatorClick(event.key);
      }
      // Equals/Enter
      else if (event.key === 'Enter' || event.key === '=') {
        handleEqualsClick();
      }
      // Decimal
      else if (event.key === '.') {
        handleDecimalClick();
      }
      // Backspace
      else if (event.key === 'Backspace') {
        handleBackspaceClick();
      }
      // Clear/Escape
      else if (event.key === 'Escape' || event.key === 'c' || event.key === 'C') {
        handleClearClick();
      }
      // Parentheses
      else if (event.key === '(') {
        handleOperatorClick('(');
      }
      else if (event.key === ')') {
        handleOperatorClick(')');
      }
      // Percent
      else if (event.key === '%') {
        handlePercentage();
      }
    };

    // Add and remove event listener
    document.addEventListener('keydown', handleKeyDown);
    return () => {
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [display, expression]); // Dependencies ensure we have current state values
  
  // Updated standard calculator with better layout control
  const renderStandardCalculator = () => (
    <Box sx={{ 
      display: 'grid', 
      gridTemplateColumns: 'repeat(4, 1fr)', 
      gap: 1, 
      mt: 2 
    }}>
      {/* Memory row */}
      <Button variant="outlined" onClick={handleMemoryClear}>MC</Button>
      <Button variant="outlined" onClick={handleMemoryRecall}>MR</Button>
      <Button variant="outlined" onClick={handleMemoryAdd}>M+</Button>
      <Button variant="outlined" onClick={handleMemorySubtract}>M-</Button>
      
      {/* Clear/Operation row */}
      <Button variant="contained" onClick={handleClearClick}>C</Button>
      <Button variant="outlined" onClick={toggleSign}>±</Button>
      <Button variant="outlined" onClick={handlePercentage}>%</Button>
      <Button variant="contained" color="primary" onClick={() => handleOperatorClick('/')}>÷</Button>
      
      {/* First row of numbers: 7-8-9 */}
      <Button variant="outlined" onClick={() => handleNumberClick(7)}>7</Button>
      <Button variant="outlined" onClick={() => handleNumberClick(8)}>8</Button>
      <Button variant="outlined" onClick={() => handleNumberClick(9)}>9</Button>
      <Button variant="contained" color="primary" onClick={() => handleOperatorClick('*')}>×</Button>
      
      {/* Second row of numbers: 4-5-6 */}
      <Button variant="outlined" onClick={() => handleNumberClick(4)}>4</Button>
      <Button variant="outlined" onClick={() => handleNumberClick(5)}>5</Button>
      <Button variant="outlined" onClick={() => handleNumberClick(6)}>6</Button>
      <Button variant="contained" color="primary" onClick={() => handleOperatorClick('-')}>-</Button>
      
      {/* Third row of numbers: 1-2-3 */}
      <Button variant="outlined" onClick={() => handleNumberClick(1)}>1</Button>
      <Button variant="outlined" onClick={() => handleNumberClick(2)}>2</Button>
      <Button variant="outlined" onClick={() => handleNumberClick(3)}>3</Button>
      <Button variant="contained" color="primary" onClick={() => handleOperatorClick('+')}>+</Button>
      
      {/* Fourth row: 0, decimal and equals */}
      <Button variant="outlined" onClick={() => handleNumberClick(0)} sx={{ gridColumn: 'span 2' }}>0</Button>
      <Button variant="outlined" onClick={handleDecimalClick}>.</Button>
      <Button variant="contained" color="secondary" onClick={handleEqualsClick}>=</Button>
    </Box>
  );

  // Updated scientific calculator with better, more logical layout
  const renderScientificCalculator = () => (
    <Box sx={{ 
      display: 'grid', 
      gridTemplateColumns: 'repeat(4, 1fr)', 
      gap: 1, 
      mt: 2 
    }}>
      {/* Memory and Clear functions row */}
      <Button variant="outlined" onClick={handleMemoryClear}>MC</Button>
      <Button variant="outlined" onClick={handleMemoryRecall}>MR</Button>
      <Button variant="outlined" onClick={handleMemoryAdd}>M+</Button>
      <Button variant="outlined" onClick={handleMemorySubtract}>M-</Button>
      
      {/* Function row */}
      <Button variant="outlined" onClick={() => handleScientificFunction('factorial')}>x!</Button>
      <Button variant="outlined" onClick={() => handleOperatorClick('(')}>(</Button>
      <Button variant="outlined" onClick={() => handleOperatorClick(')')}>)</Button>
      <Button variant="contained" onClick={handleClearClick}>C</Button>
      
      {/* Advanced function row - now with function-first workflow for trig functions */}
      <Button 
        variant={pendingFunction === 'sin' ? "contained" : "outlined"} 
        color={pendingFunction === 'sin' ? "secondary" : "default"}
        onClick={() => handleScientificFunction('sin')}
      >
        sin
      </Button>
      <Button 
        variant={pendingFunction === 'cos' ? "contained" : "outlined"} 
        color={pendingFunction === 'cos' ? "secondary" : "default"}
        onClick={() => handleScientificFunction('cos')}
      >
        cos
      </Button>
      <Button 
        variant={pendingFunction === 'tan' ? "contained" : "outlined"} 
        color={pendingFunction === 'tan' ? "secondary" : "default"}
        onClick={() => handleScientificFunction('tan')}
      >
        tan
      </Button>
      <Button variant="contained" color="primary" onClick={() => handleOperatorClick('/')}>÷</Button>
      
      {/* Power functions row */}
      <Button variant="outlined" onClick={() => handleScientificFunction('square')}>x²</Button>
      <Button variant="outlined" onClick={() => handleScientificFunction('sqrt')}>√x</Button>
      <Button variant="outlined" onClick={handlePercentage}>%</Button>
      <Button variant="contained" color="primary" onClick={() => handleOperatorClick('*')}>×</Button>
      
      {/* First row of numbers: 7-8-9 */}
      <Button variant="outlined" onClick={() => handleNumberClick(7)}>7</Button>
      <Button variant="outlined" onClick={() => handleNumberClick(8)}>8</Button>
      <Button variant="outlined" onClick={() => handleNumberClick(9)}>9</Button>
      <Button variant="contained" color="primary" onClick={() => handleOperatorClick('-')}>-</Button>
      
      {/* Second row of numbers: 4-5-6 */}
      <Button variant="outlined" onClick={() => handleNumberClick(4)}>4</Button>
      <Button variant="outlined" onClick={() => handleNumberClick(5)}>5</Button>
      <Button variant="outlined" onClick={() => handleNumberClick(6)}>6</Button>
      <Button variant="contained" color="primary" onClick={() => handleOperatorClick('+')}>+</Button>
      
      {/* Log functions and 1-2-3 */}
      <Button 
        variant={pendingFunction === 'log' ? "contained" : "outlined"} 
        color={pendingFunction === 'log' ? "secondary" : "default"}
        onClick={() => handleScientificFunction('log')}
      >
        log
      </Button>
      <Button variant="outlined" onClick={() => handleNumberClick(1)}>1</Button>
      <Button variant="outlined" onClick={() => handleNumberClick(2)}>2</Button>
      <Button variant="outlined" onClick={() => handleNumberClick(3)}>3</Button>
      
      {/* Bottom row - log, 0, ., = */}
      <Button 
        variant={pendingFunction === 'ln' ? "contained" : "outlined"} 
        color={pendingFunction === 'ln' ? "secondary" : "default"}
        onClick={() => handleScientificFunction('ln')}
      >
        ln
      </Button>
      <Button variant="outlined" onClick={toggleSign}>±</Button>
      <Button variant="outlined" onClick={() => handleNumberClick(0)}>0</Button>
      <Button variant="outlined" onClick={handleDecimalClick}>.</Button>
      <Button variant="contained" color="secondary" onClick={handleEqualsClick} sx={{ gridColumn: 'span 1' }}>
        =
      </Button>
    </Box>
  );

  // Display calculator history
  const renderHistory = () => (
    <Box sx={{ mt: 2, maxHeight: '200px', overflow: 'auto' }}>
      <Typography variant="subtitle1">History</Typography>
      {history.map((item, index) => (
        <Paper key={index} sx={{ p: 1, mb: 1 }}>
          <Typography variant="body2">{item.expression} = {item.result}</Typography>
        </Paper>
      ))}
    </Box>
  );

  // Updated clear history function to also clear localStorage
  const clearHistory = () => {
    setHistory([]);
    // Clear from localStorage too
    localStorage.removeItem('calculatorHistory');
  };

  return (
    <Container maxWidth="sm">
      <Paper elevation={3} sx={{ p: 3, my: 4 }}>
        <Typography variant="h4" gutterBottom align="center">
          Calculator
        </Typography>
        
        <Tabs value={calculatorType} onChange={handleTabChange} centered>
          <Tab label="Standard" />
          <Tab label="Scientific" />
        </Tabs>
        
        <Box sx={{ p: 2 }}>
          <Paper elevation={2} sx={{ p: 2, mb: 2, textAlign: 'right' }}>
            <Typography variant="body2" color="text.secondary">
              {expression}
            </Typography>
            <Typography variant="h4">
              {display}
            </Typography>
          </Paper>
          
          {calculatorType === 0 ? renderStandardCalculator() : renderScientificCalculator()}
          
          <Box sx={{ display: 'flex', justifyContent: 'flex-end', mt: 2 }}>
            <IconButton onClick={handleBackspaceClick}>
              <Backspace />
            </IconButton>
            <IconButton onClick={clearHistory}>
              <History />
            </IconButton>
          </Box>
          
          {history.length > 0 && renderHistory()}
        </Box>
      </Paper>
    </Container>
  );
}

export default Calculator;
