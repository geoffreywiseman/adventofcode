use std::collections::{HashSet, VecDeque};
use std::fs::File;
use std::io;
use std::io::{BufRead, BufReader, Lines, Result};
use std::path::Path;

fn main() {
    part1("puzzle.txt");
    part2("puzzle.txt");
}

// region part 1

fn part1<P>(path: P)
    where P: AsRef<Path> {
    let map = parse(read_lines(path));
    simulate(map, 100);
}

fn simulate(map: Vec<Vec<u8>>, rounds: i32) {
    let mut flashes = 0;

    let mut current = map;
    for _ in 0..rounds {
        let new_flashes = step(&mut current);
        flashes += new_flashes;
    }

    println!("After {} rounds, there have been {} flashes", rounds, flashes);
}

// endregion

// region Part 2

fn part2<P>(path: P)
    where P: AsRef<Path> {
    let map = parse(read_lines(path));
    find_sync(map);
}

fn find_sync(map: Vec<Vec<u8>>) {
    let sync_flashes = (map.len() * map[0].len()) as u32;
    let mut steps = 0;

    let mut current = map;
    loop {
        steps += 1;
        let flashes = step(&mut current);
        if flashes == sync_flashes {
            break;
        }
    }

    println!("All octopi synchronized in step {}", steps);
}


// endregion

// region Shared Code

const NEIGHBOUR_DELTAS: [(i8, i8); 8] = [(-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1)];

fn step(map: &mut Vec<Vec<u8>>) -> u32 {
    // copy
    let mut queue: VecDeque<(usize, usize)> = VecDeque::new();
    let mut flashed: HashSet<(usize, usize)> = HashSet::new();

    // energy up
    for row in 0..map.len() {
        for col in 0..map[row].len() {
            map[row][col] += 1;
            if map[row][col] > 9 {
                queue.push_back((row, col));
            }
        }
    }

    // flashes
    loop {
        match queue.pop_front() {
            None => break,
            Some(item) => {
                //println!("Flashing at {:?}", item);
                flashed.insert(item);
                for delta in NEIGHBOUR_DELTAS.iter() {
                    let row = (item.0 as i8) + delta.0;
                    let col = (item.1 as i8) + delta.1;
                    if row >= 0 && row < (map.len() as i8) && col >= 0 && col < (map[row as usize].len() as i8) {
                        let coord = (row as usize, col as usize);
                        map[coord.0][coord.1] += 1;
                        //println!("Incremented {:?} to {}", coord, map[coord.0][coord.1]);
                        if map[coord.0][coord.1] > 9 && !queue.contains(&coord) && !flashed.contains(&coord) {
                            //println!("Adding flash: {:?}", coord);
                            queue.push_back(coord);
                        }
                    }
                }
            }
        }
    }

    // flash to zero
    for row in 0..map.len() {
        for col in 0..map[row].len() {
            if map[row][col] > 9 {
                map[row][col] = 0;
            }
        }
    }

    // return
    return flashed.len() as u32;
}

fn parse(result: Result<Lines<BufReader<File>>>) -> Vec<Vec<u8>> {
    if let Ok(lines) = result {
        return lines.map(|x|
            x.unwrap()
                .chars()
                .map(|x| x.to_digit(10).unwrap() as u8)
                .collect()
        ).collect();
    } else {
        panic!("Can't read lines: {:?}", result)
    }
}

fn read_lines<P>(filename: P) -> Result<Lines<BufReader<File>>>
    where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}

// endregion