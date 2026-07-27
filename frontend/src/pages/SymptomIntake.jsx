import { Link } from "react-router-dom";

function SymptomIntake() {
  return (
    <main className="page-container">
      <h1>Symptom Intake</h1>
      <p>The symptom intake form will be added next.</p>

      <Link to="/patient/dashboard">
        Return to dashboard
      </Link>
    </main>
  );
}

export default SymptomIntake;