/* =========================================================
   Gestão de Condomínios - main.js
   CRUD para todas as entidades
   ========================================================= */

let paginaAtual = '';
let editId = null;
let formModal = null;

document.addEventListener('DOMContentLoaded', () => {
  formModal = new bootstrap.Modal(document.getElementById('formModal'));

  document.querySelectorAll('.sidebar .nav-link[data-page]').forEach(link => {
    link.addEventListener('click', (e) => {
      e.preventDefault();
      document.querySelectorAll('.sidebar .nav-link').forEach(l => l.classList.remove('active'));
      link.classList.add('active');
      carregarPagina(link.dataset.page);
      if (window.innerWidth < 768) document.getElementById('sidebar').classList.remove('open');
    });
  });

  const tg = document.getElementById('sidebarToggle');
  if (tg) tg.addEventListener('click', () => document.getElementById('sidebar').classList.toggle('open'));

  document.getElementById('formModalForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    if (window._currentSave) await window._currentSave();
  });
});

/* ---------- Notificações ---------- */
function showNotification(msg, tipo = 'success') {
  const area = document.getElementById('notificationArea');
  const id = 'n' + Date.now();
  const div = document.createElement('div');
  div.className = `alert alert-${tipo} alert-dismissible fade show notification`;
  div.id = id;
  div.innerHTML = `${msg}<button type="button" class="btn-close" data-bs-dismiss="alert"></button>`;
  area.appendChild(div);
  setTimeout(() => { const el = document.getElementById(id); if (el) el.remove(); }, 3000);
}

/* ---------- Roteador ---------- */
async function carregarPagina(pagina) {
  paginaAtual = pagina;
  const titles = {
    'condominios': 'Condomínios', 
    'unidades': 'Unidades', 
    'moradores': 'Moradores',
    'veiculos': 'Veículos', 
    'encomendas': 'Encomendas', 
    'areas_comuns': 'Áreas Comuns',
    'reservas': 'Reservas', 
    'ocorrencias': 'Ocorrências'
  };
  document.getElementById('pageTitle').textContent = titles[pagina] || '';
  document.getElementById('pageContent').innerHTML = '<div class="text-center py-5"><div class="spinner-border text-primary"></div></div>';

  try {
    switch (pagina) {
      case 'condominios': return await carregarCondominios();
      case 'unidades': return await carregarUnidades();
      case 'moradores': return await carregarMoradores();
      case 'veiculos': return await carregarVeiculos();
      case 'encomendas': return await carregarEncomendas();
      case 'areas_comuns': return await carregarAreasComuns();
      case 'reservas': return await carregarReservas();
      case 'ocorrencias': return await carregarOcorrencias();
    }
  } catch (err) {
    document.getElementById('pageContent').innerHTML = `<div class="alert alert-danger">Erro ao carregar dados: ${err.message}</div>`;
  }
}

/* ---------- Helpers ---------- */
function renderTable(headers, rows, entidade) {
  return `
    <div class="card border-0 shadow-sm">
      <div class="card-body">
        <button class="btn btn-success mb-3" onclick="mostrarModal_${entidade}()"><i class="fas fa-plus me-1"></i>Novo</button>
        <div class="table-responsive">
          <table class="table table-striped table-hover align-middle">
            <thead><tr>${headers.map(h => `<th>${h}</th>`).join('')}<th class="text-end">Ações</th></tr></thead>
            <tbody>${rows}</tbody>
          </table>
        </div>
      </div>
    </div>`;
}

function actionsCell(entidade, id) {
  return `<td class="text-end">
    <button class="btn btn-warning btn-sm me-1" onclick="mostrarModal_${entidade}(${id})"><i class="fas fa-edit"></i></button>
    <button class="btn btn-danger btn-sm" onclick="deletar_${entidade}(${id})"><i class="fas fa-trash"></i></button>
   </td>`;
}

function openForm(title, html, onSave) {
  document.getElementById('formModalTitle').textContent = title;
  document.getElementById('formModalBody').innerHTML = html;
  window._currentSave = onSave;
  formModal.show();
}

async function confirmDelete(endpoint, id, reload) {
  if (!confirm('Tem certeza que deseja excluir este registro?')) return;
  try {
    await api.del(`${endpoint}/${id}`);
    showNotification('Registro excluído com sucesso', 'success');
    reload();
  } catch (e) {
    showNotification(e.message, 'danger');
  }
}

function val(id) { return document.getElementById(id).value; }
function valNum(id) { const v = val(id); return v === '' ? null : Number(v); }

/* =========================================================
   CONDOMINIOS
   ========================================================= */
async function carregarCondominios() {
  const data = await api.get('/condominios');
  const rows = data.map(c => `
    <tr>
      <td>${c.id}${c.nome}${c.cidade}${c.estado}${c.telefone}${c.email}
      ${actionsCell('condominios', c.id)}
    </tr>`).join('');
  document.getElementById('pageContent').innerHTML =
    renderTable(['ID', 'Nome', 'Cidade', 'UF', 'Telefone', 'Email'], rows, 'condominios');
}

async function mostrarModal_condominios(id = null) {
  editId = id;
  let item = { nome: '', endereco: '', cidade: '', estado: '', telefone: '', email: '' };
  if (id) item = await api.get(`/condominios/${id}`);
  openForm(id ? 'Editar Condomínio' : 'Novo Condomínio', `
    <div class="row g-3">
      <div class="col-md-8"><label class="form-label">Nome *</label><input id="f_nome" class="form-control" value="${item.nome || ''}" required></div>
      <div class="col-md-4"><label class="form-label">UF *</label><input id="f_estado" class="form-control text-uppercase" maxlength="2" value="${item.estado || ''}" required></div>
      <div class="col-12"><label class="form-label">Endereço</label><input id="f_endereco" class="form-control" value="${item.endereco || ''}"></div>
      <div class="col-md-6"><label class="form-label">Cidade</label><input id="f_cidade" class="form-control" value="${item.cidade || ''}"></div>
      <div class="col-md-6"><label class="form-label">Telefone</label><input id="f_telefone" class="form-control" value="${item.telefone || ''}"></div>
      <div class="col-12"><label class="form-label">Email</label><input id="f_email" type="email" class="form-control" value="${item.email || ''}"></div>
    </div>`, salvar_condominios);
}

async function salvar_condominios() {
  const body = {
    nome: val('f_nome'), endereco: val('f_endereco'), cidade: val('f_cidade'),
    estado: val('f_estado').toUpperCase(), telefone: val('f_telefone'), email: val('f_email')
  };
  try {
    if (editId) await api.put(`/condominios/${editId}`, body);
    else await api.post('/condominios', body);
    formModal.hide();
    showNotification('Salvo com sucesso', 'success');
    carregarCondominios();
  } catch (e) { showNotification(e.message, 'danger'); }
}

function deletar_condominios(id) { confirmDelete('/condominios', id, carregarCondominios); }

/* =========================================================
   AREAS COMUNS
   ========================================================= */
async function carregarAreasComuns() {
  try {
    const data = await api.get('/areas-comuns');
    console.log('Áreas Comuns carregadas:', data);
    
    let rows = '';
    if (data && data.length > 0) {
      rows = data.map(a => `
        <tr>
          <td>${a.id}${a.nome || ''}${a.descricao || ''}
          <td>${a.capacidadeMaxima ?? ''}th
          <td>R$ ${(a.valorLocacao ?? 0).toFixed(2)}th
          <td>${a.ativa ? '<span class="badge bg-success">Sim</span>' : '<span class="badge bg-secondary">Não</span>'}th
          ${actionsCell('areas_comuns', a.id)}
        </tr>`).join('');
    } else {
      rows = '<tr><td colspan="7" class="text-center">Nenhuma área comum cadastrada</td></tr>';
    }
    
    document.getElementById('pageContent').innerHTML = `
      <div class="card border-0 shadow-sm">
        <div class="card-body">
          <button class="btn btn-success mb-3" onclick="mostrarModal_areas_comuns()"><i class="fas fa-plus me-1"></i>Nova Área Comum</button>
          <div class="table-responsive">
            <table class="table table-striped table-hover">
              <thead><tr><th>ID</th><th>Nome</th><th>Descrição</th><th>Capacidade</th><th>Valor</th><th>Ativa</th><th class="text-end">Ações</th></tr></thead>
              <tbody>${rows}</tbody>
            </table>
          </div>
        </div>
      </div>
    `;
  } catch (err) {
    console.error('Erro carregarAreasComuns:', err);
    document.getElementById('pageContent').innerHTML = `<div class="alert alert-danger">Erro ao carregar: ${err.message}</div>`;
  }
}

async function mostrarModal_areas_comuns(id = null) {
  editId = id;
  let item = { nome: '', descricao: '', capacidadeMaxima: '', valorLocacao: '', ativa: true };
  if (id) item = await api.get(`/areas-comuns/${id}`);
  openForm(id ? 'Editar Área Comum' : 'Nova Área Comum', `
    <div class="row g-3">
      <div class="col-md-8"><label class="form-label">Nome *</label><input id="f_nome" class="form-control" value="${item.nome || ''}" required></div>
      <div class="col-md-4 d-flex align-items-end"><div class="form-check"><input id="f_ativa" type="checkbox" class="form-check-input" ${item.ativa ? 'checked' : ''}><label class="form-check-label">Ativa</label></div></div>
      <div class="col-12"><label class="form-label">Descrição</label><textarea id="f_descricao" class="form-control" rows="2">${item.descricao || ''}</textarea></div>
      <div class="col-md-6"><label class="form-label">Capacidade Máxima</label><input id="f_capacidadeMaxima" type="number" class="form-control" value="${item.capacidadeMaxima ?? ''}"></div>
      <div class="col-md-6"><label class="form-label">Valor Locação</label><input id="f_valorLocacao" type="number" step="0.01" class="form-control" value="${item.valorLocacao ?? ''}"></div>
    </div>`, salvar_areas_comuns);
}

async function salvar_areas_comuns() {
  const body = {
    nome: val('f_nome'), descricao: val('f_descricao'),
    capacidadeMaxima: valNum('f_capacidadeMaxima'), valorLocacao: valNum('f_valorLocacao'),
    ativa: document.getElementById('f_ativa').checked
  };
  try {
    if (editId) await api.put(`/areas-comuns/${editId}`, body);
    else await api.post('/areas-comuns', body);
    formModal.hide();
    showNotification('Salvo com sucesso', 'success');
    carregarAreasComuns();
  } catch (e) { showNotification(e.message, 'danger'); }
}

function deletar_areas_comuns(id) { confirmDelete('/areas-comuns', id, carregarAreasComuns); }

/* =========================================================
   DEMAIS FUNÇÕES (placeholder para outras páginas)
   ========================================================= */
async function carregarUnidades() { document.getElementById('pageContent').innerHTML = '<div class="alert alert-info">Funcionalidade em desenvolvimento</div>'; }
async function carregarMoradores() { document.getElementById('pageContent').innerHTML = '<div class="alert alert-info">Funcionalidade em desenvolvimento</div>'; }
async function carregarVeiculos() { document.getElementById('pageContent').innerHTML = '<div class="alert alert-info">Funcionalidade em desenvolvimento</div>'; }
async function carregarEncomendas() { document.getElementById('pageContent').innerHTML = '<div class="alert alert-info">Funcionalidade em desenvolvimento</div>'; }
async function carregarReservas() { document.getElementById('pageContent').innerHTML = '<div class="alert alert-info">Funcionalidade em desenvolvimento</div>'; }
async function carregarOcorrencias() { document.getElementById('pageContent').innerHTML = '<div class="alert alert-info">Funcionalidade em desenvolvimento</div>'; }

function mostrarModal_unidades() {}
function mostrarModal_moradores() {}
function mostrarModal_veiculos() {}
function mostrarModal_encomendas() {}
function mostrarModal_reservas() {}
function mostrarModal_ocorrencias() {}

function deletar_unidades() {}
function deletar_moradores() {}
function deletar_veiculos() {}
function deletar_encomendas() {}
function deletar_reservas() {}
function deletar_ocorrencias() {}