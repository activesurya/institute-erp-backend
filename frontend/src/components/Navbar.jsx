import { AppBar, Toolbar, Button, Box, Menu, MenuItem, Container } from '@mui/material';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import useAuthStore from '../store/authStore';
import { useState } from 'react';

const Navbar = () => {
  const navigate = useNavigate();
  const { user, logout, token } = useAuthStore();
  const [anchorEl, setAnchorEl] = useState(null);

  const handleMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  if (!token) {
    return null;
  }

  return (
    <AppBar position="sticky">
      <Container maxWidth="lg">
        <Toolbar disableGutters>
          <Box sx={{ flexGrow: 1, display: 'flex', gap: 2 }}>
            <Button
              color="inherit"
              component={RouterLink}
              to="/"
              sx={{ fontSize: '1.1rem', fontWeight: 'bold' }}
            >
              Institute ERP
            </Button>
            <Button color="inherit" component={RouterLink} to="/students">
              Students
            </Button>
            <Button color="inherit" component={RouterLink} to="/courses">
              Courses
            </Button>
            <Button color="inherit" component={RouterLink} to="/batches">
              Batches
            </Button>
            <Button color="inherit" component={RouterLink} to="/departments">
              Departments
            </Button>
            <Button color="inherit" component={RouterLink} to="/attendance">
              Attendance
            </Button>
          </Box>
          <Box>
            {user && (
              <>
                <Button color="inherit" onClick={handleMenu}>
                  {user.username}
                </Button>
                <Menu
                  anchorEl={anchorEl}
                  open={Boolean(anchorEl)}
                  onClose={handleClose}
                >
                  <MenuItem onClick={handleClose}>{user.email}</MenuItem>
                  <MenuItem onClick={handleLogout}>Logout</MenuItem>
                </Menu>
              </>
            )}
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
};

export default Navbar;
