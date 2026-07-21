import { Link } from "react-router-dom";

function Home() {
  return (
    <main className="page-container">
      <section className="hero">
        <div className="hero-content">
          <p className="eyebrow">AI-supported patient intake</p>

          <h1>Understand the next step for your symptoms</h1>

          <p>
            Enter or speak your symptoms, receive an initial urgency
            recommendation, and request an appointment with the clinic.
          </p>

          <div className="hero-actions">
            <Link to="/register" className="button primary-button">
              Create Patient Account
            </Link>

            <Link to="/login" className="button secondary-button">
              Login
            </Link>
          </div>
        </div>
      </section>

      <section className="disclaimer-box">
        <strong>Medical disclaimer:</strong> This tool does not diagnose
        medical conditions or replace professional medical advice. Call
        emergency services immediately for a medical emergency.
      </section>
    </main>
  );
}

export default Home;