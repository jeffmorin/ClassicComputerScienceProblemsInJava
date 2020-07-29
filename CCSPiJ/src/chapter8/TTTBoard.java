// TTTBoard.java
// From Classic Computer Science Problems in Java Chapter 8
// Copyright 2020 David Kopec
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package chapter8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TTTBoard implements Board<Integer> {
	private static final int NUM_SQUARES = 9;
	private TTTPiece[] position;
	private TTTPiece turn;

	public TTTBoard(TTTPiece[] position, TTTPiece turn) {
		this.position = position;
		this.turn = turn;
	}

	public TTTBoard() {
		// by default start with blank board
		position = new TTTPiece[NUM_SQUARES];
		Arrays.fill(position, TTTPiece.E);
		// X goes first
		turn = TTTPiece.X;
	}

	@Override
	public Piece getTurn() {
		return turn;
	}

	@Override
	public TTTBoard move(Integer location) {
		TTTPiece[] tempPosition = Arrays.copyOf(position, position.length);
		tempPosition[location] = turn;
		return new TTTBoard(tempPosition, turn.opposite());
	}

	@Override
	public List<Integer> getLegalMoves() {
		return IntStream.range(0, NUM_SQUARES)
				// empty squares are legal moves
				.filter(i -> position[i] == TTTPiece.E)
				.mapToObj(Integer::valueOf)
				.collect(Collectors.toList());
	}

	@Override
	public boolean isWin() {
		// three row, three column, and then two diagonal checks
		return checkPos(0, 1, 2) || checkPos(3, 4, 5) || checkPos(6, 7, 8)
				|| checkPos(0, 3, 6) || checkPos(1, 4, 7) || checkPos(2, 5, 8)
				|| checkPos(0, 4, 8) || checkPos(2, 4, 6);
	}

	private boolean checkPos(int p0, int p1, int p2) {
		return position[p0] == position[p1] && position[p0] == position[p2]
				&& position[p0] != TTTPiece.E;
	}

	@Override
	public double evaluate(Piece player) {
		if (isWin() && turn == player) {
			return -1;
		}
		if (isWin() && turn != player) {
			return 1;
		}
		return 0.0;
	}

	@Override
	public String toString() {
		return IntStream.range(0, 3)
				.mapToObj(row -> IntStream.range(0, 3)
						.mapToObj(col -> position[row * 3 + col].toString())
						.collect(Collectors.joining("|", "", "\n"))
				).collect(Collectors.joining("-----\n"));
	}

}
