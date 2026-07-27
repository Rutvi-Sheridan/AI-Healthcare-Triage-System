import { Link } from "react-router-dom";

function PatientAppointments() {
  return (
    <main className="page-container">
      <h1>Appointments</h1>
      <p>The appointment request page will be added later.</p>

      <Link to="/patient/dashboard">
        Return to dashboard
      </Link>
    </main>
  );
}

export default PatientAppointments;