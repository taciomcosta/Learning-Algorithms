// get canvas and context
var canvas = document.getElementsByTagName("canvas")[0];
var context = canvas.getContext("2d");

// scale next draws
context.scale(20, 20);

// intialize arena
context.fillStyle = "#000";
context.fillRect(0, 0, canvas.width, canvas.height);

// piece T
const matrix = [
    [0, 0, 0],
    [1, 1, 1],
    [0, 1, 0]
];

// check rows collision
function arenaSweep()
{
    let rowCount = 1;
    outer: for(let y = arena.length - 1; y > 0; --y) {
        // if an entire row equals 1
        for(let x = 0; x < arena[y].length; ++x) {
            if (arena[y][x] === 0) {
                continue outer;
            }
        }
        // take arena row out and fill it with 0
        const row = arena.splice(y, 1)[0].fill(0);
        // put row to the top
        arena.unshift(row);
        // go one line back to check if it has more complete rows
        ++y;
        // assign score
        player.score += rowCount * 10;
        rowCount *= 2;
    }
}

function collide(arena, player)
{
    // break player matrix into player position???
    const [m, o] = [player.matrix, player.pos];
    for (let y = 0; y < m.length; ++y) {
        for (let x = 0; x < m[y].length; ++x) {
            // 'if undefined == if true'
            if (m[y][x] !== 0 &&
                (arena[y + o.y] && // make sure arena row exists
                 arena[y + o.y][x + o.x]) !== 0) // make sure column exists
                return true;
        }
    }
    return false;
}

function createMatrix(w, h)
{
    const matrix = [];
    // fill matrix while h isn't 0
    while (h--) {
        matrix.push(new Array(w).fill(0));
    }
    return matrix;
}

function createPiece(type)
{
    if (type === 'I') {
        return [
            [0, 1, 0, 0],
            [0, 1, 0, 0],
            [0, 1, 0, 0],
            [0, 1, 0, 0],
        ];
    } else if (type === 'L') {
        return [
            [0, 2, 0],
            [0, 2, 0],
            [0, 2, 2],
        ];
    } else if (type === 'J') {
        return [
            [0, 3, 0],
            [0, 3, 0],
            [3, 3, 0],
        ];
    } else if (type === 'O') {
        return [
            [4, 4],
            [4, 4],
        ];
    } else if (type === 'Z') {
        return [
            [5, 5, 0],
            [0, 5, 5],
            [0, 0, 0],
        ];
    } else if (type === 'S') {
        return [
            [0, 6, 6],
            [6, 6, 0],
            [0, 0, 0],
        ];
    } else if (type === 'T') {
        return [
            [0, 7, 0],
            [7, 7, 7],
            [0, 0, 0],
        ];
    }
}

// draw function
function draw()
{
    // redraw arena
    context.fillStyle = "#000";
    context.fillRect(0, 0, canvas.width, canvas.height);
    // redraw arena pieces
    drawMatrix(arena, {x: 0, y: 0});
    // draw new piece
    drawMatrix(player.matrix, player.pos);
}

function merge(arena, player) {
    // for each element of matrix
    player.matrix.forEach((row, y) => {
        row.forEach((value, x) => {
            // copy piece value to arena, if it's not 0
            if (value !== 0) {
                arena[y + player.pos.y][x + player.pos.x] = value;
            }
        });
    });
}

// drop piece down
function playerDrop()
{
    player.pos.y++;
    if (collide(arena, player)) {
        player.pos.y--;
        merge(arena, player);
        playerReset();
        arenaSweep();
        updateScore();
    }
    dropCounter = 0;       // set delay to drop again
}

function playerMove(dir)
{
    player.pos.x += dir;
    if (collide(arena, player))
        player.pos.x -= dir;
}

function playerReset()
{
    const pieces = 'TJLOSZI';
    // createPiece (pieces[ (7 * n) | 0, n E [0.00, 1] ])
    player.matrix = createPiece(pieces[pieces.length * Math.random() | 0]);
    player.pos.y = 0;
    player.pos.x = (arena[0].length / 2 | 0) - (player.matrix[0].length / 2 | 0);
    // reset game over
    if (collide(arena, player)) {
        arena.forEach(row => row.fill(0));
        player.score = 0;
        updateScore();
    }
}


function playerRotate(dir)
{
    const pos = player.pos.x;
    let offset = 1;
    rotate(player.matrix, dir);
    // check collision for don't rotate inside the wall
    while (collide(arena, player)) {
        player.pos.x += offset;
        offset = -(offset + (offset > 0 ? 1 : -1));
        if (offset > player.matrix[0].length) {
            rotate(player.matrix, -dir);
            player.pos.x = pos;
            return;
        }
    }
}

// Transpose + Reverse = Rotate
function rotate(matrix, dir)
{
    // transpose
    for(let y = 0; y < matrix.length; ++y) {
        for(let x = 0; x < y; ++x) {
            // use a tupple to switch values
            [
                matrix[x][y],
                matrix[y][x]
            ] = [
                matrix[y][x],
                matrix[x][y]
            ];
        }
    }
    // reverse
    if (dir > 0) {
        matrix.forEach(row => row.reverse());
    } else {
        matrix.reverse();
    }
}

// draw game continuosly
let dropCounter = 0;
let dropInterval = 200;        // 1s
let lastTime = 0;
function update(time = 0) //time keeps incrementing automatically
{
    const deltaTime = time - lastTime;
    lastTime = time;
    // drop piece
    dropCounter += deltaTime;
    if (dropCounter > dropInterval) {
        playerDrop();
    }
    draw();
    // make the animation happen
    requestAnimationFrame(update);
}

// update score
function updateScore()
{
    document.getElementById('score').innerText = player.score;
}

// paint piece
function drawMatrix(matrix, offset)
{
    matrix.forEach((row, y) => {
        // (value, column index)
        row.forEach((value, x) => {
            if (value !== 0) {
                context.fillStyle = colors[value];
                // fillRect(x, y, width, height)
                context.fillRect(x + offset.x,
                                 y + offset.y,
                                 1, 1);
            }
        });
    });
}

// player obj
const player = {
    pos: {x: 0, y: 0},
    matrix: matrix,
    score: 0
};

// pieces colors
const colors = [
    null,
    'red',
    'blue',
    'violet',
    'green',
    'purple',
    'orange',
    'pink'
];

const arena = createMatrix(12, 20);

// key listener
document.addEventListener('keydown', event => {
    // move left
    if (event.keyCode === 37) {
        playerMove(-1);
    // move right
    } else if (event.keyCode === 39) {
        playerMove(1);
    // move down
    } else if (event.keyCode === 40) {
        playerDrop();
    // rotate
    } else if (event.keyCode === 81) {
        playerRotate(-1);
    // rotate
    } else if (event.keyCode === 87) {
        playerRotate(1);
    }
});

playerReset();
updateScore();
update();
