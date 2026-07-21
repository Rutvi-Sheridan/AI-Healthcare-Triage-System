import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

function Login() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    password: "",
    role: "PATIENT",
  });

  const [error, setError] = useState("");

  function handleChange(event) {
    const { name, value } = event.target;

    setFormData((currentData) => ({
      ...currentData,
      [name]: value,
    }));
  }

  function handleSubmit(event) {
    event.preventDefault();
    setError("");

    if (!formData.email.trim() || !formData.password.trim()) {
      setError("Email and password are required.");
      return;
    }

    // Temporary role navigation until the Spring Boot API is connected.
    if (formData.role === "PATIENT") {
      navigate("/patient");
    } else if (
      formData.role === "CLINIC_STAFF" ||
      formData.role === "DOCTOR"
    ) {
      navigate("/clinic");
    } else if (formData.role === "ADMIN") {
      navigate("/admin");
    }
  }

  return (
    <main className="page-container">
      <section className="form-card">
        <div className="form-heading">
          <p className="eyebrow">Secure access</p>
          <h1>Login</h1>
          <p>Enter your information to access your dashboard.</p>
        </div>

        {error && <div className="error-message">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email">Email address</label>

            <input
              id="email"
              name="email"
              type="email"
              placeholder="name@example.com"
              value={formData.email}
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>

            <input
              id="password"
              name="password"
              type="password"
              placeholder="Enter your password"
              value={formData.password}
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label htmlFor="role">Demo role</label>

            <select
              id="role"
              name="role"
              value={formData.role}
              onChange={handleChange}
            >
              <option value="PATIENT">Patient</option>
              <option value="CLINIC_STAFF">Clinic Staff</option>
              <option value="DOCTOR">Doctor</option>
              <option value="ADMIN">Administrator</option>
            </select>
          </div>

          <button type="submit" className="button primary-button full-button">
            Login
          </button>
        </form>

        <p className="form-footer-text">
          Do not have an account? <Link to="/register">Create an account</Link>
        </p>
      </section>
    </main>
  );
}

export default Login;