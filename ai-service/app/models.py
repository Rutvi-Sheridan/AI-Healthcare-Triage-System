from enum import Enum
from typing import List

from pydantic import BaseModel, Field


class UrgencyLevel(str, Enum):
    SELF_CARE = "SELF_CARE"
    FAMILY_DOCTOR = "FAMILY_DOCTOR"
    WALK_IN_CLINIC = "WALK_IN_CLINIC"
    URGENT_CARE = "URGENT_CARE"
    EMERGENCY_WARNING = "EMERGENCY_WARNING"
    NEEDS_REVIEW = "NEEDS_REVIEW"


class FollowUpAnswer(BaseModel):
    question: str = Field(min_length=1, max_length=300)
    answer: str = Field(min_length=1, max_length=500)


class TriageRequest(BaseModel):
    symptom_text: str = Field(min_length=3, max_length=5000)
    severity: int = Field(default=1, ge=1, le=5)
    duration: str | None = Field(default=None, max_length=100)
    age: int | None = Field(default=None, ge=0, le=120)
    follow_up_answers: List[FollowUpAnswer] = Field(default_factory=list)


class TriageResponse(BaseModel):
    urgency_level: UrgencyLevel
    recommendation: str
    explanation: str
    summary: str
    red_flag_found: bool
    matched_red_flags: List[str]
    extracted_symptoms: List[str]
    confidence: float = Field(ge=0.0, le=1.0)
    classifier_source: str
    requires_staff_review: bool
    disclaimer: str