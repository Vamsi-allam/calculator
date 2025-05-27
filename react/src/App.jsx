import './App.css';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Button, Container, Box } from '@mui/material';
import { CalculateOutlined, HomeOutlined, InfoOutlined } from '@mui/icons-material';
import Calculator from './components/Calculator';
import Home from './components/Home';

function App() {
  return (
    <Router>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Multi-Function Calculator
          </Typography>
          <Button 
            color="inherit" 
            component={Link} 
            to="/" 
            startIcon={<HomeOutlined />}
          >
            Home
          </Button>
          <Button 
            color="inherit" 
            component={Link} 
            to="/calculator" 
            startIcon={<CalculateOutlined />}
          >
            Calculator
          </Button>
          <Button 
            color="inherit" 
            component={Link} 
            to="/about" 
            startIcon={<InfoOutlined />}
          >
            About
          </Button>
        </Toolbar>
      </AppBar>

      <Container sx={{ mt: 4, mb: 4 }}>
        <Routes>
          <Route path="/calculator" element={<Calculator />} />
          <Route path="/about" element={
            <Box sx={{ p: 3 }}>
              <Typography variant="h4" gutterBottom>About</Typography>
              <Typography variant="body1">
                This is a multi-function calculator application built with React and Material UI.
                It provides both standard and scientific calculator functionality with memory operations
                and calculation history.
              </Typography>
            </Box>
          } />
          <Route path="/" element={<Home />} />
        </Routes>
      </Container>
    </Router>
  );
}

export default App;
