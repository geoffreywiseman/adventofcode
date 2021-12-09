import itertools
from typing import Optional, Iterable

segments = 'abcdefg'
digits: dict[str, int] = {
    'abcefg': 0,
    'cf': 1,
    'acdeg': 2,
    'acdfg': 3,
    'bcdf': 4,
    'abdfg': 5,
    'abdefg': 6,
    'acf': 7,
    'abcdefg': 8,
    'abcdfg': 9
}


def parse(filename: str) -> list[list[str]]:
    with open(filename) as file:
        return [line.rstrip('\n').split(' | ') for line in file]


def part1(filename: str):
    count: int = 0
    items: list[list[str]] = parse(filename)
    for (signal, output) in items:
        for digit in output.split(' '):
            if len([v for k, v in digits.items() if len(k) == len(digit)]) == 1:
                count += 1
    return count


def part2(filename: str):
    output_sum: int = 0
    items: list[list[str]] = parse(filename)
    for (signals, outputs) in items:
        signals = [normalize(s) for s in signals.split(' ')]
        outputs = [normalize(o) for o in outputs.split(' ')]
        digit_map = map_digits(signals)
        output = translate(outputs, digit_map)
        output_sum += output
    return output_sum


def map_digits(signals: list[str]) -> dict[str, int]:
    translations = itertools.permutations(tuple(segments))
    for translation in translations:
        mapping = try_mapping(signals, translation)
        if mapping:
            return mapping
    assert False, f"No mapping found for signals: {signals}"


def try_mapping(signals: list[str], translation: tuple[str]) -> Optional[dict[str, int]]:
    dm: dict[str, int] = {}
    sm: dict[str, str] = {k: v for (k, v) in zip(tuple(segments), translation)}

    for signal in signals:
        ts = normalize([sm[s] for s in signal])
        if ts not in digits:
            # translated signal isn't a digit
            return None
        td = digits[ts]
        if signal in dm and dm[signal] != td:
            # two translations for the same signal
            return None
        dm[signal] = td
    return dm


def normalize(iterable: Iterable) -> str:
    return ''.join(sorted(iterable))


def translate(outputs: list[str], digits: dict[str, int]) -> int:
    output = 0
    for digit in outputs:
        output *= 10
        output += digits[digit]
    return output


print(f"Part 1: {part1('puzzle.txt')}")
print(f"Part 2: {part2('puzzle.txt')}")
