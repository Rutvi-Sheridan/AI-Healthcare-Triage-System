import { Link } from "react-router-dom";

function Navbar() {
  return (
    <header className="navbar">
      <Link to="/" className="navbar-brand">
        HealthTriage AI
      </Link>

      <nav className="navbar-links">
        <Link to="/">Home</Link>
        <Link to="/login">Login</Link>
        <Link to="/register" className="primary-link">
          Register
        </Link>
      </nav>
    </header>
  );
}

export default Navbar;