use std::cmp::Ordering;
use std::fs;

fn main() {
    part1("puzzle.txt");
    part2("puzzle.txt");
}

fn part1(filename: &str) {
    let (coords, folds) = parse(filename);
    let mut folded: Vec<(u32, u32)> = coords.into_iter().flat_map(folder(folds[0])).collect();
    folded.sort();
    folded.dedup();
    let dots = folded.len();
    println!("Part 1: {} dots showing", dots);
}

fn part2(filename: &str) {
    let (coords, folds) = parse(filename);
    let mut folded = coords.clone();
    for fold in folds.into_iter() {
        folded = folded.into_iter().flat_map(folder(fold)).collect();
    }
    folded.sort();
    folded.dedup();
    println!("Part 2:");
    print_dots(folded);
}

fn print_dots(dots: Vec<(u32,u32)>) {
    let (mx,my) = extents(&dots);
    for y in 0..my {
        for x in 0..mx {
            if dots.contains(&(x,y)) {
                print!("#");
            } else {
                print!(".");
            }
        }
        println!()
    }
}

fn extents(coords: &Vec<(u32,u32)>) -> (u32,u32) {
    let mut mx: u32 = 0;
    let mut my: u32 = 0;
    for item in coords {
        if item.0 > mx {
            mx = item.0;
        }
        if item.1 > my {
            my = item.1;
        }
    }
    (mx+1,my+1)
}

fn folder((axis, value): (char, u32)) -> Box<dyn Fn((u32, u32)) -> Option<(u32, u32)>> {
    match axis {
        'x' => {
            Box::new(move |(x, y)| {
                match value.cmp(&x) {
                    Ordering::Greater => Some((x, y)),
                    Ordering::Equal => None,
                    Ordering::Less => Some((2 * value - x, y))
                }
            })
        }
        'y' => {
            Box::new(move |(x, y)| {
                match value.cmp(&y) {
                    Ordering::Greater => Some((x, y)),
                    Ordering::Equal => None,
                    Ordering::Less => Some((x, 2 * value - y))
                }
            })
        }
        _ => panic!("Unexpected axis {}", axis)
    }
}

fn parse(filename: &str) -> (Vec<(u32, u32)>, Vec<(char, u32)>) {
    if let Ok(contents) = fs::read_to_string(filename) {
        let mut split = contents.split("\n\n");
        return (parse_coords(split.next().unwrap()), parse_folds(split.next().unwrap()));
    } else {
        panic!("Can't read file: {:?}", filename);
    }
}

fn parse_coords(input: &str) -> Vec<(u32, u32)> {
    input.split('\n').map(|l| {
        let mut s = l.split(',').map(|c| c.parse().unwrap());
        return (s.next().unwrap(), s.next().unwrap());
    }).collect()
}

fn parse_folds(input: &str) -> Vec<(char, u32)> {
    input.split('\n').map(|l| {
        if !l.starts_with("fold along") {
            panic!("Unexpected fold: {}", l);
        }
        let axis = l.chars().nth(11).unwrap();
        let value = l[13..].parse().unwrap();
        (axis, value)
    }).collect()
}

#[cfg(test)]
mod tests {
    // Note this useful idiom: importing names from outer (for mod tests) scope.
    use super::*;

    #[test]
    fn test_folder() {
        let fold = folder(('y', 7));
        assert_eq!(fold((0, 0)), Some((0, 0)));
        assert_eq!(fold((0, 7)), None);
        assert_eq!(fold((0, 10)), Some((0, 4)));
    }
}
