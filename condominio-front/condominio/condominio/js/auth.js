// Login page
if (document.getElementById('loginForm')) {
  document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const alertBox = document.getElementById('loginAlert');
    alertBox.innerHTML = '';
    try {
      const data = await api.post('/auth/login', { username, password });
      if (data && data.token) {
        localStorage.setItem('token', data.token);
        localStorage.setItem('role', data.role || '');
        window.location.href = 'dashboard.html';
      } else {
        throw new Error('Resposta inválida do servidor');
      }
    } catch (err) {
      alertBox.innerHTML = `<div class="alert alert-danger"><i class="fas fa-exclamation-circle me-2"></i>${err.message}</div>`;
    }
  });
}

// Dashboard - check token
if (document.getElementById('pageContent')) {
  if (!getToken()) {
    window.location.href = 'index.html';
  }
  const logoutBtn = document.getElementById('logoutBtn');
  if (logoutBtn) {
    logoutBtn.addEventListener('click', (e) => {
      e.preventDefault();
      localStorage.removeItem('token');
      localStorage.removeItem('role');
      window.location.href = 'index.html';
    });
  }
}
