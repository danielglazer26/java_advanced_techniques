var ArrayList = Java.type("java.util.ArrayList");
var Random = Java.type("java.util.Random")
var map;
var sizeMap;


function createMap(size, lowerBound, upperBound) {
    sizeMap = size
    map = new ArrayList();
    for (var i = 0; i < size; i++) {
        var y = new ArrayList(size);
        for (var j = 0; j < size; j++) {
            var number = new Random().nextInt(11);
            if (number > lowerBound && number < upperBound) y.add(10.0); else y.add(0.0);
        }
        map.add(y);
    }
    return map;
}

function nextIteration() {
    var secondMap = new ArrayList();
    var neighbours = 0;

    for (var i = 0; i < sizeMap; i++) {
        var y = new ArrayList();
        for (var j = 0; j < sizeMap; j++) {
            neighbours = 0;
            if (i - 1 >= 0) if (map.get(i - 1).get(j)) neighbours++;
            if (i + 1 < sizeMap) if (map.get(i + 1).get(j)) neighbours++;
            if (j - 1 >= 0) if (map.get(i).get(j - 1)) neighbours++;
            if (j + 1 < sizeMap) if (map.get(i).get(j + 1)) neighbours++;

            if (i - 1 >= 0 && j - 1 >= 0) if (map.get(i - 1).get(j - 1)) neighbours++;
            if (i - 1 >= 0 && j + 1 < sizeMap) if (map.get(i - 1).get(j + 1)) neighbours++;
            if (i + 1 < sizeMap && j - 1 >= 0) if (map.get(i + 1).get(j - 1)) neighbours++;
            if (i + 1 < sizeMap && j + 1 < sizeMap) if (map.get(i + 1).get(j + 1)) neighbours++;

            var state = map.get(i).get(j);
            if (neighbours === 3 && !state) y.add(10.0);
            else if (neighbours > 1 && neighbours < 4 && state) y.add(10.0);
            else y.add(0.0);
        }
        secondMap.add(y);
    }
    map = secondMap;

    return secondMap;
}
;