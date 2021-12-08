const fs = require('fs');

sum = (list) => list.reduce((p, c) => p + c, 0);

triangular = (n) => (n ** 2 + n) / 2;

leastFuel = (list, calcFuel) => {
    const mini = list.reduce((p, c) => c < p ? c : p);
    const maxi = list.reduce((p, c) => c > p ? c : p);
    let fuel = null;
    let index = null;

    for (let i = mini; i < maxi; i++) {
        const distances = list.map(p => Math.abs(i - p));
        const ifuel = sum(distances.map(d => calcFuel(d)));
        if (fuel == null || fuel > ifuel) {
            fuel = ifuel;
            index = i;
        }
    }

    return {index, fuel};
};

fuelConsumption = (part, path, calcFuel = d => d) => {
    const input = fs.readFileSync(path, 'utf8');
    const positions = input.split(',').map(Number);
    const {index, fuel} = leastFuel(positions, calcFuel);
    console.log(`Part ${part}: fuel=${fuel}`)
}

fuelConsumption(1, 'puzzle.txt');
fuelConsumption(2, 'puzzle.txt', triangular);
