var ArrayList = Java.type("java.util.ArrayList");
var map = new ArrayList();
var sizeMap;


function createMap(size, number, range) {
    sizeMap = size
    for (var i = 0; i < size; i++) {
        var y = new ArrayList(size);
        for (var j = 0; j < size; j++) {
            y.add(false);
        }
        map.add(y);
    }
    return map;
}

function nextIteration(currentMap) {
    var secondMap = new ArrayList();

    for (var i = 0; i < sizeMap; i++) {
        for (var j = 0; j < sizeMap; j++) {
            if(currentMap.get(j).get(i)){
                if(i - 1 )
            }
        }

    }

    return secondMap
}
function nextIteration(currentMap) {
    count

    for (var i = 0; i < sizeMap; i++) {
        for (var j = 0; j < sizeMap; j++) {
            if (i + 1 > sizeMap - 1) calcMap[i][j] += planetMap[0][j];
            else if (i + 1 <= sizeMap - 1) calcMap[i][j] += planetMap[i + 1][j];

            if (j - 1 < 0) calcMap[i][j] += planetMap[i][sizeMap - 1];
            else if (j - 1 >= 0) calcMap[i][j] += planetMap[i][j - 1];

            if (j + 1 > sizeMap - 1) calcMap[i][j] += planetMap[i][0];
            else if (j + 1 <= sizeMap - 1) calcMap[i][j] += planetMap[i][j + 1];

            if (i - 1 < 0) calcMap[i][j] += planetMap[sizeMap - 1][j];
            else if (i - 1 >= 0) calcMap[i][j] += planetMap[i - 1][j];

            if (i - 1 < 0 && j - 1 >= 0) calcMap[i][j] += planetMap[sizeMap - 1][j - 1];
            else if (i - 1 < 0 && j - 1 < 0) calcMap[i][j] += planetMap[sizeMap - 1][sizeMap - 1];
            else if (i - 1 >= 0 && j - 1 < 0) calcMap[i][j] += planetMap[i - 1][sizeMap - 1];
            else if (i - 1 >= 0 && j - 1 >= 0) calcMap[i][j] += planetMap[i - 1][j - 1];

            if (i - 1 < 0 && j + 1 > sizeMap - 1) calcMap[i][j] += planetMap[sizeMap - 1][0];
            else if (i - 1 >= 0 && j + 1 > sizeMap - 1) calcMap[i][j] += planetMap[i - 1][0];
            else if (i - 1 < 0 && j + 1 <= sizeMap - 1) calcMap[i][j] += planetMap[sizeMap - 1][j + 1];
            else if (i - 1 >= 0 && j - 1 <= 0) calcMap[i][j] += planetMap[i - 1][j + 1];

            if (i + 1 <= sizeMap - 1 && j + 1 <= sizeMap - 1) calcMap[i][j] += planetMap[i + 1][j + 1];
            else if (i + 1 > sizeMap - 1 && j + 1 <= sizeMap - 1) calcMap[i][j] += planetMap[0][j + 1];
            else if (i + 1 <= sizeMap - 1 && j + 1 > sizeMap - 1) calcMap[i][j] += planetMap[i + 1][0];
            else if (i + 1 > sizeMap - 1 && j + 1 > sizeMap - 1) calcMap[i][j] += planetMap[0][0];

            if (i + 1 <= sizeMap - 1 && j - 1 >= 0) calcMap[i][j] += planetMap[i + 1][j - 1];
            else if (i + 1 > sizeMap - 1 && j - 1 >= 0) calcMap[i][j] += planetMap[0][j - 1];
            else if (i + 1 <= sizeMap - 1 && j - 1 < 0) calcMap[i][j] += planetMap[i + 1][sizeMap - 1];
            else if (i + 1 > sizeMap - 1 && j - 1 < 0) calcMap[i][j] += planetMap[0][sizeMap - 1];

            if (!planetMap[i][j] && calcMap[i][j] === 3) mapCopy[i][j] = true;
            else if ((planetMap[i][j] && calcMap[i][j] > 3) || (planetMap[i][j] && calcMap[i][j] < 2)) mapCopy[i][j] = false;
        }
    }
}