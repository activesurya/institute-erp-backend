import { create } from 'zustand';

const useStudentStore = create((set) => ({
  students: [],
  isLoading: false,
  error: null,

  setStudents: (students) => set({ students, error: null }),
  addStudent: (student) =>
    set((state) => ({ students: [...state.students, student] })),
  updateStudent: (id, updatedStudent) =>
    set((state) => ({
      students: state.students.map((s) => (s.id === id ? updatedStudent : s)),
    })),
  removeStudent: (id) =>
    set((state) => ({
      students: state.students.filter((s) => s.id !== id),
    })),

  setLoading: (isLoading) => set({ isLoading }),
  setError: (error) => set({ error }),
  clearError: () => set({ error: null }),
}));

export default useStudentStore;
