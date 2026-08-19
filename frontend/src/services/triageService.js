import { apiGet, apiPost } from "./api";

export function getPatientTriageCases(patientId) {
  if (!patientId) {
    return Promise.resolve([]);
  }

  return apiGet(`/triage-cases/patient/${patientId}`);
}

export function createTriageCase(patientId, chiefComplaint) {
  return apiPost("/triage-cases", {
    patientId,
    chiefComplaint,
  });
}

export function getTriageCase(caseId) {
  return apiGet(`/triage-cases/${caseId}`);
}