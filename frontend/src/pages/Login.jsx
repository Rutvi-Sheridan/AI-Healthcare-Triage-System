import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  loginUser,
  saveAuthentication,
} from "../services/authService";

function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("PATIENT");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");

    if (!email.trim() || !password.trim()) {
      setError("Email and password are required.");
      return;
    }

    try {
      setLoading(true);

      const authData = await loginUser(
        email.trim(),
        password
      );

      saveAuthentication(authData);

      // Normalize role from backend
      const userRole = (authData.role || role)
        .replace("ROLE_", "")
        .toUpperCase();

      switch (userRole) {
        case "PATIENT":
          navigate("/patient/dashboard");
          break;

        case "STAFF":
        case "CLINIC_STAFF":
        case "DOCTOR":
        case "NURSE":
          navigate("/staff/dashboard");
          break;

        case "ADMIN":
          navigate("/admin/dashboard");
          break;

        default:
          setError(`Unsupported role: ${userRole}`);
      }
    } catch (loginError) {
      setError(loginError.message || "Login failed.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="page-container">
      <section className="form-card">
        <div className="form-heading">
          <p className="eyebrow">Secure Access</p>
          <h1>Login</h1>
          <p>Enter your information to access your dashboard.</p>
        </div>

        {error && (
          <div className="error-message">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email">
              Email Address
            </label>

            <input
              id="email"
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(event) =>
                setEmail(event.target.value)
              }
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">
              Password
            </label>

            <input
              id="password"
              type="password"
              placeholder="Enter your password"
              value={password}
              onChange={(event) =>
                setPassword(event.target.value)
              }
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="role">
              Demo Role
            </label>

            <select
              id="role"
              value={role}
              onChange={(event) =>
                setRole(event.target.value)
              }
            >
              <option value="PATIENT">
                Patient
              </option>

              <option value="CLINIC_STAFF">
                Clinic Staff
              </option>

              <option value="DOCTOR">
                Doctor
              </option>

              <option value="ADMIN">
                Administrator
              </option>
            </select>
          </div>

          <button
            type="submit"
            className="button primary-button full-button"
            disabled={loading}
          >
            {loading ? "Signing in..." : "Login"}
          </button>
        </form>

        <p className="form-footer-text">
          Don't have an account?{" "}
          <Link to="/register">
            Create an account
          </Link>
        </p>
      </section>
    </main>
  );
}

export default Login;