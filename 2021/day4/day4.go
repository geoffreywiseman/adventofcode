package main

import (
	"bufio"
	"errors"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

func main() {
	err := solveDay()
	if err != nil {
		log.Fatal(err)
	}
}

func solveDay() error {
	file, err := os.Open("puzzle.txt")
	if err != nil {
		return err
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	nums, err := bingoNumbers(scanner)
	if err != nil {
		return err
	}

	boards, err := bingoBoards(scanner)
	if err != nil {
		return err
	}

	res, err := part1(nums, boards)
	if err != nil {
		fmt.Printf("Part 1 Error: %s\n", err)
	} else {
		fmt.Printf("Part 1 Result: %d\n", res)
	}

	res, err = part2(nums, boards)
	if err != nil {
		fmt.Printf("Part 2 Error: %s\n", err)
	} else {
		fmt.Printf("Part 2 Result: %d\n", res)
	}
	return nil
}

func part1(nums []int, boards []*BingoBoard) (int, error) {
	for _, num := range nums {
		for _, board := range boards {
			board.Mark(num)
			if board.IsWon() {
				return board.SumUnmarked() * num, nil
			}
		}
	}
	return 0, errors.New("ran out of numbers without winning")
}

func part2(nums []int, boards []*BingoBoard) (int, error) {
	remaining := make([]*BingoBoard, len(boards))
	count := len(boards)
	copy(remaining, boards)
	for _, num := range nums {
		for i, board := range remaining {
			if board != nil {
				board.Mark(num)
				if board.IsWon() {
					if count == 1 {
						return board.SumUnmarked() * num, nil
					} else {
						remaining[i] = nil
						count--
					}
				}
			}
		}
	}
	return 0, errors.New("ran out of numbers without winning")
}

func bingoNumbers(scanner *bufio.Scanner) ([]int, error) {
	if scanner.Scan() {
		parts := strings.Split(scanner.Text(), ",")
		return atoi(parts)
	} else {
		return nil, scanner.Err()
	}
}

func bingoBoards(scanner *bufio.Scanner) ([]*BingoBoard, error) {
	boards := make([]*BingoBoard, 0, 10)
	for scanner.Scan() {
		if scanner.Text() != "" {
			return nil, fmt.Errorf("expected blank line, found: %s", scanner.Text())
		}
		board, err := getBoard(scanner)
		if err != nil {
			return nil, err
		}
		boards = append(boards, board)
	}
	if scanner.Err() != nil {
		return nil, scanner.Err()
	}
	return boards, nil
}

func getBoard(scanner *bufio.Scanner) (*BingoBoard, error) {
	board := NewBoard()
	for i := 0; i < 5 && scanner.Scan(); i++ {
		fields := strings.Fields(scanner.Text())
		if len(fields) != 5 {
			return nil, fmt.Errorf("expected 5 columns, found %d: %s", len(fields), scanner.Text())
		}
		nums, err := atoi(fields)
		if err != nil {
			return nil, err
		}
		board.rows = append(board.rows, nums)
	}
	if len(board.rows) != 5 {
		if scanner.Err() != nil {
			return nil, fmt.Errorf("could not complete board, only found %d rows", len(board.rows))
		} else {
			return nil, scanner.Err()
		}
	}
	return &board, nil
}

type BingoBoard struct {
	rows  [][]int
	marks [][]bool
}

func (b BingoBoard) String() string {
	return fmt.Sprintf("Board (%v)", b.rows)
}

func (b BingoBoard) reset() {
	for _, row := range b.marks {
		for c := range row {
			row[c] = false
		}
	}
}

func (b BingoBoard) Mark(num int) {
	for r, row := range b.rows {
		for c, square := range row {
			if num == square {
				b.marks[r][c] = true
			}
		}
	}
}

func (b BingoBoard) IsWon() bool {
	for r := 0; r < 5; r++ {
		for c := 0; c < 5; c++ {
			if b.marks[r][c] == false {
				break
			}
			if c == 4 {
				return true
			}
		}
	}

	for c := 0; c < 5; c++ {
		for r := 0; r < 5; r++ {
			if b.marks[r][c] == false {
				break
			}
			if r == 4 {
				return true
			}
		}
	}

	return false
}

func (b BingoBoard) SumUnmarked() int {
	sum := 0
	for r := 0; r < 5; r++ {
		for c := 0; c < 5; c++ {
			if b.marks[r][c] == false {
				sum += b.rows[r][c]
			}
		}
	}
	return sum
}

func NewBoard() BingoBoard {
	board := BingoBoard{
		make([][]int, 0, 5),
		make([][]bool, 5),
	}
	for i := 0; i < 5; i++ {
		board.marks[i] = make([]bool, 5)
	}
	return board
}

func atoi(input []string) ([]int, error) {
	output := make([]int, len(input))
	for i, part := range input {
		num, err := strconv.Atoi(part)
		if err != nil {
			return nil, err
		}
		output[i] = num
	}
	return output, nil
}
