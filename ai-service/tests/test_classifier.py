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