import { Link } from "react-router-dom";

function PatientHistory() {
  return (
    <main className="page-container">
      <h1>Triage History</h1>
      <p>The complete triage history page will be added next.</p>

      <Link to="/patient/dashboard">
        Return to dashboard
      </Link>
    </main>
  );
}

export default PatientHistory;