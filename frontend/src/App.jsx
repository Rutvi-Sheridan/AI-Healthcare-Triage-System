import { BrowserRouter, Route, Routes } from "react-router-dom";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import Home from "./pages/Home";
import Login from "./pages/Login";
import "./App.css";

function Placeholder({ title }) {
  return (
    <main className="page-container">
      <h1>{title}</h1>
      <p>This page will be created in the next feature branch.</p>
    </main>
  );
}

function App() {
  return (
    <BrowserRouter>
      <div className="app">
        <Navbar />

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Placeholder title="Register" />} />

          <Route 
            path="/patient" 
            element={<Placeholder title="Patient Dashboard" />} 
          />
          <Route
            path="/clinic"
            element={<Placeholder title="Clinic Dashboard" />}
          />
          <Route
            path="/admin"
            element={<Placeholder title="Admin Dashboard" />}
          />
        </Routes>

        <Footer />
      </div>
    </BrowserRouter>
  );
}

export default App;