import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getCurrentUser } from "../services/authService";
import { createTriageCase } from "../services/triageService";
import "./SymptomIntake.css";

function SymptomIntake() {
  const navigate = useNavigate();
  const user = getCurrentUser();

  const [chiefComplaint, setChiefComplaint] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");

    if (!user?.patientId) {
      setError("Patient account information is unavailable.");
      return;
    }

    if (!chiefComplaint.trim()) {
      setError("Please describe your symptoms.");
      return;
    }

    if (chiefComplaint.trim().length < 10) {
      setError(
        "Please provide more details about your symptoms."
      );
      return;
    }

    try {
      setLoading(true);

      const triageCase = await createTriageCase(
        user.patientId,
        chiefComplaint.trim()
      );

      navigate(`/patient/cases/${triageCase.caseId}`, {
        state: {
          triageCase,
        },
      });
    } catch (requestError) {
      setError(
        requestError.message ||
          "Unable to submit your symptoms."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="symptom-intake-page">
      <section className="symptom-intake-card">
        <div className="intake-heading">
          <p className="intake-eyebrow">Patient intake</p>
          <h1>Describe your symptoms</h1>

          <p>
            Explain what you are experiencing, when it started,
            and whether it is getting better or worse.
          </p>
        </div>

        <div className="intake-disclaimer">
          <strong>Important:</strong> This tool supports intake
          and routing only. It does not provide a diagnosis. For
          severe or life-threatening symptoms, contact emergency
          services immediately.
        </div>

        <form onSubmit={handleSubmit}>
          <label htmlFor="chiefComplaint">
            Symptoms and main concern
          </label>

          <textarea
            id="chiefComplaint"
            name="chiefComplaint"
            value={chiefComplaint}
            onChange={(event) =>
              setChiefComplaint(event.target.value)
            }
            placeholder="Example: I have had a fever, cough, and sore throat for three days. The cough is becoming worse at night."
            rows="9"
            maxLength="1500"
            disabled={loading}
          />

          <div className="character-count">
            {chiefComplaint.length}/1500 characters
          </div>

          <div className="intake-tips">
            <h2>Helpful details to include</h2>

            <ul>
              <li>When the symptoms started</li>
              <li>How severe the symptoms feel</li>
              <li>Whether they are improving or worsening</li>
              <li>Any recent injury, illness, or medication</li>
            </ul>
          </div>

          {error && (
            <p className="intake-error" role="alert">
              {error}
            </p>
          )}

          <div className="intake-actions">
            <Link
              to="/patient/dashboard"
              className="cancel-button"
            >
              Cancel
            </Link>

            <button
              type="submit"
              className="submit-intake-button"
              disabled={loading}
            >
              {loading
                ? "Submitting..."
                : "Submit symptoms"}
            </button>
          </div>
        </form>
      </section>
    </main>
  );
}

export default SymptomIntake;