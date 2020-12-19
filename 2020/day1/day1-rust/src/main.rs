use std::io::{BufReader, BufRead, Error, ErrorKind};
use std::fs::File;
use itertools::Itertools;

fn main() {
    match get_input() {
        Ok(v) => find_answer(v),
        Err(x) => println!("Err: {}", x)
    }
}

fn get_input() -> Result<Vec<i32>, Error> {
    let file = File::open("../input.txt")?;
    let reader = BufReader::new(file);
    reader.lines()
        .map(|r|
            r.and_then(|l|
                l.parse()
                    .map_err(|e| Error::new(ErrorKind::InvalidData, e))
            )
        )
        .collect()
}

fn find_answer(v: Vec<i32>) {
    println!("Part 1:");
    for p in v.iter().combinations(2) {
        if p[0] + p[1] == 2020 {
            println!("\t{} x {} = {}", p[0], p[1], p[0] * p[1]);
        }
    }

    println!("Part 2:");
    for t in v.iter().combinations(3) {
        if t[0] + t[1] + t[2] == 2020 {
            println!("\t{} x {} x {} = {}", t[0], t[1], t[2], t[0] * t[1] * t[2]);
        }
    }
}
