function createFileRow(file) {
    const tr = document.createElement('tr');
    
    // Name column
    const nameCell = document.createElement('td');
    const icon = document.createElement('i');
    icon.className = file.isDirectory ? 'bi bi-folder me-2' : 'bi bi-file me-2';
    nameCell.appendChild(icon);
    nameCell.appendChild(document.createTextNode(file.name));
    tr.appendChild(nameCell);

    // Type column
    const typeCell = document.createElement('td');
    typeCell.textContent = file.isDirectory ? 'Folder' : 'File';
    tr.appendChild(typeCell);

    // Size column
    const sizeCell = document.createElement('td');
    sizeCell.textContent = file.isDirectory ? '-' : formatFileSize(file.size);
    tr.appendChild(sizeCell);

    // Actions column
    const actionsCell = document.createElement('td');
    
    // Add share button
    const shareButton = document.createElement('button');
    shareButton.className = 'btn btn-sm btn-primary me-2';
    shareButton.innerHTML = '<i class="bi bi-share"></i>';
    shareButton.onclick = () => openShareDialog(file.path);
    actionsCell.appendChild(shareButton);

    // Add other action buttons based on file type
    if (file.isDirectory) {
        // Folder actions
        const openButton = document.createElement('button');
        openButton.className = 'btn btn-sm btn-info me-2';
        openButton.innerHTML = '<i class="bi bi-folder2-open"></i>';
        openButton.onclick = () => navigateToFolder(file.path);
        actionsCell.appendChild(openButton);
    } else {
        // File actions
        const downloadButton = document.createElement('button');
        downloadButton.className = 'btn btn-sm btn-success me-2';
        downloadButton.innerHTML = '<i class="bi bi-download"></i>';
        downloadButton.onclick = () => downloadFile(file.path);
        actionsCell.appendChild(downloadButton);
    }

    // Add delete button
    const deleteButton = document.createElement('button');
    deleteButton.className = 'btn btn-sm btn-danger';
    deleteButton.innerHTML = '<i class="bi bi-trash"></i>';
    deleteButton.onclick = () => deleteItem(file.path);
    actionsCell.appendChild(deleteButton);

    tr.appendChild(actionsCell);
    return tr;
}

function formatFileSize(bytes) {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
}

async function loadFiles(path = '') {
    try {
        const response = await fetch(`/api/files?path=${encodeURIComponent(path)}`);
        const files = await response.json();
        const tbody = document.getElementById('filesList');
        tbody.innerHTML = '';

        files.forEach(file => {
            tbody.appendChild(createFileRow(file));
        });

        updateBreadcrumbs(path);
    } catch (error) {
        console.error('Error loading files:', error);
        showToast('Error loading files', 'error');
    }
}

function updateBreadcrumbs(path) {
    const breadcrumbs = document.getElementById('breadcrumbs');
    breadcrumbs.innerHTML = '';

    // Add home
    const homeLi = document.createElement('li');
    homeLi.className = 'breadcrumb-item';
    const homeLink = document.createElement('a');
    homeLink.href = '#';
    homeLink.onclick = () => loadFiles('');
    homeLink.textContent = 'Home';
    homeLi.appendChild(homeLink);
    breadcrumbs.appendChild(homeLi);

    // Add path segments
    if (path) {
        const segments = path.split('/').filter(Boolean);
        let currentPath = '';
        segments.forEach(segment => {
            currentPath += '/' + segment;
            const li = document.createElement('li');
            li.className = 'breadcrumb-item';
            const link = document.createElement('a');
            link.href = '#';
            link.onclick = () => loadFiles(currentPath);
            link.textContent = segment;
            li.appendChild(link);
            breadcrumbs.appendChild(li);
        });
    }
}

// Initialize file list when page loads
document.addEventListener('DOMContentLoaded', () => {
    loadFiles();
}); 