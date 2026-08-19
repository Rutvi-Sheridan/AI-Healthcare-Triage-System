from fastapi import FastAPI, HTTPException

from app.classifier import classify_triage
from app.models import TriageRequest, TriageResponse


app = FastAPI(
    title="AI Healthcare Triage Service",
    description=(
        "Educational symptom-intake classification service. "
        "This service does not provide medical diagnosis."
    ),
    version="1.0.0",
)


@app.get("/health")
def health_check() -> dict[str, str]:
    return {
        "status": "UP",
        "service": "ai-triage-service",
        "version": "1.0.0",
    }


@app.post("/api/v1/triage/classify", response_model=TriageResponse)
def classify(request: TriageRequest) -> TriageResponse:
    try:
        return classify_triage(request)
    except ValueError as exc:
        raise HTTPException(
            status_code=400,
            detail=str(exc),
        ) from exc
    except Exception as exc:
        raise HTTPException(
            status_code=500,
            detail="The triage service could not process the request.",
        ) from exc