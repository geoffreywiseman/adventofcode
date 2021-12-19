import * as fs from "fs";

// region Part 1
function part1(filename: string) {
    const edges = parse(filename);
    const paths = findAllPaths(edges, false);
    console.log(`Part 1: Found ${paths.length} paths`);
}

// endregion

// region Shared Code
function addEdge(edges: Map<string, string[]>, one: string, two: string) {
    if (edges.has(one)) {
        if (!edges.get(one).includes(two)) {
            edges.get(one).push(two);
        }
    } else {
        edges.set(one, [two])
    }
}

function parse(filename: string): Map<string, string[]> {
    const lines: string[] = fs.readFileSync(filename, 'utf8').split('\n');
    const edges = new Map<string, string[]>();
    lines.forEach((l) => {
        const [one, two] = l.split('-');
        addEdge(edges, one, two);
        addEdge(edges, two, one);
    });
    return edges;
}

class Path {
    segments: string[];
    duplicateAllowed: boolean;
    duplicate: string;

    constructor(duplicateAllowed: boolean, duplicate: string = undefined, segments: string[] = ['start']) {
        this.duplicateAllowed = duplicateAllowed;
        this.duplicate = duplicate;
        this.segments = segments;
    }

    currentCave(): string {
        return this.segments[this.segments.length - 1]
    }

    complete(): boolean {
        return this.currentCave() == 'end';
    }

    add(segment: string): Path | undefined {
        if (segment == 'start')
            return;
        const smallCave = (segment.toLowerCase() == segment);
        if (smallCave && this.segments.includes(segment)) {
            if (!this.duplicateAllowed || this.duplicate != undefined) {
                return undefined;
            } else {
                return new Path(this.duplicateAllowed, segment, [...this.segments, segment])
            }
        } else {
            return new Path(this.duplicateAllowed, this.duplicate, [...this.segments, segment]);
        }
    }

    takeExits(exits: string[]): Path[] {
        return exits.map(exit => this.add(exit))
            .filter(path => path != undefined);
    }

    toString(): string {
        return this.segments.join(',');
    }
}

function findAllPaths(edges: Map<string, string[]>, duplicateAllowed: boolean): Path[] {
    const queue: Path[] = [new Path(duplicateAllowed)];
    const complete: Path[] = [];
    while (queue.length > 0) {
        const currentPath = queue.pop();
        if (currentPath.complete()) {
            complete.push(currentPath);
        } else {
            const exits = edges.get(currentPath.currentCave())
            const newPaths = currentPath.takeExits(exits);
            queue.push(...newPaths);
        }
    }
    return complete;
}

// endregion

// region Part 2
function part2(filename: string) {
    const edges = parse(filename);
    const paths = findAllPaths(edges, true);
    console.log(`Part 2: found ${paths.length} paths`)
}

// endregion

part1('puzzle.txt');
part2('puzzle.txt');