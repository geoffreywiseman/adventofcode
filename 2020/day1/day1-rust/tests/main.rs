#[cfg(test)]
mod tests {
    use itertools::Itertools;

    #[test]
    fn test_sum() {
        let v = vec!(83,51,32,94);
        v.iter()
            .combinations(2)
            .for_each( |c| {
                println!("c: {:?}", c);
                let sum: i32 = c.iter().copied().sum();
                println!("sum: {:?}", sum);
            });
    }
}