import { Link, useLocation } from "react-router-dom";
import "./TriageResult.css";

function TriageResult() {
  const location = useLocation();
  const triageCase = location.state?.triageCase;

  const formatText = (value) => {
    if (!value) {
      return "Not available";
    }

    return value
      .replaceAll("_", " ")
      .toLowerCase()
      .replace(/\b\w/g, (letter) =>
        letter.toUpperCase()
      );
  };

  if (!triageCase) {
    return (
      <main className="triage-result-page">
        <section className="triage-result-card">
          <h1>Result unavailable</h1>

          <p>
            The triage result could not be loaded.
          </p>

          <Link to="/patient/dashboard">
            Return to dashboard
          </Link>
        </section>
      </main>
    );
  }

  return (
    <main className="triage-result-page">
      <section className="triage-result-card">
        <p className="result-eyebrow">
          Intake submitted
        </p>

        <h1>Your initial triage result</h1>

        <div
          className={`result-urgency result-${triageCase.urgencyLevel?.toLowerCase()}`}
        >
          <span>Urgency level</span>

          <strong>
            {formatText(triageCase.urgencyLevel)}
          </strong>
        </div>

        {triageCase.redFlagFound && (
          <div className="result-red-flag">
            Potentially serious symptoms were detected. Seek
            immediate professional medical assistance.
          </div>
        )}

        <div className="result-section">
          <h2>Symptoms submitted</h2>
          <p>{triageCase.chiefComplaint}</p>
        </div>

        <div className="result-section">
          <h2>Recommendation</h2>

          <p>
            {triageCase.recommendation ||
              "No recommendation is available."}
          </p>
        </div>

        <div className="result-disclaimer">
          This is an intake-support recommendation and not a
          medical diagnosis. A healthcare professional should
          review your condition.
        </div>

        <div className="result-actions">
          <Link
            to="/patient/dashboard"
            className="result-secondary-button"
          >
            Return to dashboard
          </Link>

          <Link
            to="/patient/appointments"
            className="result-primary-button"
          >
            Request appointment
          </Link>
        </div>
      </section>
    </main>
  );
}

export default TriageResult;