import { apiPost } from "./api";

export async function loginUser(email, password) {
  return apiPost("/auth/login", {
    email,
    password,
  });
}

export async function registerPatient(patientData) {
  return apiPost("/auth/register", patientData);
}

export function saveAuthentication(authData) {
  localStorage.setItem("token", authData.token);
  localStorage.setItem(
    "currentUser",
    JSON.stringify({
      userId: authData.userId,
      patientId: authData.patientId,
      name: authData.name,
      email: authData.email,
      role: authData.role,
    })
  );
}

export function getCurrentUser() {
  const currentUser = localStorage.getItem("currentUser");

  if (!currentUser) {
    return null;
  }

  try {
    return JSON.parse(currentUser);
  } catch {
    localStorage.removeItem("currentUser");
    return null;
  }
}

export function getToken() {
  return localStorage.getItem("token");
}

export function logoutUser() {
  localStorage.removeItem("token");
  localStorage.removeItem("currentUser");
}