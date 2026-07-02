import { useEffect, useState } from 'react';
import { Container, Box, Typography, Grid, Card, CardContent } from '@mui/material';
import { studentService, courseService, batchService, departmentService } from '../services';
import toast from 'react-hot-toast';
import useAuthStore from '../store/authStore';

const Dashboard = () => {
  const { user } = useAuthStore();
  const [stats, setStats] = useState({
    students: 0,
    courses: 0,
    batches: 0,
    departments: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const [studentsRes, coursesRes, batchesRes, departmentsRes] = await Promise.all([
          studentService.getAll(),
          courseService.getAll(),
          batchService.getAll(),
          departmentService.getAll(),
        ]);

        setStats({
          students: studentsRes.data.length,
          courses: coursesRes.data.length,
          batches: batchesRes.data.length,
          departments: departmentsRes.data.length,
        });
      } catch (error) {
        toast.error('Failed to load dashboard data');
      } finally {
        setLoading(false);
      }
    };

    fetchStats();
  }, []);

  const StatCard = ({ title, value, color }) => (
    <Card sx={{ backgroundColor: color, color: 'white' }}>
      <CardContent>
        <Typography color="inherit" gutterBottom>
          {title}
        </Typography>
        <Typography variant="h4" color="inherit">
          {value}
        </Typography>
      </CardContent>
    </Card>
  );

  if (loading) {
    return (
      <Container>
        <Box sx={{ padding: 4, textAlign: 'center' }}>
          <Typography>Loading...</Typography>
        </Box>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg">
      <Box sx={{ padding: 4 }}>
        <Typography variant="h4" sx={{ marginBottom: 3 }}>
          Welcome, {user?.username}!
        </Typography>
        <Typography variant="body1" sx={{ marginBottom: 4, color: 'gray' }}>
          Institute Management System Dashboard
        </Typography>

        <Grid container spacing={3}>
          <Grid item xs={12} sm={6} md={3}>
            <StatCard title="Total Students" value={stats.students} color="#1976d2" />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <StatCard title="Total Courses" value={stats.courses} color="#388e3c" />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <StatCard title="Total Batches" value={stats.batches} color="#f57c00" />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <StatCard title="Total Departments" value={stats.departments} color="#c2185b" />
          </Grid>
        </Grid>

        <Box sx={{ marginTop: 4 }}>
          <Card>
            <CardContent>
              <Typography variant="h6">Quick Stats</Typography>
              <Typography variant="body2" sx={{ marginTop: 1 }}>
                📊 Monitor your institute's performance with real-time analytics
              </Typography>
              <Typography variant="body2" sx={{ marginTop: 1 }}>
                👥 Manage students, courses, batches, and departments efficiently
              </Typography>
              <Typography variant="body2" sx={{ marginTop: 1 }}>
                ✓ Track attendance and generate detailed reports
              </Typography>
            </CardContent>
          </Card>
        </Box>
      </Box>
    </Container>
  );
};

export default Dashboard;
