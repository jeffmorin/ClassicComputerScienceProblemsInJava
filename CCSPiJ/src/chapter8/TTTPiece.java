// TTTPiece.java
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

public enum TTTPiece implements Piece {
	X("X"),
	O("O"),
	E(" "); // E is Empty

	static {
		X.opposite = O;
		O.opposite = X;
		E.opposite = E;
	}
	private String code;
	private TTTPiece opposite;

	private TTTPiece(String c) {
		code = c;
	}

	@Override
	public TTTPiece opposite() {
		return opposite;
	}

	@Override
	public String toString() {
		return code;
	}

}
