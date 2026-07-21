import { BrowserRouter, Route, Routes } from "react-router-dom";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import Home from "./pages/Home";
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
          <Route path="/login" element={<Placeholder title="Login" />} />
          <Route path="/register" element={<Placeholder title="Register" />} />
        </Routes>

        <Footer />
      </div>
    </BrowserRouter>
  );
}

export default App;