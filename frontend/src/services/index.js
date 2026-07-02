import api from './api';

export const authService = {
  login: (username, password) => {
    return api.post('/auth/login', { username, password });
  },

  register: (userData) => {
    return api.post('/auth/register', userData);
  },

  validateToken: (token) => {
    return api.get('/auth/validate-token', {
      headers: { Authorization: `Bearer ${token}` },
    });
  },

  changePassword: (oldPassword, newPassword) => {
    return api.post('/auth/change-password', {
      oldPassword,
      newPassword,
      confirmPassword: newPassword,
    });
  },
};

export const studentService = {
  getAll: () => api.get('/students'),
  getById: (id) => api.get(`/students/${id}`),
  create: (studentData) => api.post('/students', studentData),
  update: (id, studentData) => api.put(`/students/${id}`, studentData),
  delete: (id) => api.delete(`/students/${id}`),
  deactivate: (id) => api.put(`/students/${id}/deactivate`),
  activate: (id) => api.put(`/students/${id}/activate`),
};

export const courseService = {
  getAll: () => api.get('/courses'),
  getById: (id) => api.get(`/courses/${id}`),
  create: (courseData) => api.post('/courses', courseData),
  update: (id, courseData) => api.put(`/courses/${id}`, courseData),
  delete: (id) => api.delete(`/courses/${id}`),
};

export const batchService = {
  getAll: () => api.get('/batches'),
  getById: (id) => api.get(`/batches/${id}`),
  create: (batchData) => api.post('/batches', batchData),
  update: (id, batchData) => api.put(`/batches/${id}`, batchData),
  delete: (id) => api.delete(`/batches/${id}`),
};

export const departmentService = {
  getAll: () => api.get('/departments'),
  getById: (id) => api.get(`/departments/${id}`),
  create: (departmentData) => api.post('/departments', departmentData),
  update: (id, departmentData) => api.put(`/departments/${id}`, departmentData),
  delete: (id) => api.delete(`/departments/${id}`),
};

export const attendanceService = {
  getAll: () => api.get('/attendance'),
  getById: (id) => api.get(`/attendance/${id}`),
  getByStudent: (studentId) => api.get(`/attendance/student/${studentId}`),
  create: (attendanceData) => api.post('/attendance', attendanceData),
  update: (id, attendanceData) => api.put(`/attendance/${id}`, attendanceData),
  markBatchAttendance: (batchId, date, presentStudentIds) =>
    api.post(`/attendance/batch/${batchId}/mark-attendance?date=${date}`, {
      presentStudentIds,
    }),
  delete: (id) => api.delete(`/attendance/${id}`),
  getPercentage: (studentId) => api.get(`/attendance/student/${studentId}/percentage`),
};
