import { useEffect, useMemo, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getCurrentUser } from "../services/authService";
import { getPatientTriageCases } from "../services/triageService";
import "./PatientHistory.css";

function PatientHistory() {
  const navigate = useNavigate();
  const [user] = useState(() => getCurrentUser());

  const [cases, setCases] = useState([]);
  const [statusFilter, setStatusFilter] = useState("ALL");
  const [urgencyFilter, setUrgencyFilter] = useState("ALL");
  const [searchText, setSearchText] = useState("");
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

        const data = await getPatientTriageCases(
          user.patientId
        );

        setCases(Array.isArray(data) ? data : []);
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

  const filteredCases = useMemo(() => {
    return cases.filter((triageCase) => {
      const matchesStatus =
        statusFilter === "ALL" ||
        triageCase.status === statusFilter;

      const matchesUrgency =
        urgencyFilter === "ALL" ||
        triageCase.urgencyLevel === urgencyFilter;

      const normalizedSearch =
        searchText.trim().toLowerCase();

      const matchesSearch =
        !normalizedSearch ||
        triageCase.chiefComplaint
          ?.toLowerCase()
          .includes(normalizedSearch) ||
        triageCase.recommendation
          ?.toLowerCase()
          .includes(normalizedSearch) ||
        String(triageCase.caseId).includes(
          normalizedSearch
        );

      return (
        matchesStatus &&
        matchesUrgency &&
        matchesSearch
      );
    });
  }, [
    cases,
    searchText,
    statusFilter,
    urgencyFilter,
  ]);

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

  const formatDate = (dateValue) => {
    if (!dateValue) {
      return "Date unavailable";
    }

    return new Date(dateValue).toLocaleString(
      "en-CA",
      {
        year: "numeric",
        month: "short",
        day: "numeric",
        hour: "numeric",
        minute: "2-digit",
      }
    );
  };

  const getUrgencyClass = (urgencyLevel) => {
    switch (urgencyLevel) {
      case "EMERGENCY":
        return "history-urgency-emergency";

      case "HIGH":
        return "history-urgency-high";

      case "MEDIUM":
        return "history-urgency-medium";

      case "LOW":
        return "history-urgency-low";

      default:
        return "history-urgency-unknown";
    }
  };

  return (
    <main className="patient-history-page">
      <section className="history-header">
        <div>
          <p className="history-eyebrow">
            Patient portal
          </p>

          <h1>Triage history</h1>

          <p>
            Review your submitted symptoms, urgency levels,
            recommendations, and case status.
          </p>
        </div>

        <Link
          to="/patient/symptom-intake"
          className="new-intake-button"
        >
          Start new intake
        </Link>
      </section>

      <section className="history-disclaimer">
        This history is for intake and routing support only.
        It does not provide a medical diagnosis or replace
        professional healthcare advice.
      </section>

      <section className="history-filters">
        <div className="history-filter-group history-search">
          <label htmlFor="historySearch">
            Search cases
          </label>

          <input
            id="historySearch"
            type="search"
            value={searchText}
            onChange={(event) =>
              setSearchText(event.target.value)
            }
            placeholder="Search symptoms or recommendations"
          />
        </div>

        <div className="history-filter-group">
          <label htmlFor="statusFilter">
            Status
          </label>

          <select
            id="statusFilter"
            value={statusFilter}
            onChange={(event) =>
              setStatusFilter(event.target.value)
            }
          >
            <option value="ALL">All statuses</option>
            <option value="PENDING">Pending</option>
            <option value="IN_PROGRESS">
              In Progress
            </option>
            <option value="COMPLETED">
              Completed
            </option>
            <option value="CANCELLED">
              Cancelled
            </option>
          </select>
        </div>

        <div className="history-filter-group">
          <label htmlFor="urgencyFilter">
            Urgency
          </label>

          <select
            id="urgencyFilter"
            value={urgencyFilter}
            onChange={(event) =>
              setUrgencyFilter(event.target.value)
            }
          >
            <option value="ALL">All urgency levels</option>
            <option value="LOW">Low</option>
            <option value="MEDIUM">Medium</option>
            <option value="HIGH">High</option>
            <option value="EMERGENCY">
              Emergency
            </option>
          </select>
        </div>
      </section>

      <div className="history-results-summary">
        Showing {filteredCases.length} of {cases.length} cases
      </div>

      {loading && (
        <section className="history-message">
          Loading triage history...
        </section>
      )}

      {!loading && error && (
        <section
          className="history-message history-error"
          role="alert"
        >
          {error}
        </section>
      )}

      {!loading &&
        !error &&
        filteredCases.length === 0 && (
          <section className="history-empty-state">
            <h2>No matching cases found</h2>

            <p>
              Change the filters or start a new symptom
              intake.
            </p>

            <Link
              to="/patient/symptom-intake"
              className="new-intake-button"
            >
              Start symptom intake
            </Link>
          </section>
        )}

      {!loading &&
        !error &&
        filteredCases.length > 0 && (
          <section className="history-case-list">
            {filteredCases.map((triageCase) => (
              <article
                className="history-case-card"
                key={triageCase.caseId}
              >
                <div className="history-card-header">
                  <div>
                    <p className="history-case-number">
                      Case #{triageCase.caseId}
                    </p>

                    <h2>
                      {triageCase.chiefComplaint ||
                        "Symptoms unavailable"}
                    </h2>

                    <p className="history-case-date">
                      Submitted{" "}
                      {formatDate(triageCase.caseDate)}
                    </p>
                  </div>

                  <div className="history-badges">
                    <span
                      className={`history-urgency-badge ${getUrgencyClass(
                        triageCase.urgencyLevel
                      )}`}
                    >
                      {formatText(
                        triageCase.urgencyLevel
                      )}
                    </span>

                    <span className="history-status-badge">
                      {formatText(triageCase.status)}
                    </span>
                  </div>
                </div>

                <div className="history-recommendation">
                  <span>Recommendation</span>

                  <p>
                    {triageCase.recommendation ||
                      "No recommendation is available."}
                  </p>
                </div>

                {triageCase.redFlagFound && (
                  <div className="history-red-flag">
                    Potentially serious symptoms were detected
                    in this intake.
                  </div>
                )}

                <div className="history-card-footer">
                  <Link
                    to={`/patient/cases/${triageCase.caseId}`}
                    state={{ triageCase }}
                    className="history-details-link"
                  >
                    View full details
                  </Link>
                </div>
              </article>
            ))}
          </section>
        )}

      <div className="history-footer-link">
        <Link to="/patient/dashboard">
          Return to patient dashboard
        </Link>
      </div>
    </main>
  );
}

export default PatientHistory;
