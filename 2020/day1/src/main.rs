use std::io::{BufReader, BufRead, Error, ErrorKind};
use std::fs::File;
use itertools::iproduct;

fn main() {
    match get_input() {
        Ok(v) => find_answer(v),
        Err(x) => println!("Err: {}", x)
    }
}

fn get_input() -> Result<Vec<i32>, Error> {
    let file = File::open("input.txt")?;
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
    for p in iproduct!(v.clone(), v.clone()) {
        if p.0 + p.1 == 2020 {
            println!("{} x {} = {}", p.0, p.1, p.0 * p.1);
        }
    }

    for p in iproduct!(v.clone(), v.clone(), v.clone()) {
        if p.0 + p.1 + p.2 == 2020 {
            println!("{} x {} x {} = {}", p.0, p.1, p.2, p.0 * p.1 * p.2);
        }
    }
}
