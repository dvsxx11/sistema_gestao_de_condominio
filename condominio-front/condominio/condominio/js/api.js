const API_URL = 'http://localhost:8080/api';

function getToken() {
  return localStorage.getItem('token');
}

async function request(endpoint, method = 'GET', body = null) {
  const headers = { 'Content-Type': 'application/json' };
  const token = getToken();
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const config = { method, headers };
  if (body) config.body = JSON.stringify(body);

  let response;
  try {
    response = await fetch(`${API_URL}${endpoint}`, config);
  } catch (e) {
    throw new Error('Não foi possível conectar à API. Verifique se o backend está rodando.');
  }

  if (response.status === 401) {
    localStorage.removeItem('token');
    if (!location.pathname.endsWith('index.html') && location.pathname !== '/') {
      window.location.href = 'index.html';
    }
    throw new Error('Sessão expirada. Faça login novamente.');
  }

  if (!response.ok) {
    let msg = `Erro ${response.status}`;
    try {
      const data = await response.json();
      msg = data.message || data.error || msg;
    } catch (_) {}
    throw new Error(msg);
  }

  if (response.status === 204) return null;
  const text = await response.text();
  return text ? JSON.parse(text) : null;
}

const api = {
  get: (e) => request(e, 'GET'),
  post: (e, b) => request(e, 'POST', b),
  put: (e, b) => request(e, 'PUT', b),
  del: (e) => request(e, 'DELETE'),
};
