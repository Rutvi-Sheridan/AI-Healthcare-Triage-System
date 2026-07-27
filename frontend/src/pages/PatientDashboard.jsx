  import { useEffect, useState } from "react";
  import { Link, useNavigate } from "react-router-dom";
  import {
    getCurrentUser,
    logoutUser,
  } from "../services/authService";
  import { getPatientTriageCases } from "../services/triageService";
  import "./PatientDashboard.css";

  function PatientDashboard() {
    const navigate = useNavigate();

    const [user] = useState(() => getCurrentUser());
    const [cases, setCases] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
      if (!user) {
        navigate("/login", { replace: true });
        return;
      }

      if (user.role !== "PATIENT") {
        navigate("/login", { replace: true });
        return;
      }

      const loadCases = async () => {
        try {
          setLoading(true);
          setError("");

          const caseData = await getPatientTriageCases(
            user.patientId
          );

          setCases(Array.isArray(caseData) ? caseData : []);
        } catch (requestError) {
          setError(
            requestError.message ||
              "Unable to load your triage history."
          );
        } finally {
          setLoading(false);
        }
      };

      loadCases();
    }, [navigate, user]);

    const handleLogout = () => {
      logoutUser();
      navigate("/login", { replace: true });
    };

    const getUrgencyClass = (urgencyLevel) => {
      switch (urgencyLevel) {
        case "EMERGENCY":
          return "urgency-emergency";

        case "HIGH":
          return "urgency-high";

        case "MEDIUM":
          return "urgency-medium";

        case "LOW":
          return "urgency-low";

        default:
          return "urgency-unknown";
      }
    };

    const formatDate = (dateValue) => {
      if (!dateValue) {
        return "Date unavailable";
      }

      return new Date(dateValue).toLocaleString("en-CA", {
        year: "numeric",
        month: "short",
        day: "numeric",
        hour: "numeric",
        minute: "2-digit",
      });
    };

    const formatText = (value) => {
      if (!value) {
        return "Not available";
      }

      return value
        .replaceAll("_", " ")
        .toLowerCase()
        .replace(/\b\w/g, (letter) => letter.toUpperCase());
    };

    const recentCases = cases.slice(0, 5);

    const pendingCases = cases.filter(
      (triageCase) => triageCase.status === "PENDING"
    ).length;

    const emergencyCases = cases.filter(
      (triageCase) =>
        triageCase.urgencyLevel === "EMERGENCY"
    ).length;

    if (!user) {
      return null;
    }

    return (
      <main className="patient-dashboard">
        <header className="dashboard-header">
          <div>
            <p className="dashboard-eyebrow">Patient portal</p>

            <h1>Welcome, {user.name || "Patient"}</h1>

            <p>
              Submit symptoms, review your triage history,
              and manage your care requests.
            </p>
          </div>

          <button
            type="button"
            className="logout-button"
            onClick={handleLogout}
          >
            Logout
          </button>
        </header>

        <section
          className="medical-disclaimer"
          aria-label="Medical disclaimer"
        >
          <strong>Medical disclaimer:</strong> This system
          provides intake and routing support only. It does not
          diagnose medical conditions or replace professional
          medical care. Call emergency services for an immediate
          medical emergency.
        </section>

        <section className="dashboard-actions">
          <Link
            to="/patient/symptom-intake"
            className="dashboard-action-card primary-action"
          >
            <span className="action-icon" aria-hidden="true">
              +
            </span>

            <div>
              <h2>Start symptom intake</h2>
              <p>
                Enter or speak your symptoms to create a new
                triage case.
              </p>
            </div>
          </Link>

          <Link
            to="/patient/history"
            className="dashboard-action-card"
          >
            <span className="action-icon" aria-hidden="true">
              H
            </span>

            <div>
              <h2>View case history</h2>
              <p>
                Review previous urgency results and
                recommendations.
              </p>
            </div>
          </Link>

          <Link
            to="/patient/appointments"
            className="dashboard-action-card"
          >
            <span className="action-icon" aria-hidden="true">
              A
            </span>

            <div>
              <h2>Appointments</h2>
              <p>
                Request or review an appointment with the clinic.
              </p>
            </div>
          </Link>
        </section>

        <section className="dashboard-statistics">
          <article className="stat-card">
            <span>Total cases</span>
            <strong>{cases.length}</strong>
          </article>

          <article className="stat-card">
            <span>Pending review</span>
            <strong>{pendingCases}</strong>
          </article>

          <article className="stat-card">
            <span>Emergency warnings</span>
            <strong>{emergencyCases}</strong>
          </article>
        </section>

        <section className="recent-cases-section">
          <div className="section-heading-row">
            <div>
              <p className="dashboard-eyebrow">
                Triage activity
              </p>

              <h2>Recent cases</h2>
            </div>

            {cases.length > 0 && (
              <Link
                to="/patient/history"
                className="text-link"
              >
                View all cases
              </Link>
            )}
          </div>

          {loading && (
            <div className="dashboard-message">
              Loading your triage history...
            </div>
          )}

          {!loading && error && (
            <div className="dashboard-message error-message">
              {error}
            </div>
          )}

          {!loading && !error && recentCases.length === 0 && (
            <div className="empty-state">
              <h3>No triage cases yet</h3>

              <p>
                Start a symptom intake to receive an initial
                urgency recommendation.
              </p>

              <Link
                to="/patient/symptom-intake"
                className="start-intake-button"
              >
                Start symptom intake
              </Link>
            </div>
          )}

          {!loading && !error && recentCases.length > 0 && (
            <div className="case-list">
              {recentCases.map((triageCase) => (
                <article
                  className="case-card"
                  key={triageCase.caseId}
                >
                  <div className="case-card-heading">
                    <div>
                      <p className="case-number">
                        Case #{triageCase.caseId}
                      </p>

                      <h3>
                        {triageCase.chiefComplaint}
                      </h3>
                    </div>

                    <span
                      className={`urgency-badge ${getUrgencyClass(
                        triageCase.urgencyLevel
                      )}`}
                    >
                      {formatText(
                        triageCase.urgencyLevel
                      )}
                    </span>
                  </div>

                  <div className="case-details">
                    <div>
                      <span>Submitted</span>
                      <strong>
                        {formatDate(triageCase.caseDate)}
                      </strong>
                    </div>

                    <div>
                      <span>Status</span>
                      <strong>
                        {formatText(triageCase.status)}
                      </strong>
                    </div>
                  </div>

                  <div className="recommendation-box">
                    <span>Recommendation</span>

                    <p>
                      {triageCase.recommendation ||
                        "Recommendation unavailable."}
                    </p>
                  </div>

                  {triageCase.redFlagFound && (
                    <div className="red-flag-warning">
                      A potentially serious symptom was detected
                      in this intake.
                    </div>
                  )}

                  <Link
                    to={`/patient/cases/${triageCase.caseId}`}
                    className="case-details-link"
                  >
                    View case details
                  </Link>
                </article>
              ))}
            </div>
          )}
        </section>
      </main>
    );
  }

  export default PatientDashboard;