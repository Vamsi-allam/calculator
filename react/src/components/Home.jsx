import { Container, Typography, Paper, Box, Button } from '@mui/material';
import { CalculateOutlined } from '@mui/icons-material';
import { Link } from 'react-router-dom';

function Home() {
  return (
    <Container maxWidth="md">
      <Paper elevation={3} sx={{ p: 4, my: 4, textAlign: 'center' }}>
        <Typography variant="h3" component="h1" gutterBottom>
          Welcome to Multi-Function Calculator
        </Typography>
        
        <Box sx={{ my: 4 }}>
          <Typography variant="body1" paragraph>
            A powerful calculator with standard and scientific functions.
          </Typography>
          <Typography variant="body1" paragraph>
            Built with React and Material UI to provide a clean, responsive interface.
          </Typography>
        </Box>
        
        <Box sx={{ my: 4 }}>
          <Typography variant="h5" gutterBottom>
            Features:
          </Typography>
          <Typography variant="body1" component="ul" sx={{ textAlign: 'left', maxWidth: '400px', margin: '0 auto' }}>
            <li>Standard Calculator</li>
            <li>Scientific Functions</li>
            <li>Memory Operations</li>
            <li>Calculation History</li>
            <li>Responsive Design</li>
          </Typography>
        </Box>

        <Button 
          component={Link} 
          to="/calculator" 
          variant="contained" 
          color="primary" 
          size="large"
          startIcon={<CalculateOutlined />}
          sx={{ mt: 2 }}
        >
          Open Calculator
        </Button>
      </Paper>
    </Container>
  );
}

export default Home;
