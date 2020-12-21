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
    find_answer_part(1, 2, &v);
    find_answer_part(2, 3, &v);
}

fn find_answer_part( part: i8, combo_size: usize, v: &Vec<i32>) {
    println!("Part {}:", part);
    v.iter()
        .combinations(combo_size)
        .filter(|c| c.iter().copied().sum::<i32>() == 2020)
        .for_each(|c| {
            let product = c.iter().copied().product::<i32>();
            println!("\t{:?} = {}", c, product);
        });
}
