# Institute ERP Frontend

A complete React-based frontend for the Institute Management ERP system.

## Features

- **User Authentication**: Login and registration with JWT tokens
- **Dashboard**: Real-time statistics and quick overview
- **Student Management**: CRUD operations for student records
- **Course Management**: Manage courses and curriculum
- **Batch Management**: Organize students into batches
- **Department Management**: Manage organizational departments
- **Attendance Tracking**: Mark and track student attendance
- **Responsive Design**: Works on desktop and mobile devices

## Prerequisites

- Node.js 16+ and npm
- React 18+
- Vite

## Installation

```bash
cd frontend
npm install
```

## Running the Application

### Development

```bash
npm run dev
```

The application will start on `http://localhost:3000`

### Build for Production

```bash
npm run build
```

## Project Structure

```
src/
├── components/          # Reusable React components
│   ├── Navbar.jsx
│   └── ProtectedRoute.jsx
├── pages/              # Page components
│   ├── Dashboard.jsx
│   ├── LoginPage.jsx
│   ├── RegisterPage.jsx
│   ├── StudentManagement.jsx
│   ├── CourseManagement.jsx
│   ├── BatchManagement.jsx
│   ├── DepartmentManagement.jsx
│   └── AttendanceManagement.jsx
├── services/           # API services
│   ├── api.js         # Axios configuration
│   └── index.js       # Service methods
├── store/             # Zustand state management
│   ├── authStore.js
│   └── studentStore.js
├── App.jsx            # Main app component
├── main.jsx           # Entry point
└── index.css          # Global styles
```

## API Integration

The frontend connects to the backend API at `http://localhost:8080/api`. 
Make sure the backend is running before starting the frontend.

## Environment Configuration

The API base URL is configured in `src/services/api.js`. Update it if your backend runs on a different port.

## Authentication

- JWT tokens are stored in localStorage
- Protected routes require authentication
- Tokens are automatically included in API requests
- Expired tokens trigger automatic logout

## State Management

- **Zustand** is used for global state management
- Authentication state: `useAuthStore`
- Student data state: `useStudentStore`

## UI Components

- **Material-UI (MUI)** for UI components
- **React Router** for navigation
- **Axios** for API calls
- **React Hot Toast** for notifications

## Features Implementation Status

- ✅ Authentication (Login/Register)
- ✅ Dashboard
- ✅ Student Management (CRUD)
- ✅ Course Management (CRUD)
- 🔄 Batch Management (in progress)
- 🔄 Department Management (in progress)
- 🔄 Attendance Management (in progress)

## Development Tips

1. **API Proxy**: Vite is configured to proxy API calls to localhost:8080
2. **Hot Reload**: Changes automatically reload in development
3. **Debugging**: Use React DevTools browser extension
4. **Network**: Check Network tab in browser DevTools for API calls

## Troubleshooting

### CORS Issues
If you get CORS errors, ensure the backend CORS configuration includes your frontend URL.

### API Connection
If API calls fail, check:
1. Backend is running on `http://localhost:8080`
2. Correct API base URL in `src/services/api.js`
3. Network tab in browser DevTools for error details

### Authentication Issues
If you keep getting logged out:
1. Check token expiration in backend (`jwt.expiration`)
2. Verify token is being stored in localStorage
3. Check Authorization header in network requests

## Future Enhancements

- [ ] Attendance marking interface
- [ ] Batch management dashboard
- [ ] Department statistics
- [ ] Advanced reporting
- [ ] User profile management
- [ ] Export to PDF/Excel
- [ ] Dark mode
- [ ] Mobile app version

## Support

For issues, check the main README.md or create an issue on GitHub.
