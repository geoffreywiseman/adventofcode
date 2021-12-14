const fs = require('fs')

const getHeightMap = (path) => {
    return fs.readFileSync(path, 'utf8')
        .split('\n')
        .map(l => l.split('').map(c => parseInt(c)));
}

const getNeighbouringHeights = (heightMap, ri, ci) => {
    neighbours = [];
    if (ri > 0)
        neighbours.push(heightMap[ri - 1][ci])
    if (ci > 0)
        neighbours.push(heightMap[ri][ci - 1])
    if ((ri + 1) < heightMap.length)
        neighbours.push(heightMap[ri + 1][ci])
    if ((ci + 1) < heightMap[ri].length)
        neighbours.push(heightMap[ri][ci + 1])
    return neighbours
}

const getLowPoints = (heightMap) => {
    const lowPoints = [];
    heightMap.forEach((row, ri) => {
        row.forEach((height, ci) => {
            if (getNeighbouringHeights(heightMap, ri, ci).every(nh => nh > height)) {
                lowPoints.push({ri, ci, height})
            }
        })
    });
    return lowPoints;
}

const part1 = (path) => {
    const heightMap = getHeightMap(path);
    const lowPointHeights = getLowPoints(heightMap).map(x => x.height);
    const risks = lowPointHeights.map(h => h + 1)
    const sum = risks.reduce((p, c) => p + c, 0);
    console.log(`Part 1: ${sum}`);
}

const basinFill = (index, row, col, heights, mapped) => {
    if (row < 0 || col < 0 || row >= heights.length || col >= heights[row].length)
        return 0;
    if (mapped[row][col] === true || heights[row][col] === 9)
        return 0;
    mapped[row][col] = true;
    return [[0, -1], [0, 1], [1, 0], [-1, 0]].reduce((sum, [dr, dc]) => sum + basinFill(index, row + dr, col + dc, heights, mapped), 1);
}

const part2 = (path) => {
    const heightMap = getHeightMap(path);
    const mapped = Array.from(heightMap, r => Array(r.length, c => false));
    const basins = getLowPoints(heightMap).map((lp, index) => basinFill(index, lp.ri, lp.ci, heightMap, mapped));
    const [one, two, three] = basins.sort((x, y) => y - x);
    console.log(`Part 2: ${one * two * three}`);
}

part1('puzzle.txt')
part2('puzzle.txt')