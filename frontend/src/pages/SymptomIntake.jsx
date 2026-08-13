import { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getCurrentUser } from "../services/authService";
import { createTriageCase } from "../services/triageService";
import "./SymptomIntake.css";

const MAX_COMPLAINT_LENGTH = 1500;

const speechErrorMessages = {
  "not-allowed":
    "Microphone access was denied. Allow microphone access in your browser settings, or type your symptoms instead.",
  "service-not-allowed":
    "Voice recognition is blocked by this browser. Please type your symptoms instead.",
  "audio-capture":
    "No working microphone was found. Check your microphone, or type your symptoms instead.",
  "no-speech":
    "No speech was detected. Try again and speak clearly, or type your symptoms instead.",
  network:
    "Voice recognition could not connect. Check your internet connection, or type your symptoms instead.",
  aborted: "Voice input was stopped. You can try again or continue typing.",
};

function getSpeechRecognitionConstructor() {
  return (
    window.SpeechRecognition ||
    window.webkitSpeechRecognition
  );
}

function SymptomIntake() {
  const navigate = useNavigate();
  const user = getCurrentUser();

  const [chiefComplaint, setChiefComplaint] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [isListening, setIsListening] = useState(false);
  const [speechSupported] = useState(() =>
    Boolean(getSpeechRecognitionConstructor())
  );
  const [speechStatus, setSpeechStatus] = useState(() =>
    getSpeechRecognitionConstructor()
      ? "Use the microphone or type your symptoms below."
      : "Voice input is not supported in this browser. You can still type your symptoms below."
  );

  const recognitionRef = useRef(null);
  const textBeforeRecordingRef = useRef("");
  const speechErrorRef = useRef(false);

  useEffect(() => {
    const SpeechRecognition =
      getSpeechRecognitionConstructor();

    if (!SpeechRecognition) {
      return undefined;
    }

    const recognition = new SpeechRecognition();

    recognition.continuous = true;
    recognition.interimResults = true;
    recognition.lang = "en-CA";

    recognition.onstart = () => {
      setIsListening(true);
      setSpeechStatus(
        "Listening... Speak naturally, then select Stop voice input."
      );
    };

    recognition.onresult = (event) => {
      const spokenText = Array.from(
        event.results,
        (result) => result[0].transcript
      )
        .join(" ")
        .replace(/\s+/g, " ")
        .trim();

      const combinedText = [
        textBeforeRecordingRef.current,
        spokenText,
      ]
        .filter(Boolean)
        .join(" ");

      setChiefComplaint(
        combinedText.slice(0, MAX_COMPLAINT_LENGTH)
      );

      if (combinedText.length > MAX_COMPLAINT_LENGTH) {
        speechErrorRef.current = true;
        setSpeechStatus(
          "The 1,500-character limit was reached. Review and edit the transcript before submitting."
        );
        recognition.stop();
      }
    };

    recognition.onerror = (event) => {
      speechErrorRef.current = true;
      setIsListening(false);
      setSpeechStatus(
        speechErrorMessages[event.error] ||
          "Voice recognition failed. Please try again or type your symptoms instead."
      );
    };

    recognition.onend = () => {
      setIsListening(false);

      if (!speechErrorRef.current) {
        setSpeechStatus(
          "Voice input stopped. Review and edit the transcript before submitting."
        );
      }
    };

    recognitionRef.current = recognition;

    return () => {
      recognition.onstart = null;
      recognition.onresult = null;
      recognition.onerror = null;
      recognition.onend = null;
      recognition.abort();
      recognitionRef.current = null;
    };
  }, []);

  const handleVoiceToggle = () => {
    setError("");

    const recognition = recognitionRef.current;

    if (!recognition) {
      setSpeechStatus(
        "Voice input is not supported in this browser. You can still type your symptoms below."
      );
      return;
    }

    if (isListening) {
      setSpeechStatus("Stopping voice input...");
      recognition.stop();
      return;
    }

    if (chiefComplaint.length >= MAX_COMPLAINT_LENGTH) {
      setSpeechStatus(
        "The symptom description is already at the 1,500-character limit. Edit it before adding more voice input."
      );
      return;
    }

    textBeforeRecordingRef.current = chiefComplaint.trim();
    speechErrorRef.current = false;
    setIsListening(true);
    setSpeechStatus("Starting microphone...");

    try {
      recognition.start();
    } catch (recognitionError) {
      setIsListening(false);
      setSpeechStatus(
        recognitionError.name === "InvalidStateError"
          ? "Voice input is already running."
          : "Voice input could not start. Please try again or type your symptoms instead."
      );
    }
  };

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
            maxLength={MAX_COMPLAINT_LENGTH}
            disabled={loading}
            readOnly={isListening}
            aria-describedby="voice-input-status"
          />

          <div className="voice-input-controls">
            <button
              type="button"
              className={`voice-input-button${
                isListening ? " is-listening" : ""
              }`}
              onClick={handleVoiceToggle}
              disabled={loading || !speechSupported}
              aria-pressed={isListening}
            >
              <span aria-hidden="true">
                {isListening ? "■" : "🎤"}
              </span>
              {isListening
                ? "Stop voice input"
                : "Start voice input"}
            </button>

            <p
              id="voice-input-status"
              className="voice-input-status"
              role="status"
              aria-live="polite"
            >
              {isListening && (
                <span
                  className="listening-indicator"
                  aria-hidden="true"
                />
              )}
              {speechStatus}
            </p>
          </div>

          <div className="character-count">
            {chiefComplaint.length}/{MAX_COMPLAINT_LENGTH} characters
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
              disabled={loading || isListening}
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
