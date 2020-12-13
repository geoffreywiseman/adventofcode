fs = require('fs');

const getIds = () => {
    return fs.readFileSync('../input.txt', {encoding: 'UTF-8'})
        .split("\n\n")
        .map(id => {
                return id.split(/[ \n]/).filter(x => x !== '')
                    .reduce((acc, field) => {
                        const halves = field.split(':')
                        acc[halves[0]] = halves[1]
                        return acc;
                    }, {});
            }
        );
}

const valid1 = (id) => {
    const required = ['byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid'];
    return required.every(prop => id.hasOwnProperty(prop));
}

const valid2 = (id) => {
    const intRange = (min,max) => {
        return (input) => {
            if( !/^[1-9][0-9]*$/.test(input) )
                return false;
            const intVal = parseInt(input);
            return intVal >= min && intVal <= max;
        }
    }
    const rules = {
        'byr': intRange(1920,2002),
        'iyr': intRange(2010,2020),
        'eyr': intRange(2020,2030),
        'hgt': hgt => {
            match = hgt.match(/^\d+(cm|in)$/)
            if( match !== null ) {
                const measure = parseInt(match[0]);
                if( match[1] === 'cm' )
                    return measure >= 150 && measure <= 193;
                else
                    return measure >= 59 && measure <= 76;
            }
            return false;
        },
        'hcl': pid => /^#[0-9a-f]{6}$/.test(pid),
        'pid': pid => /^\d{9}$/.test(pid),
        'ecl': ecl => ['amb', 'blu', 'brn', 'gry', 'grn', 'hzl', 'oth'].includes(ecl)
    }
    return Object.keys(rules).every(prop => id.hasOwnProperty(prop) && rules[prop](id[prop]));
}

const countValid = (ids, valid) => {
    return ids.filter(id => valid(id)).length
}

let count1 = countValid(getIds(), valid1)
console.log(`There are ${count1} valid passports in part1.`)

let count2 = countValid(getIds(), valid2)
console.log(`There are ${count2} valid passports in part2.`)
