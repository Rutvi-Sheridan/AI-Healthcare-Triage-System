import "./App.css";

import {
  Navigate,
  Route,
  Routes,
} from "react-router-dom";

import Login from "./pages/Login";
import PatientDashboard from "./pages/PatientDashboard";
import StaffDashboard from "./pages/StaffDashboard";
import AdminDashboard from "./pages/AdminDashboard";
import SymptomIntake from "./pages/SymptomIntake";
import PatientHistory from "./pages/PatientHistory";
import PatientAppointments from "./pages/PatientAppointments";

function App() {
  return (
    <Routes>
      <Route
        path="/"
        element={<Navigate to="/login" replace />}
      />

      <Route path="/login" element={<Login />} />

      <Route
        path="/patient/dashboard"
        element={<PatientDashboard />}
      />

      <Route
        path="/patient/symptom-intake"
        element={<SymptomIntake />}
      />

      <Route
        path="/patient/history"
        element={<PatientHistory />}
      />

      <Route
        path="/patient/appointments"
        element={<PatientAppointments />}
      />

      <Route
        path="/staff/dashboard"
        element={<StaffDashboard />}
      />

      <Route
        path="/admin/dashboard"
        element={<AdminDashboard />}
      />

      <Route
        path="*"
        element={<Navigate to="/login" replace />}
      />
    </Routes>
  );
}

export default App;