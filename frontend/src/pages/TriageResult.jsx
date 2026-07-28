import { useEffect, useState } from "react";
import {
  Link,
  useLocation,
  useParams,
} from "react-router-dom";
import { getTriageCase } from "../services/triageService";
import "./TriageResult.css";

function TriageResult() {
  const { caseId } = useParams();
  const location = useLocation();

  const [triageCase, setTriageCase] = useState(
    location.state?.triageCase || null
  );

  const [loading, setLoading] = useState(
    !location.state?.triageCase
  );

  const [error, setError] = useState("");

  useEffect(() => {
    if (triageCase) {
      return;
    }

    const loadCase = async () => {
      try {
        setLoading(true);
        setError("");

        const data = await getTriageCase(caseId);
        setTriageCase(data);
      } catch (requestError) {
        setError(
          requestError.message ||
            "Unable to load this triage case."
        );
      } finally {
        setLoading(false);
      }
    };

    loadCase();
  }, [caseId, triageCase]);

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

  if (loading) {
    return (
      <main className="triage-result-page">
        <section className="triage-result-card">
          <p>Loading triage case...</p>
        </section>
      </main>
    );
  }

  if (error || !triageCase) {
    return (
      <main className="triage-result-page">
        <section className="triage-result-card">
          <h1>Result unavailable</h1>

          <p>
            {error ||
              "The triage result could not be loaded."}
          </p>

          <Link to="/patient/history">
            Return to triage history
          </Link>
        </section>
      </main>
    );
  }

  return (
    <main className="triage-result-page">
      <section className="triage-result-card">
        <p className="result-eyebrow">
          Triage case #{triageCase.caseId}
        </p>

        <h1>Your triage result</h1>

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

        <div className="result-section">
          <h2>Case status</h2>
          <p>{formatText(triageCase.status)}</p>
        </div>

        <div className="result-disclaimer">
          This is an intake-support recommendation and not a
          medical diagnosis. A healthcare professional should
          review your condition.
        </div>

        <div className="result-actions">
          <Link
            to="/patient/history"
            className="result-secondary-button"
          >
            Return to history
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