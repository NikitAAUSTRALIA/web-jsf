const canvas = document.getElementById('coordinateGraph');
const ctx = canvas.getContext('2d');

function setupCanvas() {
    const container = canvas.parentElement;
    const displayWidth = container.clientWidth;
    const displayHeight = container.clientHeight;
    
    canvas.width = displayWidth;
    canvas.height = displayHeight;
    
    canvas.style.width = displayWidth + 'px';
    canvas.style.height = displayHeight + 'px';
    
    ctx.imageSmoothingEnabled = false;
    ctx.webkitImageSmoothingEnabled = false;
    ctx.mozImageSmoothingEnabled = false;
}

window.addEventListener('resize', setupCanvas);

class CoordinateGraph {
    constructor(canvasId) {
        this.canvas = document.getElementById(canvasId);
        this.ctx = this.canvas.getContext('2d');
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        this.padding = 0;
        this.r = null;

        this.drawGraph();
    }
    
    clear(){
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
    }

    drawCircle(){
        this.ctx.fillStyle = '#ffffff';
        this.ctx.beginPath();
        this.ctx.moveTo(this.width / 2, this.height / 2);
        this.ctx.arc(this.width / 2, this.height / 2, this.width / 8, Math.PI, Math.PI * 3 / 2, false);
        this.ctx.fill();
    }
    
    drawRectangle(){
        this.ctx.fillStyle = '#ffffff';
        this.ctx.beginPath();
        this.ctx.moveTo(this.width / 2, this.height / 2);
        this.ctx.lineTo(this.width / 2, this.height * 3 / 8);
        this.ctx.lineTo(this.width * 3 / 4, this.height / 2);
        this.ctx.lineTo(this.width / 2, this.height / 2);
        this.ctx.closePath();
        this.ctx.fill();
    }
    
    drawSquare(){
        this.ctx.fillStyle = '#ffffff';
        this.ctx.fillRect(this.width * 3 / 8, this.height / 2,  this.width / 8, this.height / 4);
    }
    
    drawAxes() {
        const centerX = this.width / 2;
        const centerY = this.height / 2;

        this.ctx.strokeStyle = '#000000';
        this.ctx.lineWidth = 2;

        this.ctx.beginPath();
        this.ctx.moveTo(this.padding, centerY);
        this.ctx.lineTo(this.width - this.padding, centerY);
        this.ctx.stroke();
        
        this.ctx.beginPath();
        this.ctx.moveTo(centerX, this.padding);
        this.ctx.lineTo(centerX, this.height - this.padding);
        this.ctx.stroke();
        
        this.drawArrow(this.width - this.padding, centerY, 10, 0);
        this.drawArrow(centerX, this.padding, 10, -Math.PI/2); 
    }
    
    drawArrow(x, y, size, angle) {
        this.ctx.save();
        this.ctx.translate(x, y);
        this.ctx.rotate(angle);

        this.ctx.beginPath();
        this.ctx.moveTo(0, 0);
        this.ctx.lineTo(-size, -size/2);
        this.ctx.lineTo(-size, size/2);
        this.ctx.closePath();
        this.ctx.fillStyle = '#000000';
        this.ctx.fill();

        this.ctx.restore();
    }
    
    drawLabels() {
        const centerX = this.width / 2;
        const centerY = this.height / 2;

        this.ctx.fillStyle = '#000000';
        this.ctx.textAlign = 'center';
        this.ctx.font = '14px Arial';
        let R = 'R';
        let halfR = 'R/2';
        if (this.r !== null){
            R = this.r;
            halfR = this.r / 2;
        }

        for (let x = this.width * 1 / 4 - this.padding; x <= this.width * 3 / 4 - this.padding; x += this.width / 8) {
            if (x === this.width * 1 / 4 - this.padding || x === this.width * 3 / 4 - this.padding) {
                this.ctx.fillText(R, x, centerY + 20);
            }
            else if (x !== centerX){
                this.ctx.fillText(halfR, x, centerY + 20);
            }
        }
        
        this.ctx.textAlign = 'right';
        for (let y = this.height * 1 / 4 - this.padding; y <= this.height * 3 / 4 - this.padding; y += this.height / 8) {
            if (y === this.height * 1 / 4 - this.padding || y === this.height * 3 / 4 - this.padding) {
                this.ctx.fillText(R, centerX - 10, y + 4);
            }
            else if (y !== centerY){
                this.ctx.fillText(halfR, centerX - 10, y + 4);
            }
        }
        
        this.ctx.textAlign = 'center';
        this.ctx.fillText('X', this.width - 15, centerY - 15);
        this.ctx.fillText('Y', centerX + 15,  15);
    }
    
    drawTicks() {
        const centerX = this.width / 2;
        const centerY = this.height / 2;

        this.ctx.strokeStyle = '#000000';
        this.ctx.lineWidth = 1;
        
        for (let x = this.width * 1 / 4 - this.padding; x <= this.width * 3 / 4 - this.padding; x += this.width / 8) {
            if (x !== centerX) {
                this.ctx.beginPath();
                this.ctx.moveTo(x, centerY - 5);
                this.ctx.lineTo(x, centerY + 5);
                this.ctx.stroke();
            }
        }
        
        for (let y = this.height * 1 / 4 - this.padding; y <= this.height * 3 / 4 - this.padding; y += this.height / 8) {
            if (y !== centerY) {
                this.ctx.beginPath();
                this.ctx.moveTo(centerX - 5, y);
                this.ctx.lineTo(centerX + 5, y);
                this.ctx.stroke();
            }
        }
    }
    
    drawPoint(x, y, hit){
        if (hit){
            this.ctx.fillStyle = 'green';
        }
        else{
            this.ctx.fillStyle = 'red';
        }
        const real_x = this.width * ((x / this.r) * 0.25 + 0.5);
        const real_y = this.height * (((y * -1) / this.r) * 0.25 + 0.5);
        console.log(real_x, real_y);
        this.ctx.beginPath();
        this.ctx.arc(real_x, real_y, 3, 0, Math.PI * 2);
        this.ctx.fill();
        this.ctx.strokeStyle = '#000000';
        this.ctx.lineWidth = 1;
        this.ctx.stroke();
        this.ctx.closePath();
        console.log("Точка нарисована");
    }
    
    drawGraph() {
        this.clear();
        this.drawSquare();
        this.drawCircle();
        this.drawRectangle();
        this.drawAxes();
        this.drawTicks();
        this.drawLabels();
    }
    
    setR(r){
        this.r = r;
    }
}

let graph;

function drawPoint(x, y, hit){
    graph.drawPoint(x, y, hit);
}

document.addEventListener('DOMContentLoaded', function() {
    setupCanvas();
    graph = new CoordinateGraph('coordinateGraph');
});

window.addEventListener('resize', function(){
    setupCanvas();
    graph.drawGraph();
});

canvas.addEventListener('click', function(event) {
    const rect = canvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;
    const x_temp = (x / (rect.right - rect.left)) - 0.5;
    const y_temp = ((y / (rect.bottom - rect.top)) - 0.5) * -1;
    const rInput = document.querySelector('[id*="R"]');
    const r = rInput ? rInput.value : null;
    if (r !== null){
        const x_r = x_temp / 0.25 * r;
        const y_r = y_temp / 0.25 * r;
        addPointFromCanvas([{
            name: 'canvasX',
            value: x_r
        }, {
            name: 'canvasY',
            value: y_r
        }, {
            name: 'canvasR',
            value: r
        }]);
        console.log(`Клик в координатах: X: ${x_r}, Y: ${y_r}`);
    }
    else {
        console.log('Невозможно определить координаты точки');
    }
});

function handleCanvasResponse(xhr, status, args) {
    console.log('Ответ сервера пойман');
    if (args.success) {
        console.log('Проверка пройдена');
        const x = args.x;
        const y = args.y;
        const r = args.r;
        const hit = args.hit;
        drawPoint(x, y, hit);
    }
}

let rValue = null;

function watchRChanges() {
    const rInput = document.querySelector('[id*="R"]');

    if (rInput) {
        const currentValue = rInput.value;

        if (currentValue !== rValue) {
            rValue = currentValue;
            const rNum = parseFloat(currentValue);
            if (!isNaN(rNum)) {
                graph.setR(rNum);
            }
            else{
                graph.setR(null);
            }
            graph.clear();
            graph.drawGraph();
        }
    }
    else {
        if (rValue !== null) {
            rValue = null;
            graph.setR(null);
            graph.clear();
            graph.drawGraph();
        }
    }
}

setInterval(watchRChanges, 100);

document.addEventListener('DOMContentLoaded', function() {
    setTimeout(watchRChanges, 500);
});
