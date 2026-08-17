import pytest

from app.classifier import classify_triage
from app.models import TriageRequest, UrgencyLevel


def test_emergency_phrase_overrides_other_rules():
    request = TriageRequest(
        symptom_text=(
            "I have severe chest pain "
            "and difficulty breathing"
        ),
        severity=5,
        duration="10 minutes",
    )

    result = classify_triage(request)

    assert (
        result.urgency_level
        == UrgencyLevel.EMERGENCY_WARNING
    )
    assert result.red_flag_found is True
    assert result.requires_staff_review is True


@pytest.mark.parametrize(
    "symptom_text",
    [
        "I have pain in heart",
        "I have pain in my heart",
        "I have heart pain",
        "I feel pressure in chest",
        "I have chest tightness",
    ],
)
def test_heart_and_chest_wording_returns_emergency_warning(
    symptom_text,
):
    request = TriageRequest(
        symptom_text=symptom_text,
        severity=1,
        duration="10 minutes",
    )

    result = classify_triage(request)

    assert (
        result.urgency_level
        == UrgencyLevel.EMERGENCY_WARNING
    )
    assert result.red_flag_found is True
    assert result.requires_staff_review is True


def test_heartburn_does_not_match_heart_pain_rule():
    request = TriageRequest(
        symptom_text="I have heartburn after eating spicy food",
        severity=1,
        duration="one hour",
    )

    result = classify_triage(request)

    assert (
        result.urgency_level
        != UrgencyLevel.EMERGENCY_WARNING
    )
    assert result.red_flag_found is False


def test_mild_input_can_return_self_care():
    request = TriageRequest(
        symptom_text="I have a mild cold and runny nose",
        severity=1,
        duration="1 day",
    )

    result = classify_triage(request)

    assert result.urgency_level == UrgencyLevel.SELF_CARE
    assert result.red_flag_found is False


def test_ambiguous_input_requires_review():
    request = TriageRequest(
        symptom_text="I do not feel normal today",
        severity=2,
        duration="today",
    )

    result = classify_triage(request)

    assert result.urgency_level == UrgencyLevel.NEEDS_REVIEW
    assert result.requires_staff_review is True