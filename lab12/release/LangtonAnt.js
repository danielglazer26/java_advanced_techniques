// biały - 0
// czarny - 10
// lewo - 1
// dół - 2
// prawo - 3
// góra - 4


var ArrayList = Java.type("java.util.ArrayList");
var map;
var antX;
var antY;
var sizeMap;


function createMap(size, startingX, startingY) {
    sizeMap = size
    map = new ArrayList();

    for (var i = 0; i < size; i++) {
        var y = new ArrayList();
        for (var j = 0; j < size; j++) {
            if (startingX === j && startingY === i) {
                y.add(3.0);
            } else {
                y.add(0.0);
            }
        }
        map.add(y);
    }
    antX = startingX;
    antY = startingY;
    return map;
}

function nextIteration() {
    var fieldValue = map.get(antY).get(antX);
    var direction;
    if (fieldValue < 10) {
        direction = changeAntDirection(fieldValue, 1)
        map.get(antY).set(antX, 10.0);
    } else {
        direction = changeAntDirection(fieldValue - 10, -1)
        map.get(antY).set(antX, 0.0);
    }
    moveAnt(direction);
    map.get(antY).set(antX, map.get(antY).get(antX) + direction);

    return map;
}

function changeAntDirection(direction, move) {
    direction += move;
    if (direction === 5.0) return 1.0;
    else if (direction === 0.0) return 4.0;
    return direction;
}

function moveAnt(direction) {
    switch (direction) {
        case 1: // lewo
            antX -= 1;
            break;
        case 2: // dół
            antY += 1;
            break;
        case 3: // prawo
            antX += 1;
            break;
        case 4: // góra
            antY -= 1;
            break;
    }
    if (antX === -1)
        antX = sizeMap - 1;
    if (antY === -1)
        antY = sizeMap - 1;

    if (antX === sizeMap)
        antX = 0;
    if (antY === sizeMap)
        antY = 0;

}

