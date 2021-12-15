import * as fs from "fs";

const BRACE_MATCH = new Map([
    ['{', '}'],
    ['<', '>'],
    ['(', ')'],
    ['[', ']'],
]);

// region Part 1
const CORRUPTION_SCORES = new Map([
    [')', 3],
    [']', 57],
    ['}', 1197],
    ['>', 25137],
]);

function firstCorruption(line: string): string | undefined {
    const stack = [];
    return line.split('').find(actual => {
        if (BRACE_MATCH.has(actual) ) {
            stack.push(actual)
        } else {
            if (stack.length == 0 || BRACE_MATCH.get(stack.pop()) != actual)
                return true;
        }
        return false;
    })
}

function part1(path: string): void {
    const input = fs.readFileSync(path, 'utf-8').split('\n');
    const totalScore = input.map(firstCorruption)
        .map(c => CORRUPTION_SCORES.get(c) || 0)
        .reduce((p, c) => p + c, 0);
    console.log(`Part 1: ${totalScore}`);
}

part1('puzzle.txt');
// endregion

// region Part 2
const AUTOCOMPLETE_SCORES: Map<string, number> = new Map([
    [')', 1],
    [']', 2],
    ['}', 3],
    ['>', 4]
]);

function autocomplete(line: string): string[] {
    const stack = [];
    let corrupt = false;
    line.split('').forEach(actual => {
        if (!corrupt) {
            if (BRACE_MATCH.has(actual)) {
                stack.push(actual)
            } else {
                if (stack.length == 0 || BRACE_MATCH.get(stack.pop()) != actual)
                    corrupt = true;
            }
        }
    })
    return corrupt ? null : stack.reverse().map(c => BRACE_MATCH.get(c));
}

function part2(path: string): void {
    const input = fs.readFileSync(path, 'utf-8').split('\n');
    const scores = input.map(autocomplete)
        .filter(x => x != null)
        .map(l => l.reduce((p, c) => p * 5 + AUTOCOMPLETE_SCORES.get(c), 0));
    scores.sort((x, y) => x - y);
    const medianIndex = Math.round(scores.length / 2) - 1;
    console.log(`Part 2: ${scores[medianIndex]}`);
}

part2('puzzle.txt');
// endregion