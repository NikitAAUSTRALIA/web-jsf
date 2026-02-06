function updateClock() {
    const now = new Date();

    const dateOptions = {
        day: 'numeric',
        month: 'long',
        year: 'numeric'
    };
    const date = now.toLocaleDateString('ru-RU', dateOptions);

    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    const time = `${hours}:${minutes}:${seconds}`;

    document.getElementById('current-date').textContent = date;
    document.getElementById('current-time').textContent = time;
}

updateClock();

setInterval(updateClock, 5000);