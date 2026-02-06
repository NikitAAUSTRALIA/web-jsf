function validateX() {
    if (!document.forms[0].elements['X'].value){
        return false;
    }
    return true;
}

function validateY(y) {
    const num = parseFloat(y);
    return !isNaN(num) && num.toString() === y.trim() && num >= -3 && num <= 3;
}

function validateR() {
    const r_element = document.getElementById("R");
    if (!r_element.value || r_element.value === ""){
        return false;
    }
    return true;
}

function showError(message) {
    const errorElement = document.getElementById('error-validate');
    errorElement.textContent = message;
    errorElement.classList.remove('error-hidden-validate');
    errorElement.classList.add('error-visible-validate');
}

function hideError() {
    const errorElement = document.getElementById('error-validate');
    errorElement.classList.remove('error-visible-validate');
    errorElement.classList.add('error-hidden-validate');
    errorElement.textContent = '';
}

function validateForm() {
    hideError();
    const form = document.forms[0];
    const y = form.elements['Y'].value;

    if (!validateX()) {
        showError('Выберите X');
        return false;
    }
    
    if (!validateY(y)) {
        showError('Y должен быть от -3 до 3');
        return false;
    }
    
    if (!validateR()){
        showError('Выберите R');
        return false;
    }

    return true;
}

function sendForm(){
    hideError();
    if (validateForm()){
        const x = document.forms[0].elements['X'].value;
        const y = document.forms[0].elements['Y'].value;
        const r = document.forms[0].elements['R'].value;
        const url = `/myapp/app/?r=${r}&x=${x}&y=${y}&form=${true}`;
        fetch(url)
            .then(response => response.text())
            .then(html => {
                const responseWindow = window.open('', '_self');
                responseWindow.document.write(html);
                responseWindow.document.close();
            })
            .then(html => loadResults())
            .catch(error => showError('Ошибка: ' + error.message));
    }
}

function clearHistory(){
    fetch('/myapp/app/?history=true&clear=true')
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('resultsBody');
            tbody.innerHTML = '';
            graph.drawGraph();
            data.forEach(result => {
                drawPoint(result.x, result.y, result.r, (result.hit || result.hit === 'true' || result.hit === 'True'));
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${result.x.toFixed(5)}</td>
                    <td>${result.y.toFixed(5)}</td>
                    <td>${result.r}</td>
                    <td>${result.hit ? 'True' : 'False'}</td>
                    <td>${result.timestamp}</td>
                    <td>${result.timestamp2}</td>
                `;
                tbody.appendChild(row);
            });
        });
}
